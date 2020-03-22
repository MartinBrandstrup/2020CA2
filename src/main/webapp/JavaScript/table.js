/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//attributs
var URLTest = "http://localhost:8080/2020CA2/api/person/all"
var URLAll = "/2020CA2/api/person/all"
var URLpvh = "/2020CA/api/person/PersonsWithHobby/"
var URLpvhc =  "/2020CA/api/person/countPersonsWithHobby/" 
const fillBtn = document.getElementById("loadPersons");
const chosenHobby = document.getElementById("txt")

//events
fillBtn.addEventListener("click", fillTable, false )
chosenHobby.addEventListener("change", eventHandler, false )

/**
 * 
 * @returns {All persons for table from api}
 */
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

/**
 * 
 * @param {type} id
 * @returns {table data for all persons with given hoppy id}
 */
function personsWithHobbies(id){
    fetch(URLpvh + id).then(res => res.json())
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
        // next time il use promises        
        countPersonWithHobbies(id);
});
}
/**
 * 
 * @param {type} id
 * @returns {count of persons with given hobby}
 */
function countPersonWithHobbies(id){
    fetch(URLpvh + id).then(res => res.json())
        .then(data =>{ 
            document.getElementById("count").innerHTML = data;
        
           
            
});
}
/*
 * Manage event and confirm data in development fase
 */
function eventHandler(e) {
// tag id givet fra event 
  var hobbyId = e.target.value;
  console.log(hobbyId);
  
    personsWithHobbies(hobbyId);
    
  }
 // getCountry henter daten og retuner det til returnPoints 
 

//error handling if time
//fetch (...).then( res => {
//    if (res.ok) {
//        console.log("Success")
//    }else {
//        console.log("Not successfull")
//    }
//}
