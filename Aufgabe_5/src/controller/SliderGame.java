package controller;

import helper.JsonHelper;
import model.JsonMessage;
import model.JsonMessageType;
import model.Player;
import model.Verschiebespiel;
import model.VerschiebespielMP;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * @author Manuel Adams
 * @since 2019-12-25
 */

@ServerEndpoint("/SliderGame")
public class SliderGame {

    // Map session id to username
    // private static HashMap<Session, String> playerList = new HashMap<>();
    private static ConcurrentHashMap<Session, String> playerList = new ConcurrentHashMap<>();
    private VerschiebespielMP sliderGameModel = VerschiebespielMP.getSingleton();

    @OnOpen
    public void open(Session session) {
        System.out.println("Connection opened with session id: " + session.getId());
    }

    @OnMessage
    public void message(Session session, String messageString) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("S" + session.getId() + " -- " + messageString);
        System.out.println("/////////////////////////////////////");

        JsonMessage message = JsonHelper.fromJson(messageString, JsonMessage.class);
        if (message == null) {
            sendMessage(session, new JsonMessage(JsonMessageType.Error, "Incoming message was null"));
            return;
        }

        switch (message.type) {
            case LoginRequest:
                onLoginRequest(session, message.value);
                break;
            case GameStartRequest:
                onGameStartRequest();
                break;
            case MovementRequest:
                onMovementRequest(session, message.value);
                break;
            default:
                System.err.println("Unhandled message type: " + message.type);
                break;
        }
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        System.out.println("Connection closed for session id: " + session.getId() + " with reason: " + reason);
        playerList.remove(session);
        sliderGameModel.removePlayer(Integer.parseInt(session.getId()));
        updatePlayerListResponse();
    }

    @OnError
    public void error(Session session, Throwable t) {
        System.err.println("Error for session id: " + session.getId());
        t.printStackTrace();
    }

    private void sendMessage(Session session, JsonMessage message) {
        String messageString = JsonHelper.toJson(message);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("S" + session.getId() + " -- " + messageString);
        System.out.println("/////////////////////////////////////");
        try {
            session.getBasicRemote().sendText(messageString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onLoginRequest(Session session, String username) {
        System.out.println("Login try by: " + username);

        // Handle duplicate name
        if (playerList.containsValue(username)) {
            sendMessage(session, new JsonMessage(JsonMessageType.LoginResponse, "Name ist schon vergeben", true));
            return;
        }
        if (sliderGameModel.getSpielstatus() == Player.State.ACTIV) {
            sendMessage(session, new JsonMessage(JsonMessageType.LoginResponse, "Spiel läuft schon, anmelden nicht möglich", true));
            return;
        }

        // Register player
        playerList.put(session, username);
        sliderGameModel.createPlayer(username, Integer.parseInt(session.getId()));

        // Send LoginResponse
        sendMessage(session, new JsonMessage(JsonMessageType.LoginResponse, username));

        System.out.println("Login done by: " + username);
        System.out.println("Now available users:");
        playerList.values().forEach(System.out::println);
        System.out.println();

        //  Send PlayerListResponse
        updatePlayerListResponse();
    }

    private void updatePlayerListResponse() {
        String playerListString = playerList.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.joining(", "));

        for (Session session : playerList.keySet()) {
            sendMessage(session, new JsonMessage(JsonMessageType.PlayerListResponse, playerListString));
        }
    }

    private void onGameStartRequest() {
        if (sliderGameModel.getSpielstatus() == Player.State.WAITING) {
            sliderGameModel.newGame();
            for (Session session : playerList.keySet()) {
                Verschiebespiel currentModel = sliderGameModel.getSpiel(Integer.parseInt(session.getId()));
                sendMessage(session, new JsonMessage(JsonMessageType.GameStartResponse, (currentModel.toString() + currentModel.getMoveCount()).replace(",", "")));
            }
        }
    }

    private void onMovementRequest(Session session, String movementString) {
        int movement;
        try {
            movement = Integer.parseInt(movementString);
        } catch (NumberFormatException | NullPointerException e) {
            sendMessage(session, new JsonMessage(JsonMessageType.Error, "Invalid movement input"));
            return;
        }

        Verschiebespiel currentModel = sliderGameModel.getSpiel(Integer.parseInt(session.getId()));
        if (sliderGameModel.getSpielstatus() == Player.State.ACTIV) {
            currentModel.move(movement);
            sendMessage(session, new JsonMessage(JsonMessageType.MovementResponse, (currentModel.toString() + currentModel.getMoveCount()).replace(",", "")));

            sliderGameModel.getSpiel(Integer.parseInt(session.getId())).TestSpielEnde();
            if (currentModel.richtigeReihenfolge()) {
                String winner = sliderGameModel.getPlayer(Integer.parseInt(session.getId())).getName();
                sliderGameModel.resetGame();
                sliderGameModel.setWaiting();
                for (Session s : playerList.keySet()) {
                    sendMessage(s, new JsonMessage(JsonMessageType.GameWonResponse, winner));
                }
                playerList.clear();
            }
        }
    }
}
