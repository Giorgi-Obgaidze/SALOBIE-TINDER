
function findNext() {
    $.ajax({
        url: "FindMyMatch",
        type: "POST",
        data:{
            command: "next"
        },
        success: function (data) {
            document.getElementById("description").textContent = data;
        }
    });
}