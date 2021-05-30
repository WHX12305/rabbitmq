package cn.keepyoursmile.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * 生产者
 * </p>
 *
 * @author whx
 * @date 2021/5/5 0:24
 * @since 1.0
 */
public class ProducerClient {
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
        channel.queueDeclare("hello", true, false, false, null);
        //6.发送消息
        // String exchange, 交换机
        // String routingKey, 路由
        // BasicProperties props, 配置
        // byte[] body, 消息体
        channel.basicPublish("", "hello", null, "hello world !!!".getBytes());
        //7.释放资源
        channel.close();
        connection.close();
    }
}
