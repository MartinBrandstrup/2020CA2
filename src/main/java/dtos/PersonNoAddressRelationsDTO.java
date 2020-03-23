/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christian & Brandstrup
 */
public class PersonNoAddressRelationsDTO
{

    private int id;
    private String firstName, lastName, email;
//    private List<PhoneNoPersonRelationsDTO> phones = new ArrayList<>();
    private List<HobbyNoPersonRelationsDTO> hobbies = new ArrayList<>();

    public PersonNoAddressRelationsDTO(Person p)
    {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.email = p.getEmail();

        if (!(p.getHobbies() == null && p.getHobbies().isEmpty()))
        {
            for (Hobby hobby : p.getHobbies())
            {
                this.hobbies.add(new HobbyNoPersonRelationsDTO(hobby));
            }
        }

//        if (!(p.getPhones() == null && p.getPhones().isEmpty()))
//        {
//            for (Phone phone : p.getPhones())
//            {
//                this.phones.add(new PhoneNoPersonRelationsDTO(phone));
//            }
//        }
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

//    public List<PhoneNoPersonRelationsDTO> getPhones()
//    {
//        return phones;
//    }

    public List<HobbyNoPersonRelationsDTO> getHobbies()
    {
        return hobbies;
    }

    @Override
    public String toString()
    {
        return "PersonNoAddressRelationsDTO{" + "id=" + id + ", firstName=" 
                + firstName + ", lastName=" + lastName + ", email=" + email 
//                + ", phones=" + phones 
                + ", hobbies=" + hobbies + '}';
    }


}
