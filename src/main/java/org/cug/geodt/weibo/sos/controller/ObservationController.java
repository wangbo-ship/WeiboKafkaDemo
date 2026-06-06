package org.cug.geodt.weibo.sos.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.annotation.OperationInterceptor;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.cug.geodt.weibo.sos.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/observation")
public class ObservationController {
    @Autowired
    private ObservationService observationService;

    /*
     * 2.传感器观测详情数据接口
     * */
    @GetMapping("/{sensor_id}/latest/{interval_in_min}")
    @OperationInterceptor
    public Result<ReturnVO> getLatestMetricValueById(@PathVariable("sensor_id") List<String> sensorId,
                                                     @PathVariable("interval_in_min") Float intervalInMin){
        ReturnVO all = observationService.getLatestMetricValueById(sensorId, intervalInMin);
        return Result.OK(all);
    }

    @GetMapping("/{sensor_id}/latest/{interval_in_min}/{metric_name}")
    @OperationInterceptor
    public Result<ReturnVO> getLatestMetricValueByIdAndMetricName(@PathVariable("sensor_id") List<String> sensorId,
                                                                  @PathVariable("interval_in_min") Float intervalInMin,
                                                                  @PathVariable("metric_name") String metricName) {
        ReturnVO all = observationService.getLatestMetricValueByIdAndMetricName(sensorId, intervalInMin, metricName);
        return Result.OK(all);
    }

    @GetMapping("/{sensor_id}/time-range")
    @OperationInterceptor
    public Result<ReturnVO> getMetricValueByIdAndTimeRang(@PathVariable("sensor_id") List<String> sensorId,
                                                          Integer start_time, Integer end_time) {
        ReturnVO all = observationService.getMetricValueByIdAndTimeRange(sensorId, start_time, end_time);
        return Result.OK(all);
    }

    @GetMapping("/{sensor_id}/time-range/{metric_name}")
    @OperationInterceptor
    public Result<ReturnVO> getMetricValueByIdAndTimeRangAndMetricName(@PathVariable("sensor_id") List<String> sensorId,
                                                                       Integer start_time, Integer end_time,
                                                                       @PathVariable("metric_name") String metricName) {
        ReturnVO all = observationService.getMetricValueByIdAndTimeRangeAndMetricName(sensorId, start_time, end_time, metricName);
        return Result.OK(all);
    }

    /*
     * 3.传感器组观测详情数据接口
     * */

    @GetMapping("/sensor-group/{group_name}/{sensor_id}/latest/{interval_in_min}/{metric_name}")
    @OperationInterceptor
    public Result<ReturnVO> getMetricValueByTypeAndMetricName(@PathVariable("group_name") String groupName,
                                                              @PathVariable("sensor_id") List<String> sensorId,
                                                              @PathVariable("interval_in_min") Float intervalInMin,
                                                              @PathVariable("metric_name") String metricName) {
        ReturnVO all = observationService.getMetricValueByTypeAndMetricName(groupName, sensorId, intervalInMin, metricName);
        return Result.OK(all);
    }

    @GetMapping("/sensor-group/{group_name}/{sensor_id}/time-range")
    @OperationInterceptor
    public Result<ReturnVO> getMetricValueByTypeAndTimeRang(@PathVariable("group_name") String groupName,
                                                            @PathVariable("sensor_id") List<String> sensorId,
                                                            Integer start_time, Integer end_time) {
        ReturnVO all = observationService.getMetricValueByTypeAndTimeRange(groupName, sensorId, start_time, end_time);
        return Result.OK(all);
    }

    @GetMapping("/sensor-group/{group_name}/{sensor_id}/time-range/{metric_name}")
    @OperationInterceptor
    public Result<ReturnVO> getMetricValueByTypeAndTimeRangAndMetricName(@PathVariable("group_name") String groupName,
                                                                         @PathVariable("sensor_id") List<String> sensorId,
                                                                         Integer start_time, Integer end_time,
                                                                         @PathVariable("metric_name") String metricName) {
        ReturnVO all = observationService.
                getMetricValueByTypeAndTimeRangeAndMetricName(groupName, sensorId, start_time, end_time, metricName);
        return Result.OK(all);
    }

    @GetMapping("/sensor-group/{group_name}/{sensor_id}/latest/{interval_in_min}")
    @OperationInterceptor
    public Result<ReturnVO> getMetricValueByType(@PathVariable("group_name") String groupName,
                                                 @PathVariable("sensor_id") List<String> sensorId,
                                                 @PathVariable("interval_in_min") Float intervalInMin) {
        ReturnVO all = observationService.getMetricValueByType(groupName, sensorId, intervalInMin);
        return Result.OK(all);
    }

    @PostMapping("/insert-observations")
    @OperationInterceptor
    public Result<ReturnVO> insertObservations(@RequestBody String OMXml) throws IOException, ParserConfigurationException, SAXException, XmlException, JAXBException {
//        String key = "device50";
//        Long number = 300L;
        ReturnVO returnVO = observationService.insertObservation(OMXml);
        return Result.ok(returnVO);
    }

    /**
     * 按月统计数据接入条数数据
     */
    @GetMapping("/dataAccess/{num}/month/latest")
    @OperationInterceptor
    public Result getDataEntryByMonth(@PathVariable int num) {
        Assert.notNull(num);
        return observationService.getDataEntryByMonth(num);
    }

    /**
     * 按天统计数据接入条数数据
     */
    @GetMapping("/dataAccess/{num}/year")
    @OperationInterceptor
    public Result getDataEntryByYear(@PathVariable int num) {
        Assert.notNull(num);
        return observationService.getDataEntryByYear(num);
    }

    @GetMapping("/dataAccess/{num}/day")
    @OperationInterceptor
    public Result getDataEntryByDay(@PathVariable int num) {
        Assert.notNull(num);
        return observationService.getDataEntryByDay(num);
    }


    /**
     * 返回当天接入的数据条数
     */
    @GetMapping("/dataEntry/day")
    @OperationInterceptor
    public Result getDataEntryToday() {
        return observationService.getTodayEntry();
    }

    /**
     * 返回数据库总数据条数
     */
    @GetMapping("/dataEntry/all")
    @OperationInterceptor
    public Result getAllDataEntry() {
        return observationService.getAllDataEntry();
    }

    /**
     * 返回数据总量
     */
    @GetMapping("/data/totalVolume")
    @OperationInterceptor
    public Result getTotalDataVolume() {
        return observationService.getTotalVolume();
    }

    /**
     * 返回7天微博，百度地图，中国气象网，推特，中国天气网，全国空气质量网的数据条数
     */
    @GetMapping("/data/spiders")
    @OperationInterceptor
    public Result getSpidersData() {
        return observationService.getSpidersData();
    }

    @GetMapping("/data/todayVolume")
    @OperationInterceptor
    public Result getTodayVolume() {
        return observationService.getTodayVolume();
    }

    @ApiOperation("按天统计物联感知设备")
    @OperationInterceptor
    public Result getStatisticalDataOnAccessesByDayAndType() {
        return observationService.getStatisticalDataOnAccessesByDayAndType();
    }


}
