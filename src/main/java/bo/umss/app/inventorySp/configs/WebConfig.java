package bo.umss.app.inventorySp.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import bo.umss.app.inventorySp.configs.web.WebProperties;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private WebProperties webProperties;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(webProperties.getCors().getOrigins())
				.allowedMethods(webProperties.getCors().getMethods())
				.allowedHeaders(webProperties.getCors().getHeaders());
	}
}
