package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersonFacade implements IPersonFacade
{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    @Override
    public long getPersonCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long personCount = (long) em.createQuery("SELECT COUNT(p) FROM Peron p").getSingleResult();
            return personCount;
        }
        finally
        {
            em.close();
        }

    }

    @Override
    public long getPersonCountByHobby(Hobby hobby)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PersonDTO> getAllPersons()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PersonDTO> getAllPersonsByHobby(Hobby hobby)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PersonDTO> getAllPersonsByCity(CityInfo cityInfo)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person getPersonById(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO getPersonDTOById(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO getPersonDTOByPhone(Phone phone)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person persistPerson(Person person)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        }
        catch (Exception ex)
        {
            System.out.println("Operation persistPerson failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to delete the provided Person object's entry from the database.
     * Returns the deleted Person object; null if the operation fails.
     *
     * @param person The Person object to delete.
     * @return the deleted Person object.
     */
    @Override
    public Person deletePerson(PersonDTO person)
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, person.getId());

            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return p;
        }
        catch (Exception ex)
        {
            System.out.println("Operation deletePerson failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to delete the Person of the provided id object's entry from the
     * database. Returns the deleted Person object; null if the operation fails.
     *
     * @param id The id of the Person object to delete.
     * @return the deleted Person object.
     */
    @Override
    public Person deletePersonById(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, id);

            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return p;
        }
        catch (Exception ex)
        {
            System.out.println("Operation deletePersonById failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to edit an existing entry in the database to match a given Person
     * object. Returns the changed object; null if the operation fails.
     *
     * @param oldPersonId The id of the old person to be changed.
     * @param newPerson The object containing the information to change to.
     * @return the changed object with the new values.
     */
    @Override
    public Person editPerson(int oldPersonId, Person newPerson)
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, oldPersonId);

            p.setFirstName(newPerson.getFirstName());
            p.setLastName(newPerson.getLastName());
            p.setEmail(newPerson.getEmail());
            p.setAddress(newPerson.getAddress());
            for (Hobby hobby : p.getHobbies())
            {
                newPerson.addHobby(hobby);
            }

            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return p;
        }
        catch (Exception ex)
        {
            System.out.println("Operation editPerson failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO addHobbiesToPerson(Person person, List<Hobby> list)
    {
        EntityManager em = getEntityManager();
        try
        {
            person = em.find(Person.class, person.getId()); //Getting managed

            for (Hobby hobby : list)
            {
                if (!(person.getHobbies().contains(hobby)))
                {
                    person.addHobby(hobby);
                }
            }

            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        }
        catch (Exception ex)
        {
            System.out.println("Operation persistPerson failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO addPhonesToPerson(Person person, List<Phone> list)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO addAddressToPerson(Person person, Address adrs)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO removeHobbyFromPerson(Person person, Hobby hobby)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO removePhoneFromPerson(Person person, Phone phone)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO removeAddressFromPerson(Person person, Address adrs)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
