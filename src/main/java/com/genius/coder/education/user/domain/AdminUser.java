package com.genius.coder.education.user.domain;

import com.genius.coder.education.user.enums.StatusEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 * 主键使用微信UnionId
 */
@Entity(name = "admin_user")
@Comment("用户表")
@Data
public class AdminUser implements Persistable<String>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Comment("你懂的")
    private String password;

    @Comment("姓名")
    private String name;

    @Comment("手机号，目前手机号当做登录名")
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    @Comment("账号状态")
    private StatusEnum status = StatusEnum.ENABLE;

    @Comment("微信unionid")
    private String unionid;

    @Comment("微信用户openid")
    private String openid;

    @Comment("性别")
    private Integer sex;

    @Comment("头像路径")
    private String headimgurl;

    @CreatedDate
    @Comment("注册时间")
    @Column(updatable = false)
    private LocalDateTime createdDate;


    @Override
    public boolean isNew() {
        return StringUtils.isBlank(id);
    }
}
