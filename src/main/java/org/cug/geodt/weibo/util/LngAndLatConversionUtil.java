package org.cug.geodt.weibo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @className: test01
 * @author: caiyixun
 * @description: 经纬度转行政区初始化工具类
 * @date: 2024/7/10 11:38
 * @version: 1.0
 */
@Service
@Slf4j
public class LngAndLatConversionUtil {

    /**
     * 初始化路由配置
     */
    @PostConstruct
    public void initData(){
        /**
         * 按需初始化数据, 离线数据处理可以不加载该方法
         *
         * @param needBoundary    是否需要加载边界数据，用于经纬度转换省市区县
         * @param needStreet      是否需要加载街道数据
         * @param needArea        是否需要加载商圈数据
         * @param needCityLevel   是否需要加载城市级别数据
         * @return
         */
//        GeoTrans.init(true, true, false, false);
        log.info("初始化边界数据成功");
    }

}
