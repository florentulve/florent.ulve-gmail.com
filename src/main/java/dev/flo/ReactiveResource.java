package dev.flo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;

@Path("/reactive")
public class ReactiveResource{


    @GET()
    @Path("/")
    @Consumes({MediaType.TEXT_PLAIN})
    public Uni<String> say(@QueryParam("word") String word){
        System.out.println(Thread.currentThread().getName());
        return Uni.createFrom().item(Thread.currentThread().getName()+" "+word);
    }

}