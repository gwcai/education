package com.genius.coder.education.user.form;

import lombok.Data;

@Data
public class UserPhoneForm {

    private String userId;

    private String phoneNum;

    private String verificationCode;
}
