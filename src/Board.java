import java.util.ArrayList;

public class Board {
    private static ArrayList<Domino> gameBoard = new ArrayList<Domino>();

    public void addDominoRight (Domino d){ gameBoard.add(d); }
    public void addDominoLeft (Domino d){ gameBoard.add(0,d); }
    public static boolean validMoveLeft (Domino d) {
        return d.getSecond() == gameBoard.get(0).getFirst() || d.getSecond() == 0 || gameBoard.get(0).getFirst() == 0;
    }

    public static boolean validMoveRight (Domino d) {
        return d.getFirst() == gameBoard.get(gameBoard.size() - 1).getSecond()
                || d.getFirst() == 0
                || gameBoard.get(gameBoard.size() - 1).getSecond() == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < gameBoard.size(); i = i + 2) {
            sb.append(gameBoard.get(i));
        }
        sb.append("\n   ");
        for(int i = 1; i < gameBoard.size(); i = i + 2) {
            sb.append(gameBoard.get(i));
        }
        return sb.toString();
    }
}
