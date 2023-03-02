import java.util.ArrayList;

public class ComputerPlayer {
    private ArrayList<Domino> rack = new ArrayList<>();
    public ComputerPlayer(Boneyard boneyard) {
        for (int i = 0; i < 7; i++) {
            rack.add(boneyard.draw());
        }
    }
    public String toString() {
        return "Computer has " + rack.size() + " dominos\n";
    }
    public ArrayList<Domino> getRack() {
        return rack;
    }
    public void add (Domino d) {
        rack.add(d);
    }

    public Domino remove (Domino d) {
        rack.remove(d);
        return d;
    }
}
