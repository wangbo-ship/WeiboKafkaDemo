package org.cug.geodt.weibo.sos.codec.encode;

import net.opengis.om.x20.OMObservationDocument;
import net.opengis.om.x20.OMObservationType;
import org.cug.geodt.weibo.sos.domain.observation.ObservationDataEntity;

import static org.cug.geodt.weibo.sos.codec.encode.GetObservationConverterV2.convertOMObservationType;


/**
 * Author WJW
 * Date 2023/7/18 20:05
 * @Description OM文档编码器，输入ObservationDataEntity对象，
 *输出2.0版本的OM文档(OMObservationDocument文档)
 */
public class OMConverter {

    public static OMObservationDocument convert(ObservationDataEntity observationDataEntity) {
        OMObservationDocument omObservationDocument = OMObservationDocument.Factory.newInstance();
        OMObservationType omObservationType = convertOMObservationType(observationDataEntity);
        omObservationDocument.setOMObservation(omObservationType);
        return omObservationDocument;
    }

}
