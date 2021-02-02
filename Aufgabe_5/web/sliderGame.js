/**
 * @author Manuel Adams
 * @since 2019-12-25
 */
let webSocket;

document.addEventListener('DOMContentLoaded', init, false);

function init() {
    addListeners();
    initSocket();
    document.getElementById('login').hidden = false;
    document.getElementById('gameBoard').hidden = true;
}

function addListeners() {
    let button = document.getElementsByClassName('number');
    for (let i = 0; i < button.length; i++) {
        button[i].addEventListener('click', move, false);
    }

    let newgameButton = document.getElementById('newGameButton');
    newgameButton.addEventListener('click', startGame, false);

    let submitButton = document.getElementById('submitButton');
    submitButton.addEventListener('click', login, false);
}

function initSocket() {
    let uri = 'ws://' + location.hostname + ':' + location.port + '/SliderGame';
    console.log(uri);
    webSocket = new WebSocket(uri);

    webSocket.onopen = function () {
        console.log('Connected to server game socket');
    };
    webSocket.onmessage = function (e) {
        receiveMessage(e.data);
    };
    webSocket.onerror = function () {
        alert('Websocket error');
    };
    webSocket.onclose = function () {
        alert('Websocket connection closed');
    };
}

function login() {
    let username = document.getElementById('playerName').value;
    if (username.length <= 0) {
        alert('Leerer Nutzername');
    } else {
        sendMessage('LoginRequest', username);
    }
}

function sendMessage(type, message) {
    let messageString = JSON.stringify({
        type: type,
        value: message
    });
    console.log(
        ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
        messageString +
        "\n/////////////////////////////////////////////"
    );
    webSocket.send(messageString);
}

function receiveMessage(message) {
    console.log(
        "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n" +
        message +
        "\n/////////////////////////////////////////////"
    );
    let jsonMessage = JSON.parse(message);

    switch (jsonMessage.type) {
        case 'LoginResponse':
            onLoginResponse(jsonMessage.value, !jsonMessage.hasError);
            break;
        case 'PlayerListResponse':
            updatePlayerList(jsonMessage.value);
            break;
        case 'GameStartResponse':
            alert('Spielstart!');
            updateGame(jsonMessage.value);
            break;
        case 'MovementResponse':
            updateGame(jsonMessage.value);
            break;
        case 'GameWonResponse':
            alert(jsonMessage.value + ' hat gewonnen');
            init();
            break;
        case 'Error':
            alert(jsonMessage.value);
            break;
        default:
            console.log('Unhandled message type: ' + jsonMessage.type)
    }
}

function onLoginResponse(username, success) {
    if (!success) {
        // in error case the username stores the error value
        alert(username);
        return;
    }
    document.getElementById('playerNameField').innerHTML = username;
    document.getElementById('login').hidden = true;
    document.getElementById('gameBoard').hidden = false;
}

function startGame() {
    sendMessage('GameStartRequest');
}

function move(event) {
    console.log('Button: ' + event.target.innerHTML, false);
    let Id = event.target.innerHTML;
    sendMessage('MovementRequest', Id);
}

function updateGame(numbers) {
    document.getElementById('counter').innerText = '';
    for (let i = 0; i < numbers.length; i++) {
        if (i < 9) {
            if (numbers[i] === '0') {
                document.getElementById('button_' + (i + 1)).innerHTML = "&nbsp;";
            } else {
                document.getElementById('button_' + (i + 1)).innerHTML = numbers[i];
            }
        }
        else {
            document.getElementById('counter').innerText += numbers[i];
        }
    }
}

function updatePlayerList(playerList) {
    document.getElementById('playerList').innerText = playerList;
}
