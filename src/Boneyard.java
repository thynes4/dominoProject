import java.util.ArrayList;
import java.util.Collections;

public class Boneyard {
    static ArrayList<Domino> boneyard = new ArrayList<Domino>();

    public final void initialize () {
        for (int i = 0; i <= 6; i++) {
            for (int n = 0; n <= 6; n++) {
                Domino d = new Domino(i, n);
                boneyard.add(d);
            }
        }
        Collections.shuffle(boneyard);
    }

    public boolean isEmpty() {
        return boneyard.size() == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Domino d : boneyard) {
            sb.append(d).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }

    public Domino draw() {
        Domino d = boneyard.get(0);
        boneyard.remove(0);
        return d;
    }
}
