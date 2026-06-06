package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/12 10:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsEntity {
    private List<DocumentEntity>  documentEntity;
}
