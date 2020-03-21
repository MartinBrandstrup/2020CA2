/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var URLTest = "http://localhost:8080/2020CA2/api/person/all"
var URL = "/2020CA2/api/person"
var URLAll = "/2020CA2/api/person/all"
var URLpvh = "/2020CA/api/person//personswithhobby"
var URLpvhc =  "/2020CA/api/person/amountofpersonswithhobby"


const table = document.getElementById("IndexTable");
const Reload = document.getElementById("reloadNewMembers");


fetch(URLTest)
        .then(res => res.json())
        .then(data =>{
        let list = data.map(function(person){
                // table with values
                return "<tr><td>" + person.id + "</td>" +
                        "<tr><td>" + person.firstName + "</td>" +
                        "<td>" + person.lastName + "</td>" +
                        "<td>" + person.email + "</td>" +
                        "</tr>";
            }).join("");
            document.getElementById("indexTabelBody").innerHTML = list;
})