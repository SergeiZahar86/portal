package com.indas.portal.configure;//package com.indas.portal.configure;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//@Configuration
//public class MyWebInitializer extends
//        AbstractAnnotationConfigDispatcherServletInitializer {
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return null;
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//
//        return new Class[]{WebConfig.class};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//
//        return new String[]{"/*","/box.html"};
//    }
//}

import com.indas.portal.security.UserDetailsServiceImpl;
import com.indas.portal.security.encoder.Md5;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private Md5 md5;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/vendor/**", "/fonts/**", "/images/**").permitAll()
//                .antMatchers("/catalogs/**").hasAnyRole("portalAdmin")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/security_check")
                .loginPage("/login")
                .failureUrl("/login?err=1")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll().defaultSuccessUrl("/tablePart", true)
//                .exceptionHandling().authenticationEntryPoint(new ICAuthEntryPoint("/login")).accessDeniedPage("/errorpg")
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence charSequence) {
                return md5.hesh(charSequence.toString());
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return md5.hesh(charSequence.toString()).toUpperCase().equals(s);
            }
        };
    }
}
