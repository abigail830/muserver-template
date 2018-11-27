package handler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("health")
public class Health {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatus() {
        return "{ \"status\": \"UP\" }";
    }
}
