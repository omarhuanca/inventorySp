package bo.umss.app.inventorySp.configs.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:web.properties")
@ConfigurationProperties(prefix = "web")
public class WebProperties {
	private Cors cors;

	public Cors getCors() {
		return cors;
	}

	public void setCors(Cors cors) {
		this.cors = cors;
	}
}
