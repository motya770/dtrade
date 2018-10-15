package com.dtrade;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by kudelin on 8/24/16.
 */
@Configuration
@EnableWebSecurity
public class SimulatorWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().
                disable().
            authorizeRequests().
                antMatchers("/**").permitAll();

    }
}
