// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];
var loadNotification = true;
// When the user clicks on the button, open the modal
// btn.onclick = function() {
//     modal.style.display = "block";
// }

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
    loadNotification = true;
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        loadNotification = true;
    }
}


function findingAtTheBeginning(){
    document.getElementById("description").textContent = "WAITING";
    var next = generateNext();
    console.log("onload next " + next);
    loopNextGeneration();
}

function loopNextGeneration() {
    var next = generateNext();
    while (next.length == 0) {
        setTimeout(function () {
            console.log("looping " + next);
            next = generateNext();
        }, 2000)
    }
    document.getElementById("description").textContent = next;
}

function findNext() {
    var next = generateNext();
    if (next === "null") {
        alert("no more users for now");
        document.getElementById("description").textContent = "WAITING";
        while (next.length == 0) {
            setTimeout(function () {
                console.log(next);
                next = generateNext();
            }, 2000)
        }
    }
    document.getElementById("description").textContent = next;
}

function generateNext(){
    var status = "";
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data:{
            nextCommand: "next"
        },
        success: function (data) {
            status = data;
        }
    });
    console.log(status);
    return status;
}

function  matchPerson() {
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data: {
            matchCommand: "match"
        },
        success: function (data) {
            if(data === "next"){

            if (data === "next") {
                findNext();
            } else {
                alert("you are Friends with " + data.substr(8));
                findNext();
            if(data == "next"){
                 findNext();
            }else {
                 findNext();
                if (data === "next") {
                    findNext();
                } else {
                    $("#matchMessage").text("you are Friends with " + data.substr(8));
                    modal.style.display = "flex";
                    findNext();
                }
            }
        }
    }}});
}
setInterval(function () {
    console.log("getFriendsList");
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data: {
            matchCommand: "friends"
        },success: function(data) {
            if (data !== "noFriend") {
                var names = data.split(" ");
                for (var i = 0; i < names.length; i++) {
                    var name = names[i];
                    if (loadNotification) {
                        loadNotification = false;
                        $("#matchMessage").text("you are Friends with " + name);
                        modal.style.display = "flex";
                    }

                }
            }
    }});
}, 7000);