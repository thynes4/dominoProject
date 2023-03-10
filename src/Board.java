import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * board representation used in javafx and commandline versions
 */
public class Board {
    private static final ArrayList<Domino> gameBoard = new ArrayList<>();

    /**
     * adds a domino on the right side
     * @param d domino to be added
     */
    public void addDominoRight (Domino d) {
        gameBoard.add(d);
    }
    /**
     * adds a domino on the left side
     * @param d domino to be added
     */
    public void addDominoLeft (Domino d){
        gameBoard.add(0,d);
    }

    /**
     * checks if domino can be played on left side
     * @param d domino to be played
     * @return true if can false otherwise
     */
    public boolean validMoveLeft (Domino d) {
        return  gameBoard.size() == 0
                || d.getSecond() == gameBoard.get(0).getFirst()
                || d.getSecond() == 0
                || gameBoard.get(0).getFirst() == 0;
    }

    /**
     * @return number of pips on left side of the first domino
     */
    public int getFirstValue () {
        return gameBoard.get(0).getFirst();
    }

    /**
     * @return true if game board is empty
     */
    public boolean isEmpty() {
        return gameBoard.size() == 0;
    }

    /**
     * @return number of pips on right side of the last domino
     */
    public int getLastValue () {
        return gameBoard.get(gameBoard.size() - 1).getSecond();
    }

    /**
     * checks if domino can be played on right side
     * @param d domino to be played
     * @return true if can false otherwise
     */
    public boolean validMoveRight (Domino d) {
        return
                gameBoard.size() == 0
                ||d.getFirst() == gameBoard.get(gameBoard.size() - 1).getSecond()
                || d.getFirst() == 0
                || gameBoard.get(gameBoard.size() - 1).getSecond() == 0;
    }

    /**
     * @return String of commandline representation of the game board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < gameBoard.size(); i = i + 2) {
            sb.append(gameBoard.get(i));
        }
        sb.append("\n");
        sb.append("   ");
        for(int i = 1; i < gameBoard.size(); i = i + 2) {
            sb.append(gameBoard.get(i));
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * @return javafx vbox containing dominos of the game board placed in two rows staggered
     */
    public VBox drawBoard() {
        VBox boardDrawing = new VBox();
        HBox topRow = new HBox();
        HBox buffer = new HBox();
        HBox bottomRow = new HBox();

        boardDrawing.setAlignment(Pos.CENTER);
        topRow.setAlignment(Pos.CENTER);
        bottomRow.setAlignment(Pos.CENTER);

        boardDrawing.setSpacing(10);
        buffer.setPrefWidth(78);
        topRow.setSpacing(5);
        bottomRow.setSpacing(5);
        bottomRow.getChildren().add(buffer);

        for (int i = 0; i < gameBoard.size(); i = i + 2) {
            topRow.getChildren().add(gameBoard.get(i).draw());
        }

        for(int i = 1; i < gameBoard.size(); i = i + 2) {
            bottomRow.getChildren().add(gameBoard.get(i).draw());
        }
        boardDrawing.getChildren().addAll(topRow,bottomRow);
        return boardDrawing;
    }
}
