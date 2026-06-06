package org.cug.geodt.weibo.sos.domain.describeSensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/12 9:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    private String city;
    private String country;
    private String postalCode;
    private String administrativeArea;
    private List<String> deliveryPoint;
    private List<String> email;
}
