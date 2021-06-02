package cn.keepyoursmile;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 队列监听器
 * </p>
 *
 * @author whx
 * @date 2021/6/2 22:39
 * @since 1.0
 */
@Component
public class RabbitmqListener {

    @RabbitListener(queues = "bootQueue")
    public void listenerQueue(Message message){
        System.out.println("消息："+message);
    }
}
