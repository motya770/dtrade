package com.dtrade.model.account;

import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.diamond.Diamond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * C
 * zreated by kudelin on 8/24/16.
 */
@Data
@Entity
public class Account implements UserDetails {

    private static SimpleGrantedAuthority ROLE_ACCOUNT = new SimpleGrantedAuthority("ROLE_ACCOUNT");
    private static SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    public static final String F_MAIL = "login";
    public static final String F_CONFIRMED = "confirmed";
    public static final String F_GUID = "guid";
    public static final String F_AUTHORITY = "authority";

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Diamond> ownedDiamonds;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<BalanceActivity> balanceActivities;

    private String mail;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private String guid;

    private boolean confirmed;

    private boolean canceled;

    private boolean blocked;

    private BigDecimal balance;

    private String phone;

    public Account(){

    }


    public Account(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Arrays.asList(ROLE_ACCOUNT);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled(){
        //TODO see enabled field
        return true;
    }

    @Override
    public String toString(){
        return " account : " + id;
    }
}
