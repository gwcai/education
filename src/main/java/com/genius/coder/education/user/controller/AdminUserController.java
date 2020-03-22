package com.genius.coder.education.user.controller;

import com.genius.coder.base.controller.VueController;
import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.education.auth.verifycode.VerificationCode;
import com.genius.coder.education.redis.RedisService;
import com.genius.coder.education.user.domain.AdminUser;
import com.genius.coder.education.user.form.AdminUserDetail;
import com.genius.coder.education.user.form.AdminUserForm;
import com.genius.coder.education.user.form.UserPhoneForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Api(tags = "系统用户接口")
public class AdminUserController extends VueController<AdminUser,String> {
    @Autowired
    private TokenStore tokenStore;

    private final RedisService redisService;

    public AdminUserController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpSession session, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream());
    }

    @GetMapping("info")
    @ApiOperation("根据用户token获取用户信息")
    public AdminUserDetail getUserById() {
        AdminUserDetail userDetail = (AdminUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDetail.setPassword(null);
        return userDetail;
    }

    /***
     * 微信端绑定手机号码
     * @param userPhoneForm
     * @return
     */
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
        return new AdminUserForm().toForm(adminUser);
    }
}
