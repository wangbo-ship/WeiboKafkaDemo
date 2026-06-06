package org.cug.geodt.weibo.sos.service;

import org.cug.geodt.weibo.sos.pojo.FeatureOfInterest;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.service
 * @Description
 * @date 2023/6/13 16:57
 */
public interface FeatureOfInterestService {
    List<FeatureOfInterest> findInterest();
}
