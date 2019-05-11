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

        http.requiresChannel()
                .antMatchers("/login").requiresSecure();
       // http.authorizeRequests().antMatchers("/**").permitAll();


        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/accounts/register").permitAll()
                .antMatchers("/bower_components/**").permitAll()
                .antMatchers("/content/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/account/**").authenticated()
                .antMatchers("/balance-activity/**").authenticated()
                .antMatchers("/customer/**").authenticated()//remove?
                .antMatchers("/diamond/available").permitAll()
                .antMatchers("/diamond/by-id").permitAll()
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
                .and().formLogin().defaultSuccessUrl("/trade#!/basic")
                .loginPage("/trade#!/login-form").permitAll()
                .loginProcessingUrl("/login")
                //.failureUrl("/trade#!/login-form")
                .failureHandler(new SimpleUrlAuthenticationFailureHandler("/trade#!/login-form"))
                //.failureForwardUrl("/trade#!/login-form")
                .and().logout().permitAll().logoutSuccessUrl("/trade");


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
