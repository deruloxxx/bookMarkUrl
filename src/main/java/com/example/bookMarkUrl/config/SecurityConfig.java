package com.example.bookMarkUrl.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .loginProcessingUrl("/user/login") // フォーム認証の設定記述開始
        .loginPage("/user/login") // ユーザー名・パスワードの送信先URL
        .failureUrl("/user/login?error") // ログイン失敗後のリダイレクト先URL
        .usernameParameter("userId")
        .passwordParameter("password")
        .defaultSuccessUrl("/user/url", true) // ログイン成功後のリダイレクト先URL
      )
      .logout(logout -> logout
        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutUrl("/user/logout")
        .logoutSuccessUrl("/user/login?logout")
      )
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .requestMatchers("/user/login").permitAll()
        .requestMatchers("/user/signup").permitAll()
        .requestMatchers("/").permitAll()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
        .anyRequest().authenticated()
      )
      .headers(headers -> headers.disable())
      .csrf(csrf -> csrf
        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
      );
    return http.build();
  }
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User
      .withUsername("user")
      .password(passwordEncoder().encode("user"))
      .roles("USER")
      .build();
    return new InMemoryUserDetailsManager(user);
  }
}
