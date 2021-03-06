var id = 0;
var images = [];
var availableID = [5, 4, 3 , 2, 1, 0];
var maxImageCount = 6;
window.onload = function (){
    updateIdTable();
}
function imagePreview() {

    var preview = document.querySelector('#preview');
    var files   = document.querySelector('input[type=file]').files;

    if (files) {
        [].forEach.call(files, readImage);
    }

    function readImage(file) {
        // Make sure `file.name` matches our extensions criteria
        if ( /\.(jpe?g|png|gif)$/i.test(file.name) ) {
            var reader = new FileReader();

            reader.addEventListener("load", function () {
                if(availableID.length == 0){
                    return alert("image limit has been reached");
                }else {
                    var image = document.createElement("IMG");
                    var close = document.createElement("button");
                    var  loc_id = availableID.pop();

                    image.id = "image" + loc_id.toString();
                    close.id = "button" + loc_id.toString();
                    id++;
                    image.height = 200;
                    image.width = 300;
                    image.title = file.name;
                    image.src = this.result;
                    image.style.padding = "10px";
                    images.push(image);

                    //var close = document.createElement('span');
                    close.style.borderRadius = "50%";
                    close.style.height = "100%";
                    close.style.width = "auto";
                    close.style.position = "relative";
                    close.style.left = "-30";
                    close.style.color = "#aaa";
                    close.style.float = "right";
                    close.style.fontSize = "26px";
                    close.style.fontWeight = "bold";
                    close.innerHTML="X";
                    preview.appendChild(image);
                    preview.appendChild(close);
                    close.onclick = function () {
                        var notNeeded = document.getElementById(image.id);
                        var suicideButton = document.getElementById(close.id);
                        availableID.push(parseInt(image.id.substr(5), 10));
                        notNeeded.parentNode.removeChild(notNeeded);
                        suicideButton.parentNode.removeChild(suicideButton);

                    }
                }
            });
                reader.readAsDataURL(file);
        }else{
            return alert("File type is not supported or it is not an image!")
        }

    }
}


function ImageUpload(){
    let i;
    let data = [];
    for (i = 0; i < maxImageCount; i++) {
        if(id > 0 && availableID.indexOf(i) == -1) {
            let imageID = "image" + i.toString();
            console.log("new image IDs " + imageID);
            let image = document.getElementById(imageID);
            let imageTitle = image.title;
            if(imageTitle.length != 0){
                console.log("new image title " + imageTitle);
                let imageData = image.src.replace(/^data:image\/\w+;base64,/, "");
                let imgData = {elem: imageData, name: imageTitle, title: imageID};
                data.push(imgData);
            }
        }
    }
    $.ajax({
        url: "UploadImages",
        type: 'POST',
        data: JSON.stringify(data),
        dataType: 'json'
    });
    console.log(data);
    window.location.href = "logIn.jsp";
}

function deleteImage(data) {
    $.ajax({
        url: "RemoveImages",
        type: 'POST',
        data: {
            command: "deleteImage",
            data: data
        }
    });
    var id = data.substring(5, 6);
    availableID.push(parseInt(id));
    var image = document.getElementById(data.substring(0,6));
    var buttonId = "clear" + id;
    console.log("id: " + id);
    console.log("button_id: " + buttonId);
    var button = document.getElementById(buttonId);
    button.parentNode.removeChild(button);
    image.parentNode.removeChild(image);
}

function submitChanges() {
    var description = document.getElementById("info");
    $.ajax({
        url: "AddDescription",
        type: 'POST',
        data:{
            command: "changeDescription",
            description: description.value
        }
    });
    ImageUpload();
    window.location.href = "findMatch.jsp";
}

function updateIdTable() {
    var i;
    console.log(availableID.toString());
    for(i = 0; i < maxImageCount; i++){
        var image = document.getElementById("image" + i);
        if (image != null){
            console.log(i);
            for( var j = 0; j < availableID.length; j++){
                if ( availableID[j] == i) {
                    availableID.splice(j, 1);
                    j--;
                }
            }
        }
    }
    console.log(availableID.toString());
}
