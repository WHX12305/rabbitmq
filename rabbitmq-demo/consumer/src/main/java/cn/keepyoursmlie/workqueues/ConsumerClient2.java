package cn.keepyoursmlie.workqueues;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * 消费者
 * </p>
 *
 * @author whx
 * @date 2021/5/5 12:37
 * @since 1.0
 */
public class ConsumerClient2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2.设置参数（用户名/密码...）
        connectionFactory.setHost("192.168.35.151");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("hello");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        //3.创建Connection
        Connection connection = connectionFactory.newConnection();
        //4.创建channel
        Channel channel = connection.createChannel();
        //5.创建队列
        // String queue, 队列名称
        // boolean durable, 是否持久化
        // boolean exclusive,  是否独占（只能由一个消费者监听队列），Connection 关闭是否删除队列
        // boolean autoDelete, 是否自动删除 没有consumer自动删除
        // Map<String, Object> arguments 参数
        //没有该队列时才会创建
        channel.queueDeclare("work_queues", true, false, false, null);
        // String queue, 队列名称
        // boolean autoAck, 是否自动确认
        // Consumer callback 回调对象
        Consumer consumer = new DefaultConsumer(channel){

            /**
             * 收到消息后执行的回调方法
             *
             * @param consumerTag 标识
             * @param envelope 信息，交换机  路由key
             * @param properties 配置信息
             * @param body 数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag："+consumerTag);
                System.out.println("exchange："+envelope.getExchange());
                System.out.println("routingKey："+envelope.getRoutingKey());
                System.out.println("properties："+properties);
                System.out.println("body："+new String(body));
            }
        };
        channel.basicConsume("hello", true, consumer);
    }
}
