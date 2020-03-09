package com.genius.coder.education.basicInfo.domain;

import com.genius.coder.base.domain.BaseDomain;
import com.genius.coder.education.user.domain.AdminUser;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
@Entity(name="classes")
@Data
public class Classes extends BaseDomain<AdminUser,String> {
    private String no;//班级编号
    private String name;//班级名称
    private String status;

    //一对多关联
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private List<Course> courses;
}
