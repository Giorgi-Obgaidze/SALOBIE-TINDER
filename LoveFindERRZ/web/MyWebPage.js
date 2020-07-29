var id = 0;
var images = [];
<<<<<<< HEAD
var availableID = [0, 1, 2 , 3, 4, 5];
=======
var availableID = [0, 1, 2, 3, 4, 5];
>>>>>>> 79115e691f89a3123098fe2112227e19f497fe42
function imagePreview() {

    var preview = document.querySelector('#preview');
    var files   = document.querySelector('input[type=file]').files;

    if (files) {
        [].forEach.call(files, readImage);
    }

    function readImage(file) {
        //console.log(file.toString());
        // Make sure `file.name` matches our extensions criteria
        if ( /\.(jpe?g|png|gif)$/i.test(file.name) ) {
            var reader = new FileReader();

            reader.addEventListener("load", function () {
                if(availableID.length == 0){
                    return alert("image limit has been reached");
                }else {
                    var image = document.createElement("IMG");
                    //new Image();
                    var close = document.createElement("button");
                    //var loc_id;
                    //if (availableID.length > 0) {
                    var  loc_id = availableID.pop();
                    //} else {
                      //  loc_id = id;
                    //}

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
                        console.log(image.id);
                        console.log(close.id);
                        availableID.push(parseInt(image.id.substr(5), 10));
                        notNeeded.parentNode.removeChild(notNeeded);
                        suicideButton.parentNode.removeChild(suicideButton);
                        //document.getElementById(image.id).remove();
                        //document.getElementById(close.id).remove();
                        //document.removeChild(notNeeded);
                        //document.removeChild(suicideButton);
                    }
                    //image.appendChild(close);
                    // preview.appendChild(close);
                    // preview.appendChild( image );
                }
            });
                reader.readAsDataURL(file);
        }else{
            return alert("File type is not supported or it is not an image!")
        }

    }
}

// document.getElementById("button0").onclick = function(){
//     consol.log("blaaaa");
//     let notNeeded = document.getElementById("0");
//     notNeeded.parentNode.removeChild(notNeeded);
// }

//document.querySelector('#file-input').addEventListener("change", previewImages);



/*<!--const newFile = document.getElementById("imageID");
const imageContainer = document.getElementById("myImage");
const newImage = imageContainer.querySelector(".profile_image");
const defaultMassage = imageContainer.querySelector(".default_image_message");

newFile.addEventListener("change", function () {
    const image = this.files[1];
    if(image){
        const fileReader = new FileReader();
        defaultMassage.style.display = "none";
        newImage.style.display = "block";

        fileReader.addEventListener("load", function () {
            newImage.setAttribute("src", this.result);
        });
        fileReader.readAsDataURL(image);
    }else {
        defaultMassage.style.display = null;
        newImage.style.display = null;
        newImage.setAttribute("src", "");
    }
});*/