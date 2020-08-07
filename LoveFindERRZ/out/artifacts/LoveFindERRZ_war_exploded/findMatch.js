
var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var chatOpen = true;
var span = document.getElementsByClassName("close")[0];
var loadNotification = true;
span.onclick = function() {
    modal.style.display = "none";
    loadNotification = true;
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        loadNotification = true;
    }
}


window.onload=function () {
    $.ajax({
        url: "ChatServlet",
        type: "POST",
        data: {
            command: "create"
        }
        });
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data: {
            matchCommand: "totFriends"
        },success: function(data) {
            if (data !== "noFriend") {
                console.log("onload req >> " + data);
                var names = data.split(" ");
                for (var i = 0; i < names.length; i++) {
                    var name = names[i];
                    if (loadNotification) {
                        loadNotification = false;
                        addToList(name);
                    }

                }
            }
        }});
    recieveMessage();
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

function newfriend(name) {
    $("#matchMessage").text("you are Friends with " + name);
    modal.style.display = "flex";
    addToList(name)
}

function addToList(name) {
    var button = document. createElement("button");
    button.innerHTML = name;
<<<<<<< HEAD
=======
    button.onclick = openForm;
>>>>>>> 69d81b2d4d2a26da78eae6b6ec91fdd7960814cb
    button.style.height = "10%";
    button.style.width = "100%";
    button.style.backgroundColor = "deeppink";
    button.style.color = "white";
    button.addEventListener("mouseout", function() {
<<<<<<< HEAD
        document.getElementById("myID").style.backgroundColor = "pink";
=======
        button.style.backgroundColor = "pink";
>>>>>>> 69d81b2d4d2a26da78eae6b6ec91fdd7960814cb
    });
    $("#matchChat").append(button);
}

function openForm(e) {
    var name = e.target.innerHTML;
    console.log("CHAT MUST BE OPEN");
    $("#messageTo").text(name);
    var chatBox = document.getElementById("chatBox")
    chatBox.style.display = "flex";
    chatBox.style.flexDirection="column";
    curChat = chatBox;
}

function chatClose() {
    curChat.style.display="none";
}

function sendMessage() {
    var chat = $("#message");
    var text = chat.val();
    if(chat.length != 0){
        chat.val("");
        var ms = document.createElement("p");
        ms.innerText = text;
        ms.style.width = "70%";
        ms.style.wordWrap= "break-word";
        ms.style.overflowY= "hidden";
        ms.style.height= "auto";
        ms.style.width = "70%";
        ms.style.backgroundColor = "pink";
        ms.style.color = "white";
        ms.style.paddingLeft = "3px";
        ms.style.borderRadius = "5%";
        ms.style.marginLeft = "2px";
        ms.style.fontSize = "14px";
        ms.style.marginTop = "5px";
        $("#messageBox").append(ms);
    }
}

async function recieveMessage() {

    var chat = $("#message");
    var text = chat.val();
    if (chat.length != 0) {
        chat.val("");
        var ms = document.createElement("p");
        ms.innerText = text;
        ms.style.width = "70%";
        ms.style.wordWrap = "break-word";
        ms.style.overflowY = "hidden";
        ms.style.height = "auto";
        ms.style.width = "70%";
        ms.style.backgroundColor = "blue";
        ms.style.color = "white";
        ms.style.paddingRight = "3px";
        ms.style.borderRadius = "5%";
        ms.style.marginRight = "2px";
        ms.style.fontSize = "14px";
        ms.style.float = "right";
        ms.style.marginTop = "5px";
        $("#messageBox").append(ms);
    }
}