
var modal = document.getElementById("myModal");
var picCon = document.getElementById("friendsPic");
var btn = document.getElementById("myBtn");
var chatOpen = true;
var span = document.getElementsByClassName("close")[0];
var span2 = document.getElementsByClassName("close")[1];
var loadNotification = true;
let messeges = new Map();
var myId;
var openChatId;


<<<<<<< HEAD
span2.onclick = function(){
    removeCurFriendPictures();
}
=======
>>>>>>> ceecf3b9b5ce7ada577741f9a6d7676c66189156

window.onclick = function(event) {
    if (event.target == modal) {
        closeFriendNot()
    }else if(event.target == picCon){
        closeFriendPic();
    }
}
function closeFriendNot(){
    modal.style.display = "none";
    loadNotification = true;
    console.log("close Friend NOt " + loadNotification);
}

function closeFriendPic(){
    removeCurFriendPictures();
    console.log("fried pcis removed");
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
                    if(name !== "") addToList(name,id);
                    i++;
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
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data: {
            friendCommand: "friends"
        },success: function(data) {
            if (data !== "noFriend") {
                console.log("attantion >>>" + data + loadNotification);
                var names = data.split(" ");
                for (var i = 0; i < names.length; i+=2) {
                    var name = names[i];
                    var id = names[i + 1];
                    if (loadNotification === true) {
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
    //clear all texts that are sent
    var nd= document.getElementById("messageBox");
    while (nd.firstChild) {
        nd.removeChild(nd.firstChild);
    }
    chatBox.style.display = "flex";
    chatBox.style.flexDirection="column";
    curChat = chatBox;
    if(messeges.has(openChatId)) {
        e.target.style.backgroundColor = "deeppink";
        var t = messeges.get(openChatId);
        writeRecievedMessege(t);
    }
    $.ajax({
        url: "ChatServlet",
        type: "POST",
        data: {
            command: "checkStatus",
            frdId: openChatId
        }, success: function (data) {
            var nxtBt = document.getElementById("nextStpBtn");
            if(data === "true") addSeePicturesButton(1);
            else addSeePicturesButton(0)
        }
    });
}

function chatClose() {
    curChat.style.display="none";
    openChatId = -1;
}

function sendMessage(e) {
    var chat = $("#message");
    var text = chat.val();
    if(e.target.id === "nextStpBtn"){
        text = "code:NEXTSTEP";
    }else if (chat.length != 0) {
        chat.val("");
        var ms = document.createElement("p");
        ms.innerText = text;
        ms.style.width = "70%";
        ms.style.wordWrap = "break-word";
        ms.style.overflowY = "hidden";
        ms.style.height = "auto";
        ms.style.width = "70%";
        ms.style.backgroundColor = "pink";
        ms.style.color = "white";
        ms.style.paddingLeft = "3px";
        ms.style.borderRadius = "5%";
        ms.style.marginLeft = "2px";
        ms.style.fontSize = "14px";
        ms.style.marginTop = "5px";
        $("#messageBox").append(ms);
        var el = document.getElementById("messageBox");
        el.scrollTop = el.scrollHeight;
    }
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


setInterval(function () {
    var friends = document.getElementsByClassName("friendButton");
    for (var i = 0; i < friends.length; i++) {
        var from_id = friends[i].id;
        var frBt = friends[i];
        $.ajax({
            url: "ChatServlet",
            type: "POST",
            data: {
                command: "get",
                fromId: from_id
            }, success: function (data) {
                if (data !== "noMessege") {
                    if (from_id === openChatId) {
                        writeRecievedMessege(data);
                    } else {
                        console.log(frBt);
                        frBt.style.backgroundColor = "red";
                        messeges.set(from_id, data);
                    }
                }
            }
        })
    }
}, 8000);

function writeRecievedMessege(data) {
    var sp = data.split("|");
    for(var i = 0; i < sp.length; i++) {
        var msText = sp[i];
        var ms = null;
        if(msText === "code:NEXTSTEP"){
            ms = document.createElement("button");
            ms.innerText = "NEXT STEP?";
            ms.style.height = "auto";
            ms.style.width = "70%";
            ms.style.backgroundColor = "blue";
            ms.style.color = "white";
            ms.style.paddingRight = "3px";
            ms.style.borderRadius = "5%";
            ms.style.marginRight = "12%";
            ms.style.fontSize = "14px";
            ms.style.float = "right";
            ms.style.marginTop = "5px";
            ms.onclick = nextStepAccepted;
        }else if(msText === "code:NEXTSTEPACCEPTED"){
            addSeePicturesButton(1);
        }else {
            ms = document.createElement("p");
            ms.innerText = msText;
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
        }
        messeges.delete(openChatId);
        $("#messageBox").append(ms);
        var el = document.getElementById("messageBox");
        el.scrollTop = el.scrollHeight;
    }
}

function nextStepAccepted() {
    addSeePicturesButton(1);
    $.ajax({
        url: "ChatServlet",
        type: "POST",
        data: {
            command: "nextstep",
            frdId: openChatId
        }, success: function (data) {
        }
    });
}

function addSeePicturesButton(indicator){
    var nxtBt = document.getElementById("nextStpBtn");
    if(indicator === 1) {
        if(document.getElementsByClassName("btn step").length == 1) {
            nxtBt.disabled = true;
            var btn = document.createElement("button");
            btn.className = "btn step";
            btn.innerText = "See Pics";
            btn.onclick = seeFriendsPics;
            $("#chatBox").append(btn);
        }
    }else{
        nxtBt.disabled = false;
        if(document.getElementsByClassName("btn step").length === 2) {
            var chtb = document.getElementById("chatBox");
            chtb.removeChild(chtb.lastChild);
        }
    }
}

function seeFriendsPics() {
    $.ajax({
        url: "FriendsPicsServlet",
        type: "POST",
        data: {
            command: "showpics",
            frdId: openChatId
        }, success: function (data) {
            if(data === "EMPTY"){

            }else{
                var picList = document.getElementById("picList");
                var sp = data.split(" ");
                for(var i = 0; i < sp.length; i++){
                    var path = sp[i];
                    var img = document.createElement("img");
                    img.src = path;
                    img.className = "image";
                    picList.append(img);
                }
                picCon.style.display = "flex";
                picCon.style.flexDirection = "row";
            }
        }
    });
}

function removeCurFriendPictures() {
    var picList = document.getElementById("picList");
    var childSize = picList.children.length;
    if(childSize > 1){
        for(var i = 1; i > childSize; i++){
            picList.removeChild(picList.childNodes[i]);
        }
    }
    picCon.style.display ="none";
}