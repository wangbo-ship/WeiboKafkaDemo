package org.cug.geodt.weibo.sos.controller;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.sos.annotation.OperationInterceptor;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.service.Imp.PlatformServiceImpl;
import org.cug.geodt.weibo.sos.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @ClassName : GroundStationController  //类名
 * @Description :   //描述
 * @Author : cyx //作者
 * @Date: 2023/8/2  23:19
 */
@RestController
@RequestMapping("/platform")
@Slf4j
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @GetMapping("platform-id/{platform_id}")
    @OperationInterceptor
    public Result<Object> ObjConvertToXml(@PathVariable("platform_id") String platformId) throws ParseException {
        Object o = platformService.selectByPlatformId(platformId);
        return Result.ok(o);
    }
    @OperationInterceptor
    @PostMapping("/insert")
    public Result<String> xmlToObj(@RequestBody String xml) throws Exception {
        Assert.notNull(xml, "参数不能为null");
//        log.info("xml=" + xml);
        String s = platformService.xmlToObj(xml);
        return Result.OK(s);
    }

    @GetMapping("/id-name")
    @OperationInterceptor
    public Result<List<PlatformServiceImpl.IdAndName>> getAllIdAndName(){
        List<PlatformServiceImpl.IdAndName> allIdAndName = platformService.getAllIdAndName();
        return Result.ok(allIdAndName);
    }

    @GetMapping("/id-name/{type}")
    @OperationInterceptor
    public Result<List<PlatformServiceImpl.IdAndName>> getAllIdAndNameByType(@PathVariable("type") String type){
        List<PlatformServiceImpl.IdAndName> allIdAndName = platformService.getAllIdAndNameByType(type);
        return Result.ok(allIdAndName);
    }

    @PutMapping("platform-id/{platform_id}")
    @OperationInterceptor
    public Result<String> updateByPlatformId(@PathVariable("platform_id") String platformId, @RequestBody String xml) throws Exception {
        int isSuccess = platformService.updateByPlatformId(platformId, xml);
        if(isSuccess != 1){
            return Result.error("更新失败");
        }
        return Result.ok("更新成功");
    }

    @DeleteMapping("platform-id/{platform_id}")
    @OperationInterceptor
    public Result<String> deleteByPlatformId(@PathVariable("platform_id") String platformId){
        int isSuccess = platformService.deleteByPlatformId(platformId);
        if(isSuccess != 1){
            return Result.error("删除失败");
        }
        return Result.ok("删除成功");
    }

    //根据平台查询传感器id和name
    @GetMapping("platform-id/sensor-detail/{platform_id}")
    @OperationInterceptor
    public Result<Object> getAllSensorDetailByPlatformId(@PathVariable("platform_id") List<String> platformId) {
        Assert.notNull(platformId,"平台id不能为空");
        List<SensorInfo> allSensorDetail = platformService.getAllSensorDetailByPlatformId(platformId);
        return Result.ok(allSensorDetail);
    }


}
