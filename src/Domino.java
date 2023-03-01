public class Domino {

    private int first;
    private int second;

    public Domino (int first, int second) {
        this.first = first;
        this.second = second;
    }

    public boolean contains (int n) {
        return first == n || second == n;
    }

    public String toString() {
        return "[" + first + " " + second + "]";
    }
}
