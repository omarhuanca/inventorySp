package bo.umss.app.inventorySp.configs;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import bo.umss.app.inventorySp.configs.web.Cors;
import bo.umss.app.inventorySp.configs.web.WebProperties;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
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
