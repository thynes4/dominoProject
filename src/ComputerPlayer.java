import java.util.ArrayList;

/**
 * Computer player used in javafx and command line version
 */
public class ComputerPlayer {
    private final ArrayList<Domino> rack = new ArrayList<>();

    /**
     * initializes computer player
     * @param boneyard takes dominos from the boneyard for computers hand
     */
    public ComputerPlayer(Boneyard boneyard) {
        for (int i = 0; i < 7; i++) {
            rack.add(boneyard.draw());
        }
    }

    /**
     * @return string stating number of dominos computer has used in javafx and commandline version
     */
    public String toString() {
        if (rack.size() == 1) {
            return "Computer has one domino\n";
        } else {
            return "Computer has " + rack.size() + " dominos\n";
        }
    }

    /**
     * @return the computer rack
     */
    public ArrayList<Domino> getRack() {
        return rack;
    }

    /**
     * @param d domino to add to computer rack
     */
    public void add (Domino d) {
        rack.add(d);
    }
    /**
     * @param d domino to remove from computer rack
     */
    public Domino remove (Domino d) {
        rack.remove(d);
        return d;
    }
}
