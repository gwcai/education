package com.genius.coder.education.user.controller;

import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.base.controller.VueController;
import com.genius.coder.education.redis.RedisService;
import com.genius.coder.education.user.domain.AdminUser;
import com.genius.coder.education.user.form.AdminUserForm;
import com.genius.coder.education.user.form.UserPhoneForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("user")
@Api(tags = "系统用户接口")
public class AdminUserController extends VueController<AdminUser,String> {

    private final RedisService redisService;

    public AdminUserController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("info/{id}")
    @ApiOperation("根据用户ID获取用户信息")
    public AdminUserForm getUserById(@PathVariable String id) {
        return getForm(service.findOneOrElseThrow(id));
    }


    @PostMapping("bindPhone")
    public BaseDataResponse bindPhone(@RequestBody UserPhoneForm userPhoneForm) {
        String code = redisService.get(userPhoneForm.getPhoneNum());
        if (code == null) {
            return BaseDataResponse.fail().msg("验证码已失效!");
        }
        if (!Objects.equals(userPhoneForm.getVerificationCode(), code)) {
            return BaseDataResponse.fail().msg("验证码错误!");
        }
        AdminUser adminUser = service.findOneOrElseThrow(userPhoneForm.getUserId());
        adminUser.setPhoneNum(userPhoneForm.getPhoneNum());
        service.save(adminUser);
        return BaseDataResponse.ok().msg("绑定手机成功!");
    }

    @Override
    protected AdminUserForm getForm(AdminUser adminUser) {
        return new AdminUserForm();
    }
}
