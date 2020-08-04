
function findNext() {
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data:{
            nextCommand: "next"
        },
        success: function (data) {
            if(data == "null"){
                alert("sorry no one left");
            }else {
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
            if(data == "next"){
                findNext();
            }else {
                findNext();
                alert("you are Friends with " + data.substr(8));
            }
        }
    });
}