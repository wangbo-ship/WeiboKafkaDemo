package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/12 11:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {
    private String label;
    private List<DocumentsEntity> documentEntity;
    private TimeEntity timeEntity;
}
