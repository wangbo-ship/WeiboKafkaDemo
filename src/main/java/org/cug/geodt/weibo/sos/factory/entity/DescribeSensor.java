package org.cug.geodt.weibo.sos.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/6/21 9:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeSensor {
    private String service;
    private String version;
    private String procedure;
    private String procedureDescriptionFormat;
}
