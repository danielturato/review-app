package com.danielturato.reviewapi.core;

import com.danielturato.reviewapi.model.Account;
import com.danielturato.reviewapi.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;


@EnableWebSecurity
@Configuration
public class ApiSecurity extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;

    private final AccountRepository accountRepository;

    public ApiSecurity(JwtRequestFilter jwtRequestFilter, AccountRepository accountRepository) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/accounts/auth").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/accounts").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            Optional<Account> optAccount =
                    accountRepository.findAccountByEmail(email);

            if (optAccount.isEmpty()) {
                throw new UsernameNotFoundException("No user with the email: " + email);
            }
            Account account = optAccount.get();
            return new
                    User(account.getEmail(), account.getPassword(),
                         AuthorityUtils.createAuthorityList("ROLE_USER"));
        });
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
