package com.dtrade.model.account;

import com.dtrade.model.diamond.Diamond;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * C
 * zreated by kudelin on 8/24/16.
 */
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

    public Account(String mail, String password){
        super(mail, password, Arrays.asList(ROLE_ACCOUNT));
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Diamond> getOwnedDiamonds() {
        return ownedDiamonds;
    }

    public void setOwnedDiamonds(List<Diamond> ownedDiamonds) {
        this.ownedDiamonds = ownedDiamonds;
    }
}
