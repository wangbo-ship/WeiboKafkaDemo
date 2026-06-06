package org.cug.geodt.weibo.sos.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import org.cug.geodt.weibo.sos.annotation.OperationInterceptor;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.common.constants.ActuatorWebSite;
import org.cug.geodt.weibo.sos.service.InterfaceCallStatisticsService;
import org.cug.geodt.weibo.sos.vo.CpuUsage;
import org.cug.geodt.weibo.sos.vo.DiskUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName ActuatorController
 * @Author WJW
 * @Date 2023/10/6 9:34
 * @Description 运维监控接口
 */

@RestController
@RequestMapping("/actuator")
public class ActuatorController {

    @Autowired
    private InterfaceCallStatisticsService interfaceStatisticsService;
    /**
     * cpu使用率
     */
    @OperationInterceptor
    @GetMapping(value = "/cpuUsage", produces = { "application/json;charset=UTF-8"})
    public Result cpuUsage() {
        String s = HttpUtil.get(ActuatorWebSite.cpuUsage);
        CpuUsage cpuUsage = JSONUtil.toBean(s, CpuUsage.class);
        interfaceStatisticsService.processCollectResult();
        interfaceStatisticsService.cpuUsageCount();
        return Result.ok(cpuUsage);
    }

    /**
     * 内存使用率
     */
    @OperationInterceptor
    @GetMapping("/memoryUsage")
    public Result memoryUsage() {
        String s = HttpUtil.get(ActuatorWebSite.memoryUsage);
        CpuUsage memoryUsage = JSONUtil.toBean(s, CpuUsage.class);
//        // 使用BigDecimal解析科学计数法表示的数字
//        BigDecimal bigDecimal = new BigDecimal(String.valueOf(memoryUsage.getMeasurements().get(0)));
//
//        // 将BigDecimal转换为Long类型
//        long convertedValue = bigDecimal.longValue();
        return Result.ok(memoryUsage);
    }

    /**
     * 磁盘使用率
     */
    @OperationInterceptor
    @GetMapping("/diskUsage")
    public Result diskUsage() {
        String s = HttpUtil.get(ActuatorWebSite.diskUsage);
        DiskUsage diskUsage = JSONUtil.toBean(s, DiskUsage.class);
        return Result.ok(diskUsage);
    }

    /**
     * 节点类型
     */
    @OperationInterceptor
    @GetMapping("/nodeType")
    public Result nodeType() {
        String s = HttpUtil.get(ActuatorWebSite.cpuUsage);
        CpuUsage cpuUsage = JSONUtil.toBean(s, CpuUsage.class);
        return Result.ok(cpuUsage);
    }


    /**
     * 网络IO数据量
     */
    @OperationInterceptor
    @GetMapping("/networkIODataVolume")
    public Result networkIODataVolume() {
        String s = HttpUtil.get(ActuatorWebSite.cpuUsage);
        CpuUsage cpuUsage = JSONUtil.toBean(s, CpuUsage.class);
        return Result.ok(cpuUsage);
    }


    /**
     * 获取所有接口的调用次数
     * @return
     */
    @OperationInterceptor
    @GetMapping("/requestCount/all")
    public Result getAllRequestCount() {
        return interfaceStatisticsService.getAllRequestCount();
    }
    @OperationInterceptor
    @GetMapping("/requestCounts/{path}")
    public Result getRequestCount(@PathVariable("path") String path) {
        return interfaceStatisticsService.getRequestCount(path);
    }

    /**
     * 返回cpu总核数以及可用核数
     */

    @OperationInterceptor
    @ApiOperation("统计cpu总核数以及可用核数")
    @GetMapping("/cpuCores")
    public Result getCpuCores() {
        return interfaceStatisticsService.getCpuCores();
    }

}
