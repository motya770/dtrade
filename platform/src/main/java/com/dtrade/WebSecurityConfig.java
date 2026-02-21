package com.dtrade;

import com.dtrade.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Created by kudelin on 8/24/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/accounts/register").permitAll()
                        .requestMatchers("/bower_components/**").permitAll()
                        .requestMatchers("/content/**").permitAll()
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers("/account/**").authenticated()
                        .requestMatchers("/balance-activity/**").authenticated()
                        .requestMatchers("/customer/**").authenticated()
                        .requestMatchers("/diamond/available").permitAll()
                        .requestMatchers("/diamond/by-id").permitAll()
                        .requestMatchers("/diamond/**").denyAll()
                        .requestMatchers("/quote/**").permitAll()
                        .requestMatchers("/graph/**").permitAll()
                        .requestMatchers("/book-order/**").permitAll()
                        .requestMatchers("/stock/**").authenticated()
                        .requestMatchers("/trade-order/get-quotes").permitAll()
                        .requestMatchers("/trade-order/history-orders").permitAll()
                        .requestMatchers("/trade-order/**").authenticated()
                        .requestMatchers("/trade-order/").authenticated()
                        .requestMatchers("/coin-payment/notify").permitAll()
                        .requestMatchers("/coin-payment/**").authenticated()
                        .requestMatchers("/ico/**").permitAll()
                        .requestMatchers("/theme/**").permitAll()
                        .requestMatchers("/trade").permitAll()
                        .requestMatchers("/diamonds").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/**").permitAll()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/trade#!/basic")
                        .loginPage("/trade#!/login-form").permitAll()
                        .loginProcessingUrl("/login")
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/trade#!/login-form"))
                )
                .logout(logout -> logout.permitAll().logoutSuccessUrl("/trade"))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
