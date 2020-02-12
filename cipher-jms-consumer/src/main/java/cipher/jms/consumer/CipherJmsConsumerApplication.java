package cipher.jms.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("cipher.jms.consumer.mapper")
@ServletComponentScan
public class CipherJmsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CipherJmsConsumerApplication.class, args);
    }

}

