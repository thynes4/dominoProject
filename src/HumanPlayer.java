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

}
