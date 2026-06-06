package org.cug.geodt.weibo.sos.service;

import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.service.Imp.PlatformServiceImpl;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

/**
 * @InterfaceName : GroundStationService  //接口名
 * @Description :   //描述
 * @Author : cyx //作者
 * @Date: 2023/8/2  22:20
 */
@Repository
public interface PlatformService<T> {

    List<PlatformServiceImpl.IdAndName> getAllIdAndName();

    List<PlatformServiceImpl.IdAndName> getAllIdAndNameByType(String type);

    List<String> objConvertToXml(String platformId);

    String xmlToObj(String xml) throws Exception;

    Object selectByPlatformId(String platformId) throws ParseException;

    int updateByPlatformId(String platformId, String xml) throws Exception;

    int deleteByPlatformId(String platformId);

    List<SensorInfo> getAllSensorDetailByPlatformId(List<String> platformId);


}
