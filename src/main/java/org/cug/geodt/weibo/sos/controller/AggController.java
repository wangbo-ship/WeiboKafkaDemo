package org.cug.geodt.weibo.sos.controller;

import org.cug.geodt.weibo.sos.annotation.OperationInterceptor;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.cug.geodt.weibo.sos.service.AggService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/observation/agg")
public class AggController {
    @Resource
    private AggService aggService;

    /*
     * 4.观测数据聚合查询接口
     * */

    @GetMapping("/{agg_name}/{agg_span}/sensor/{sensor_id}/{metric_name}/time-range")
    @OperationInterceptor
    public Result<ReturnVO> getAggValueByIdAndMetricName(@PathVariable("agg_name") String aggName,
                                                         @PathVariable("agg_span") String aggSpan,
                                                         @PathVariable("sensor_id") List<String> sensorId,
                                                         @PathVariable("metric_name") String metricName,
                                                         Integer start_time,
                                                         Integer end_time){
        ReturnVO all = aggService.getAggValueByIdAndMetricName(aggName, aggSpan, sensorId, metricName, start_time, end_time);
        return Result.OK(all);
    }

    @GetMapping("/{agg_name}/{agg_span}/sensor/{sensor_id}/time-range")
    @OperationInterceptor
    public Result<ReturnVO> getAggValueById(@PathVariable("agg_name") String aggName,
                                            @PathVariable("agg_span") String aggSpan,
                                            @PathVariable("sensor_id") List<String> sensorId,
                                            Integer start_time,
                                            Integer end_time){
        ReturnVO all = aggService.getAggValueById(aggName, aggSpan, sensorId, start_time, end_time);
        return Result.OK(all);
    }

    @GetMapping("/{agg_name}/{agg_span}/sensor-group/{group_name}/{sensor_id}/time-range")
    @OperationInterceptor
    public Result<ReturnVO> getAggValueBySensorType(@PathVariable("agg_name") String aggName,
                                                    @PathVariable("agg_span") String aggSpan,
                                                    @PathVariable("group_name") String groupName,
                                                    @PathVariable("sensor_id") List<String> sensorId,
                                                    Integer start_time,
                                                    Integer end_time) {
        ReturnVO all = aggService.getAggValueBySensorType(aggName, aggSpan, groupName, sensorId, start_time, end_time);
        return Result.OK(all);
    }

    @GetMapping("/{agg_name}/{agg_span}/sensor-group/{group_name}/{sensor_id}/{metric_name}/time-range")
    @OperationInterceptor
    public Result<ReturnVO> getAggValueByGroupAndMetricName(@PathVariable("agg_name") String aggName,
                                                            @PathVariable("agg_span") String aggSpan,
                                                            @PathVariable("group_name") String groupName,
                                                            @PathVariable("sensor_id") List<String> sensor_id,
                                                            @PathVariable("metric_name") String metricName,
                                                            Integer start_time,
                                                            Integer end_time) {
        ReturnVO all = aggService.
                getAggValueByGroupAndMetricName(aggName, aggSpan, groupName, sensor_id, metricName, start_time, end_time);
        return Result.OK(all);
    }

    @GetMapping("/{agg_name}/{span_min}/sensor/{sensor_id}/{metric_name}/latest/{interval_in_mins}")
    @OperationInterceptor
    public Result<ReturnVO> getLatestAggValueByMins(@PathVariable("agg_name") String aggName,
                                                    @PathVariable("span_min") Float spanMin,
                                                    @PathVariable("sensor_id") List<String> sensorId,
                                                    @PathVariable("metric_name") String metricName,
                                                    @PathVariable("interval_in_mins") Float intervalInMins) throws NoSuchMethodException {
        ReturnVO all = aggService.getLatestAggValueByMetricNameAndMins(aggName, spanMin, sensorId, metricName, intervalInMins);
        return Result.OK(all);
    }




    @GetMapping("/{agg_name}/{span_min}/sensor/{sensor_id}/latest/{interval_in_mins}")
    @OperationInterceptor
    public Result<ReturnVO> getLatestAggValueByMins(@PathVariable("agg_name") String aggName,
                                                    @PathVariable("span_min") Float spanMin,
                                                    @PathVariable("sensor_id") List<String> sensorId,
                                                    @PathVariable("interval_in_mins") Float intervalInMins) throws NoSuchMethodException {
        ReturnVO all = aggService.getLatestAggValueByMins(aggName, spanMin, sensorId, intervalInMins);
        return Result.OK(all);
    }

    @GetMapping("/{agg_name}/{span_min}/sensor-group" +
            "/{group_name}/{sensor_id}/{metric_name}/latest/{interval_in_mins}")
    @OperationInterceptor
    public Result<ReturnVO> getAggValueBySensorTypeAndMetricNameAndMins(@PathVariable("agg_name") String aggName,
                                                                        @PathVariable("span_min") Float spanMin,
                                                                        @PathVariable("group_name") String groupName,
                                                                        @PathVariable("sensor_id") List<String> sensorId,
                                                                        @PathVariable("metric_name") String metricName,
                                                                        @PathVariable("interval_in_mins") Float intervalInMins) throws NoSuchMethodException {
        ReturnVO all = aggService.
                getAggValueBySensorTypeAndMetricNameAndMins(aggName, spanMin, groupName, sensorId, metricName, intervalInMins);
        return Result.OK(all);
    }

    @GetMapping("/{agg_name}/{span_min}/sensor-group" +
            "/{group_name}/{sensor_id}/latest/{interval_in_mins}")
    @OperationInterceptor
    public Result<ReturnVO> getLatestAggValueBySensorTypeAndMins(@PathVariable("agg_name") String aggName,
                                                                 @PathVariable("span_min") Float spanMin,
                                                                 @PathVariable("group_name") String groupName,
                                                                 @PathVariable("sensor_id") List<String> sensorId,
                                                                 @PathVariable("interval_in_mins") Float intervalInMins) throws NoSuchMethodException {
        ReturnVO all = aggService.getLatestAggValueBySensorTypeAndMins(aggName, spanMin, groupName, sensorId, intervalInMins);
        return Result.OK(all);
    }


}
