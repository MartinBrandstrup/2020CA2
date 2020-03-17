/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;
import entities.Person;
import java.util.List;

/**
 *
 * @author Christian
 */
public class AddressDTO {
    
    private int id;
    private String street, additionalInfo;
    List<String> persons;

    public AddressDTO(Address a) 
    {
        this.id = a.getId();
        this.street = a.getStreet();
        this.additionalInfo = a.getAdditionalInfo();
         if (a.getPersons() != null)
        {
            for (Person person : a.getPersons())
            {
                this.persons.add(person.toString());
            }
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
