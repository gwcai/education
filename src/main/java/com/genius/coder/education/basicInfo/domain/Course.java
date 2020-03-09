package com.genius.coder.education.basicInfo.domain;

import com.genius.coder.base.domain.BaseDomain;
import com.genius.coder.education.user.domain.AdminUser;
import lombok.Data;

import javax.persistence.Entity;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
@Entity(name="course")
@Data
public class Course extends BaseDomain<AdminUser,String> {
    private String no;//课程编号
    private String type;//课程类型
    private String subType;//子类型
    private String status;
}
