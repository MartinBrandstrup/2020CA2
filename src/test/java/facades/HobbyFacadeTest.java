package facades;

import dtos.HobbyDTO;
import entities.Hobby;
import java.util.Collections;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class HobbyFacadeTest
{

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;

    private final int numberOfEntries = 5;
    private List<Hobby> hobbyList;

    public HobbyFacadeTest()
    {
    }

    //@BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/2020CA2_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = HobbyFacade.getHobbyFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2()
    {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = HobbyFacade.getHobbyFacade(emf);
    }

    @AfterAll
    public static void tearDownClass()
    {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp()
    {

        hobbyList = facade.populateDatabaseWithHobbies(numberOfEntries);

//        hobbyList = Collections.sort(hobbyList, c);


//        EntityManager em = emf.createEntityManager();
//        try
//        {
//            em.getTransaction().begin();
//            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
////            em.persist(new Hobby("name", "description"));
////            em.persist(new Hobby("name", "description"));
//
//            em.getTransaction().commit();
//        }
//        finally
//        {
//            em.close();
//        }
    }

    @AfterEach
    public void tearDown()
    {
//        Remove any data after each test was run
    }

    @Test
    public void hobbyCountTest()
    {
        assertEquals(numberOfEntries, facade.getHobbyCount(),
                "Expects " + numberOfEntries + " rows in the database");
    }

    @Test
    public void getAllHobbiesTest()
    {
        //populating database twice in order to get access to the objects I am
        //persisting to the database
        List<HobbyDTO> databaseList = facade.getAllHobbies();

        assertFalse(databaseList == null);
        assertFalse(databaseList.isEmpty());
        assertEquals(numberOfEntries, databaseList.size(),
                "Expects " + numberOfEntries + " rows in the database");

        assertTrue(databaseList.size() == hobbyList.size()
                && databaseList.get(numberOfEntries - 1).getId()
                == hobbyList.get(numberOfEntries - 1).getId());

    }

    @Test
    public void persistHobbyTest()
    {
        Hobby testHobby = facade.persistHobby(new Hobby("testHobby", "testHobby"));
        assertEquals(numberOfEntries + 1, facade.getAllHobbies().size(), "Expects " + numberOfEntries + 1 + " rows in the database");
        assertEquals(numberOfEntries, testHobby.getId());
    }

    @Test
    public void getHobbyByIDTest()
    {
        assertEquals(hobbyList.get(hobbyList.size() -1).getId(), 
                facade.getHobbyById(hobbyList.size()).getId());
    }

    @Test
    public void getHobbyDTOByIDTest()
    {
        assertEquals(hobbyList.get(hobbyList.size() -1).getId(), 
                facade.getHobbyDTOById(hobbyList.size()).getId());
    }
}
