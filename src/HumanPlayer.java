import java.util.ArrayList;

public class HumanPlayer {
    private ArrayList<Domino> rack = new ArrayList<>();

    public HumanPlayer(Boneyard boneyard) {
        for (int i = 0; i < 7; i++) {
            rack.add(boneyard.draw());
        }
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tray: [");

        for (Domino domino : rack) {
            sb.append(domino);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]\n");
        return sb.toString();
    }

    public void add (Domino d) {
        rack.add(0, d);
    }

}
