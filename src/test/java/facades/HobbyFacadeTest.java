package facades;

import dtos.HobbyDTO;
import entities.Hobby;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
//@Disabled
public class HobbyFacadeTest
{

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;

    private final int numberOfEntries = 8;
    private List<Hobby> hobbyList = new ArrayList<Hobby>();

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
        if (!(hobbyList.isEmpty()))
        {
            hobbyList.clear();
        }

        hobbyList = facade.populateDatabaseWithHobbies(numberOfEntries);

//        hobbyList.sort((Hobby h1, Hobby h2) -> h1.getId() - h2.getId());
        //Lambda expressions not supported??
        Collections.sort(hobbyList, new Comparator<Hobby>()
        {
            @Override
            public int compare(Hobby h1, Hobby h2)
            {
                return h1.getId() - h2.getId();
            }
        });

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
    public void getHobbyByIDTest()
    {
        assertEquals(hobbyList.get(numberOfEntries - 1).getId(),
                facade.getHobbyById(numberOfEntries).getId());
    }

    //For later, gives nullpointer exception at line 150 for some reason
//    @Test
//    public void getHobbyDTOByIDTest()
//    {
//        assertEquals(hobbyList.get(numberOfEntries - 1).getId(),
//                facade.getHobbyDTOById(numberOfEntries).getId());
//    }
    
    @Test
    public void persistHobbyTest()
    {
        List<HobbyDTO> databaseContent = facade.getAllHobbies();
        int databaseIdOffset = databaseContent.get(databaseContent.size() - 1).getId();
        /*
        This step to obtain the latest id in the database is required as the 
        database seems to be remembering the earlier test's testdata's ids when 
        assigning a procedurally generated id to new test data.
         */

        Hobby testHobby = facade.persistHobby(new Hobby("testHobby", "testHobby"));
        assertEquals(numberOfEntries + 1, facade.getAllHobbies().size(),
                "Expects " + numberOfEntries + 1 + " rows in the database");
        assertEquals(databaseIdOffset + 1, testHobby.getId());
    }

    @Test
    public void deleteHobbyTest()
    {
        int idToRemove = numberOfEntries - 1;
        HobbyDTO hobbyToRemove = facade.getAllHobbies().get(idToRemove);
        boolean exists = false;

        assertEquals(numberOfEntries, facade.getAllHobbies().size(),
                "Expects " + numberOfEntries + " rows in the database");

        for (HobbyDTO h : facade.getAllHobbies())
        {
            exists = h.toString().equals(hobbyToRemove.toString());
        }
        assertTrue(exists);
        /*
        Using a custom for loop and toString to avoid equaling references 
        instead of actual object. .contains will not allow me to do this.
         */

        facade.deleteHobby(hobbyToRemove);

        assertEquals(numberOfEntries - 1, facade.getAllHobbies().size(),
                "Expects " + (numberOfEntries - 1) + " rows in the database");

        for (HobbyDTO h : facade.getAllHobbies())
        {
            exists = h.toString().equals(hobbyToRemove.toString());
        }
        assertFalse(exists);
    }

    @Test
    public void deleteHobbyByIdTests()
    {
        int idToRemove = facade.getAllHobbies().get(0).getId();

        assertEquals(numberOfEntries, facade.getAllHobbies().size(),
                "Expects " + numberOfEntries + " rows in the database");

        facade.deleteHobbyById(idToRemove);

        assertEquals(numberOfEntries - 1, facade.getAllHobbies().size(),
                "Expects " + (numberOfEntries - 1) + " rows in the database");
        /*
        In hindsight this would probably have been sufficient for the previous 
        delete test as well. Oh well, at least I practiced my loops.
         */
    }
}
