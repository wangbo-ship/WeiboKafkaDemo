package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity {
    private String href;
    private List<ConstraintEntity> constraintEntity;
}
