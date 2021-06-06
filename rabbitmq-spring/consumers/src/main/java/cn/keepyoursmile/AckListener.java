package cn.keepyoursmile;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * <p>
 * rabitmq消息确认机制
 * Consumer ack机制
 * 1.设置手动签收 acknowledge="manual"
 * 2.监听器实现ChannelAwareMessageListener接口
 * 3.消息成功处理，使用channel的basicAckS()确认签收
 * 4.消息处理失败，使用channel的basicNAck(),broker重新发送给consumer
 * </p>
 *
 * @author whx
 * @date 2021/6/5 14:59
 * @since 1.0
 */
public class AckListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            System.out.println("ack failed!");
            channel.basicNack(deliveryTag, true, true);
        }
    }
}
