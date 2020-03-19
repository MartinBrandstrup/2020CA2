/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;

/**
 *
 * @author Brandstrup
 */
public interface IPersonFacade
{

    public long getPersonCount();

    public long getPersonCountByHobby(Hobby hobby);

    public List<PersonDTO> getAllPersons();

    public List<PersonDTO> getAllPersonsByHobby(Hobby hobby);

    public List<PersonDTO> getAllPersonsByCity(CityInfo cityInfo);

    public Person getPersonById(int id);

    public PersonDTO getPersonDTOById(int id);

    public PersonDTO getPersonDTOByPhone(Phone phone);

    public Person persistPerson(Person person);

    public Person deletePerson(PersonDTO person);

    public Person deletePersonById(int id);

    public Person editPerson(int oldPersonId, Person newPerson);

    public PersonDTO addHobbiesToPerson(int personId, List<Hobby> hobbies);

    public PersonDTO addPhonesToPerson(int personId, List<Phone> hobbies);

    public PersonDTO removeHobbyFromPerson(int personId, Hobby hobby);

    public PersonDTO removePhoneFromPerson(int personId, Phone phone);

}
