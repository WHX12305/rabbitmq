package cn.keepyoursmile;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * <p>
 * Consumer限流机制
 * 1.确保Ack机制为手动确认
 * 2.listener-container配置属性
 *  perfetch = 1，标识消费端每次拉取一条消息进行消费,知道手动确认消费完毕才继续拉取下一条消息
 * </p>
 *
 * @author whx
 * @date 2021/6/6 14:39
 * @since 1.0
 */
public class QosListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取消息
        System.out.println(new String(message.getBody()));

        //签收
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
