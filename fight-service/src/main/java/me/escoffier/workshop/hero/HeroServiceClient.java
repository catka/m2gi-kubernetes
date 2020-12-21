package me.escoffier.workshop.hero;

import java.time.temporal.ChronoUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.netty.handler.timeout.TimeoutException;

@RegisterRestClient(configKey="heroservice")
public interface HeroServiceClient {

    @Timeout(value = 1, unit = ChronoUnit.SECONDS) 
    @Fallback(fallbackMethod = "getFallbackHero")
	//@Retry(retryOn = TimeoutException.class,
    //    maxRetries = 2,
    //    maxDuration = 10,
    //    durationUnit = ChronoUnit.SECONDS)
    @CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio=0.75, delay = 1000)
    @Path("/hero")
    @GET
    Hero getHero();
    
    default Hero getFallbackHero() {
    	return new Hero("Stickman", "Wannabe warrior", 1, "https://static.thenounproject.com/png/1638142-200.png", "He can be really sticky.");
    }
    

    @Path("/crash")
    @GET
    String crash();
}
