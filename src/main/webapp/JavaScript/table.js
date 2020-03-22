/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//attributs
var URLTest = "http://localhost:8080/2020CA2/api/person/all"
var URLpopulate = "/2020CA2/api/master/populate"
var URLAll = "/2020CA2/api/person/all"
var URLpvh = "/2020CA2/api/person/personsWithHobby/"
var URLpvhc = "/2020CA2/api/person/countPersonsWithHobby/"
const fillBtn = document.getElementById("loadPersons")
const populateBtn = document.getElementById("populate")
const apidocBtn = document.getElementById("APIDOC")
const chosenHobby = document.getElementById("txt")


//events
populateBtn.addEventListener("click", populateTable, false )
fillBtn.addEventListener("click", fillTable, false)
chosenHobby.addEventListener("blur", eventHandler, false)
apidocBtn.addEventListener("click",ShowApi , false)

function  ShowApi(){
    document.getElementById("Documentation").style.display = "block" 
    document.getElementById("indexTable").style.display = "none" 
}
function  HideApi(){
    document.getElementById("Documentation").style.display = "none" 
    document.getElementById("indexTable").style.display = "block" 
}
HideApi();

function populateTable(){
fetch(URLpopulate,{
    method: 'POST',
    headers: {
        'content-Type': 'application/jason'
    }//,
//    body:{
//        
//    }
})
        .then(res => res.json())
        .then(data =>{ 
           // console.log(data);
})
}


/**
 * 
 * @returns {All persons for table from api}
 */
function fillTable() {
    fetch(URLAll)
            .then(res => res.json())
            .then(data => {
                 let table = data.map(person => "<tr><td>" + person.id + "</td>" +
                            "<td>" + person.firstName + "</td>" +
                            "<td>" + person.lastName + "</td>" +
                            "<td>" + person.email + "</td>" +
                            "</tr>");
                table.unshift("<table><thead><tr><th>id</th><th>Firstname</th><th>LastName</th><th>Email</th></tr></thead>");
                table.push("</table>");
                table.join('');
                document.getElementById("indexTable").innerHTML = table;
                document.getElementById("Documentation").style.display = "none";
                document.getElementById("indexTable").style.display = "block" 
                // next time il use promises        
            })
}

/**
 * 
 * @param {type} id
 * @returns {table data for all persons with given hoppy id}
 */
function personsWithHobbies(id) {
    fetch(URLpvh + id).then(res => res.json())
            .then(data => {
                // table with values
                let table = data.map(person => "<tr><td>" + person.id + "</td>" +
                            "<td>" + person.firstName + "</td>" +
                            "<td>" + person.lastName + "</td>" +
                            "<td>" + person.email + "</td>" +
                            "</tr>");
                table.unshift("<table><thead><tr><th>id</th><th>Firstname</th><th>LastName</th><th>Email</th></tr></thead>");
                table.push("</table>");
                table.join('');
                document.getElementById("indexTable").innerHTML = table;
                document.getElementById("Documentation").style.display = "none";
                document.getElementById("indexTable").style.display = "block" 
                // next time il use promises        
                countPersonWithHobbies(id);
            })
}
/**
 * 
 * @param {type} id
 * @returns {count of persons with given hobby}
 */
function countPersonWithHobbies(id) {
    fetch(URLpvhc + id).then(res => res.json())
            .then(data => {
                document.getElementById("count").innerHTML = "Number of persons with hobby is: " + data;



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
