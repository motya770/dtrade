package com.dtrade;

import com.dtrade.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by kudelin on 8/24/16.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //TODO investigate
        http
                .csrf().disable().
                 authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/accounts/register").permitAll()
                .antMatchers("/bower_components/**").permitAll()
                .antMatchers("/content/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/account/**").authenticated()
                .antMatchers("/balance-activity/**").authenticated()
                .antMatchers("/customer/**").authenticated()//remove?
                .antMatchers("/diamond/available").permitAll()
                .antMatchers("/diamond/**").denyAll()//remove?
                .antMatchers("/quote/**").permitAll()
                .antMatchers("/graph/**").permitAll()
                .antMatchers("/book-order/**").permitAll()
                .antMatchers("/stock/**").authenticated()
                .antMatchers("/trade-order/get-quotes").permitAll()
                .antMatchers("/trade-order/history-orders").permitAll()
                .antMatchers("/trade-order/**").authenticated()
                .antMatchers("/trade-order/").authenticated()
                .antMatchers("/coin-payment/notify").permitAll()
                .antMatchers("/coin-payment/**").authenticated()
                .antMatchers("/ico/**").permitAll()
                .antMatchers("/theme/**").permitAll()
                .antMatchers("/trade").permitAll()
                .antMatchers("/diamonds").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/**").permitAll()
                .and().formLogin().defaultSuccessUrl("/trade")
                .loginPage("/login-page").permitAll()
                .loginProcessingUrl("/login")
                .failureForwardUrl("/login-page?error=fail")
                .and().logout().permitAll().logoutSuccessUrl("/trade");

        http.headers()
                .frameOptions()
                .sameOrigin();
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
