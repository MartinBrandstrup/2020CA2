/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christian
 */
public class AddressDTO {

    private int id;
    private String street, additionalInfo, city, zip;
    List<PersonNoAddressRelationsDTO> persons = new ArrayList();

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        
        if (address.getPersons() != null) {
            for (Person person : address.getPersons()) {
                this.persons.add(new PersonNoAddressRelationsDTO(person));
            }
        }
        
        if (address.getCityInfo() != null) {
            this.zip = String.valueOf(address.getCityInfo().getZipCode());
            this.city = address.getCityInfo().getCity();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<PersonNoAddressRelationsDTO> getPersons() {
        return persons;
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

    @Override
    public String toString() {
        return "AddressDTO{" + "id=" + id + ", street=" + street + ", additionalInfo=" + additionalInfo + ", persons=" + persons + '}';
    }

}
