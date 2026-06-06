package org.cug.geodt.weibo.sos.domain.observation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Author WJW
 * Date 2023/6/5 19:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity {
    private DataArrayEntity dataArrayEntity; //存放返回的观测值

}
