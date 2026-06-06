package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 21:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {
    private String description;
    private List<KeyWordEntity>  keyWordEntities;
    private List<IdentifierEntity> identifierEntityList;
    private List<ClassifierEntity> classifierEntityList;
    private ValidTimeEntity validTimeEntity;
    private List<CapabilitiesEntity> capabilitiesEntity;
    private List<ContactEntity> contactEntity;
    private PositionEntity positionEntity;
    private IOEntityV1 inputEntities;
    private IOEntityV1 outputEntities;

}
