# Agent 交互层功能说明

> 包路径：`org.cug.geodt.weibo.agent.interaction`  
> 接口前缀：`/agent/interaction`  
> 版本：初版（规则引擎 + 内存会话 + SOS→WFS 执行闭环）

---

## 1. 功能定位

本模块是业务 Agent 体系的**第一层：Agent 交互层**，负责把用户的自然语言任务转化为可执行的结构化流程，并在用户确认后触发 Skill / Tool 执行。

它与现有 `org.cug.geodt.weibo.agent`（demo 包）的关系：

| 模块 | 职责 |
|------|------|
| `agent.interaction`（本包） | 多轮补参、会话状态、任务规划、确认门控、溯源记录 |
| `agent`（demo 包） | 早期 function calling 演示、底层 Tool 封装（如 `SosAgentToolService`） |
| Skill 层（后续） | 固定业务流程封装，如 `sos_to_wfs_skill` |
| Tool 层 | 具体执行能力，如 `query_sos`、`publish_wfs` |

整体分层示意：

```
用户自然语言
    ↓
Agent 交互层（本包）  ← 抽参 / 补参 / 规划 / 确认
    ↓
Skill 层              ← 业务流程编排
    ↓
Tool 层               ← SOS / WFS / GeoMesa / 爬虫等
    ↓
数据与服务层          ← Kafka / Accumulo / GeoServer 等
```

---

## 2. 已实现的核心能力

### 2.1 多轮参数抽取与合并

- 用户不必一次说全所有参数，可分多轮补充。
- 每轮从 `userMessage` 中抽取新参数，**合并**到会话已有 `slots`，不会覆盖已有有效值（除非本轮提供了新值）。
- 自动计算 `missingInputs`，告知用户还缺什么。

**示例场景：**

| 轮次 | 用户输入 | 系统行为 |
|------|----------|----------|
| 第 1 轮 | 「我要 2024-03-30 到 2024-05-30 的情感得分数据并发布 WFS」 | 识别任务类型、抽取时间范围和属性，提示缺少「城市」 |
| 第 2 轮 | 「我要武汉市的」 | 合并城市参数，参数齐全，生成执行计划，等待确认 |
| 第 3 轮 | `confirmed: true` 或回复「确认执行」 | 真正调用 Tool 执行 |

### 2.2 意图识别

系统识别用户本轮意图（`UserIntent`）：

| 意图 | 含义 | 触发示例 |
|------|------|----------|
| `NEW_TASK` | 发起新任务 | 首轮描述任务目标 |
| `SUPPLY_PARAMS` | 补充缺失参数 | 「我要武汉市的」 |
| `CONFIRM_EXECUTE` | 确认执行计划 | 「确认」「执行」「好的」或 `confirmed: true` |
| `CHANGE_TASK` | 修改参数或变更任务 | 「改成北京市」 |
| `CANCEL` | 取消当前任务 | 「取消」或 `cancel: true` |

> 当前为**规则引擎**实现，后续可替换为 LLM 意图分类，接口保持不变。

### 2.3 执行计划与确认门控

参数齐全后，系统**不会立即执行**，而是：

1. 生成 `ExecutionPlan`（含 Skill、Tool、步骤说明）
2. 返回 `needConfirmation: true`
3. 等待用户显式确认（`confirmed: true` 或确认关键词）

这符合「先规划、后执行」的业务 Agent 设计原则。

### 2.4 会话记忆（Memory）

本模块具备**任务会话级记忆**，用于支撑多轮补参、确认执行与结果追溯。需要与 ChatGPT 式「全文聊天历史」或「跨会话用户偏好」区分开来。

#### 2.4.1 记忆是什么

记忆的本质是：在同一会话（`conversationId`）内，系统持久保存一份 **`TaskContext`（任务上下文）**，每轮 `/chat` 请求都会：

1. 按 `conversationId` 加载已有上下文（首轮无 ID 则自动创建）
2. 从本轮 `userMessage` 抽取新参数，**合并**进已有 `slots`（有值才覆盖，不会无故清空）
3. 更新任务状态、执行计划、溯源日志等
4. 写回存储，供下一轮继续使用

因此用户可以在多轮对话中逐步补充参数，无需每轮重复说明城市、时间等信息。

#### 2.4.2 会记住什么

| 字段 / 内容 | 说明 |
|-------------|------|
| `conversationId` / `taskId` | 会话与任务标识 |
| `taskType` | 已识别的任务类型 |
| `status` | 当前阶段（补参中 / 待确认 / 执行中 / 已完成等） |
| `slots` | 已抽取的业务参数（城市、日期、观测属性等） |
| `missingInputs` | 仍缺失的必填参数 |
| `plan` | 参数齐全后生成的执行计划 |
| `confirmed` | 用户是否已确认执行 |
| `artifacts` | 中间产物与最终结果（如 SOAP 预览、`wfsUrl`） |
| `stepLogs` | 每轮交互与 Tool 调用的溯源记录 |
| `lastUserIntent` / `lastUserMessage` | 最近一轮意图与原始输入 |
| `turnCount` | 对话轮数 |
| `createdAt` / `updatedAt` | 创建与最后更新时间 |

`TaskContext` 结构示例：

```json
{
  "conversationId": "abc-123",
  "taskId": "task-456",
  "taskType": "SOS_TO_WFS",
  "status": "PLANNED",
  "slots": {
    "city": "武汉市",
    "beginDate": "2024-03-30",
    "endDate": "2024-05-30",
    "beginTime": "2024-03-30T00:00:00.000Z",
    "endTime": "2024-05-30T23:59:59.999Z",
    "observedProperty": "情感得分",
    "featureOfInterest": "http://www.org.cug.geodt/feature/city4201",
    "shouldPublishWfs": true
  },
  "missingInputs": [],
  "confirmed": false,
  "lastUserIntent": "SUPPLY_PARAMS",
  "lastUserMessage": "我要武汉市的",
  "artifacts": {},
  "plan": { "executable": true, "steps": ["..."] },
  "stepLogs": [],
  "turnCount": 2,
  "createdAt": 1780795194534,
  "updatedAt": 1780795194534
}
```

#### 2.4.3 不会记住什么

| 能力 | 当前状态 |
|------|----------|
| 完整聊天历史（每轮 user/assistant 全文） | **未实现**，仅保留 `lastUserMessage` |
| 跨会话长期记忆（用户偏好、常用城市等） | **未实现**，新 `conversationId` 即新任务 |
| 服务重启后恢复会话 | **不支持**，内存存储重启即丢失 |
| Redis / 数据库持久化 | **接口已预留**（`TaskContextStore`），当前仅内存实现 |

#### 2.4.4 前端如何使用记忆

1. **首轮**调用 `POST /agent/interaction/chat` 时可不传 `conversationId`
2. **保存**响应中的 `conversationId`（localStorage / 页面状态均可）
3. **后续每一轮**请求都带上同一个 `conversationId`
4. 页面刷新后若仍要续聊，用同一 ID 继续请求；若 ID 已过期或服务已重启，需重新发起任务
5. 可用 `GET /agent/interaction/context/{conversationId}` 恢复当前任务状态用于 UI 展示

记忆使用流程：

```
首轮 chat（无 conversationId）
    → 响应返回 conversationId + 已收集的 slots
第二轮 chat（携带 conversationId + 补充参数）
    → 合并 slots，更新 missingInputs / plan
确认轮 chat（携带 conversationId + confirmed: true）
    → 执行 Tool，写入 artifacts / stepLogs
```

#### 2.4.5 存储实现与过期策略

- 实现类：`InMemoryTaskContextStore`（进程内 `ConcurrentHashMap`）
- 配置项：`agent.interaction.session-ttl-seconds`，默认 **7200 秒（2 小时）**
- 每次 `get` / `save` / `create` 时会清理已过期会话
- 过期后会话不可恢复，再次使用旧 `conversationId` 会按新会话处理或查不到上下文

后续计划通过实现 `TaskContextStore` 接口，将存储替换为 Redis 或数据库，上层 REST 接口与 `TaskContext` 模型保持不变。

> 前端对接细节与字段说明见：[agent-interaction-api.md](./agent-interaction-api.md)

### 2.5 数据溯源（Provenance）

不只记录「接口被调用了」，而是形成**证据链**，回答：

- 用户说了什么？
- 系统识别到什么意图？
- 抽取/合并了哪些参数？
- 生成了什么执行计划？
- 调用了哪些 Skill / Tool？
- 每步输入输出是什么？是否成功？
- 最终结果对应哪个 WFS 地址？

溯源步骤记录在 `StepLogEntry` 中，可通过专用接口查询完整链路。

### 2.6 面向用户的可理解摘要

响应中包含 `understandingSummary`、`plan`、`replyText`，展示**可理解的推理摘要**，不暴露 LLM 原始思维链。

---

## 3. 支持的任务类型

| TaskType | 代码 | 必填参数 | 可选参数 | 执行状态 |
|----------|------|----------|----------|----------|
| SOS 查询并发布 WFS | `sos_to_wfs` | city, beginDate, endDate, observedProperty | shouldPublishWfs | **已接入** |
| WFS 空间分析 | `wfs_spatial_analysis` | wfsLayer, analysisType | outputLayerName | 占位，待 Skill 层 |
| 微博事件分析 | `weibo_event_analysis` | city, eventKeyword, beginDate, endDate | analysisDepth | 占位，待 Skill 层 |

> 对于 `sos_to_wfs`，若城市不在支持列表（当前仅武汉市有 `featureOfInterest` 映射），`missingInputs` 会包含 `supportedCity`。

---

## 4. REST API 说明

> 面向前端的完整接口文档（请求/响应字段、枚举、调用示例、状态机）见：**[agent-interaction-api.md](./agent-interaction-api.md)**

### 4.1 多轮对话（主入口）

```
POST /agent/interaction/chat
Content-Type: application/json
```

**请求体：**

```json
{
  "conversationId": "可选，首轮不传则自动生成",
  "userMessage": "用户本轮自然语言输入",
  "confirmed": false,
  "cancel": false
}
```

**响应体（关键字段）：**

```json
{
  "conversationId": "abc-123",
  "taskId": "task-456",
  "status": "COLLECTING_PARAMS",
  "taskType": "SOS_TO_WFS",
  "recognizedIntent": "SUPPLY_PARAMS",
  "understandingSummary": "任务类型：SOS 查询并发布 WFS\n- 城市：武汉市\n...",
  "extractedParams": { "city": "武汉市", "beginDate": "2024-03-30", ... },
  "missingInputs": [],
  "plan": {
    "summaryPoints": ["任务：SOS 查询并可选发布 WFS", "城市：武汉市", ...],
    "skills": ["sos_to_wfs_skill"],
    "tools": ["query_sos", "publish_wfs"],
    "steps": ["生成 SOS GetObservation 请求", "调用 query_sos 查询观测结果", ...],
    "executable": true
  },
  "canExecute": true,
  "needConfirmation": true,
  "replyText": "我将执行以下步骤：\n- ...\n是否确认执行？",
  "resultArtifacts": {},
  "stepLogs": [...],
  "turnCount": 2
}
```

### 4.2 查询会话上下文

```
GET /agent/interaction/context/{conversationId}
```

返回完整的 `TaskContext`，用于调试或前端展示当前任务状态。

### 4.3 查询溯源链

```
GET /agent/interaction/provenance/{conversationId}
```

返回 `ProvenanceChain`，包含全部 `StepLogEntry` 和中间产物 `artifacts`。

---

## 5. 完整调用示例

### 场景：SOS 查询 + 发布 WFS（三轮对话）

**第一轮 — 发起任务（缺城市）**

```http
POST /agent/interaction/chat
{
  "userMessage": "我要2024年3月30日到2024年5月30日的情感得分数据并发布WFS"
}
```

响应要点：
- `status`: `COLLECTING_PARAMS`
- `missingInputs`: `["city"]`
- `replyText`: 「当前还缺少：city。请补充后继续。」

**第二轮 — 补充城市**

```http
POST /agent/interaction/chat
{
  "conversationId": "<第一轮返回的 conversationId>",
  "userMessage": "我要武汉市的"
}
```

响应要点：
- `status`: `PLANNED`
- `missingInputs`: `[]`
- `needConfirmation`: `true`
- `plan.tools`: `["query_sos", "publish_wfs"]`

**第三轮 — 确认执行**

```http
POST /agent/interaction/chat
{
  "conversationId": "<conversationId>",
  "confirmed": true
}
```

响应要点：
- `status`: `COMPLETED` 或 `FAILED`
- `resultArtifacts.wfsUrl`: WFS 服务地址（成功时）
- `stepLogs`: 包含 `query_sos`、`publish_wfs` 的调用记录

---

## 6. 包内模块说明

```
agent/interaction/
├── config/
│   └── AgentInteractionProperties.java      # 会话 TTL 等配置
├── controller/
│   └── AgentInteractionController.java      # REST 入口
├── enums/
│   ├── UserIntent.java                      # 用户意图
│   ├── TaskType.java                        # 任务类型
│   └── InteractionStatus.java               # 会话阶段
├── memory/
│   ├── TaskContextStore.java                # 存储接口
│   └── InMemoryTaskContextStore.java        # 内存实现
├── model/
│   ├── TaskContext.java                     # 会话任务上下文
│   ├── AgentInteractionRequest/Response.java
│   ├── ExecutionPlan.java                   # 执行计划
│   ├── ProvenanceChain.java                 # 溯源链
│   ├── StepLogEntry.java                      # 单步溯源
│   └── TaskSlotDefinition.java                # 任务参数槽位定义
├── provenance/
│   └── ProvenanceService.java               # 溯源记录与查询
└── service/
    ├── AgentInteractionService.java         # 主编排服务
    ├── IntentRecognitionService.java        # 意图识别
    ├── ParameterExtractionService.java      # 参数抽取与合并
    ├── TaskPlanningService.java             # 计划生成
    ├── TaskExecutor.java                    # 执行器接口
    ├── TaskExecutorRegistry.java            # 执行器注册表
    └── executor/
        ├── SosToWfsTaskExecutor.java        # 已接入
        ├── WfsSpatialAnalysisTaskExecutor.java # 占位
        └── WeiboEventAnalysisTaskExecutor.java # 占位
```

### 6.1 主编排流程（AgentInteractionService）

```
接收请求
  → 加载/创建 TaskContext
  → 识别 UserIntent
  → 若需抽参：detectTaskType + extractAndMerge
  → 生成 ExecutionPlan
  → 更新 InteractionStatus
  → 若 CONFIRM 且参数齐全：路由 TaskExecutor 执行
  → 记录 Provenance
  → 返回 AgentInteractionResponse
```

### 6.2 Skill 扩展方式

新增一种任务类型时：

1. 在 `TaskType` 枚举中添加类型
2. 在 `ParameterExtractionService` 中定义槽位与抽取规则
3. 在 `TaskPlanningService` 中定义计划模板
4. 实现 `TaskExecutor` 接口，标注 `@Component`
5. `TaskExecutorRegistry` 会自动注册

无需修改 Controller 和主编排逻辑。

---

## 7. 配置项

在 `application.yml` 中：

```yaml
agent:
  interaction:
    session-ttl-seconds: 7200        # 会话过期时间（秒），默认 2 小时
    in-memory-store-enabled: true    # 是否启用内存会话存储
```

---

## 8. 与整体架构的衔接

| 层级 | 本包中的体现 | 后续演进 |
|------|-------------|----------|
| Agent 交互层 | `AgentInteractionService`、REST API | 接入 LLM 做 NLU / 意图识别 |
| Skill 层 | `TaskExecutor` + `executor/` | 完善 spatial / weibo Skill |
| Tool 层 | `SosToWfsTaskExecutor` 调用 `SosAgentToolService` | 更多 Tool 注册 |
| Memory | `TaskContextStore` | Redis / DB 持久化 |
| Provenance | `ProvenanceService` | 输入输出快照、调用链可视化 |
| MCP | 暂无 | 后期 Tool 标准化 |

---

## 9. 设计原则

1. **先规划后执行**：参数齐全 + 用户确认后才调用 Tool。
2. **多轮Stateful**：同一会话内参数累积合并，不重复询问已有信息。
3. **可扩展**：TaskExecutor 插件化，Skill 层独立演进。
4. **可溯源**：每步操作有记录，结果可追踪到输入和中间产物。
5. **用户友好**：展示计划摘要和执行日志，不暴露模型内部推理原文。
6. **LLM 可插拔**：当前规则引擎可无缝替换为 LLM function calling，上层接口不变。

---

## 10. 已知限制与后续计划

| 项目 | 当前状态 | 计划 |
|------|----------|------|
| 参数抽取 | 正则 / 规则 | 接入 OpenRouter LLM |
| 意图识别 | 关键词规则 | LLM 分类 |
| 会话存储 | 内存，重启丢失 | Redis / 数据库 |
| WFS 空间分析 | 占位执行器 | 接入 GeoServer MCP |
| 微博事件分析 | 占位执行器 | 接入爬虫 + GeoMesa Skill |
| 长期记忆 | 未实现 | 用户偏好向量库 |
| Swagger | 已标注 `@Api` | 可通过 Knife4j 文档页访问 |

---

## 11. 相关文件索引

- 前端接口文档：`docs/agent-interaction-api.md`
- 接口入口：`agent/interaction/controller/AgentInteractionController.java`
- 主编排：`agent/interaction/service/AgentInteractionService.java`
- 底层 Tool（已有）：`agent/service/SosAgentToolService.java`
- 早期 Demo：`agent/controller/OpenAiWorkflowDemoController.java`（`/agent-demo/responses`）
- 配置文件：`src/main/resources/application.yml`
