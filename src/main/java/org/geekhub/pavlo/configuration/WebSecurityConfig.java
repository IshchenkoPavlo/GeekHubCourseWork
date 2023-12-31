package org.geekhub.pavlo.configuration;

import org.geekhub.pavlo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/purchase-invoice/**", "/api/v*/purchase-invoice/**").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/upload-goods/**", "/goods/upload-csv/**").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/admin-panel/**", "/user-add/**", "/user-edit/**", "/api/v*/users/**").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/api/v*/user-registration/**", "/error-page/**").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }

    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder auth,
            JdbcTemplate template
    ) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(template.getDataSource())
                .usersByUsernameQuery("SELECT name AS username, password, 'TRUE' as enabled FROM users WHERE name = ?")
                .authoritiesByUsernameQuery("SELECT name AS username, role AS authority FROM users WHERE name = ?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}