package com.genius.coder.education.user.enums;

import com.genius.coder.base.enumType.StringRemarkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginTypeEnum implements StringRemarkEnum {

    password("密码登录"), phone("手机验证码登录"), web("网页登录"),weChat("微信登录");

    private String remark;
}
