package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.describeSensor.EnvelopeEntity;

/**
 * Author WJW
 * Date 2023/6/16 15:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundedByEntityV1 {
    private EnvelopeEntity envelopeEntity;
}
