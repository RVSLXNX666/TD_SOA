package resourcerest;
import entities.Option;
import metiers.OptionBusiness;

import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/options")
public class restOption {
    public static OptionBusiness optB = new OptionBusiness();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String D){
        List <Option> l = new ArrayList<Option>();
        if (D==null) {
            l=optB.getListeOptions();
        }
        else{
            l=optB.getOptionsByDomaine(D);}

        if(l.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
            return  Response.status(200).entity(l).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option op){
        if (optB.addOption(op)){
            return Response.status(200).build();
        }
        else return Response.status(404).build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam ("code")int id) {
        if (optB.deleteOption(id)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("id") int id, Option op){
        op.setCodeOption(id);
        if (optB.updateOption(id,op)){
            return Response.status(200).entity(op).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("id") int id) {
        Option op = optB.getOptionByCode(id);
        if (op != null) {
            return Response.status(200).entity(op).build();
        }
        return Response.status(404).build();
    }


}

