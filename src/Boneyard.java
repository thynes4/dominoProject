import java.util.ArrayList;
import java.util.Collections;

public class Boneyard {
    private final ArrayList<Domino> boneyard = new ArrayList<Domino>();

    public Boneyard() {
        for (int i = 0; i <= 6; i++) {
            for (int n = i; n <= 6; n++) {
                Domino d = new Domino(i, n);
                boneyard.add(d);
            }
        }
        Collections.shuffle(boneyard);
    }

    public boolean isEmpty() {
        return boneyard.size() == 0;
    }

    public ArrayList<Domino> getBoneyard() {
        return boneyard;
    }

//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//
//        for (Domino d : boneyard) {
//            sb.append(d).append(" ");
//        }
//        sb.append("\n");
//        return sb.toString();
//    }

    public String toString() {
        return "Boneyard contains " + boneyard.size() + " dominos\n";
    }

    public Domino draw() {
        return boneyard.remove(0);
    }
}
