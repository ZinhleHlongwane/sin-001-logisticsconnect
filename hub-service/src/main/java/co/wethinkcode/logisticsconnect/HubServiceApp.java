package co.wethinkcode.logisticsconnect;

import io.javalin.Javalin;

public class HubServiceApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7051);

        IngestionClient ingestionClient = new IngestionClient();

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Serves provinces and sorting centers (place-name source of truth).)
        // Add domain endpoints for hub-service here.
        app.get("/hubs/{id}", ctx -> {
            String requestedId = ctx.pathParam("id").trim().toUpperCase();

            Hub hub = ingestionClient.getHubs()
                    .stream()
                    .filter(item -> item.getId().equals(requestedId))
                    .findFirst()
                    .orElse(null);

            if (hub == null) {
                ctx.status(404).result("Hub not found");
                return;
            }

            ctx.json(hub);
        });
    }
}
