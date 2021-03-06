package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.NoObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

    /**
     * Counts the amount of Person entries existing in the database.
     *
     * @return The amount of existing entries in the database.
     */
    @Override
    public long getPersonCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long personCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        }
        finally
        {
            em.close();
        }

    }

    @Override
    public long countPeopleWithHobby(int id)
    {
        long count = getAllPersonsByHobby(id).size();
        return count;
    }
    
    @Override
    public long countPeopleWithHobbyName(String name)
    {
        long count = getAllPersonsByHobbyName(name).size();
        return count;
    }

    /**
     * Retrieves all Persons from the database as PersonDTOs objects.
     *
     * @return a List<PersonDTO>.
     */
    @Override
    public List<PersonDTO> getAllPersons()
    {
        EntityManager em = getEntityManager();
        try
        {
            List<PersonDTO> personDTOList = new ArrayList<>();
            TypedQuery<Person> query
                    = em.createQuery("SELECT p FROM Person p", Person.class);

            for (Person p : query.getResultList())
            {
                personDTOList.add(new PersonDTO(p));
            }

            return personDTOList;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getAllPersonsByHobby(int id)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            List<PersonDTO> ListOfPersonDTO = new ArrayList<>();

            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p "
                    + "JOIN p.hobbies h WHERE h.id = :hobbyId", Person.class)
                    .setParameter("hobbyId", id);

            for (Person person : query.getResultList())
            {
                ListOfPersonDTO.add(new PersonDTO(person));
            }

            return ListOfPersonDTO;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getAllPersonsByHobbyName(String name)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            List<PersonDTO> ListOfPersonDTO = new ArrayList<>();

            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p "
                    + "JOIN p.hobbies h WHERE h.name = :hobbyName", Person.class)
                    .setParameter("hobbyName", name);

            for (Person person : query.getResultList())
            {
                ListOfPersonDTO.add(new PersonDTO(person));
            }

            return ListOfPersonDTO;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getAllPersonsByCityName(String name)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attempts to retrieve a Person object from the database corresponding to
     * the provided ID. Used mainly for back-end work, since not all information
     * of the Person object should be displayed on the front-end.
     *
     * @param id The provided ID to search the database for.
     * @return a Person object containing all information.
     * @throws exceptions.NoObjectException - if the provided id does not match
     * an entry in the database.
     */
    @Override
    public Person getPersonById(int id) throws NoObjectException
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, id);
            if (p == null)
            {
                throw new NoObjectException("No object matching provided id exists in database.");
            }
            return p;
        }
        catch (IllegalArgumentException ex)
        {
            throw new NoObjectException("No object matching provided id exists in database. IllegalArgumentException.");
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to retrieve a PersonDTO object from the database corresponding
     * to the provided ID. Used mainly for front-end since not all necessary
     * info is provided with a DTO object.
     *
     * @param id The provided ID to search the database for.
     * @return a PersonDTO object containing the necessary information to be
     * displayed on the front-end.
     * @throws exceptions.NoObjectException - if the provided id does not match
     * an entry in the database.
     */
    @Override
    public PersonDTO getPersonDTOById(int id) throws NoObjectException
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, id);
            PersonDTO pDTO = new PersonDTO(p);
            if (pDTO == null)
            {
                throw new NoObjectException("No object matching provided id exists in database.");
            }
            return pDTO;
        }
        catch (IllegalArgumentException ex)
        {
            throw new NoObjectException("No object matching provided id exists in database.");
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to retrieve a Person object from the database corresponding to
     * the provided name. Used mainly for back-end work, since not all information
     * of the Person object should be displayed on the front-end.
     *
     * @param name The provided first name to search the database for.
     * @return a Person object containing all information.
     */
    @Override
    public Person getPersonByName(String name)
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p;

            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p "
                    + "WHERE p.firstName = :personName", Person.class)
                    .setParameter("personName", name);
            p = query.getSingleResult();
            
            return p;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO getPersonDTOByPhone(Phone phone)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attempts to persist a Person object to the database. Returns the
     * persisted object if successful.
     *
     * @param person The Person object to persist.
     * @return the Person object after it has been managed by the Entity
     * Manager.
     */
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
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to delete the provided Person object's entry from the database.
     * Returns the deleted Person object.
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
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to delete the Person of the provided id object's entry from the
     * database. Returns the deleted Person object.
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
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to edit an existing entry in the database to match a given
     * Person object. Returns the changed object.
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
            if (p.getAddress() != null)
            {
                p.setAddress(newPerson.getAddress());
            }
            if (p.getHobbies() != null)
            {
                for (Hobby hobby : p.getHobbies())
                {
                    newPerson.addHobby(hobby);
                }
            }

            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return p;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO addHobbyToPerson(int personId, Hobby hobby)
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, personId); //Getting managed

            p.addHobby(hobby);

            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO addPhoneToPerson(int personId, Phone phone)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO removeHobbyFromPerson(int personId, Hobby hobby)
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, personId);

            p.removeHobby(hobby);

            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO removePhoneFromPerson(int personId, Phone phone)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Populates the database with a set of dummy entries for testing. Returns a
     * list of these entries as Java objects after they have been managed; null
     * if the operation fails. WARNING: wipes the database of existing entries!
     * Part of this method is synchronized to avoid race conditions and thus
     * match the object ids with their position in the returned List.
     *
     * @param numberOfEntries The number of entries to populate database with.
     * @return a List<Person> containing the created objects after they have
     * been managed by the Entity Manager.
     */
    public List<Person> populateDatabaseWithPersons(int numberOfEntries)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            List<Person> personList = new ArrayList<>();
            synchronized (this)
            {
                for (int i = 0; i < numberOfEntries; i++)
                {
                    String firstName = "firstName" + i;
                    String lastName = "lastName" + i;
                    String email = "email" + i;
                    Person p = new Person(firstName, lastName, email);
                    personList.add(p);
                    em.persist(p);
                }
            }
            em.getTransaction().commit();
            return personList;
        }
        catch (IllegalStateException ex)
        {
            System.out.println("Operation populateDatabaseWithHobbies "
                    + "encountered an error with the EntityManager");
            ex.printStackTrace();
            return null;
        }
        catch (Exception ex)
        {
            System.out.println("Operation populateDatabaseWithHobbies failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    
    
    

}
