package com.genius.coder.education.user.form;

import com.genius.coder.base.form.BaseForm;
import com.genius.coder.education.user.domain.Role;
import lombok.Data;

import java.util.List;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/12
 */
@Data
public class RoleForm extends BaseForm<Role,String> {
    private String name;
    private String zhName;

    private List<PermissionForm> permissions;
    private List<AdminUserForm> users;
}
