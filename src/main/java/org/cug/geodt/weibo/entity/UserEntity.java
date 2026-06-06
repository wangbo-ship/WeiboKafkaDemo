package org.cug.geodt.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * @className: UserEntity
 * @author: caiyixun
 * @description: 存放所有user
 * @date: 2024/5/30 16:17
 * @version: 1.0
 */
@Data
@Entity
@Table(name = "w_all_user")
@TableName("w_all_user")
@ApiModel(value = "存放所有user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "poi_id")
    private String poiId;

    @ApiModelProperty(value = "用户id")
    private BigInteger userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "发布时间")
    private String createdAt;

    @ApiModelProperty(value = "微博文本")
    private String weiboText;

}
