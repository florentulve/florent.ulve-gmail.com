package dev.flo;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/")
public class WordResource{

    @Inject 
    WordProducer wordProducer;

    @POST
    @Path("/say")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void say(@FormParam("word") String word){
        wordProducer.say(word);
    }

}