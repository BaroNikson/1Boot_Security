package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll() // Разрешаем доступ к главной странице без аутентификации
                .antMatchers("/admin").hasRole("ADMIN") // Доступ только для пользователей с ролью "ADMIN"
                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                .and()
                .formLogin().successHandler(successUserHandler) // Включаем форму входа
                .permitAll()
                .and()
                .logout() // Включаем поддержку выхода из системы
                .logoutUrl("/logout") // Устанавливаем URL для выхода из системы
                .logoutSuccessUrl("/login") .permitAll(); // Устанавливаем URL, на который будет перенаправлен пользователь после успешного выхода
               // .invalidateHttpSession(true) // Недействительность HTTP-сессии при выходе
              //  .deleteCookies("JSESSIONID") // Удаление cookie при выходе
                // Разрешаем доступ к странице выхода без аутентификации
    }



    // аутентификация inMemory
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
