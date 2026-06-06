package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.capabilities.ContactInfoEntity;

/**
 * Author WJW
 * Date 2023/6/7 21:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {
    private String title;
    private String IndividualName; //1.0
    private String OrganizationName; //1.0
    private ContactInfo contactInfo; //1.0
    private String gmlId;
    private ContactInfoEntity contactInfoEntity;
    private OrganisationNameEntity organisationNameEntity;
    private PositionNameEntity positionNameEntity;
    private IndividualNameEntity individualNameEntity;
    private RoleEntity roleEntity;
}

