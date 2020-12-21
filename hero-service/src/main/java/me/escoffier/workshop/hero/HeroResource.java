package me.escoffier.workshop.hero;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import io.vertx.core.Vertx;

@Path("/")
public class HeroResource {
	private static final Logger LOGGER = Logger.getLogger("hero-resource");
	
	@Inject Vertx vertx;
    
	@GET
	@Path("/")
	public String welcome() {
		return "Hello I am HeroResource";
	}
	
	@GET
    @Path("/hero")
    public Hero getRandomHero() {
	 	Hero hero = Hero.findRandom();
        LOGGER.debug("Found random hero " + hero);
        return hero;
    }
	

    @GET 
    @Path("/crash")
    @Produces(MediaType.TEXT_PLAIN)
    public String crash() {
        LOGGER.info("Hero dying in ~1 second");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            vertx.close();
        }).start();
        return "Hero has crashed.";
    }
}
