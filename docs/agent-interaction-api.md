# Agent 交互层 — 前端接口文档

> 模块：`org.cug.geodt.weibo.agent.interaction`  
> 接口前缀：`/agent/interaction`  
> 默认服务地址：`http://localhost:54323`（见 `application.yml` 中 `server.port`）  
> 功能说明文档：[agent-interaction.md](./agent-interaction.md)

---

## 1. 通用约定

### 1.1 Base URL

```
http://{host}:54323/agent/interaction
```

### 1.2 请求头

| Header | 值 | 说明 |
|--------|-----|------|
| `Content-Type` | `application/json` | `POST /chat` 必填 |
| `Accept` | `application/json` | 建议携带 |

### 1.3 会话记忆（前端必读）

- 首轮 `POST /chat` **可不传** `conversationId`，服务端自动生成并在响应中返回
- **后续每一轮**必须携带同一个 `conversationId`，否则会被当作新任务
- 建议将 `conversationId` 保存在页面状态或 `localStorage`
- 会话默认 **2 小时**无活动后过期（`agent.interaction.session-ttl-seconds`，默认 `7200`）；服务重启后会话丢失
- 记忆的是**任务参数与状态**（`slots`、`plan` 等），不是完整聊天文本历史

### 1.4 推荐的前端状态机

根据响应字段驱动 UI：

| `status` | 前端建议展示 |
|----------|-------------|
| `COLLECTING_PARAMS` | 展示 `missingInputs`（或 `taskTrace.missingInputs`），引导用户补充参数 |
| `PLANNED` | 展示 `plan` 与 `replyText`，显示「确认执行」按钮 |
| `EXECUTING` | 展示 loading（通常仅短暂出现） |
| `COMPLETED` | 展示 `resultArtifacts`（如 `wfsUrl`）与 `stepLogs` / `taskTrace` |
| `FAILED` | 展示 `replyText` 与失败步骤 |
| `CANCELLED` | 提示任务已取消，可引导发起新任务 |

辅助布尔字段：

- `canExecute === true`：参数已齐全且任务未取消
- `needConfirmation === true`：等待用户确认，可传 `confirmed: true` 或发送「确认」类文本

### 1.5 三种查询接口的区别

| 接口 | 返回类型 | 适用场景 |
|------|----------|----------|
| `GET /context/{id}` | `TaskContext` | 页面刷新后恢复 UI，含 `plan`、`lastUserMessage` 等完整会话状态 |
| `GET /trace/{id}` | `TaskTrace` | 任务追踪面板：状态 + 参数快照 + 步骤日志 + 产物（面向展示） |
| `GET /provenance/{id}` | `ProvenanceChain` | 审计 / 调试：完整证据链，侧重步骤与输入快照 |

> `POST /chat` 的响应中已内嵌 `taskTrace`，一般对话场景无需额外请求 `/trace`。

---

## 2. 枚举说明

### 2.1 InteractionStatus（任务阶段）

| 值 | 含义 |
|----|------|
| `COLLECTING_PARAMS` | 正在收集参数 |
| `PLANNED` | 参数齐全，已生成计划，等待确认 |
| `EXECUTING` | 用户已确认，正在执行 |
| `COMPLETED` | 执行成功 |
| `FAILED` | 执行失败 |
| `CANCELLED` | 用户取消 |

### 2.2 TaskType（任务类型）

| 值 | 含义 | 可执行 |
|----|------|--------|
| `SOS_TO_WFS` | SOS 查询并可选发布 WFS | 是 |
| `WFS_SPATIAL_ANALYSIS` | WFS 图层空间分析 | 占位 |
| `WEIBO_EVENT_ANALYSIS` | 微博事件分析 | 占位 |
| `UNKNOWN` | 未识别 | 否 |

### 2.3 UserIntent（本轮识别意图）

| 值 | 含义 |
|----|------|
| `NEW_TASK` | 发起新任务 |
| `SUPPLY_PARAMS` | 补充缺失参数 |
| `CONFIRM_EXECUTE` | 确认执行 |
| `CHANGE_TASK` | 修改参数或变更任务 |
| `CANCEL` | 取消任务 |
| `UNKNOWN` | 无法识别 |

确认关键词（文本触发，参数齐全时生效）：`确认`、`执行`、`开始`、`好的`、`可以`、`没问题`、`go`、`yes`、`ok`

取消关键词：`取消`、`不要`、`算了`、`停止`、`cancel`、`no`

### 2.4 参数槽位中文名（SlotLabels）

`replyText`、`understandingSummary` 中的缺参提示使用中文展示名；`missingInputs` / `extractedParams` 的键名仍为英文：

| 键名 | 中文展示名 |
|------|-----------|
| `city` | 城市 |
| `beginDate` | 开始日期 |
| `endDate` | 结束日期 |
| `observedProperty` | 观测属性 |
| `shouldPublishWfs` | 是否发布 WFS |
| `featureOfInterest` | 兴趣区域 |
| `supportedCity` | 支持的城市（当前仅武汉市可用） |
| `wfsLayer` | WFS 图层 |
| `analysisType` | 分析类型 |
| `outputLayerName` | 输出图层名 |
| `eventKeyword` | 事件关键词 |
| `analysisDepth` | 分析深度 |
| `taskType` | 任务类型 |

---

## 3. 接口列表

| 方法 | 路径 | 说明 |
|------|------|------|
| `POST` | `/agent/interaction/chat` | 多轮对话主入口 |
| `GET` | `/agent/interaction/context/{conversationId}` | 查询会话任务上下文（短期记忆） |
| `GET` | `/agent/interaction/trace/{conversationId}` | 查询任务追踪视图 |
| `GET` | `/agent/interaction/provenance/{conversationId}` | 查询完整数据溯源链 |

---

## 4. POST /agent/interaction/chat

多轮对话主入口：参数抽取、补参、规划、确认执行均通过此接口完成。

### 4.1 请求

**URL**

```
POST /agent/interaction/chat
```

**Body（AgentInteractionRequest）**

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `conversationId` | string | 否 | 会话 ID；首轮不传，后续必传 |
| `userMessage` | string | 否* | 用户本轮自然语言输入 |
| `confirmed` | boolean | 否 | 显式确认执行，优先级高于文本意图识别 |
| `cancel` | boolean | 否 | 显式取消当前任务 |

\* 确认或取消时可只传 `confirmed` / `cancel`，但补参场景需传 `userMessage`。

**请求示例 — 首轮发起任务**

```json
{
  "userMessage": "我要2024年3月30日到2024年5月30日的情感得分数据并发布WFS"
}
```

**请求示例 — 补充参数**

```json
{
  "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "userMessage": "我要武汉市的"
}
```

**请求示例 — 确认执行**

```json
{
  "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "confirmed": true
}
```

或使用文本：

```json
{
  "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "userMessage": "确认执行"
}
```

**请求示例 — 取消任务**

```json
{
  "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "cancel": true
}
```

### 4.2 响应

**Body（AgentInteractionResponse）**

| 字段 | 类型 | 说明 |
|------|------|------|
| `conversationId` | string | 会话 ID，**前端必须保存** |
| `taskId` | string | 任务 ID |
| `status` | InteractionStatus | 当前任务阶段 |
| `taskType` | TaskType | 任务类型 |
| `recognizedIntent` | UserIntent | 本轮识别到的用户意图 |
| `understandingSummary` | string | 面向用户的理解摘要（多行文本，含任务类型与缺失参数中文名） |
| `extractedParams` | object | 已抽取参数（即 `slots` 的快照，键名为英文） |
| `missingInputs` | string[] | 仍缺失的参数键名（英文） |
| `plan` | ExecutionPlan | 执行计划，参数未齐时可能步骤较少 |
| `canExecute` | boolean | 参数是否已齐全且未取消 |
| `needConfirmation` | boolean | 是否需要用户确认后才执行（`status === PLANNED`） |
| `replyText` | string | 可直接展示给用户的回复文本（缺参提示为中文） |
| `resultArtifacts` | object | 结果产物（执行成功后含 `wfsUrl` 等） |
| `stepLogs` | StepLogEntry[] | 本轮为止的溯源步骤 |
| `taskTrace` | TaskTrace | 任务追踪视图（状态 + 参数 + 日志 + 产物整合） |
| `turnCount` | number | 对话轮数 |

**ExecutionPlan 结构**

| 字段 | 类型 | 说明 |
|------|------|------|
| `summaryPoints` | string[] | 计划摘要要点 |
| `skills` | string[] | 将调用的 Skill 名称 |
| `tools` | string[] | 将调用的 Tool 名称 |
| `steps` | string[] | 步骤说明（适合展示给用户） |
| `executable` | boolean | 计划是否可执行 |

**StepLogEntry 结构**

| 字段 | 类型 | 说明 |
|------|------|------|
| `stepIndex` | number | 步骤序号 |
| `phase` | string | 阶段，如 `user_input`、`planning`、`tool_execution`、`execution_summary` |
| `skillName` | string | Skill 名称（Tool 执行时有值） |
| `toolName` | string | Tool 名称（Tool 执行时有值） |
| `callId` | string | 调用 ID |
| `inputSnapshot` | string | 输入快照（可能被截断至 2000 字符） |
| `outputSnapshot` | string | 输出快照（可能被截断） |
| `success` | boolean | 是否成功 |
| `errorMessage` | string | 错误信息 |
| `timestamp` | number | 时间戳（毫秒） |
| `metadata` | object | 扩展元数据 |

**TaskTrace 结构**

| 字段 | 类型 | 说明 |
|------|------|------|
| `conversationId` | string | 会话 ID |
| `taskId` | string | 任务 ID |
| `taskType` | TaskType | 任务类型 |
| `status` | InteractionStatus | 当前阶段 |
| `understandingSummary` | string | 理解摘要（同响应顶层字段） |
| `inputSnapshot` | object | 已抽取参数快照（同 `extractedParams`） |
| `missingInputs` | string[] | 缺失参数键名 |
| `stepLogs` | StepLogEntry[] | 逐步执行日志 |
| `resultArtifacts` | object | 中间/最终结果产物 |
| `confirmed` | boolean | 用户是否已确认执行 |
| `turnCount` | number | 对话轮数 |
| `createdAt` | number | 创建时间（毫秒） |
| `updatedAt` | number | 更新时间（毫秒） |

**响应示例 — 缺参数（COLLECTING_PARAMS）**

```json
{
  "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "taskId": "f9e8d7c6-b5a4-3210-fedc-ba9876543210",
  "status": "COLLECTING_PARAMS",
  "taskType": "SOS_TO_WFS",
  "recognizedIntent": "NEW_TASK",
  "understandingSummary": "任务类型：SOS 查询并发布 WFS\n- 任务：SOS 查询并可选发布 WFS\n缺失参数：城市",
  "extractedParams": {
    "beginDate": "2024-03-30",
    "endDate": "2024-05-30",
    "beginTime": "2024-03-30T00:00:00.000Z",
    "endTime": "2024-05-30T23:59:59.999Z",
    "observedProperty": "情感得分",
    "shouldPublishWfs": true
  },
  "missingInputs": ["city"],
  "plan": {
    "summaryPoints": ["任务：SOS 查询并可选发布 WFS"],
    "skills": ["sos_to_wfs_skill"],
    "tools": ["query_sos", "publish_wfs"],
    "steps": ["生成 SOS GetObservation 请求", "调用 query_sos 查询观测结果"],
    "executable": false
  },
  "canExecute": false,
  "needConfirmation": false,
  "replyText": "我已识别到部分参数，当前还缺少：城市。请补充后继续。",
  "resultArtifacts": {},
  "stepLogs": [
    {
      "stepIndex": 1,
      "phase": "user_input",
      "inputSnapshot": "我要2024年3月30日到2024年5月30日的情感得分数据并发布WFS",
      "outputSnapshot": "intent=NEW_TASK",
      "success": true,
      "timestamp": 1780795194534,
      "metadata": {}
    }
  ],
  "taskTrace": {
    "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "taskId": "f9e8d7c6-b5a4-3210-fedc-ba9876543210",
    "taskType": "SOS_TO_WFS",
    "status": "COLLECTING_PARAMS",
    "understandingSummary": "任务类型：SOS 查询并发布 WFS\n- 任务：SOS 查询并可选发布 WFS\n缺失参数：城市",
    "inputSnapshot": { "beginDate": "2024-03-30", "endDate": "2024-05-30" },
    "missingInputs": ["city"],
    "stepLogs": [],
    "resultArtifacts": {},
    "confirmed": false,
    "turnCount": 0,
    "createdAt": 1780795194000,
    "updatedAt": 1780795194534
  },
  "turnCount": 1
}
```

**响应示例 — 待确认（PLANNED）**

```json
{
  "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "taskId": "f9e8d7c6-b5a4-3210-fedc-ba9876543210",
  "status": "PLANNED",
  "taskType": "SOS_TO_WFS",
  "recognizedIntent": "SUPPLY_PARAMS",
  "extractedParams": {
    "city": "武汉市",
    "beginDate": "2024-03-30",
    "endDate": "2024-05-30",
    "observedProperty": "情感得分",
    "featureOfInterest": "http://www.org.cug.geodt/feature/city4201",
    "shouldPublishWfs": true
  },
  "missingInputs": [],
  "canExecute": true,
  "needConfirmation": true,
  "replyText": "我将执行以下步骤：\n- 生成 SOS GetObservation 请求\n- 调用 query_sos 查询观测结果\n- 调用 publish_wfs 发布 WFS 图层\n是否确认执行？",
  "resultArtifacts": {},
  "turnCount": 2
}
```

**响应示例 — 执行成功（COMPLETED）**

```json
{
  "conversationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "taskId": "f9e8d7c6-b5a4-3210-fedc-ba9876543210",
  "status": "COMPLETED",
  "taskType": "SOS_TO_WFS",
  "recognizedIntent": "CONFIRM_EXECUTE",
  "canExecute": true,
  "needConfirmation": false,
  "replyText": "已完成 SOS 查询并发布 WFS。WFS URL: http://localhost:8082/geoserver/weibo-test/wfs?...",
  "resultArtifacts": {
    "generatedSoapPreview": "<soap12:Envelope>...</soap12:Envelope>",
    "sosResponseXml": "...",
    "wfsUrl": "http://localhost:8082/geoserver/weibo-test/wfs?..."
  },
  "stepLogs": [],
  "taskTrace": {
    "status": "COMPLETED",
    "resultArtifacts": {
      "wfsUrl": "http://localhost:8082/geoserver/weibo-test/wfs?..."
    },
    "confirmed": true
  },
  "turnCount": 3
}
```

### 4.3 extractedParams 常见字段（按任务类型）

**SOS_TO_WFS**

| 字段 | 说明 |
|------|------|
| `city` | 城市名 |
| `beginDate` / `endDate` | 日期（`yyyy-MM-dd`） |
| `beginTime` / `endTime` | ISO 时间（系统内部生成） |
| `observedProperty` | 观测属性 |
| `featureOfInterest` | 城市对应的 FOi URI（系统根据 city 推导） |
| `shouldPublishWfs` | 是否发布 WFS |

**WFS_SPATIAL_ANALYSIS**

| 字段 | 说明 |
|------|------|
| `wfsLayer` | WFS 图层 |
| `analysisType` | 分析类型 |
| `outputLayerName` | 输出图层名（可选） |

**WEIBO_EVENT_ANALYSIS**

| 字段 | 说明 |
|------|------|
| `city` | 城市 |
| `eventKeyword` | 事件关键词 |
| `beginDate` / `endDate` | 时间范围 |
| `analysisDepth` | 分析深度（可选） |

**特殊 missingInputs 值**

| 值 | 含义 |
|----|------|
| `supportedCity` | 城市不在支持列表（当前仅武汉市有完整映射） |
| `taskType` | 未能识别任务类型 |

---

## 5. GET /agent/interaction/context/{conversationId}

查询指定会话的完整任务上下文，用于页面刷新后恢复 UI 状态。

### 5.1 请求

```
GET /agent/interaction/context/{conversationId}
```

**路径参数**

| 参数 | 类型 | 说明 |
|------|------|------|
| `conversationId` | string | 会话 ID |

### 5.2 响应

**Body（TaskContext）**

| 字段 | 类型 | 说明 |
|------|------|------|
| `conversationId` | string | 会话 ID |
| `taskId` | string | 任务 ID |
| `taskType` | TaskType | 任务类型 |
| `status` | InteractionStatus | 当前阶段 |
| `slots` | object | 已收集参数（同 `extractedParams`） |
| `missingInputs` | string[] | 缺失参数键名 |
| `confirmed` | boolean | 是否已确认执行 |
| `lastUserIntent` | string | 最近一轮意图（枚举名字符串） |
| `lastUserMessage` | string | 最近一轮用户输入 |
| `artifacts` | object | 中间产物与最终结果 |
| `plan` | ExecutionPlan | 执行计划 |
| `stepLogs` | StepLogEntry[] | 溯源步骤 |
| `createdAt` | number | 创建时间（毫秒） |
| `updatedAt` | number | 更新时间（毫秒） |
| `turnCount` | number | 对话轮数 |

### 5.3 错误

会话不存在或已过期时，服务端抛出异常（通常 HTTP 500，消息含 `会话不存在: {conversationId}`）。前端应捕获后提示用户重新发起任务。

---

## 6. GET /agent/interaction/trace/{conversationId}

查询任务追踪视图，整合状态、参数快照、步骤日志与产物，适合「任务详情 / 进度追踪」面板。

### 6.1 请求

```
GET /agent/interaction/trace/{conversationId}
```

**路径参数**

| 参数 | 类型 | 说明 |
|------|------|------|
| `conversationId` | string | 会话 ID |

### 6.2 响应

**Body（TaskTrace）**

结构与 `POST /chat` 响应中的 `taskTrace` 字段相同，字段说明见上文 **4.2 响应 — TaskTrace 结构**。

### 6.3 错误

同「查询会话上下文」，会话不存在或已过期时返回错误。

---

## 7. GET /agent/interaction/provenance/{conversationId}

查询从用户输入到 Tool 执行的完整证据链，适合「执行详情 / 审计 / 调试」面板。

### 7.1 请求

```
GET /agent/interaction/provenance/{conversationId}
```

### 7.2 响应

**Body（ProvenanceChain）**

| 字段 | 类型 | 说明 |
|------|------|------|
| `conversationId` | string | 会话 ID |
| `taskId` | string | 任务 ID |
| `taskType` | TaskType | 任务类型 |
| `status` | InteractionStatus | 当前阶段 |
| `steps` | StepLogEntry[] | 完整步骤链 |
| `inputSnapshot` | object | 已抽取参数快照（同 `slots`） |
| `artifacts` | object | 全部中间产物与结果 |
| `createdAt` | number | 创建时间（毫秒） |
| `updatedAt` | number | 更新时间（毫秒） |

### 7.3 错误

同「查询会话上下文」，会话不存在时返回错误。

---

## 8. 前端集成示例

### 8.1 最简对话流程（伪代码）

```javascript
let conversationId = localStorage.getItem('agentConversationId');

async function sendMessage(userMessage, options = {}) {
  const body = {
    conversationId: conversationId || undefined,
    userMessage,
    confirmed: options.confirmed,
    cancel: options.cancel,
  };

  const res = await fetch('http://localhost:54323/agent/interaction/chat', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  });

  const data = await res.json();
  conversationId = data.conversationId;
  localStorage.setItem('agentConversationId', conversationId);

  // 根据 status 更新 UI
  if (data.status === 'COLLECTING_PARAMS') {
    showMissingInputs(data.missingInputs, data.replyText);
  } else if (data.status === 'PLANNED') {
    showPlan(data.plan, data.replyText);
    showConfirmButton(() => sendMessage('', { confirmed: true }));
  } else if (data.status === 'COMPLETED') {
    showResult(data.resultArtifacts, data.replyText);
    showTaskTrace(data.taskTrace); // 可选：展示追踪视图
  } else if (data.status === 'FAILED') {
    showError(data.replyText, data.taskTrace?.stepLogs);
  } else if (data.status === 'CANCELLED') {
    showCancelled(data.replyText);
    localStorage.removeItem('agentConversationId');
  }

  return data;
}
```

### 8.2 页面刷新后恢复状态

```javascript
async function restoreSession(conversationId) {
  const res = await fetch(
    `http://localhost:54323/agent/interaction/context/${conversationId}`
  );
  if (!res.ok) {
    // 会话过期或不存在，引导用户重新开始
    return null;
  }
  return res.json();
}
```

### 8.3 独立拉取任务追踪

```javascript
async function loadTaskTrace(conversationId) {
  const res = await fetch(
    `http://localhost:54323/agent/interaction/trace/${conversationId}`
  );
  if (!res.ok) return null;
  return res.json();
}
```

### 8.4 curl 快速验证

```bash
# 第一轮
curl -X POST "http://localhost:54323/agent/interaction/chat" \
  -H "Content-Type: application/json" \
  -d "{\"userMessage\":\"我要2024年3月30日到2024年5月30日的情感得分数据并发布WFS\"}"

# 第二轮（替换 conversationId）
curl -X POST "http://localhost:54323/agent/interaction/chat" \
  -H "Content-Type: application/json" \
  -d "{\"conversationId\":\"<conversationId>\",\"userMessage\":\"我要武汉市的\"}"

# 确认执行
curl -X POST "http://localhost:54323/agent/interaction/chat" \
  -H "Content-Type: application/json" \
  -d "{\"conversationId\":\"<conversationId>\",\"confirmed\":true}"

# 查询上下文
curl "http://localhost:54323/agent/interaction/context/<conversationId>"

# 查询任务追踪
curl "http://localhost:54323/agent/interaction/trace/<conversationId>"

# 查询溯源
curl "http://localhost:54323/agent/interaction/provenance/<conversationId>"
```

---

## 9. 注意事项

1. **URL 不要有多余空格**：路径末尾空格会被编码为 `%20`，导致 404
2. **`conversationId` 必须持久化**：否则每轮都是新任务，无法多轮补参
3. **确认门控**：`needConfirmation === true` 时不会自动执行，必须用户确认
4. **会话有效期**：默认 2 小时无活动后过期；服务重启后会话全部丢失
5. **缺参提示语言**：`replyText` 使用中文参数名，`missingInputs` 仍为英文键名，前端展示建议优先用 `replyText` 或查上文 **2.4 参数槽位中文名**
6. **`taskTrace` 与顶层字段**：`taskTrace` 是整合视图，与 `extractedParams`、`resultArtifacts`、`stepLogs` 等内容一致或为其子集，前端可任选一种展示方式
7. **Swagger / Knife4j**：Controller 已标注 `@Api`，也可在 `http://localhost:54323/doc.html` 查看在线文档（若项目已启用 Knife4j）

---

## 10. 相关源码

| 文件 | 说明 |
|------|------|
| `AgentInteractionController.java` | REST 入口（chat / context / trace / provenance） |
| `AgentInteractionService.java` | 主编排服务 |
| `InteractionResponseBuilder.java` | 响应与理解摘要组装 |
| `AgentInteractionRequest.java` | 请求模型 |
| `AgentInteractionResponse.java` | 响应模型 |
| `TaskContext.java` | 会话上下文模型 |
| `TaskTrace.java` | 任务追踪视图模型 |
| `ProvenanceChain.java` | 溯源链模型 |
| `ProvenanceService.java` | 溯源记录与链/追踪视图构建 |
| `SlotLabels.java` | 参数槽位中文展示名 |
| `InMemoryTaskContextStore.java` | 内存会话存储 |
