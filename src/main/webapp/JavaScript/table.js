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
const eventClick = document.getElementById("loadPersons");
const chosenHobby = document.getElementById("Hobby");

//const table = document.getElementById("IndexTable");
//const Reload = document.getElementById("reloadNewMembers");

//eventClick.addEventListener("click", eventHandler, false )
eventClick.addEventListener("click", fillTable(), false );

function fillTable(){
fetch(URLAll)
        .then(res => res.json())
        .then(data =>{
        let list = data.map(function(person){
                // table with values
                return "<tr><td>" + person.id + "</td>" +
                        "<td>" + person.firstName + "</td>" +
                        "<td>" + person.lastName + "</td>" +
                        "<td>" + person.email + "</td>" +
                        "</tr>";
            }).join("");
            document.getElementById("indexTabelBody").innerHTML = list;
});
}

function fillTableWithHobbies(){
    fetch().then(res => res.json())
        .then(data =>{
        let list = data.map(function(person){
                // table with values
                return "<tr><td>" + person.id + "</td>" +
                        "<td>" + person.firstName + "</td>" +
                        "<td>" + person.lastName + "</td>" +
                        "<td>" + person.email + "</td>" +
                        "</tr>";
            }).join("");
            document.getElementById("indexTabelBody").innerHTML = list;
});
}

function eventHandler(e) {
// tag id givet fra event 
  var id = e.target.id;
  console.log(id);
//    fillTable(e)(id);
  }
 // getCountry henter daten og retuner det til returnPoints 
 

//function fillTable(e){
//    console.log(id)  
//if (id == "svg2"){
//    returnPoint1.innerText = "Name: Ocean" 
//    returnPoint2.innerText = "Population: alot " 
//    returnPoint3.innerText = "Area: less than 510.100.000 km2"
//    returnPoint4.innerText = "Borders: incuding rivers? idk!"  
//  } else {
//
//    var url = "http://restcountries.eu/rest/v1/alpha?codes=" + id
//    console.log(url)
//    fetch(url)
//    .then(res => res.json())
//    .then(data => {
//      // kontrol af data indhold
//        console.log(data)
//      // then data =>{} = hvad skal der ske med dataen    
//        returnPoint1.innerText = "Name: " + data[0].name
//        returnPoint2.innerText = "Population: " + data[0].population
//        returnPoint3.innerText = "Area: " + data[0].area
//        returnPoint4.innerText = "Borders: " + data[0].borders
//  })}
//}

