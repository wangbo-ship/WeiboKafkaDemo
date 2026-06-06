package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 9:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceIdentificationEntity {
    private List<String> title;  //默认Geodt SOS
    private List<String> abstracts; //默认Geodt Sensor Observation Service - Data Access for the Sensor Web
    private List<String> serviceType; //默认SOS
    private List<String> serviceTypeVersion; //默认服务类型
    private List<String> profileList;
    private List<KeywordsEntity> keyWordsList;
    private String fees;
    private List<String> accessConstraints;
}
