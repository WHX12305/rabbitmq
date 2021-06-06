package cn.keepyoursmile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 生产者启动类
 * </p>
 *
 * @author whx
 * @date 2021/6/2 21:54
 * @since 1.0
 */
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class ,args);
    }
}
