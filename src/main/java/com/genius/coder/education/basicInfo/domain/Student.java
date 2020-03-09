package com.genius.coder.education.basicInfo.domain;

import com.genius.coder.base.domain.BaseDomain;
import com.genius.coder.education.user.domain.AdminUser;
import lombok.Data;
import org.hibernate.cfg.annotations.Comment;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/8
 */
@Entity(name = "student")
@Data
public class Student extends BaseDomain<AdminUser,String> {
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

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CardInfo> cardList;
}
