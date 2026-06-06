package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.mapper.CapabilityMapper;
import org.cug.geodt.weibo.sos.pojo.FeatureOfInterest;
import org.cug.geodt.weibo.sos.service.FeatureOfInterestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.service
 * @Description
 * @date 2023/6/13 16:57
 */

@Service
public class FeatureOfInterestServiceImpl implements FeatureOfInterestService {
    @Resource
    CapabilityMapper capabilityMapper;
    @Override
    public List<FeatureOfInterest> findInterest() {
        return null;
    }
}
