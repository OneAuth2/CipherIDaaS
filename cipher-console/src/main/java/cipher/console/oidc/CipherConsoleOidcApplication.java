package cipher.console.oidc;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("cipher.console.oidc.mapper")
@ServletComponentScan
@EnableTransactionManagement
@Configuration
//@ImportResource({"classpath:config/applicationContext-service.xml"})
public class CipherConsoleOidcApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(CipherConsoleOidcApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CipherConsoleOidcApplication.class);
	}
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory(); // 单个数据大小
		factory.setMaxFileSize("10MB");// KB,MB
		// 总上传数据大小
		factory.setMaxRequestSize("100MB");
		factory.setLocation("/data/tmp");
		return factory.createMultipartConfig();
	}

}
