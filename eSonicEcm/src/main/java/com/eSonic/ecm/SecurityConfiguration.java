package com.eSonic.ecm;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.DispatcherType;
/**
 * An example of explicitly configuring Spring Security with the defaults.
 *
 * @author Rob Winch
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
	
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    AntPathRequestMatcher interfaceMatcher = new AntPathRequestMatcher("/interface/**");
	    AntPathRequestMatcher authJoinMatcher = new AntPathRequestMatcher("/auth/join");
	    AntPathRequestMatcher styleMatcher = new AntPathRequestMatcher("/css/**");
	    AntPathRequestMatcher jsMatcher = new AntPathRequestMatcher("/js/**");
	    

	    http.csrf().disable().cors().disable()
	        .authorizeHttpRequests(request -> request
	            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
	            .requestMatchers(interfaceMatcher, authJoinMatcher, styleMatcher, jsMatcher).permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .loginProcessingUrl("/login-process")
	            .usernameParameter("userid")
	            .passwordParameter("pw")
	            .defaultSuccessUrl("/main", true)
	            .permitAll()
	        )
	        .logout(withDefaults());

	    return http.build();
	}
	
	// @formatter:off
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		@SuppressWarnings("deprecation")
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("rosis")
				.password("1")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	// @formatter:on
}