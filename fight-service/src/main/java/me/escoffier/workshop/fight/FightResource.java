package me.escoffier.workshop.fight;

import org.jboss.logging.Logger;

import io.micrometer.core.annotation.Counted;
import me.escoffier.workshop.hero.Hero;
import me.escoffier.workshop.hero.HeroServiceClient;
import me.escoffier.workshop.villain.Villain;
import me.escoffier.workshop.villain.VillainServiceClient;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FightResource {
    private static final Logger LOGGER = Logger.getLogger(FightResource.class);
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted("fight-service.hello.invocations")
    public String print() {
    	LOGGER.debug("I said hi");
        return "Hello from " + System.getenv("HOSTNAME") + "!";
    }
    

    @Inject @RestClient HeroServiceClient heroClient;
    @Inject @RestClient VillainServiceClient villainClient;
    
    @GET
    @Path("/fight")
    @Counted("fight-service.fight.invocations")
    public Fight fight() {
    	LOGGER.debug("Fight will soon start");
    	return fight(
    			heroClient.getHero(),
    			villainClient.getVillain()
    			);
    }

    private final Random random = new Random();

    private Fight fight(Hero hero, Villain villain) {
        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((hero.level + heroAdjust) >= (villain.level + villainAdjust)) {
            return new Fight(hero, villain, hero.name);
        } else {
            return new Fight(hero, villain, villain.name);
        }
    }

    @GET
    @Path("/crash")
    @Counted("fight-service.crash.invocations")
    @Produces(MediaType.TEXT_PLAIN)
    public String crash() {
    	return (random.nextInt(1) == 0 ?  heroClient.crash() : villainClient.crash());
    }

}
