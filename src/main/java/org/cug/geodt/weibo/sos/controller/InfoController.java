package org.cug.geodt.weibo.sos.controller;


import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.annotation.OperationInterceptor;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.cug.geodt.weibo.sos.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/info")
public class InfoController {


    @Autowired
    private InfoService infoService;

    /*
    传感器基本信息查询接口
    * */
    //查询全部的传感器信息
    @GetMapping("/sensors")
    @OperationInterceptor
    public Result<ReturnVO> getAllSensorInfo(){
        ReturnVO all = infoService.getAllSensorInfo();
        return Result.OK(all);
    }
//根据sensorIds查询传感器信息
    @GetMapping("/{sensor_id}/bs-info")
    @OperationInterceptor
    public Result<ReturnVO> getSensorInfoById(@PathVariable("sensor_id") List<String> sensorId) {
        ReturnVO all = infoService.getSensorInfoById(sensorId);
        return Result.OK(all);
    }
//根据sensorIds查询测值项信息
    @GetMapping("/{sensor_id}/metrics")
    @OperationInterceptor
    public Result<ReturnVO> getAllMetricsById(@PathVariable("sensor_id") List<String> sensorId){
        ReturnVO all = infoService.getAllMetricsById(sensorId);
        return Result.OK(all);
    }
//?????????
    @GetMapping("/{sensor_id}/{metric_name}")
    @OperationInterceptor
    public Result<ReturnVO> getMetricsByMetricName(@PathVariable("sensor_id") String sensorId,
                                                   @PathVariable("metric_name") String metricName){
        ReturnVO all = infoService.getMetricsByMetricName(sensorId, metricName);
        return Result.OK(all);
    }
//查询指定多个传感器的经纬度
    @GetMapping("/{sensor_id}/latlon")
    @OperationInterceptor
    public Result<ReturnVO> getLatlonById(@PathVariable("sensor_id") List<String> sensorId){
        ReturnVO all = infoService.getLatlonById(sensorId);
        return Result.OK(all);
    }
//获取所有传感器分组的基本信息
    @GetMapping("/sensor-group")
    @OperationInterceptor
    public Result<ReturnVO> getAllSensorGroup(){
        ReturnVO all = infoService.getAllSensorGroup();
        return Result.OK(all);
    }
//获取指定分组下传感器的测值项名称
    @GetMapping("/sensor-group/{group_name}")
    @OperationInterceptor
    public Result<ReturnVO> getSensorInfoBySensorType(@PathVariable("group_name") String groupName){
        ReturnVO all = infoService.getSensorInfoBySensorType(groupName);
        return Result.OK(all);
    }



    //获取指定多个传感器最早观测时间
    @GetMapping("/{sensor_id}/obs-time/oldest")
    @OperationInterceptor
    public Result<ReturnVO> getOldestObsTimeById(@PathVariable("sensor_id") List<String> sensorId){
        ReturnVO all = infoService.getOldestObsTimeById(sensorId);
        return Result.OK(all);
    }
//获取指定多个传感器最近观测时间
    @GetMapping("/{sensor_id}/obs-time/latest")
    @OperationInterceptor
    public Result<ReturnVO> getLatestObsTimeById(@PathVariable("sensor_id") List<String> sensorId){
        ReturnVO all = infoService.getLatestObsTimeById(sensorId);
        return Result.OK(all);
    }
//获取指定多个传感器指定观测日期最近观测时间戳
    @GetMapping("/{sensor_id}/obs-timestamp/{date}/latest")
    @OperationInterceptor
    public Result<ReturnVO> getLatestObsTimestampByIdAndDate(@PathVariable("sensor_id") List<String> sensorId,
                                                             @PathVariable("date") String date){
        ReturnVO all = infoService.getLatestObsTimeStampByIdAndDate(sensorId, date);
        return Result.OK(all);
    }
//获取指定多个传感器指定观测日期最早观测时间戳
    @GetMapping("/{sensor_id}/obs-timestamp/{date}/oldest")
    @OperationInterceptor
    public Result<ReturnVO> getOldestObsTimestampByIdAndDate(@PathVariable("sensor_id") List<String> sensorId,
                                                             @PathVariable("date") String date){
        ReturnVO all = infoService.getOldestObsTimeStampByIdAndDate(sensorId, date);
        return Result.OK(all);
    }

    //获取所有传感器的id
    @GetMapping("/sensor-id")
    @OperationInterceptor
    public Result<ReturnVO> getSensorIdInSensorInfo(){
        ReturnVO all = infoService.getSensorIdInSensorInfo();
        return Result.OK(all);
    }

    @GetMapping("/sensor-num")
    @OperationInterceptor
    public Result<ReturnVO> getSensorNumBySensorType(){
        ReturnVO all = infoService.getSensorNumBySensorType();
        return Result.OK(all);
    }

    //获取所有分组的传感器的数量
    @GetMapping("/{group_name}/metric-name")
    @OperationInterceptor
    public Result<ReturnVO> getMetricsBySensorType(@PathVariable("group_name") String groupName){
        ReturnVO all = infoService.getMetricsBySensorType(groupName);
        return Result.OK(all);
    }

    //插入传感器
    @PostMapping("/insert-sensor/{sensorType}")
    @OperationInterceptor
    public Result insertSensors(@PathVariable("sensorType") String sensorType,@RequestBody String xml) throws XmlException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String id = infoService.insertSensor(xml, sensorType);
        return Result.oK(id);
    }

    //查询传感器的基础信息
    @GetMapping("/{sensor_id}/basic-info")
    @OperationInterceptor
    public Result getSensorBasicInfoById(@PathVariable("sensor_id") String sensorId) throws InvocationTargetException, IllegalAccessException {
        Assert.notNull(sensorId,"传感器id不能为空");
        Object basicSensorInfo = infoService.getBasicSensorInfoById(sensorId);
        return Result.ok(basicSensorInfo);
    }

   //删除传感器
    @DeleteMapping("/sensor-id/{sensor_id}")
    @OperationInterceptor
    public Result deleteSensorById(@PathVariable("sensor_id") String sensorId) throws InvocationTargetException, IllegalAccessException {
        Assert.notNull(sensorId,"传感器id不能为空");
        int i = infoService.deleteSensorBySensorId(sensorId);
        if (i != 1) {
            return Result.error("删除失败");
        }
        return Result.ok("删除成功");
    }

    @PutMapping("/sensor-id/{sensor_type}")
    @OperationInterceptor
    public Result updateSensorById(@PathVariable("sensor_type") String sensorType, @RequestBody String xml) throws XmlException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Assert.notNull(sensorType,"传感器类型不能为空");
        int i = infoService.updateSensorById(sensorType, xml);
        if (i != 1) {
            return Result.ok("更新失败");
        }
        return Result.ok("更新成功");
    }


    /**
     * 获取卫星传感器的数量统计信息
     */
    @GetMapping("/satellite")
    @OperationInterceptor
    public Result getSatelliteStatisticalDataByType() {
        return infoService.getSatelliteStatisticalDataByType();
    }

    /**
     * 获取卫星传感器的数量统计信息
     */
    @GetMapping("/uav")
    @OperationInterceptor
    public Result getUavStatisticalDataByType() {
        return infoService.getUavStatisticalDataByType();
    }

    /**
     * 获取卫星传感器的数量统计信息
     */
    @GetMapping("/groundStation")
    @OperationInterceptor
    public Result getGroundStationStatisticalDataByType() {
        return infoService.getGroundStationStatisticalDataByType();
    }

    /**
     * 获取卫星传感器的数量统计信息
     */
    @GetMapping("/ocean")
    @OperationInterceptor
    public Result getOceanStatisticalDataByType() {
        return infoService.getOceanStatisticalDataByType();
    }

    /**
     * 获取传感器平台个数
     */
































}
