package cn.keepyoursmile;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * <p>
 * spring_fanout_queue_2监听器
 * </p>
 *
 * @author whx
 * @date 2021/5/31 11:52
 * @since 1.0
 */
public class SpringFanoutQueue2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("spring_fanout_queue_2队列监听器：" + new String(message.getBody()));
    }
}
