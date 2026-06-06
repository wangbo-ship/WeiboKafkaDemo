package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationEntity {
    private String name;
    private List<DCPEntity> entity;
    private List<ParameterEntity> parameterEntity;
}
