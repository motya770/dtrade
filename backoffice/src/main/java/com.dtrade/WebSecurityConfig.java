package com.dtrade;

import com.dtrade.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Created by kudelin on 8/24/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests().antMatchers("/**").hasRole("ADMIN")
                .and().httpBasic();

//        http
//                .csrf().disable()
//                .httpBasic()
//                .and()
//                .authorizeRequests()
//                .anyRequest().authenticated();

        //TODO investigate
       /* http
                .csrf().disable().
                 authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and().formLogin().defaultSuccessUrl("/trade")
                .loginPage("/trade#!/login-form").permitAll()
                .loginProcessingUrl("/login")
                //.failureUrl("/trade#!/login-form")
                .failureHandler(new SimpleUrlAuthenticationFailureHandler("/trade#!/login-form"))
                //.failureForwardUrl("/trade#!/login-form")
                .and().logout().permitAll().logoutSuccessUrl("/trade");*/

        http.headers()
                .frameOptions().disable();


    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AccountService accountService, AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
    }
}
