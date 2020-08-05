
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
            }
        }
    });
}