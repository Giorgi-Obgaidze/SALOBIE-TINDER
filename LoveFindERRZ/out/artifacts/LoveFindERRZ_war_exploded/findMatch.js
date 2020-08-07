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





function findNext() {
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data:{
            nextCommand: "next"
        },
        success: function (data) {
           if(data == "null"){
               alert("no more people for now");
               $("#description").text("WAITING");
           }else{
               $("#description").text(data);
           }
        }
    });
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
                        newfriend(name);
                    }

                }
            }
    }});
}, 7000);

function newfriend( name) {
    $("#matchMessage").text("you are Friends with " + name);
    modal.style.display = "flex";
    var button = document. createElement("button");
    button.innerHTML = name;
    button.style.height = "10%";
    button.style.width = "100%";
    button.style.backgroundColor = "deeppink";
    button.style.color = "white";
    button.addEventListener("mouseout", function() {
        document.getElementById("myID").style.backgroundColor = "pink";
    });
    $("#matchChat").append(button);
}