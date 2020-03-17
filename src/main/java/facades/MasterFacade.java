/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 *
 * @author Brandstrup
 */
public class MasterFacade
{

    private static EntityManagerFactory emf;

    HobbyFacade hobbyFacade = HobbyFacade.getHobbyFacade(emf);
    PersonFacade personFacade = PersonFacade.getPersonFacade(emf);

    private static MasterFacade instance;

    //Private Constructor to ensure Singleton
    private MasterFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MasterFacade getMasterFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new MasterFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    /**
     * This thing here needs some work!
     */
    public void tempMethodToTest()
    {
        Person martin = new Person("Martin", "Brandstrup", "martin.l.brandstrup@gmail.com");
        Hobby java = new Hobby("Java", "Software development 101");
        Hobby js = new Hobby("JavaScript", "Software development 102");

        //Remember to manage entities first thing for id
        personFacade.persistPerson(martin);
        hobbyFacade.persistHobby(java);
        hobbyFacade.persistHobby(js);

        List<Hobby> hobbies = new ArrayList<>();
        hobbies.add(java);
        hobbies.add(js);

        personFacade.addHobbiesToPerson(martin, hobbies);

    }
}
