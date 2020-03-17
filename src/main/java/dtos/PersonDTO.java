/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;

/**
 *
 * @author Christian & Brandstrup
 */
public class PersonDTO
{

    private int id;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private List<String> phones;
    private List<String> hobbies;

    public PersonDTO(Person person)
    {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.street = person.getAddress().getStreet();
        this.city = person.getAddress().getCityInfo().getCity();
        this.zip = String.valueOf(person.getAddress().getCityInfo().getZipCode());

        if (person.getHobbies() != null)
        {
            for (Hobby hobby : person.getHobbies())
            {
                this.hobbies.add(hobby.toString());
            }
        }

        if (person.getPhones() != null)
        {
            for (Phone phone : person.getPhones())
            {
                this.phones.add(phone.toString());
            }
        }
    }

    /*
    Martin: ikke sikker på vi behøver de her; har lavet om i parameterlisten, så
    de dur ikke lige pt, derfor har jeg udkommenteret dem. Hvis det viser sig, at
    vi får brug for dem senere, må vi lige kigge på det igen
     */
//    public PersonDTO(int id, String firstName, String street, String city, String zip, String phones, String hobbies)
//    {
//        this.id = id;
//        this.firstName = firstName;
//        this.street = street;
//        this.city = city;
//        this.zip = zip;
//        this.phones = phones;
//        this.hobbies = hobbies;
//    }
//
//    // Constructor for adding person
//    public PersonDTO(String firstName, String street, String city, String zip, String phones, String hobbies)
//    {
//        this.firstName = firstName;
//        this.street = street;
//        this.city = city;
//        this.zip = zip;
//        this.phones = phones;
//        this.hobbies = hobbies;
//    }
    public int getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public List<String> getPhones()
    {
        return phones;
    }

    public List<String> getHobbies()
    {
        return hobbies;
    }

    @Override
    public String toString()
    {
        return "PersonDTO{" + "id=" + id + ", name=" + firstName + ", street="
                + street + ", city=" + city + ", zip=" + zip + ", phones="
                + phones + ", hobbies=" + hobbies + '}';
    }

}
