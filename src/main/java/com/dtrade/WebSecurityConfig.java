package com.dtrade;

import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                 authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/accounts/register").permitAll()
                .antMatchers("/bower_components/**").permitAll()
                .antMatchers("/content/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/account/**").permitAll()
                .antMatchers("/balance-activity/**").authenticated()
                .antMatchers("/customer/**").authenticated()//remove?
                .antMatchers("/diamond/available").permitAll()
                .antMatchers("/diamond/**").denyAll()//remove?
                .antMatchers("/quote/**").permitAll()
                .antMatchers("/stock/**").authenticated()
                .antMatchers("/trade-order/history-orders").permitAll()
                .antMatchers("/trade-order/**").authenticated()
                .anyRequest().permitAll()
                .and().formLogin().defaultSuccessUrl("/")
                .loginPage("/login").permitAll()
                .failureForwardUrl("/login-page?error=fail")
                .and().logout().permitAll().logoutSuccessUrl("/");
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder() throws Exception {
        return new Md5PasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AccountService accountService, AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }
}
