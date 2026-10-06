package resourcerest;
import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import entities.EtudiantList;
import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/etudiants")
public class restEtudiant {
    public static EtudiantBusiness optE = new EtudiantBusiness();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> l = optE.getAllEtudiants();
        return Response.status(200).entity(l).build();
    }



    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant e = optE.getEtudiantByIdentifiant(identifiant);
        if (e == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(e).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant e){
        if (optE.addEtudiant(e)){
            return Response.status(200).build();
        }
        else return Response.status(404).build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteEtudiant(@PathParam ("code")String identifiant) {
        if (optE.deleteEtudiant(identifiant)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String identifiant, Etudiant e){
        e.setIdentifiant(identifiant);
        if (optE.updateEtudiant(identifiant,e)){
            return Response.status(200).entity(e).build();
        }
        return Response.status(404).build();
    }


    @GET
    @Path("Opp")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeO) {
        Option op = restOption.optB.getOptionByCode(codeO);
        if (op == null) {
            return Response.status(404).build();
        }

        List<Etudiant> res = new ArrayList<>();
        for (Etudiant e : optE.getEtudiantsByOption(op)) {
            res.add(new Etudiant(e.getIdentifiant(), e.getNom(), e.getPrenom(),
                    null, e.getAnneeEtude(), e.getEmail()));
        }
        return Response.status(200).entity(new EtudiantList(res)).build();
    }

}