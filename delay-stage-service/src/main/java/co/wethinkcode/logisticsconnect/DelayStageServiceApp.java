package co.wethinkcode.logisticsconnect;

import io.javalin.Javalin;

public class DelayStageServiceApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7052);

        PackageStatusPublisher packageStatusPublisher = new PackageStatusPublisher();

        int[] delayStage = {0};

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Tracks the Transit Delay Stage (0-8, e.g. weather shutdowns).)
        // Add domain endpoints for delay-stage-service here.
        app.get("/delay-stage", ctx -> {
            ctx.json(delayStage[0]);
        });

        app.put("/delay-stage/{stage}", ctx -> {
            int stage;

            try {
                stage = Integer.parseInt(ctx.pathParam("stage"));
            } catch (NumberFormatException e) {
                ctx.status(400).result(
                        "Delay stage must be a number between 0 and 8"
                );
                return;
            }

            if (stage < 0 || stage > 8) {
                ctx.status(400).result(
                        "Delay stage must be between 0 and 8"
                );
                return;
            }

            delayStage[0] = stage;

            packageStatusPublisher.publish(stage);

            ctx.result("Delay stage updated to " + stage);
        });
    }
}

// MQ TODO: publishes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.logisticsconnect.mq.MqConfig)
