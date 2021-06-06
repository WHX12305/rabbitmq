package cn.keepyoursmile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 *
 * </p>
 *
 * @author whx
 * @date 2021/5/31 9:08
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testHelloWorld() {
        rabbitTemplate.convertAndSend("spring_queue", "helloWorld Spring");
    }

    @Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "helloFanout Spring");
    }

    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("spring_topic_exchange", "hello.queue1", "helloTopic 1 Spring");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "hello.topic.queue2", "helloTopic 2 Spring");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "smile.topic.queue3", "helloTopic 3 Spring");
    }

    /**
     * ============================================================================================
     * 确认模式
     * 1.ConnectionFactory中开启publisher-confirms="true"
     * 2.rabbitTemplate定义ConfirmCallBack回调函数
     */
    @Test
    public void testConfirm() {
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            System.out.println("confirm执行...");
            if (b) {
                System.out.println("success.");
            } else {
                System.out.println("fail.");
            }
        });
        rabbitTemplate.convertAndSend("confirm_exchange", "confirm_queue", "confirm message!");
    }

    /**
     * ============================================================================================
     * 回退模式
     * 1.ConnectionFactory中开启回退模式publisher-returns="true"
     * 2.rabbitTemplate定义ReturnCallBack回调函数
     * 3.设置Exchange处理消息的模式
     * 1.如果消息没有路由到Queue，则丢弃消息（默认）
     * 2.如果没有路由到Queue,则返回给消息发送方ReturnCallback
     */
    @Test
    public void testReturn() {

        //设置交换机处理失败消息的模式
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("return...");
        });
        rabbitTemplate.convertAndSend("confirm_exchange", "confirm", "confirm message!");
    }

    /**
     * ============================================================================================
     * 限流
     */
    @Test
    public void testQos() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("qos_exchange", "qos_queue", "qos message!" + i);
        }
    }

    /**
     * ============================================================================================
     * TTL
     *
     * 消息单独过期
     * 消息过期时间和队列过期时间同时存在已时间短的为准
     * 消息过期后只有消息在队列顶端，才会判断是否过期
     */
    @Test
    public void testTtl() {
        rabbitTemplate.convertAndSend("ttl_exchange", "ttl.hello", "ttl message!");
        rabbitTemplate.convertAndSend("ttl_exchange", "ttl.world", "ttl message five!", message -> {
            //消息后处理对象，可以设置消息的参数信息
            //设置5s过期
            message.getMessageProperties().setExpiration("5000");
            return message;
        });
    }

    /**
     * ============================================================================================
     * 死信队列
     * 1.过期时间
     * 2.长度限制
     * 3.消息拒收
     */
    @Test
    public void testDead() {
        rabbitTemplate.convertAndSend("dlx_exchange_normal", "dlx.normal.hello", "dlx normal message!");
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend("dlx_exchange_normal", "dlx.normal.hello", "dlx normal message!" + i);
//        }
    }
}
