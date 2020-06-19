package dev.flo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.Context;

//@Path("/word")
@ApplicationScoped
public class WordReactiveRoute{

    @Inject @Channel("words-internal")
    Emitter<String> emitter;
    
    @Inject
    Vertx vertx;

    /*@GET
    @Path("/say")*/
    @Route(path = "/reactive/say", methods = HttpMethod.GET)
    public void say(RoutingExchange ex){
        ex.response().setStatusCode(200).end(Thread.currentThread().getName());
    }

}