/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Christian
 */
public class PersonDTO {
     private int id;
  private String name;
  private String street;
  private String city;
  private String zip;
  private String phones;
//  private Set<String> phones2 = new HashSet(); // Disscuse with grp 
  private String hobbies; // may be better as list

    public PersonDTO(int id, String name, String street, String city, String zip, String phones, String hobbies) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.phones = phones;
        this.hobbies = hobbies;
    }

  // Constructor for adding person
    public PersonDTO(String name, String street, String city, String zip, String phones, String hobbies) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.phones = phones;
        this.hobbies = hobbies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "id=" + id + ", name=" + name + ", street=" + street + ", city=" + city + ", zip=" + zip + ", phones=" + phones + ", hobbies=" + hobbies + '}';
    }

}
