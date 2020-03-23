/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//attributs
var URLTest = "http://localhost:8080/2020CA2/api/person/all"
var URLpopulate = "/2020CA2/api/master/populate"
var URLAll = "/2020CA2/api/person/all"
var URLpvh = "/2020CA2/api/person/personsWithHobbyName/"
var URLpvhc = "/2020CA2/api/person/countPersonsWithHobbyName/"
var URLPerson = "/2020CA2/api/person/"
const fillBtn = document.getElementById("loadPersons")
const populateBtn = document.getElementById("populate")
const apidocBtn = document.getElementById("APIDOC")
const chosenHobby = document.getElementById("txt")
const POSTTest = document.getElementById("newPerson")
const PUTTest = document.getElementById("editPerson")


//events
populateBtn.addEventListener("click", populateTable, false)
populateBtn.addEventListener("click", () => {console.log(data);
    document.getElementById("count").innerHTML = "Database populated successfully";}, false)
fillBtn.addEventListener("click", fillTable, false)
chosenHobby.addEventListener("blur", eventHandler, false)
apidocBtn.addEventListener("click", ShowApi, false)
POSTTest.addEventListener("click", newPerson, false)
PUTTest.addEventListener("click", editPerson, false)

function  ShowApi() {
    document.getElementById("Documentation").style.display = "block"
    document.getElementById("count").style.display = "none"
    document.getElementById("input").style.display = "none"
    document.getElementById("indexTable").style.display = "none"
}
function  HideApi() {
    document.getElementById("Documentation").style.display = "none"
    document.getElementById("count").style.display = "block"
    document.getElementById("input").style.display = "block"
    document.getElementById("indexTable").style.display = "block"
}

HideApi();

function populateTable() {
    fetch(URLpopulate, {
        method: 'POST',
        body: '',
        headers: {'Content-Type': 'application/json'}
    })
            .then(res => res.json())
            .then(data => {
            })
}

function Person(first, last, email) {
    this.firstName = first;
    this.lastName = last;
    this.email = email;
}

let person = new Person("JustAdded", "AsATest", "ThisNeedsToExist")

function newPerson(person) {
    fetch(URLPerson, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(person)
    }).then(res => res.json())
            .then(data => {
                console.log(data);
            })
}

function editPerson() {
    let per = new Person("JustEdited", "AlsoATest", "HopefullyThisWorks")
    console.log("Per" + per);
    console.log("Per og Jason" + JSON.stringify(per));
    console.log("URL1" + URLPerson + 1);
    fetch(URLPerson + 1, {
//            document.getElementById("txt").target.value, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(per)
    }).then(res => res.json())
            .then(data => {
                console.log(data);
                console.log("URL2" + URLPerson + 1);
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
                HideApi();
            })
}

/**
 * 
 * @param {type} name
 * @returns {table data for all persons with given hoppy id}
 */
function personsWithHobbies(name) {
    fetch(URLpvh + name).then(res => res.json())
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
                HideApi();
                // next time I'll use promises        
                countPersonWithHobbies(name);
            })
}
/**
 * 
 * @param {type} id
 * @returns {count of persons with given hobby}
 */
function countPersonWithHobbies(name) {
    fetch(URLpvhc + name).then(res => res.json())
            .then(data => {
                document.getElementById("count").innerHTML = "Number of persons with hobby: \"" + name + "\" is: " + data;
            });
}
/*
 * Manage event and confirm data in development phase
 */
function eventHandler(e) {
// tag id givet fra event 
    var hobbyName = e.target.value;
    console.log(hobbyName);

    personsWithHobbies(hobbyName);

}

//error handling if time
//fetch (...).then( res => {
//    if (res.ok) {
//        console.log("Success")
//    }else {
//        console.log("Not successfull")
//    }
//}
