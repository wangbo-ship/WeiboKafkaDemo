package org.cug.geodt.weibo.sos.common.proceduremanage;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 切分策略接口
 * Author WJW
 * Date 2023/7/11 15:14
 */
@Service
public interface ProcedureSegmentationStrategy {


    HashMap<String,Long>  ProcedureSegmentationMap = null;

    void updateProcedureSegmentationMap(String key,Long number);

    void resetProcedureSegmentationMap(String key);

    void start();
}
