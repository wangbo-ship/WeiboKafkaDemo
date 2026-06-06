package org.cug.geodt.weibo.sos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.cug.geodt.weibo.sos.annotation.OperationInterceptor;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.sensor.SensorPublish;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.SensorPublishedResponse;
import org.cug.geodt.weibo.sos.service.*;
import org.cug.geodt.weibo.sos.utils.csw.CSWRecordInfo;
import org.cug.geodt.weibo.sos.utils.csw.ExceptionResponseInfo;
import org.cug.geodt.weibo.sos.utils.csw.RecordTransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sos")
@Api(value = "sos服务", tags = "sos")
public class SOSController {

    @Autowired
    private SosService sosService;

    @Resource
    private GetCapabilitiesService getCapabilitiesService;


    @Resource
    private GetDescribeSensorService getDescribeSensorService;


    @Resource
    private GetObservationService getObservationService;

    @Autowired
    private InfoService infoService;

    @OperationInterceptor
    @GetMapping(value ="/getCapabilities",produces = "application/json;charset=UTF-8")
    public String getCapabilities(){
        return getCapabilitiesService.getGeoDTCapabilities();
    }


    @OperationInterceptor
    @PostMapping(value = "/getDescribeSensor",produces = "application/json;charset=UTF-8")
    public String getDescribeSensor(@RequestBody String str){
        return getDescribeSensorService.getGeoDTDescribeSensor(str);
    }

    @OperationInterceptor
    @PostMapping(value = "/getObservation",produces = "application/json;charset=UTF-8")
    public String getObservation(@RequestBody String str){
        return getObservationService.getObservationService(str);
    }

    /**
     * 查询所有传感器的发布信息
     * @return
     */
    @OperationInterceptor
    @PostMapping("/publishedInfo")
    public Result getPublishedInfo(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        List<SensorPublishedResponse> publishedInfo = sosService.getPublishedInfo(pageNum, pageSize);
        int num = sosService.getTotalRecordCount();
        Map<String,Object> merge = new HashMap<>();
        merge.put("totalRecord",num);
        merge.put("dataInfo",publishedInfo);
        return Result.ok(merge);
    }

    /**
     * 查询所有传感器的id
     * @return
     */
    @OperationInterceptor
    @ApiOperation("查询所有传感器id")
    @GetMapping("/all/sensorId")
    public Result getAllSensorId() {
        List<String> allSensorId = sosService.getAllSensorId();
        return Result.ok(allSensorId);
    }

    /**
     * 根据传感器id判断传感器是否发布
     * @return
     */
    @OperationInterceptor
    @ApiOperation("根据传感器id判断传感器是否发布")
    @GetMapping("/{sensorId}/isPublished")
    public Result getSensorIsPublished(@PathVariable(value = "sensorId" , required = false) String sensorId) {
        Assert.notNull(sensorId,"sensorId不能为空");
        List<SensorPublishedResponse> publishedInfo  = sosService.getSensorIsPublished(sensorId);

        return Result.ok(publishedInfo);


    }

    /**
     * 发布传感器信息
     * @return
     */
    @OperationInterceptor
    @ApiOperation("发布传感器信息")
    @PostMapping("/sensor/published")
    public Result insertPublishedData(@RequestBody SensorPublish sensorPublish) {
        try {
            SensorInfo sensorInfo= infoService.getSensorBasicById(sensorPublish.getSensorId());
            //注册到csw
            RecordTransactionUtil transactionUtil
                    = new RecordTransactionUtil("http://192.168.10.12:8180/geoserver/cug_csw");
            transactionUtil.setResponseHandler(responseInfo -> {
                String responseType = responseInfo.getResponseType();
                if(responseType.equals("Exception")) {
                    ExceptionResponseInfo info = (ExceptionResponseInfo) responseInfo;
                    String exceptionText = info.getExceptionText();
                    System.out.println("错误原因: " + exceptionText);
                }
            });
            CSWRecordInfo info1 = new CSWRecordInfo();
            info1.setIdentifier("sos:"+ sensorPublish.getSensorId());
            info1.setTitle(sensorInfo.getSensorLongName());
            info1.setSubject(Arrays.asList(sensorInfo.getSensorType(), sensorInfo.getFkPlatform(),sensorInfo.getObsTheme()));
            info1.setType("Service");
            info1.setFormat("SOS");
            info1.setCreator("cug_geodt");
            info1.setModified(Instant.now().toEpochMilli());
            info1.setRelation("sos/getDescribeSensor?procedure=" + sensorInfo.getSensorId());

            // 添加记录
            transactionUtil.addRecord(Arrays.asList(info1));
            //发布传感器
            int i = sosService.insertPublishedData(sensorPublish);


            if (i != 1) {
                return Result.error("发布失败");
            }else {
                return Result.ok("发布成功");
            }
        } catch (Exception e) {
            return Result.ok("您已经发布id:" + sensorPublish.getSensorId() +"的传感器");
        }

    }

    /**
     * 删除已发布的传感器
     */
    @OperationInterceptor
    @ApiOperation("删除已发布的传感器")
    @DeleteMapping("{sensorId}/delete")
    public Result deletePublishedSensor(@PathVariable("sensorId") String sensorId) {
        Assert.notNull(sensorId,"sensorId不能为空");
        int i = sosService.deletePublishedSensor(sensorId);
        RecordTransactionUtil transactionUtil
                = new RecordTransactionUtil("http://192.168.10.12:8180/geoserver/cug_csw");
        transactionUtil.deleteByIdentifier("sos:" + sensorId);
        if (i != 1 ) {
            return Result.error("删除失败");
        } else {
            return Result.ok("删除成功");
        }

    }

    /**
     * 查询所有传感器类型
     */
    @OperationInterceptor
    @ApiOperation("查询所有传感器类型")
    @GetMapping("/sensor/allSensorTypes")
    public Result getAllSensorType() {
        List<String> allSensorTypes = sosService.getAllSensorType();
        return Result.ok(allSensorTypes);
    }


    /**
     * 根据类型查询传感器id
     */
    @OperationInterceptor
    @ApiOperation("根据类型查询传感器id")
    @GetMapping("/{sensorType}/sensorId")
    public Result getSensorIdByType(@PathVariable String sensorType) {
        Assert.notNull(sensorType);
        List<String> SensorIds = sosService.getAllSensorIdBySensorType(sensorType);
        return Result.ok(SensorIds);
    }

    /**
     * 根据id查询观测值
     */
    @OperationInterceptor
    @ApiOperation("根据id查询观测值")
    @GetMapping("/{sensorId}/metrics")
    public Result getMetricsBySensorId(@PathVariable String sensorId) {
        Assert.notNull(sensorId);
        List<String> metrics = sosService.getMetricsBySensorId(sensorId);
        return Result.ok(metrics);
    }


    /**
     * 根据type查询传感器info
     */
//    @ApiOperation("根据type查询传感器info")
//    @GetMapping("/{sensorType}/sensorInfo")
//    public Result getSensorInfoBySensorType(@PathVariable String sensorType) {
//        Assert.notNull(sensorType);
//        SensorPublishedResponse sensorPublishedResponse = sosService.getSensorInfoBySensorType(sensorType);
//        return Result.ok(sensorPublishedResponse);
//    }

    /**
     *根据传感器id查询传感器的开始时间和结束时间
     */
    @OperationInterceptor
    @ApiOperation("根据传感器id查询传感器的开始时间和结束时间")
    @GetMapping("/{sensorId}/dataTime")
    public Result getDataTimeBySensorId(@PathVariable String sensorId) {
        Assert.notNull(sensorId);
        Long timeStamp = sosService.getDataTimeBySensorId(sensorId);
        Map dataTime = new HashMap<String,Long>();
        dataTime.put("数据开始时间",timeStamp-86400*3);
        dataTime.put("数据结束时间",timeStamp);
        return Result.ok(dataTime);
    }

    /**
     * 筛选一组传感器中未发布的传感器id
     */
    @OperationInterceptor
    @ApiOperation("筛选一组传感器中未发布的传感器id")
    @GetMapping("/{sensorId}/notPublishedIds")
    public Result getNotPublishedIds(@PathVariable List<String> sensorId) {
        Assert.notNull(sensorId);
        List<String> notPublishedIds = sosService.getNotPublishedIds(sensorId);
        return Result.ok(notPublishedIds);

    }

    @OperationInterceptor
    @ApiOperation("CSW需要记录数据资源的统计类元信息")
    @GetMapping("/csw/metaDataOnSos")
    public Result CswMetaDataOnSos() {
        return sosService.getCswMetaDataOnSos();
    }



}
