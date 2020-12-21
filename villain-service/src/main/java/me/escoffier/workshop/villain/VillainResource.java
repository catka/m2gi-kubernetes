package me.escoffier.workshop.villain;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import io.vertx.core.Vertx;

@Path("/")
public class VillainResource {
	private static final Logger LOGGER = Logger.getLogger("villain-resource");
	@Inject Vertx vertx;
	
	@GET
    @Path("/villain")
    public Villain getRandomVillain() {
	 	Villain villain = Villain.findRandom();
        LOGGER.debug("Found random villain " + villain);
        return villain;
    }
	
    @GET 
    @Path("/crash")
    @Produces(MediaType.TEXT_PLAIN)
    public String crash() {
        LOGGER.info("Villain dying in ~1 second");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            vertx.close();
        }).start();
        return "Villain has crashed.";
    }
}
