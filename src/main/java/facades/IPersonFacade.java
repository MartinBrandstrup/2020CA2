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

    public Person editPerson(Person person);

    public PersonDTO addHobbiesToPerson(Person person, List<Hobby> hobbies);

    public PersonDTO addPhonesToPerson(Person person, List<Phone> hobbies);

    public PersonDTO addAddressToPerson(Person person, Address address);
    
    public PersonDTO removeHobbyFromPerson(Person person, Hobby hobby);

    public PersonDTO removePhoneFromPerson(Person person, Phone phone);

    public PersonDTO removeAddressFromPerson(Person person, Address address);

}
