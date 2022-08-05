package com.example.northwindwebapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableWebSecurity
@Configuration
public class NorthwindConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("Marc").password("{noop}password1").authorities("USER");
        auth.inMemoryAuthentication().withUser("Michael").password("{noop}password1").authorities("ADMIN");
        auth.inMemoryAuthentication().withUser("Jeffrey").password("{noop}password1").authorities("USER");
        auth.inMemoryAuthentication().withUser("Omar").password("{noop}password1").authorities("USER");
        auth.inMemoryAuthentication().withUser("Ray").password("{noop}password1").authorities("USER");
        auth.inMemoryAuthentication().withUser("Mustafa").password("{noop}password1").authorities("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/home", "/shop").permitAll()
                .antMatchers("/shop/**", "/logout").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/home", true).permitAll().and().exceptionHandling().accessDeniedPage("/accessDenied")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/home").clearAuthentication(true).deleteCookies();
    }

//      New method to do authentication
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
//        http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/customers/**","/orders/**","/products/**","/orderDetails/**","/shippers/**").hasAuthority("ADMIN").anyRequest().authenticated().and
//
//    }


}
