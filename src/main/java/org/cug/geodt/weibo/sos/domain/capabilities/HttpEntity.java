package org.cug.geodt.weibo.sos.domain.capabilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 20:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpEntity {
    private List<RequestEntity> getRequestEntityList;
    private List<RequestEntity> postRequestEntityList;
}
