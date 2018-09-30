package com.dtrade.service;

/**
 * Created by matvei on 4/13/15.
 */
public interface ITuringService {

    void check(String captcha, String remoteIp) throws Exception;

}
