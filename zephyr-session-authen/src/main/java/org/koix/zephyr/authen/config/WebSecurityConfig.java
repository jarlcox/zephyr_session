package org.koix.zephyr.authen.config;

import org.koix.zephyr.authen.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true, proxyTargetClass=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Autowired
    //private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
        	.authorizeRequests().antMatchers("/oauth/**","/login/**", "/logout").permitAll().anyRequest().authenticated()
        .and()
        	.formLogin().loginPage("/login")
        .and()
        	.logout().logoutSuccessUrl("/");*/
    	http
    		.authorizeRequests().antMatchers("/oauth/**","/login/**", "/logout").permitAll().anyRequest().authenticated()
    	.and()
    		.formLogin().loginPage("/login")
    	.and()
    		.csrf().disable().cors();
    		//.csrf().and().cors();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }*/
    
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception
    {
        //设置用户验证逻辑
        auth.authenticationProvider(this.authenticationProvider);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        //Web安全验证（默认）
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
