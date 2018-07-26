package com.dtrade;

import com.dtrade.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * Created by kudelin on 8/24/16.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //TODO change
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPeriod(true);
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowBackSlash(true);
        firewall.setAllowSemicolon(true);

        return firewall;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

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
                .frameOptions().disable();


    }




    //TODO migrate
    @Bean
    public PasswordEncoder passwordEncoder() throws Exception {
        DelegatingPasswordEncoder passwordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        passwordEncoder.setDefaultPasswordEncoderForMatches(new MessageDigestPasswordEncoder("MD5"));
        return passwordEncoder;

    }

    @Autowired
    public void configureGlobal(AccountService accountService, AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }
}
