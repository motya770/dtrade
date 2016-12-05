package com.dtrade.model.account;

import lombok.Data;

/**
 * Created by kudelin on 11/6/16.
 */
@Data
public class RegistrationAccount {


    private String email;

    private String pwd;

    private String phone;

    private String captcha;

//    @RequestBody
//    String email,
//    @RequestParam
//    String pwd,
//    @RequestParam String phone,
//    @RequestParam(value = "g-recaptcha-response", required = false) String recaptchaResponse,
}
