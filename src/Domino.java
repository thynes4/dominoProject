public class Domino {
    private int first;
    private int second;

    public Domino (int first, int second) {
        this.first = first;
        this.second = second;
    }
    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public boolean contains (int n) {
        return first == n || second == n;
    }

    public String toString() {
        return "[" + first + "  " + second + "]";
    }
    public void rotate() {
        int temp = first;
        first = second;
        second = temp;
    }
}
