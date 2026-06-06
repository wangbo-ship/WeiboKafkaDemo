package org.cug.geodt.weibo.sos.common.proceduremanage;

import org.springframework.stereotype.Service;

/**
 * Author WJW
 * Date 2023/7/13 9:37
 */
@Service
public interface ProcedureManager {

    void ScanProcedureMap() throws InterruptedException;

}
