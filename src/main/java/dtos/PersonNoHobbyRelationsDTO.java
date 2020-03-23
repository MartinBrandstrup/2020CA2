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
public class PersonNoHobbyRelationsDTO
{

    private int id;
    private String firstName, lastName, email, street, city, zip;
//    private List<PhoneNoPersonRelationsDTO> phones = new ArrayList<>();

    public PersonNoHobbyRelationsDTO(Person p)
    {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.email = p.getEmail();
        if (p.getAddress() != null)
        {
            this.street = p.getAddress().getStreet();
            if (p.getAddress().getCityInfo() != null)
            {
                this.city = p.getAddress().getCityInfo().getCity();
                this.zip = String.valueOf(p.getAddress().getCityInfo().getZipCode());
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

//    public List<PhoneNoPersonRelationsDTO> getPhones()
//    {
//        return phones;
//    }

    @Override
    public String toString()
    {
        return "PersonNoHobbyRelationsDTO{" + "id=" + id + ", firstName="
                + firstName + ", lastName=" + lastName + ", email=" + email
                + ", street=" + street + ", city=" + city + ", zip=" + zip 
//                + ", phones=" + phones 
                + '}';
    }

}
