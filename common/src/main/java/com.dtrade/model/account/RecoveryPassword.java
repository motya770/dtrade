package com.dtrade.model.account;

import lombok.Data;

@Data
public class RecoveryPassword {

    private String recoveryGuid;
    private String pwd;
}
