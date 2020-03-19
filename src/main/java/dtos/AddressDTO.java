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
    private String street, additionalInfo;
    private String city;
    private String zip;
    List<String> persons = new ArrayList();

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        if (address.getPersons() != null) {
            for (Person person : address.getPersons()) {
                this.persons.add(person.toString());
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

    public List<String> getPersons() {
        return persons;
    }

    public void setPersons(List<String> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "AddressDTO{" + "id=" + id + ", street=" + street + ", additionalInfo=" + additionalInfo + ", persons=" + persons + '}';
    }

}
