package com.ttn.project2.config;

import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    public MySecurityConfig() {
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder);
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new WebUserDetailsService(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                .antMatchers("/users/register/**", "/users/**", "/api/v*/registration/**", "/customer").permitAll()
                //.antMatchers("/get/**").hasAnyAuthority("ADMIN","CUSTOMER","SELLER")
                .antMatchers("/get/**").permitAll()
                .antMatchers("/confirm/**").permitAll()
                .antMatchers("/forgot/**", "/reset/**").permitAll()
                .antMatchers("/activate/**").permitAll()
                .antMatchers("/deactivate/**").permitAll()
                .antMatchers("/seller/profile","/customer/profile").permitAll()
                .antMatchers("/seller/**","/customer/**").permitAll()
                .antMatchers("/sellers","/customers").permitAll()
                .antMatchers("/add/**","/view/**").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .formLogin()

                //.usernameParameter("username").passwordParameter("password").permitAll()

                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                .sessionManagement()
              .and()
                   .csrf().disable();

    }
}

/*  @Bean
  public BCryptPasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
 }

 @Bean
 UserDetailsService getUserDetailsService(){
      return new UserDetailsServiceImpl();
  }

  @Bean
 public DaoAuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
     daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       return daoAuthenticationProvider;
   }*/


    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("aartijha@gmail.com").password("1234").authorities("ROLE_ADMIN");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, enabled FROM user WHERE email=?")
                .authoritiesByUsernameQuery("SELECT email, authority FROM role WHERE email=?");

    }*/