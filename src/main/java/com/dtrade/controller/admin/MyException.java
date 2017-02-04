package com.dtrade.controller.admin;

import lombok.Data;

/**
 * Created by kudelin on 1/9/17.
 */

@Data
public class MyException extends Exception {

    private ExceptionClass exceptionClass = new ExceptionClass();
}
