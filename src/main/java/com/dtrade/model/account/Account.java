package com.dtrade.model.account;

import com.dtrade.model.diamond.Diamond;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * C
 * zreated by kudelin on 8/24/16.
 */
@Data
@Entity
public class Account extends User {

    private static SimpleGrantedAuthority ROLE_ACCOUNT = new SimpleGrantedAuthority("ROLE_ACCOUNT");
    private static SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Diamond> ownedDiamonds;

    private String mail;

    private String password;

    private boolean enabled;

    public Account(String mail, String password){
        super(mail, password, Arrays.asList(ROLE_ACCOUNT));
    }

}
