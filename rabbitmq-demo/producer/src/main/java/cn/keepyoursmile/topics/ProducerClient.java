package cn.keepyoursmile.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        //4.创建Channel
        Channel channel = connection.createChannel();
        //5.创建交换机
        // exchange:交换机名称
        // type:交换机类型
        //      DIRECT("direct") 定向
        //      FANOUT("fanout") 扇形（广播），发送消息到每个与之绑定的队列
        //      TOPIC("topic") 通配符的方式
        //      HEADERS("headers") 参数匹配
        // durable:是否持久化
        // autoDelete:是否自动删除
        // internal:内部使用。一般false
        // arguments: 参数
        String exchangeName = "test_topic";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, false, null);
        //6.创建队列
        String queueName1 = "test_topic_queue1";
        String queueName2 = "test_topic_queue2";
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueDeclare(queueName2, true, false, false, null);
        //7.绑定队列
        // queue:队列名
        // exchange:交换机名称
        // routingKey:路由键，绑定规则
        //      类型为fanout，routingKey为“”
        channel.queueBind(queueName1, exchangeName, "#.error");
        channel.queueBind(queueName2, exchangeName, "*.*");
        channel.queueBind(queueName1, exchangeName, "order.*");
        //8.发送消息
        String body = "Topic的形式发送消息";
        channel.basicPublish(exchangeName, "goods.info", null, body.getBytes(StandardCharsets.UTF_8) );
        //9.释放资源
        channel.close();
        connection.close();
    }
}
