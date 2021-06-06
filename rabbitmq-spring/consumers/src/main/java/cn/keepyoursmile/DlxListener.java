package cn.keepyoursmile;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * <p>
 * 死信队列拒收测试
 * </p>
 *
 * @author whx
 * @date 2021/6/6 16:04
 * @since 1.0
 */
public class DlxListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            int i = 3/0;
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            System.out.println("dlx ack failed!");
            channel.basicNack(deliveryTag, true, false);
        }
    }
}
