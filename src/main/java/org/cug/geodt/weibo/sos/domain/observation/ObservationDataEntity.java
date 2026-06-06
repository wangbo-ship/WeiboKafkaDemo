package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.describeSensor.TimeEntity;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/5 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObservationDataEntity {
    private String OMType; //返回的OM类型，默认为"http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement"
    private PhenomenonTimeEntity phenomenonTimeEntity; //现象时间
    private String resultTime; //同Phenomenon字段中的gmlId
    private String procedure; //现象的处理过程
    private String observedProperty; //观测属性
    private FeatureOfInterestEntity featureOfInterestEntity; //地理要素
    private ResultEntity resultEntity; //返回的结果集
    private RelatedFeatures relatedFeatures; // 相关观测属性
    private TimeEntity timeEntity;
    private List<String>  nameEntity;

}
