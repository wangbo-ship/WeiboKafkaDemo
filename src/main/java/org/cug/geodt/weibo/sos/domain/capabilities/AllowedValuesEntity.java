package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 20:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Nullable
public class AllowedValuesEntity {
    private List<String> valueArray;
    private List<ValueRangeEntity> valueRangeEntity;

    public List<String> getValueArray() {
        return valueArray;
    }

    public List<ValueRangeEntity> getValueRangeEntity() {
        return valueRangeEntity;
    }
}
