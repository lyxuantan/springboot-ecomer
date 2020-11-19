package vn.ecomer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@SpringBootApplication(scanBasePackages = "vn")
@EnableAutoConfiguration(exclude = { 
	SecurityAutoConfiguration.class, 
	HibernateJpaAutoConfiguration.class 
})
@EnableJpaRepositories("vn.repositorys")
public class EcomerApplication {

	public static void main(String... args) {
		SpringApplication.run(EcomerApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(
			@Value("${application-description}") String appDesciption,
			@Value("${application-version}") String appVersion) {
		return new OpenAPI().info(new Info().title("sample application API").version(appVersion)
			.description(appDesciption).termsOfService("http://swagger.io/terms/")
			.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
	
	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID"); 
		serializer.setCookiePath("/"); 
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
		return serializer;
	}
}