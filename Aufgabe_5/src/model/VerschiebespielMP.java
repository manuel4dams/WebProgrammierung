package model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class VerschiebespielMP {

    public static VerschiebespielMP getSingleton() {
        if (me == null) me = new VerschiebespielMP();
        return me;
    }

    // In listeSpiel wird einer PlayerId ein eigenes Spiel zugeordnet
    private ConcurrentHashMap<Integer, Verschiebespiel> listeSpiel = new ConcurrentHashMap<Integer, Verschiebespiel>();

    // In dieser Liste werden alle Spieler gespeichert
    private CopyOnWriteArrayList<Player> listePlayer = new CopyOnWriteArrayList<Player>();

    // Wird für Sigleton gebraucht
    private static VerschiebespielMP me = null;

    private Player.State spielstatus = Player.State.WAITING;

    // Klasse als Singelton realisieren
    private VerschiebespielMP() {
    }

    public synchronized int getAnzahlSpieler() {
        return listePlayer.size();
    }

    public synchronized void newGame() {
        Verschiebespiel spiel = new Verschiebespiel();
        Integer[] grid = spiel.getGrid();

        for (Player aListePlayer : listePlayer) {
            Verschiebespiel kopie = new Verschiebespiel(grid);
            aListePlayer.status = Player.State.ACTIV;
            listeSpiel.put(aListePlayer.getId(), kopie);
            spielstatus = Player.State.ACTIV;
        }
    }

    public synchronized void resetGame() {
        System.out.println("VerschiebespielMP: Reset Game!");

        int anzahlSpieler = listePlayer.size();
        for (int i = anzahlSpieler - 1; i >= 0; i--) {
            Player player = listePlayer.get(i);
            listeSpiel.remove(player.getId());
            listePlayer.remove(player);
        }
    }

    public synchronized Verschiebespiel getSpiel(int spielerId) {
        System.out.println("VerschiebespielMP:" + listeSpiel.get(spielerId));
        return listeSpiel.get(spielerId);
    }

    public synchronized Player createPlayer(String name, int id) {
        if (spielstatus == Player.State.WAITING) {
            Player player = new Player(name);
            listePlayer.add(player);
            player.setId(id);
            player.setStatus(Player.State.WAITING);
            player.setPoints(0);

            return player;
        }
        return null;
    }

    public synchronized boolean removePlayer(int id) {
        Player player = null;
        for (Player p : listePlayer) {
            if (p.getId() == id) player = p;
        }

        if (player != null) {
            if (player.getStatus().equals(Player.State.ACTIV)) listeSpiel.remove(player.getId());
            listePlayer.remove(player);
            return true;
        }
        return false;
    }

    public synchronized Player getPlayer(int id) {
        Player player = null;
        for (Player p : listePlayer) {
            if (p.getId() == id) player = p;
        }
        return player;
    }

    public Player.State getSpielstatus() {
        return spielstatus;
    }

    public void setGameOver() {
        this.spielstatus = Player.State.GAMEOVER;
    }

    public void setActiv() {
        this.spielstatus = Player.State.ACTIV;
    }

    public void setWaiting() {
        this.spielstatus = Player.State.WAITING;
    }


    public boolean isGameOver() {
        return spielstatus == Player.State.GAMEOVER;
    }
}
