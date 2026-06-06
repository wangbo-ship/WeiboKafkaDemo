package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/8 9:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {
    private List<String> DeliveryPoint;
    private String city;
    private String postalCode;
    private String country;
    private String electronicMailAddress;
}
