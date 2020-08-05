// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
// btn.onclick = function() {
//     modal.style.display = "block";
// }

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}



function findNext() {
    console.log("findNext");
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data:{
            nextCommand: "next"
        },
        success: function (data) {
            if(data === "null"){
                console.log("null");
                alert("sorry no one left");
            }else {
                console.log("nextUser" + data);
                document.getElementById("description").textContent = data;
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
                findNext();
            }else {
                alert("you are Friends with " + data.substr(8));
                console.log("after alert");
                findNext();
            if(data == "next"){
                 findNext();
            }else {
                $("#matchMessage").text("you are Friends with " + data.substr(8));
                modal.style.display = "flex";
                 findNext();
            }
        }
    }});
}
setInterval(function () {
    console.log("getFriendsList");
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data: {
            matchCommand: "friends"
        },
        success: function (data) {
            console.log(data);
        }
    });
}, 5000);