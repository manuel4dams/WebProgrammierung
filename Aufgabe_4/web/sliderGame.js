/**
 * @author Manuel Adams
 * @since 2018-12-10
 */
var request = null;

window.onload = function () {
    addListeners();
    newGameButtonListener();
    sendFirstrequest();
};

function addListeners() {
    let button = document.getElementsByClassName("number");
    for (let i = 0; i < button.length; i++) {
        button[i].addEventListener("click", sendData, false);
    }
}

function sendFirstrequest() {
    request = new XMLHttpRequest();
    request.onreadystatechange = receiveData;
    request.open("GET", "/Verschiebespiel", true);
    console.log("onload data send");
    request.send();
}

function sendData() {
    request = new XMLHttpRequest();
    let pressedButtonNumber = "button=" + this.innerHTML;
    console.log(pressedButtonNumber);

    request.onreadystatechange = receiveData;
    request.open("GET", "/Verschiebespiel?" + pressedButtonNumber, true);
    request.send();
}

function receiveData() {
    console.log("Server meldet sich mit readyState: " + request.readyState + "\n status server " + request.status);
    switch (request.readyState) {
        case 2:
            console.log("ready State = Anfrage wurde gesendet");
            break;
        case 3:
            console.log("readyState = ein Teil der Antwort vom Server erhalten\n Server Antwort: " + request.responseText);
            break;
        case 4:
            console.log("ready State = Antwort vom Server vollstaendig erhalten\n Server Antwort: " + request.responseText)

            let numbers = request.responseXML.getElementsByTagName("numbers");
            numbers = numbers[0].firstChild.nodeValue;
            let count = request.responseXML.getElementsByTagName("count");
            updateGame(numbers, count);
            if (request.responseXML.getElementsByTagName("status")[0].firstChild.nodeValue === "won") {
                alert("Gewonnen!");
            }
            break;
        default:
            console.log("noch kein open fuer XMLHttpRequest-Objekt erfolgt");
    }
}

function updateGame(numbers, count) {
    for (let i = 0; i < numbers.length; i++) {
        if (numbers[i] === '0') {
            document.getElementById("button_" + (i + 1)).innerHTML = "&nbsp;";
        } else {
            document.getElementById("button_" + (i + 1)).innerHTML = numbers[i];
        }
    }
    document.getElementById("counter").innerText = count[0].firstChild.nodeValue;
}

function newGameButtonListener() {
    document.getElementById("newGameButton").addEventListener("click", function(){
        request = new XMLHttpRequest();
        request.open("GET", "/Verschiebespiel?button=true", true);
        console.log("start new game");
        request.send();
        request.onreadystatechange = receiveData;
    });
}

