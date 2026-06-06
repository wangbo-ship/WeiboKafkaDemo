package org.cug.geodt.weibo.sos.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author ChengFl
 * @version 1.0
 * @description: TODO
 * @date 2023/7/14 9:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogicTemporalFilter {
    private String logicOps;
    private ArrayList<TemporalFilter<?>> temporalFilters;
    private ArrayList<LogicTemporalFilter> logicTemporalFilter;
}
