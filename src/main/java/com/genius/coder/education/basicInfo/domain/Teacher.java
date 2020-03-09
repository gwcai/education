package com.genius.coder.education.basicInfo.domain;

import com.genius.coder.base.domain.BaseDomain;
import com.genius.coder.education.user.domain.AdminUser;
import lombok.Data;
import org.hibernate.cfg.annotations.Comment;

import javax.persistence.Entity;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
@Entity(name = "teacher")
@Data
public class Teacher extends BaseDomain<AdminUser,String> {
    @Comment("教师编号")
    private String no;
    @Comment("姓名")
    private String name;
    private String gander;
    private String idNo;
    private String phoneNo;
    private String major;//专业
    private String status;
}
