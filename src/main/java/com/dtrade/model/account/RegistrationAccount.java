package com.dtrade.model.account;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
