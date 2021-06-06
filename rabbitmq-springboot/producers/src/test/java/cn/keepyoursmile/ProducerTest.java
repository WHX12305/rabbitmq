package cn.keepyoursmile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 测试producers
 * </p>
 *
 * @author whx
 * @date 2021/6/2 22:09
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    //注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend("boot_topic_exchange", "boot.test", "springboot go!");
    }
}