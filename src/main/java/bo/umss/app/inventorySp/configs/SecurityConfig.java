package bo.umss.app.inventorySp.configs;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import bo.umss.app.inventorySp.configs.web.Cors;
import bo.umss.app.inventorySp.configs.web.WebProperties;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() // Disable CSRF only if you're not using session cookies
				.authorizeHttpRequests(auth -> auth.antMatchers("/v1/**").permitAll() // Allow public access to /api/**
						.anyRequest().authenticated() // All other requests need authentication
				);

		return http.build();
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> cors(WebProperties props) {
		Cors cors = props.getCors();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList(cors.getOrigins()));
		config.setAllowedHeaders(Arrays.asList(cors.getHeaders()));
		config.setAllowedMethods(Arrays.asList(cors.getMethods()));
		config.setExposedHeaders(Arrays.asList("Location"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}
}
