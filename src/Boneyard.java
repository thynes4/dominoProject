import java.util.ArrayList;
import java.util.Collections;

/**
 * Boneyard representation used for both javafx and commandline versions
 */
public class Boneyard {
    private final ArrayList<Domino> boneyard = new ArrayList<>();

    /**
     * creates and fills the boneyard when initialized also shuffles it used in both commandline and javafx version
     */
    public Boneyard() {
        for (int i = 0; i <= 6; i++) {
            for (int n = i; n <= 6; n++) {
                Domino d = new Domino(i, n);
                boneyard.add(d);
            }
        }
        Collections.shuffle(boneyard);
    }

    /**
     * @return true if the boneyard is empty false otherwise used in both commandline and javafx version
     */
    public boolean isEmpty() {
        return boneyard.size() == 0;
    }

    /**
     * @return string of how many dominos are in the boneyard used in both commandline and javafx version
     */
    public String toString() {
        if (boneyard.size() == 1) return "Boneyard contains " + 1 + " domino\n";
        if (boneyard.isEmpty()) return "Boneyard is empty\n";
        return "Boneyard contains " + boneyard.size() + " dominos\n";
    }

    /**
     * @return the domino removed from the boneyard at the first index
     */
    public Domino draw() {
        return boneyard.remove(0);
    }
}
