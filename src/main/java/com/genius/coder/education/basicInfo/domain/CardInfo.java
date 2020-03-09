package com.genius.coder.education.basicInfo.domain;

import com.genius.coder.base.domain.BaseDomain;
import com.genius.coder.education.user.domain.AdminUser;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
@Entity(name = "card_info")
@Data
public class CardInfo extends BaseDomain<AdminUser,String> {
    private String cardType;//可类型：年卡、半年卡、季卡、月卡
    private int classTimes;//课时
    private int askForLeave;//允许请假次数
    private Date enterDate;//入学日期
    private Date graduateDate;//毕业日期
    private String status;//状态

    @OneToOne(targetEntity = Classes.class)
    private Classes classes;

    @ManyToOne
    private Student student;
}
