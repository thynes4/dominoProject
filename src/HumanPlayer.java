import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class HumanPlayer {
    private final ArrayList<Domino> rack = new ArrayList<>();
    public HumanPlayer(Boneyard boneyard) {
        for (int i = 0; i < 7; i++) {
            rack.add(boneyard.draw());
        }
    }
    public int getSize() {
        return rack.size();
    }
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
    public Domino remove (int index) {
        Domino removed = rack.get(index);
        rack.remove(removed);
        return removed;
    }
    public Domino remove (Domino d) {
        rack.remove(d);
        return d;
    }
    public void rotateAt(int index) {
        Domino d = rack.get(index);
        d.rotate();
        rack.set(index, d);
    }
    public ArrayList<Domino> getRack() {
        return rack;
    }
    public Domino get (int index) {
        return rack.get(index);
    }

    public void add (Domino d) {
        rack.add(0, d);
    }
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
