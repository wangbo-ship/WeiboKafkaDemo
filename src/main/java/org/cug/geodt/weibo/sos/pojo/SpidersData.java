package org.cug.geodt.weibo.sos.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @FileName SpidersData
 * @Author WJW
 * @Date 2023/9/14 16:30
 * @Description
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class SpidersData {

    @JsonInclude(JsonInclude.Include.NON_NULL)  // 只包含非空属性
    private String name;

    private Integer number;

    @TableField("obs_timestamp")
    private Long ObsTimeStamp;

}
