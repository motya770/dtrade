package com.dtrade.model.account;

import com.dtrade.model.balance.Balance;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * C
 * zreated by kudelin on 8/24/16.
 */

@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"mail"}))
public class Account implements UserDetails {

    public static final String F_ROLE_ACCOUNT = "ROLE_ACCOUNT";
    public static final String F_ROLE_ADMIN = "ROLE_ADMIN";

    public static final String F_MAIL = "login";
    public static final String F_CONFIRMED = "confirmed";
    public static final String F_GUID = "guid";
    public static final String F_AUTHORITY = "authority";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Diamond> ownedDiamonds;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Stock> stocks;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<BalanceActivity> balanceActivities;

    private String mail;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private String recoveryGuid;

    private String guid;

    private boolean confirmed;

    private boolean canceled;

    private boolean blocked;

    @Column(columnDefinition = "boolean default false")
    private boolean roboAccount;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Balance> balances;

    private String referral;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int referredCount;

    private String phone;

    @JsonIgnore
    private String role;

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
        return Arrays.asList(new SimpleGrantedAuthority(role));
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
       return enabled;
    }

    public boolean isAdmin(){
        return this.getAuthorities().stream()
                .filter(ga ->
                        ga.getAuthority().equals(F_ROLE_ADMIN)
                    ).findFirst().isPresent();
    }

    @Override
    public String toString(){
        return " account : " + id;
    }
}
