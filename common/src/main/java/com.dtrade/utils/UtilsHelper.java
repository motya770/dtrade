package com.dtrade.utils;

import com.dtrade.exception.TradeException;

/**
 * Created by matvei on 4/6/15.
 */
public class UtilsHelper {

    public static boolean isValidEmailAddress(String email) {
        try {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            return m.matches();
        }catch (Exception e){
            e.printStackTrace();
            throw new TradeException("Mail not valid");
        }
    }
}
