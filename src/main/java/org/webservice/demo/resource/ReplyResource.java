package org.webservice.demo.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/test")
public class ReplyResource {

    @GET
    @Path("/{somestring}")
    @Produces("application/json")
    public Response getMsg(@PathParam("somestring") String somestring) {
        if (somestring != null) {
            String output = "Returning " + somestring + ". It worked!\n";
            return Response.status(200).entity(output).build();
        } else {
            throw new WebApplicationException("somestring is required", javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE);
        }

    }
}
