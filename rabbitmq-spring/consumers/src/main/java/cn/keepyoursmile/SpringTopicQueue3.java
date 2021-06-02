package cn.keepyoursmile;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * <p>
 * spring_topic_queue_3监听器
 * </p>
 *
 * @author whx
 * @date 2021/5/31 11:54
 * @since 1.0
 */
public class SpringTopicQueue3 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("spring_topic_queue_3队列监听器：" + new String(message.getBody()));
    }
}
