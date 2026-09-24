package co.wethinkcode.logisticsconnect;

import co.wethinkcode.logisticsconnect.mq.MqConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PackageStatusPublisher {

    public void publish(int stage) {
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(MqConfig.BROKER_URL);

        try (
                Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(
                        false,
                        Session.AUTO_ACKNOWLEDGE
                )
        ) {
            connection.start();

            Topic topic = session.createTopic(MqConfig.TOPIC);

            try (MessageProducer producer = session.createProducer(topic)) {
                TextMessage message =
                        session.createTextMessage(String.valueOf(stage));

                producer.send(message);
            }

        } catch (JMSException e) {
            throw new RuntimeException(
                    "Could not publish package status",
                    e
            );
        }
    }
}
