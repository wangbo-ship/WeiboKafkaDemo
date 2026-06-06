package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/6 9:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedFeatures {
    private List<String> relatedFeatureList;
}
