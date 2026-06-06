package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Author WJW
 * Date 2023/6/5 19:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "DataArray")
public class DataArrayEntity {
    private String id; //dataArray的标识
//    @XmlElement(name = "xmlns:swe")
//    private String uri;
    private ElementCountEntity elementCountEntity;
    private ElementTypeEntity elementTypeEntity;
    private EncodingEntity encodingEntity;
    private ValuesEntity valuesEntity;

}
