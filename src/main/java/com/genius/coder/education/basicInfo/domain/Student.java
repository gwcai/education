package com.genius.coder.education.basicInfo.domain;

import com.genius.coder.base.domain.BaseDomain;
import lombok.Data;
import org.hibernate.cfg.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/8
 */
@Entity(name = "student")
@Data
public class Student extends BaseDomain<String> {
    @Comment("学号")
    @Column(unique = true)
    private String no;
    @Comment("姓名")
    private String name;
    @Comment("身份证号")
    private String idNo;
    @Comment("性别")
    private String gander;
    @Comment("出生年月")
    private Date birthDate;
    @Comment("父亲姓名")
    private String father;
    @Comment("母亲姓名")
    private String mother;
    @Comment("父亲联系电话")
    private String f_phone;
    @Comment("母亲联系电话")
    private String m_phone;

    @Comment("二维码图片，上课时使用")
    @Column(length = 1024)
    private String imgCode;
}
