import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Domino {
    private int first;
    private int second;
    private boolean selected;

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

    public void setSelected() {
        selected = true;
    }

    public void removeSelected() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
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

    private Canvas drawPips (int n) {
        double size = 50;
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

    private void twoDots(double size, double pipRadius, GraphicsContext gc) {
        gc.fillOval(size/5 - pipRadius, 4*size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
        gc.fillOval(4*size/5 - pipRadius, size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
    }

    private void secondTwoDots(double size, double pipRadius, GraphicsContext gc) {
        gc.fillOval(size/5 - pipRadius, size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
        gc.fillOval(4*size/5 - pipRadius, 4*size/5 - pipRadius,2 * pipRadius, 2 * pipRadius);
    }

}
