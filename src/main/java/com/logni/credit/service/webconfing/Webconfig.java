package com.logni.credit.service.webconfing;

import com.logni.credit.service.authentication.AuthEntryPointJwt;
import com.logni.credit.service.authentication.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@EnableWebMvc
public class Webconfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/product/**").hasAnyRole("CHECKER", "MAKER")
				.antMatchers(HttpMethod.GET, "/api/application/**").hasAnyRole("CHECKER", "MAKER")
				.antMatchers(HttpMethod.GET, "/api/loan/**").hasAnyRole("CHECKER", "MAKER")
				.antMatchers(HttpMethod.POST, "/api/loan/**").hasAnyRole("CHECKER", "MAKER")
				.antMatchers(HttpMethod.POST, "/api/loan-repayment/**").hasAnyRole("CHECKER", "MAKER")
				.antMatchers(HttpMethod.GET, "/api/loan-repayment/**").hasAnyRole("CHECKER", "MAKER")
				.antMatchers(HttpMethod.POST,"/api/product").hasAnyRole("MAKER")
				.antMatchers(HttpMethod.DELETE,"/api/product/**").hasAnyRole("MAKER")
				.antMatchers(HttpMethod.POST,"/api/application").hasAnyRole("MAKER")
				.anyRequest().authenticated();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public TokenFilter authenticationJwtTokenFilter() {
		return new TokenFilter();
	}
}
