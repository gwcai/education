package com.genius.coder.education.user.form;

import com.genius.coder.base.form.BaseForm;
import com.genius.coder.education.user.domain.Permission;
import lombok.Data;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/12
 */
@Data
public class PermissionForm extends BaseForm<Permission,String> {
    private String name;
    private String zhName;
}
