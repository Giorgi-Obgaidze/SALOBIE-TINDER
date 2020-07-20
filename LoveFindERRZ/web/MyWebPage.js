const newFile = document.getElementById("imageID");
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
});