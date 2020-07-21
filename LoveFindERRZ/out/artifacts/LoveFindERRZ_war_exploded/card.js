// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
// btn.onclick = function() {
//     modal.style.display = "block";
// }

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

//set smaple text
var semp = document.getElementById("sampleCard");
semp.value = " საყვარელი სპორტი: კიორლინგი \n სურვილები: ოოპ-ს არ შეტენვა \n ჰობი: წყალში ხელების ტყაპუნი \n წიგნები რომლებსაც ვკითხულობ: უნივერსიტეტიდან სალობიემდე \n საქმიანობა: ჟღურტული";

function updateWordCount(e) {
    if(e.keyCode == 32 || e.keyCode  == 8){
        var count = document.getElementById("countWord");
        var curNum = countWord();
        count.innerHTML = "სიტყვების რაოდენობა თქვენს ბარათში - " + curNum;
    }
}

function countWord(){
    var text = document.getElementById("cardTextArea");
    var a = text.value.split(" ");
    var curNum = a.length;
    return curNum;
}

function submit() {
    var text = document.getElementById("cardTextArea");
    var numC = text.value.length;
    if(numC == 0){
        alert("შეავსეთ ბარათის ველი");
    }else {
        var curNum = countWord();
        if(curNum > 50) alert("ბარათში სიტყვების რაოდენობა მეტია 50-ზე");
        else{
            var post = document.getElementById("card");
            post.submit();
        }
    }
}