import java.util.ArrayList;

public class ComputerPlayer {
    private ArrayList<Domino> rack = new ArrayList<Domino>();
    public ComputerPlayer(Boneyard boneyard) {
        for (int i = 0; i < 7; i++) {
            rack.add(boneyard.draw());
        }
    }

    public String toString() {
        return "Computer has " + rack.size() + " dominos\n";
    }

    public void add (Domino d) {
        rack.add(d);
    }
}
