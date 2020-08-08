
var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var chatOpen = true;
var span = document.getElementsByClassName("close")[0];
var loadNotification = true;
let friendsList = new Map();
var myId;
var openChatId;

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
        url: "FindMyMatch",
        type: "POST",
        success: function(data) {
            $("#description").text(data);
        }
    })
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
            friendCommand: "totFriends"
        },success: function(data) {
            if (data !== "noFriend") {
                var names = data.split(" ");
                for (var i = 0; i < names.length; i++) {
                    var name = names[i];
                    var id = names[i + 1];
                    if (loadNotification) {
                        loadNotification = false;
                        addToList(name,id);
                    }

                }
            }
        }});
    // findNext();
    // getMessege();
}


function findNext() {
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data:{
            matchCommand: "next"
        },
        success: function (data) {
            if(data === "null"){
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
            if(data === "null"){
                alert("no more people for now");
                $("#description").text("WAITING");
            }else{
                $("#description").text(data);
            }
        }
    });
}

setInterval(function () {
    console.log("getFriendsList");
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data: {
            friendCommand: "friends"
        },success: function(data) {
            if (data !== "noFriend") {
                console.log(data);
                var names = data.split(" ");
                for (var i = 0; i < names.length; i+=2) {
                    var name = names[i];
                    var id = names[i + 1];
                    if (loadNotification) {
                        loadNotification = false;
                        newfriend(name, id);
                    }

                }
            }
    }});
}, 10000);
//
function newfriend(name, id) {
    $("#matchMessage").text("you are Friends with " + name);
    modal.style.display = "flex";
    addToList(name, id)
}

function addToList(name, id) {
    var button = document. createElement("button");
    button.innerHTML = name;
    button.onclick = openForm;
    button.style.height = "10%";
    button.style.width = "100%";
    button.style.backgroundColor = "deeppink";
    button.style.color = "white";
    button.className = "friendButton";
    button.id = id;
    button.addEventListener("mouseout", function() {
        button.style.backgroundColor = "pink";
    });
    $("#matchChat").append(button);
}

function openForm(e) {
    openChatId = e.target.id;
    var name = e.target.innerHTML;
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
        $.ajax({
            url:"ChatServlet",
            type:"POST",
            data:{
                command:"message",
                msg:text,
                toId: openChatId.toString()
            }
        })
    }
}
//
//
//
// function getMessege() {
//     setTimeout(function (){while(true) {
//         setTimeout(function () {
//             var friends = $(".friendButton");
//             console.log("megobrebis raodeboba: " + friends.length);
//             for(var i = 0; i < friends.length; i++) {
//                 var to_id = friends[i].id;
//                 $.ajax({
//                     url: "ChatServlet",
//                     type: "POST",
//                     data: {
//                         command: "get",
//                         toId: to_id
//                     }, success: function (data) {
//                         console.log("RECIEVED " + msg + " FROM " + to_id);
//                         if (data !== "noMessege") {
//                             if (to_id === openChatId) {
//                                 var ms = document.createElement("p");
//                                 ms.innerText = data;
//                                 ms.style.width = "70%";
//                                 ms.style.wordWrap = "break-word";
//                                 ms.style.overflowY = "hidden";
//                                 ms.style.height = "auto";
//                                 ms.style.width = "70%";
//                                 ms.style.backgroundColor = "blue";
//                                 ms.style.color = "white";
//                                 ms.style.paddingRight = "3px";
//                                 ms.style.borderRadius = "5%";
//                                 ms.style.marginRight = "2px";
//                                 ms.style.fontSize = "14px";
//                                 ms.style.float = "right";
//                                 ms.style.marginTop = "5px";
//                                 $("#messageBox").append(ms);
//                             } else {
//                                 friends[i].style.backgroundColor = "red";
//                             }
//                         }
//                     }
//                 })
//             }
//             console.log("gmerto gvishvele")
//         }, 15000)
//     }}, 10000);
// }