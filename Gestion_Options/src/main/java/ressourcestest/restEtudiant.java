package ressourcestest;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class restEtudiant {


    public static EtudiantBusiness etuB = new EtudiantBusiness();
    public static OptionBusiness optB = new OptionBusiness();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajoutEtudiant(Etudiant e) {

        if (e == null || e.getOption() == null) {
            return Response.status(400)
                    .entity("Etudiant or option is null")
                    .build();
        }

        boolean added = etuB.addEtudiant(e);

        if (added) {
            return Response.status(200).build();
        }

        return Response.status(404)
                .entity("Option not found")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> liste = etuB.getAllEtudiants();

        if (liste.isEmpty()) {
            return Response.status(204).build();
        }

        return Response.ok(liste).build();
    }

    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(
            @PathParam("identifiant") String identifiant) {

        Etudiant e = etuB.getEtudiantByIdentifiant(identifiant);

        if (e == null) {
            return Response.status(404).build();
        }

        return Response.ok(e).build();
    }

    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(
            @PathParam("identifiant") String identifiant) {

        if (etuB.deleteEtudiant(identifiant)) {
            return Response.status(204).build();
        }

        return Response.status(404).build();
    }

    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(
            @PathParam("identifiant") String identifiant,
            Etudiant e) {

        if (e.getOption() == null) {
            return Response.status(400).build();
        }

        Option option = optB.getOptionByCode(
                e.getOption().getCodeOption());

        if (option == null) {
            return Response.status(404).build();
        }

        e.setOption(option);

        if (etuB.updateEtudiant(identifiant, e)) {
            return Response.status(200).build();
        }

        return Response.status(404).build();
    }

    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantsByOption(
            @QueryParam("codeOption") int codeOption) {

        Option option = optB.getOptionByCode(codeOption);

        if (option == null) {
            return Response.status(404).build();
        }

        List<Etudiant> liste = etuB.getEtudiantsByOption(option);

        return Response.ok(liste).build();
    }

}
