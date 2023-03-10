import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Domino object representation used in both commandline and javafx version
 */
public class Domino {
    //value of left half of domino
    private int first;
    //value of right half of domino
    private int second;
    //used in javafx version to mark the domino clicked by the user
    private boolean selected;

    /**
     * creates a domino
     * @param first value of left half
     * @param second value of right half
     */
    public Domino (int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return value of left side of domino
     */
    public int getFirst() {
        return first;
    }
    /**
     * @return value of right side of domino
     */
    public int getSecond() {
        return second;
    }

    /**
     * sets a domino as the selected domino for javafx version
     */
    public void setSelected() { selected = true; }

    /**
     * removes the selected property of a domino used in javafx version
     */
    public void removeSelected() { selected = false; }

    /**
     * @return true if the domino is selected false otherwise
     */
    public boolean isSelected() { return selected; }

    /**
     * @param n value to check if domino contains
     * @return true if the domino has that value on the left or right side false otherwise
     */
    public boolean contains (int n) { return first == n || second == n; }

    /**
     * @return domino string representation for commandline version
     */
    public String toString() { return "[" + first + "  " + second + "]"; }

    /**
     * swaps first and second value of domino used in javafx and commandline
     */
    public void rotate() {
        int temp = first;
        first = second;
        second = temp;
    }

    /**
     * @return domino javafx representation for javafx version
     */
    public HBox draw() {
        HBox domino = new HBox();
        domino.setMaxSize(105, 55);
        domino.setSpacing(-2);
        if (selected) {
            domino.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-color: gold;");
        }
        domino.getChildren().addAll(drawPips(first), drawPips(second));
        return domino;
    }

    /**
     * @param n number of pips to draw
     * @return a canvas of half of a domino with the number of pips drawn on
     */
    private Canvas drawPips (int n) {
        double size = 40;
        double pipRadius = size / 8;

        Canvas half = new Canvas(size, size);
        GraphicsContext gc = half.getGraphicsContext2D();
        gc.setLineWidth(4);

        gc.setFill(Color.BLACK);
        gc.strokeRect(0,0,size,size);

        switch (n) {
            case 0 -> {
            }
            case 1 -> gc.fillOval(size / 2 - pipRadius, size / 2 - pipRadius, 2 * pipRadius, 2 * pipRadius);
            case 2 -> twoDots(size, pipRadius, gc);
            case 3 -> {
                gc.fillOval(size / 2 - pipRadius, size / 2 - pipRadius, 2 * pipRadius, 2 * pipRadius);
                twoDots(size, pipRadius, gc);
            }
            case 4 -> {
                twoDots(size, pipRadius, gc);
                secondTwoDots(size, pipRadius, gc);
            }
            case 5 -> {
                gc.fillOval(size / 2 - pipRadius, size / 2 - pipRadius, 2 * pipRadius, 2 * pipRadius);
                twoDots(size, pipRadius, gc);
                secondTwoDots(size, pipRadius, gc);
            }
            case 6 -> {
                twoDots(size, pipRadius, gc);
                secondTwoDots(size, pipRadius, gc);
                gc.fillOval(size / 5 - pipRadius, size / 2 - pipRadius, 2 * pipRadius, 2 * pipRadius);
                gc.fillOval(4 * size / 5 - pipRadius, size / 2 - pipRadius, 2 * pipRadius, 2 * pipRadius);
            }
        }
        return half;
    }

    /**
     * draws two dots used when drawing 2-6 pips
     * @param size length or width of domino canvas
     * @param pipRadius radius wanted for the pip
     * @param gc graphics context used to draw
     */
    private void twoDots(double size, double pipRadius, GraphicsContext gc) {
        gc.fillOval(size/5 - pipRadius, 4*size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
        gc.fillOval(4*size/5 - pipRadius, size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
    }
    /**
     * draws second two dots used when drawing 4-6 pips
     * @param size length or width of domino canvas
     * @param pipRadius radius wanted for the pip
     * @param gc graphics context used to draw
     */
    private void secondTwoDots(double size, double pipRadius, GraphicsContext gc) {
        gc.fillOval(size/5 - pipRadius, size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
        gc.fillOval(4*size/5 - pipRadius, 4*size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
    }
}
