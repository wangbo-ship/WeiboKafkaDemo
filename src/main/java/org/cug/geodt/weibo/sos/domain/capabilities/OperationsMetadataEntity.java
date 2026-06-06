package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 9:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationsMetadataEntity {
    private List<OperationEntity> operations;
    private List<ParameterEntity> parameterEntity;
}
