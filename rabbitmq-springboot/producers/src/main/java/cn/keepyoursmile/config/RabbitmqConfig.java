package cn.keepyoursmile.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * mq config配置
 * </p>
 *
 * @author whx
 * @date 2021/6/2 21:56
 * @since 1.0
 */
@Configuration
public class RabbitmqConfig {

    /**
     * 交换机
     */
    @Bean("bootExchange")
    public Exchange bootExchange(){
        return ExchangeBuilder.topicExchange("boot_topic_exchange").durable(true).build();
    }

    /**
     * Queue队列
     */
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable("bootQueue").build();
    }

    /**
     * 队列和交换机绑定
     */
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue")Queue queue, @Qualifier("bootExchange")Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
