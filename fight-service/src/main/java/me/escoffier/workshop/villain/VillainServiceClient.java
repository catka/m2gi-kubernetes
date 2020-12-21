package me.escoffier.workshop.villain;

import java.time.temporal.ChronoUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.netty.handler.timeout.TimeoutException;
import me.escoffier.workshop.hero.Hero;

@RegisterRestClient(configKey = "villainservice")
public interface VillainServiceClient {
	
    @Timeout(value = 1, unit = ChronoUnit.SECONDS) 
    @Fallback(fallbackMethod = "getFallbackVillain")
	//@Retry(retryOn = TimeoutException.class,
   //     maxRetries = 2,
    //    maxDuration = 10,
    //    durationUnit = ChronoUnit.SECONDS)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio=0.75, delay = 1000)
    @Path("/villain")
    @GET
    Villain getVillain();
    
    default Villain getFallbackVillain() {
    	return new Villain("The Cat", "Big fluffy thing with claws", 1, "https://i.pinimg.com/originals/33/e1/2d/33e12d5fa2181030f4f1db09a3c2bd3c.jpg", "Meows until you lose your mind.");
    }
    

    @Path("/crash")
    @GET
    String crash();
}
