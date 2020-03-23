package facades;

import entities.Address;
import entities.Hobby;
import entities.Person;
import exceptions.CouplingException;
import exceptions.ORMException;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static MasterFacade mf;
    Person pers1 = new Person("Jon", "Black", "notSnow@lol");
    Person pers2 = new Person("Bat", "Duck", "Nanananana");
    Hobby hb1 = new Hobby("Programing", "Something");
    Hobby hb2 = new Hobby("Snacking", "Allways");

    public PersonFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/2020CA2_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = PersonFacade.getPersonFacade(emf);
        mf = MasterFacade.getMasterFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getPersonFacade(emf);
        mf = MasterFacade.getMasterFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() throws ORMException, CouplingException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(hb1);
            em.persist(hb2);
            em.persist(pers1);
            em.persist(pers2);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
        int[] hobbyList = new int[2];
        int pId = pers1.getId();
        int hb1Id = hb1.getId();
        hobbyList[0] = hb1Id;
//        hobbyList[1] = hb2.getId();
//        mf.coupleHobbiesToPerson(hobbyList, pId);
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void personCountTest() {
        assertEquals(2, facade.getPersonCount(), "Expects two rows in the database");
    }
//    @Test
//    public void getAllPersonsByHobby(){
//          assertEquals(1, facade.getAllPersonsByHobby(hb1), "Expects 1 rows in the database");
//    }
//    @Test
//    public void countPeopleWithHobby(){
//        assertEquals(1, facade.getPersonCountByHobby(hb1), "Expects 1 rows in the database");
//    }

}
