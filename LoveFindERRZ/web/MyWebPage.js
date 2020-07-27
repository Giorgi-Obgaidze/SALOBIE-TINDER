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
                var image = new Image();
                image.height = 200;
                image.width = 300;
                image.title = file.name;
                image.src = this.result;
                image.style.padding = "10px";
                //var close = document.createElement('span');
                // close.style.color = "#aaa";
                // close.style.float = "right";
                // close.style.fontSize = "26px";
                // close.style.fontWeight = "bold";
                // preview.appendChild(close);
                preview.appendChild( image );
            });

            reader.readAsDataURL(file);
        }else{
            return alert("File type is not supported or it is not an image!")
        }

    }

}
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