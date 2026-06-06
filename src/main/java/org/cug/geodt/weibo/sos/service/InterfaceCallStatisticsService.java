package org.cug.geodt.weibo.sos.service;

import org.cug.geodt.weibo.sos.common.Result;

/**
 * @FileName InterfaceStatisticsService
 * @Author WJW
 * @Date 2023/10/6 15:47
 * @Description 统计接口使用次数
 */

public interface InterfaceCallStatisticsService {


    public void processCollectResult();

    public void cpuUsageCount();


    Result getAllRequestCount();

    Result getRequestCount(String path);

    Result getCpuCores();
}
