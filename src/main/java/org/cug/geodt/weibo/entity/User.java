package org.cug.geodt.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @className: User
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/05/14 16:54
 * @version: 1.0
 */
@Data
@Entity
@Table(name = "w_user")
@TableName("w_user")
@ApiModel(value = "微博用户基本信息")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "用户id")
    private BigInteger userId;

    @ApiModelProperty(value = "poi id")
    private String poiId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "微博爬取结束日期")
    private String endDate;

}
