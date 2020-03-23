/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.NoObjectException;
import java.util.List;

/**
 *
 * @author Brandstrup
 */
public interface IPersonFacade
{

    public long getPersonCount();

    public long countPeopleWithHobby(int id);
    
    public long countPeopleWithHobbyName(String name);

    public List<PersonDTO> getAllPersons();

    public List<PersonDTO> getAllPersonsByHobby(int id);
    
    public List<PersonDTO> getAllPersonsByHobbyName(String name);

    public List<PersonDTO> getAllPersonsByCityName(String name);

    public Person getPersonById(int id) throws NoObjectException;

    public PersonDTO getPersonDTOById(int id) throws NoObjectException;
    
    public Person getPersonByName(String name);

    public PersonDTO getPersonDTOByPhone(Phone phone);

    public Person persistPerson(Person person);

    public Person deletePerson(PersonDTO person);

    public Person deletePersonById(int id);

    public Person editPerson(int oldPersonId, Person newPerson);

    public PersonDTO addHobbyToPerson(int personId, Hobby hobby);

    public PersonDTO addPhoneToPerson(int personId, Phone phone);

    public PersonDTO removeHobbyFromPerson(int personId, Hobby hobby);

    public PersonDTO removePhoneFromPerson(int personId, Phone phone);

}
