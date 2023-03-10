import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Human player used in commandline and javafx version
 */
public class HumanPlayer {
    private final ArrayList<Domino> rack = new ArrayList<>();
    /**
     * initializes human player
     * @param boneyard takes dominos from the boneyard for computers hand
     */
    public HumanPlayer(Boneyard boneyard) {
        for (int i = 0; i < 7; i++) {
            rack.add(boneyard.draw());
        }
    }

    /**
     * @return number of dominos in player rack
     */
    public int getSize() {
        return rack.size();
    }

    /**
     * @return string representation of the players domino tray
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!rack.isEmpty()) {
            sb.append("         ");

            for (Domino d : rack) {
                sb.append(rack.indexOf(d));
                sb.append("      ");
            }
            sb.append("\n");
        }
        sb.append("Tray: [");

        if (rack.isEmpty()) {
        sb.append("]\n");
        return sb.toString();
        }

        for (Domino domino : rack) {
            sb.append(domino);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]\n");
        return sb.toString();
    }

    /**
     * @param index index of domino to remove from player rack
     * @return the removed domino
     */
    public Domino remove (int index) {
        Domino removed = rack.get(index);
        rack.remove(removed);
        return removed;
    }
    /**
     * @param d domino to remove from player rack
     * @return the removed domino
     */
    public Domino remove (Domino d) {
        rack.remove(d);
        return d;
    }
    /**
     * rotates the domino at the given index in the player rack
     */
    public void rotateAt(int index) {
        Domino d = rack.get(index);
        d.rotate();
        rack.set(index, d);
    }
    /**
     * returns the player rack
     */
    public ArrayList<Domino> getRack() {
        return rack;
    }

    /**
     * @param index index of domino to return
     * @return domino at current index
     */
    public Domino get (int index) {
        return rack.get(index);
    }

    /**
     * @param d domino to add to player rack
     */
    public void add (Domino d) {
        rack.add(0, d);
    }
    /**
     * Draws the player rack for the javafx version also contains the logic to set and remove selected status of a
     * domino when they are clicked
     * @param root borderpane to be drawn on
     */
    public VBox drawRack(BorderPane root) {
        VBox container = new VBox();
        VBox buffer = new VBox();
        buffer.setPrefHeight(30);
        container.setAlignment(Pos.CENTER);
        HBox playerRack = new HBox();
        playerRack.setSpacing(10);
        playerRack.setAlignment(Pos.CENTER);
        for (Domino d: rack) {
            HBox drawing = d.draw();
            drawing.setOnMouseClicked(event -> {
                for (Domino dom: rack) {
                    dom.removeSelected();
                }
                d.setSelected();
                root.setBottom(null);
                root.setBottom(drawRack(root));
            });
            playerRack.getChildren().add(drawing);
        }
        container.getChildren().addAll(playerRack, buffer);
        return container;
    }

}
