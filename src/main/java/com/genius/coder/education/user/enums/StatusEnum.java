package com.genius.coder.education.user.enums;

import com.genius.coder.base.enumType.StringRemarkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@AllArgsConstructor
@Getter
public enum StatusEnum implements StringRemarkEnum {

    DELETED("已删除"),
    ENABLE("启用"),
    STOP("停用");

    private String remark;

}
