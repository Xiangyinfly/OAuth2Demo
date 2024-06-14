package com.xiang.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName client_info
 */
@TableName(value ="client_info")
@Data
public class ClientInfo implements Serializable {
    @TableId
    private String clientId;

    private String clientSecret;

    private String allowUrl;

    private String contractScope;

    private Integer autoMode;

    private static final long serialVersionUID = 1L;
}