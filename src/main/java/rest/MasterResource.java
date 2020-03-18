package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import utils.EMF_Creator;
import facades.HobbyFacade;
import facades.MasterFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("master")
public class MasterResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA2_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final MasterFacade FACADE = MasterFacade.getMasterFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test()
    {
        PersonDTO pDTO = FACADE.tempMethodToTest();
        return GSON.toJson(pDTO);
    }

}
