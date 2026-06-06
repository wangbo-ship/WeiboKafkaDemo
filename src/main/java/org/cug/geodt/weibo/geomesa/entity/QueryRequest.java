package org.cug.geodt.weibo.geomesa.entity;

import lombok.Data;
import org.cug.geodt.weibo.entity.Poi;

/**
 * @className: QueryRequest
 * @author: caiyixun
 * @description: 查询请求转换中间类
 * @date: 2024/8/5 15:29
 * @version: 1.0
 */
@Data
public class QueryRequest {
    private String startTime;
    private String endTime;
    private Poi poi;
    private String polygonWKT;

}
