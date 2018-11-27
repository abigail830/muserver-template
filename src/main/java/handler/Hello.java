package handler;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class Hello {

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("name") String name) {
        return "{ \"name\": \"orange\" }";

    }
}
