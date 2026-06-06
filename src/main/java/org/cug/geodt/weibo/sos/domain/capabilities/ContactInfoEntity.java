package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cug.geodt.weibo.sos.domain.describeSensor.AddressEntity;

/**
 * Author WJW
 * Date 2023/6/7 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoEntity {
    private AddressEntity addressEntity;
    private PhoneEntity phoneEntity;
}
