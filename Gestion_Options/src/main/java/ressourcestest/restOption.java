package ressourcestest;
import javax.ws.rs.*;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("options")
public class restOption {
    public static OptionBusiness optB= new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getALLOptions(@QueryParam("domaine") String d,
                                  @QueryParam("semestre") Integer semestre){

      List<Option> l= new ArrayList<Option>();

      if (d != null && !d.trim().isEmpty()) {
          l = optB.getOptionsByDomaine(d);
      } else if (semestre != null) {
          l = optB.getOptionsBySemestre(semestre);
      } else {
          l = optB.getListeOptions();
      }

      if (l.isEmpty()){
          return Response.status(204).build();}

      return Response.status(200).entity(l).build();


    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("id") int id) {
        Option option = optB.getOptionByCode(id);
        if (option == null) {
            return Response.status(404).build();
        }
        return Response.ok(option).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOption( @PathParam("id") int id){
        if(optB.deleteOption(id)){
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOpt(@PathParam("id") int id, Option op){
        if(optB.updateOption(id,op)){
            return Response.status(200).build();

        }
        return Response.status(400).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)

    public Response ajoutOption(Option op){
        if(optB.addOption(op)){
            return Response.status(200).build();
        }
        return Response.status(400).build();

    }


}
