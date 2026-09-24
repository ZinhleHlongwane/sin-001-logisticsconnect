package co.wethinkcode.logisticsconnect;

import co.wethinkcode.logisticsconnect.mq.MqConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PackageStatusSubscriber {

    private volatile int latestDelayStage = 0;

    public void start() {
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(MqConfig.BROKER_URL);

        try {
            Connection connection = connectionFactory.createConnection();

            Session session = connection.createSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE
            );

            Topic topic = session.createTopic(MqConfig.TOPIC);

            MessageConsumer consumer = session.createConsumer(topic);

            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage textMessage) {
                    try {
                        latestDelayStage =
                                Integer.parseInt(textMessage.getText());

                        System.out.println(
                                "Received delay stage: " + latestDelayStage
                        );

                    } catch (JMSException | NumberFormatException e) {
                        System.err.println(
                                "Could not read package status message"
                        );
                    }
                }
            });

            connection.start();

        } catch (JMSException e) {
            throw new RuntimeException(
                    "Could not subscribe to package status topic",
                    e
            );
        }
    }

    public int getLatestDelayStage() {
        return latestDelayStage;
    }
}
