
function moveToLogin() {
    console.log("should log in");
    location.href = "logIn.jsp";
}

function checkCheckBox() {
    const s = document.getElementById("agreeTerms");
    if(s.checked){
        window.location.href = "registerAccount.jsp";
    }else{
        alert("PLEASE AGREE OUR TERMS OF CONDITION IF YOU WANT TO REGISTER");
    }
}