package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Author WJW
 * Date 2023/6/12 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {
    private LinkageEntity linkageEntity;
    private DescriptionEntity descriptionEntity;
}
