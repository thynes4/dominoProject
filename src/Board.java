import java.util.ArrayList;

public class Board {
    private static final ArrayList<Domino> gameBoard = new ArrayList<>();

    public void addDominoRight (Domino d) {
        gameBoard.add(d);
    }
    public void addDominoLeft (Domino d){
        gameBoard.add(0,d);
    }
    public boolean validMoveLeft (Domino d) {
        return  gameBoard.size() == 0
                || d.getSecond() == gameBoard.get(0).getFirst()
                || d.getSecond() == 0
                || gameBoard.get(0).getFirst() == 0;
    }

    public int getFirstValue () {
        return gameBoard.get(0).getFirst();
    }
    public boolean isEmpty() {
        return gameBoard.size() == 0;
    }
    public int getLastValue () {
        return gameBoard.get(gameBoard.size() - 1).getSecond();
    }

    public boolean validMoveRight (Domino d) {
        return
                gameBoard.size() == 0
                ||d.getFirst() == gameBoard.get(gameBoard.size() - 1).getSecond()
                || d.getFirst() == 0
                || gameBoard.get(gameBoard.size() - 1).getSecond() == 0;
    }

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
}
