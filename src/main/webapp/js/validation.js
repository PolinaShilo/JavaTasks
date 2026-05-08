function validateNumber() {
    let num = document.getElementById("number").value;
    if(isNaN(num) || num === "") {
        alert("Enter a valid number");
        return false;
    }
    return true;
}
function validateRegistration() {
    let pwd = document.querySelector("input[name='password']").value;
    if(pwd.length < 8) {
        alert("Password too short");
        return false;
    }
    return true;
}