package model;

import java.util.Random;

/**
 * In dieser Klasse wird die Spiellogik des Verschiebe-Spiels implementiert
 * Sie realisiert die Schnittstelle zwischen GUI und Spiel.
 * <p>
 * Die Ziffern des Spiels werden von links nach rechts und oben nach unten in einer Liste gespeichert.
 * Das leere Feld wird durch eine leere Position in der Liste realisiert.
 * Enthï¿½lt die Liste von links nach rechts gelesen die Ziffernreihenfolge 1,2,3,4,5,6,7,8,null so ist das Spiel gewonnen
 * Das Spiel beginnt mit einer unsortierten Liste von Werten  - mit dem leeren Feld am Ende.
 * Die Position einer Ziffer wird durch einen Zufallssgenerator ermittelt.
 * In einem Zï¿½hler ( statische Eigenschaft ) werden die Anzahl der Verschiebungen wï¿½hrend dem Spiel gespeichert
 *
 * @author Prof. S. Keller
 */

public class Verschiebespiel {
    static final int GRIDSIZE = 3;

    Integer grid[] = new Integer[GRIDSIZE * GRIDSIZE];
    Random zufall = new Random();
    int movecount = 0;

    /**
     * Konstruktor der Klasse
     * Initialisiert ein array von Integer-Objekten.
     * <p>
     * Die Ziffern 1 - 8 werden an zufällig errechneten Positionen gespeichert,
     * so dass man eine unsortierte Liste erhält.
     * Der letzte Wert im array ist zu Beginn null
     * <p>
     * Der Zähler movecount wird auf 0 gesetzt
     */
    public Verschiebespiel() {
        for (int i = 0; i < GRIDSIZE * GRIDSIZE; i++) grid[i] = new Integer(i + 1);
        grid[(GRIDSIZE * GRIDSIZE) - 1] = null;
        this.mische();
        movecount = 0;
    }

    /**
     * Konstruktor der Klasse
     * Initialisiert ein array von Integer-Objekten mit den im Parameter ziffern vorgegebenen Werten
     * Der letzte Wert im array ist zu Beginn null
     * <p>
     * Der Zähler movecount wird auf 0 gesetzt
     *
     * @param ziffern Array von Integer-Objekten
     */
    public Verschiebespiel(Integer[] ziffern) {
        for (int i = 0; i < GRIDSIZE * GRIDSIZE; i++) grid[i] = ziffern[i];
        grid[(GRIDSIZE * GRIDSIZE) - 1] = null;
        System.out.println(" new Verschiebespiel: " + grid);
        movecount = 0;
    }

    //  Mischt die Zahlen im grid
    private void mische() {
        int zufallsposition;

        for (int i = 0; i < GRIDSIZE * GRIDSIZE - 1; i++) {   // zufällige Position zum tauschen errechnen
            zufallsposition = zufall.nextInt() % 8;
            if (zufallsposition < 0) zufallsposition = -zufallsposition;


            //tausche position 0 mit zufallsposition
            Integer temp = grid[zufallsposition];
            grid[zufallsposition] = grid[0];
            grid[0] = temp;
        }
    }

    // Freie Position suchen
    private int getFrei() {
        int i = 0, stelle = -1;

        while (grid[i] != null) i++;
        stelle = i;

        return stelle;
    }

    /**
     * Die Methode move() verschiebt das Feld mit der 'ziffer',
     * falls das Nachbarfeld leer ist
     *
     * @param ziffer zu verschiebende Ziffer
     * @return true, wenn eine Verschiebung durchgefï¿½hrt wurde
     * false, wenn keine Verschiebung stattgefunden hat
     */

    public boolean move(Integer ziffer) {
        int von = -1, nach = -1;
        Integer i = null;
        boolean move = false;

        for (int y = 0; y < GRIDSIZE * GRIDSIZE; y++) {
            i = grid[y];
            if (i != null && i.equals(ziffer)) von = y;
        }
        if (von != -1) {
            nach = this.getFrei();
            if (GRIDSIZE == 2) {
                switch (von) {
                    case 0:
                        if (nach == 1 || nach == 2) move = true;
                        break;
                    case 1:
                        if (nach == 0 || nach == 3) move = true;
                        break;
                    case 2:
                        if (nach == 0 || nach == 3) move = true;
                        break;
                    case 3:
                        if (nach == 1 || nach == 2) move = true;
                        break;
                    default:
                        move = false;
                }

            } else {
                switch (von) {
                    case 0:
                        if (nach == 1 || nach == 3) move = true;
                        break;
                    case 1:
                        if (nach == 0 || nach == 2 || nach == 4) move = true;
                        break;
                    case 2:
                        if (nach == 1 || nach == 5) move = true;
                        break;
                    case 3:
                        if (nach == 0 || nach == 6 || nach == 4) move = true;
                        break;
                    case 4:
                        if (nach == 1 || nach == 3 || nach == 5 || nach == 7) move = true;
                        break;
                    case 5:
                        if (nach == 8 || nach == 2 || nach == 4) move = true;
                        break;
                    case 6:
                        if (nach == 7 || nach == 3) move = true;
                        break;
                    case 7:
                        if (nach == 6 || nach == 8 || nach == 4) move = true;
                        break;
                    case 8:
                        if (nach == 5 || nach == 7) move = true;
                        break;
                    default:
                        move = false;
                }
            }
            if (move) {
                grid[nach] = grid[von];
                grid[von] = null;
                movecount = movecount + 1;
            }
        }
        return move;
    }

    /**
     * Die Methode getMoveCount() liefert die Anzahl der Verschiebungen
     *
     * @return Anzahl der Verschiebungen
     */
    public int getMoveCount() {
        return movecount;
    }

    /**
     * Die Methode liefert die array-Werte als String. Die leere Stelle im array wird mit 0 angegeben
     *
     * @return Zahlen im Grid als String.
     */

    public int[] toArray() {
        int[] nummern = new int[9];

        for (int i = 0; i < (GRIDSIZE * GRIDSIZE); i++) {
            if (grid[i] != null) {
                nummern[i] = grid[i].intValue();
            } else {
                nummern[i] = 0;
            }
        }

        return nummern;

    }

    ;

    public String toString() {
        String nummernstring = new String("");
        Integer i;

        for (int y = 0; y < (GRIDSIZE * GRIDSIZE) - 1; y++) {
            i = grid[y];
            if (i != null) nummernstring = nummernstring + i.toString() + ",";
            else nummernstring = nummernstring + "0" + ",";
        }

        if (grid[(GRIDSIZE * GRIDSIZE) - 1] != null)
            nummernstring = nummernstring + grid[(GRIDSIZE * GRIDSIZE) - 1].toString();
        else nummernstring = nummernstring + "0";

        return nummernstring;

    }

    ;


    /**
     * Die Methode getGrid() liefert das Integer-Array, in dem die Ziffern des Spiels gespeichert sind
     *
     * @return Integer[], in dem die Ziffern von links nach recht / oben nach unten gespeichert sind
     * Das leere Feld des Spiels wird durch durch den Wert null reprï¿½sentiert
     */
    public Integer[] getGrid() {
        return grid;
    }


    /**
     * Die Methode richtigeReihenfolge()prï¿½ft, ob die Ziffern in der richtigen
     * Reihenfolge 1,2,3,4,5,6,7,8,null im in der Liste stehen
     *
     * @return true , falls die richtige Reihenfolge erkannt wurde, sonst false
     */
    public boolean richtigeReihenfolge() {
        boolean erg = false;
        for (int i = 0; i < GRIDSIZE * GRIDSIZE - 1; i++) {
            Integer wert = grid[i];
            if (wert != null && wert.intValue() == i + 1) erg = true;
            else return false;
        }
        return erg;
    }

    /**
     * Funktion zum Testen von GameOver. Setzt die Richtig Reihenfolge im Integer-Array
     */
    public boolean TestSpielEnde() {
        if (movecount > 5) {
            for (int i = 0; i < GRIDSIZE * GRIDSIZE; i++) {
                grid[i] = new Integer(i + 1);
                if (i == (GRIDSIZE * GRIDSIZE) - 1) grid[i] = null;
            }
            return true;
        } else return false;
    }

    public Integer getGridElement(int x, int y) {
        int position = GRIDSIZE * x + y;
        return this.grid[position];
    }
}
