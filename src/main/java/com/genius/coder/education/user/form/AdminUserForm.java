package com.genius.coder.education.user.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genius.coder.base.form.BaseForm;
import com.genius.coder.education.user.domain.AdminUser;
import com.genius.coder.education.user.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@Getter
@Setter
@ApiModel("用户信息dto")
public class AdminUserForm extends BaseForm<AdminUser, String> implements Serializable {

    private String id;

    @ApiModelProperty("密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty("名称，可以是昵称等信息")
    private String name;

    @ApiModelProperty("手机号")
    private String phoneNum;

    @ApiModelProperty("用户状态")
    private StatusEnum status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdDate;

    //微信unionid
    private String unionid;

    private String openid;
    //性别
    private Integer sex;

    //头像
    private String headimgurl;

    @Override
    public AdminUserForm toForm(AdminUser entity) {
        return (AdminUserForm) super.toForm(entity);
    }
}
