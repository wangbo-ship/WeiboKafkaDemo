package org.cug.geodt.weibo.agent.interaction.service;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntentRecognitionServiceTest {

    private IntentRecognitionService service;
    private TaskContext context;

    @BeforeEach
    void setUp() {
        service = new IntentRecognitionService();
        context = new TaskContext();
        context.setTaskType(TaskType.SOS_TO_WFS);
    }

    @Test
    void firstTurnWithUserMessageIsNewTask() {
        assertEquals(UserIntent.NEW_TASK,
                service.recognize(context, "我要武汉市的情感得分数据", null, null));
    }

    @Test
    void explicitConfirmMapsToConfirmExecute() {
        context.setTurnCount(1);
        assertEquals(UserIntent.CONFIRM_EXECUTE,
                service.recognize(context, null, true, null));
    }

    @Test
    void missingParamsWithoutConfirmIsSupplyParams() {
        context.setTurnCount(1);
        context.setMissingInputs(Collections.singletonList("city"));
        assertEquals(UserIntent.SUPPLY_PARAMS,
                service.recognize(context, "武汉市", null, null));
    }
}
