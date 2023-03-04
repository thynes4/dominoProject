import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {
    private static boolean myTurn = true;
    private static boolean finished = false;
    private static String leftOrRight = null;

    @Override
    public void start(Stage stage) {
        Boneyard boneyard = new Boneyard();
        HumanPlayer user = new HumanPlayer(boneyard);
        ComputerPlayer computer = new ComputerPlayer(boneyard);
        Board gameBoard = new Board();
        BorderPane root = new BorderPane();
        Button flipBtn = new Button();
        Button drawBtn = new Button();
        Button placeLeft = new Button();
        Button placeRight = new Button();
        HBox topHolder = new HBox(flipBtn, drawBtn, placeLeft, placeRight);

        stage.setTitle("Dominos!");
        flipBtn.setText("Flip");
        drawBtn.setText("Draw");
        placeRight.setText("Place right");
        placeLeft.setText("Place left");

        root.setTop(topHolder);
        root.setCenter(gameBoard.drawBoard());
        root.setBottom(user.drawRack(root));
        root.setStyle("-fx-background-color: lightgray");

        stage.setScene(new Scene(root, 1500,600));
        stage.show();

        drawBtn.setOnAction(event -> {
            if ((user.getRack().size() == 0 && !boneyard.isEmpty())
                    || (!boneyard.isEmpty() && !availableMove(gameBoard, user.getRack()))) {
                user.add(boneyard.draw());

                root.setCenter(null);
                root.setBottom(null);
                root.setCenter(gameBoard.drawBoard());
                root.setBottom(user.drawRack(root));
            }
        });

        flipBtn.setOnAction(event -> {
            for (Domino d: user.getRack()) {
                if (d.isSelected()) {
                    d.rotate();
                }
            }
            root.setBottom(null);
            root.setBottom(user.drawRack(root));
        });

        placeLeft.setOnAction(event -> {
            for (Domino d: user.getRack()) {
                if (d.isSelected() && gameBoard.validMoveLeft(d)) {
                    d.removeSelected();
                    gameBoard.addDominoLeft(user.remove(d));
                    guiComputerMove(boneyard, user, computer, gameBoard, root);
                    break;
                }
            }
        });

        placeRight.setOnAction(event -> {
            for (Domino d: user.getRack()) {
                if (d.isSelected() && gameBoard.validMoveRight(d)) {
                    d.removeSelected();
                    gameBoard.addDominoRight(user.remove(d));
                    guiComputerMove(boneyard, user, computer, gameBoard, root);
                    break;
                }
            }
        });
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String input;

        System.out.print("Which version of Dominos would you like to play?\n[c] Console\n[g] Graphical\n");
        input = scnr.next();

        if (Objects.equals(input, "g") || Objects.equals(input, "G")) {
            Application.launch(args);
            return;
        }

        Boneyard boneyard = new Boneyard();
        HumanPlayer user = new HumanPlayer(boneyard);
        ComputerPlayer computer = new ComputerPlayer(boneyard);
        Board gameBoard = new Board();

        System.out.println("Dominos!");

        while (!finished) {

            if (computer.getRack().size() == 0 && boneyard.isEmpty()) break;
            if (user.getRack().size() == 0 && boneyard.isEmpty()) break;
            if (boneyard.isEmpty() && !availableMove(gameBoard, user.getRack()) && !availableMove(gameBoard, computer.getRack())) break;

            while (myTurn) {
                if (boneyard.isEmpty() && !availableMove(gameBoard, user.getRack())) {
                    System.out.println("Nothing I can play!");
                    myTurn = false;
                    break;
                }
                System.out.print(computer.toString() + boneyard + gameBoard + user + printTurn() + printMenu());
                input = scnr.next();
                switch (input) {
                    case ("p") -> {
                        System.out.println("Which Domino?");
                        input = scnr.next();
                        if (Integer.parseInt(input) > user.getSize() - 1 || Integer.parseInt(input) < 0)
                        {
                            System.out.println("Invalid domino index, select again.");
                        } else {
                            int selectedIndex = Integer.parseInt(input);
                            System.out.println("Left or Right? (l/r)");
                            input = scnr.next();

                            if (Objects.equals(input, "l")) {

                                leftOrRight = "left";
                                playUserMove(scnr, user, gameBoard, selectedIndex);
                                if (user.getRack().size() == 0 && boneyard.isEmpty()) finished = true;

                            } else if (Objects.equals(input, "r")) {

                                leftOrRight = "right";
                                playUserMove(scnr, user, gameBoard, selectedIndex);
                                if (user.getRack().size() == 0 && boneyard.isEmpty()) finished = true;

                            } else {
                                leftOrRight = null;
                                System.out.println("Please choose either \"l\" or \"r\", select again.");
                            }
                        }
                    }
                    case ("d") -> {
                        if (user.getRack().size() == 0 && !boneyard.isEmpty()) {
                            user.add(boneyard.draw());
                            System.out.println("Domino drawn");
                        } else if (availableMove(gameBoard, user.getRack())) {
                            System.out.println("You have a domino that can be played!");
                        } else if (!boneyard.isEmpty()) {
                            user.add(boneyard.draw());
                            System.out.println("Domino drawn");
                        } else {
                            finished = true;
                        }
                    }
                    case ("q") -> {
                        System.out.println("Thanks for playing!");
                        System.exit(0);
                    }
                }
            }

            if (boneyard.isEmpty() && !availableMove(gameBoard, user.getRack()) && !availableMove(gameBoard, computer.getRack())) break;

            System.out.print(printTurn());

            while (!myTurn) {
                if (computer.getRack().size() == 0 && boneyard.isEmpty()) finished = true;
                if (boneyard.isEmpty() && !availableMove(gameBoard, computer.getRack())) {
                    System.out.println("Nothing I can play!");
                    myTurn = true;
                    break;
                }
                computerPlayed(boneyard, computer, gameBoard);
            }
        }

        final int usersScore = calculateScore(user.getRack());
        final int computerScore = calculateScore(computer.getRack());

        System.out.println("Computer's score: " + computerScore);
        System.out.println("Player's score: " + usersScore);

        System.out.println();

        if (usersScore < computerScore) {
            System.out.println("You Win!");
        }
        if (usersScore > computerScore) {
            System.out.println("You Lose :(");
        }
        if (computerScore == usersScore) {
            if (myTurn) {
                System.out.println("Same score but you played last so you win!");
            } else {
                System.out.println("Same score but the computer played last so you lose :(");
            }
        }
    }

    private static void computerPlayed(Boneyard boneyard, ComputerPlayer computer, Board gameBoard) {
        for (Domino d : computer.getRack()) {
            if (playIfPossible(computer, gameBoard, d)) {
                if (computer.getRack().size() == 0 && boneyard.isEmpty()) finished = true;
                myTurn = true;
                break;
            }
            d.rotate();
            if (playIfPossible(computer, gameBoard, d)) {
                if (computer.getRack().size() == 0 && boneyard.isEmpty()) finished = true;
                myTurn = true;
                break;
            }
        }
        if (!myTurn && !boneyard.isEmpty()) {
            computer.add(boneyard.draw());
        }
        if (!myTurn && boneyard.isEmpty()) {
            myTurn = true;
        }
    }

    private void guiComputerMove(Boneyard boneyard, HumanPlayer user, ComputerPlayer computer, Board gameBoard, BorderPane root) {
        myTurn = false;
        while (!myTurn) {
            if (computer.getRack().size() == 0 && boneyard.isEmpty()) finished = true;
            if (boneyard.isEmpty() && !availableMove(gameBoard, computer.getRack())) {
                myTurn = true;
                break;
            }
            computerPlayed(boneyard, computer, gameBoard);
        }

        root.setCenter(null);
        root.setBottom(null);
        root.setCenter(gameBoard.drawBoard());
        root.setBottom(user.drawRack(root));
    }

    private static int calculateScore (ArrayList<Domino> rack) {
        int score = 0;
        for (Domino d: rack) {
            score = score + d.getFirst();
            score = score + d.getSecond();
        }
        return score;
    }
    private static void playUserMove(Scanner scnr, HumanPlayer user, Board gameBoard, int selectedIndex) {
        String input;
        System.out.println("Rotate first? (y/n)");
        input = scnr.next();
        String rotate = input;

        if (Objects.equals(rotate, "y")) {
            user.rotateAt(selectedIndex);
        }
        else if (!Objects.equals(rotate, "n")) {
            System.out.println("Please choose either \"y\" or \"n\", select again.");
            return;
        }

        if (Objects.equals(leftOrRight, "left")) {
            if (gameBoard.validMoveLeft(user.get(selectedIndex))) {
                System.out.println(printMove(user.get(selectedIndex), leftOrRight));
                gameBoard.addDominoLeft(user.remove(selectedIndex));
                myTurn = false;
            } else {
                System.out.println("Invalid move, try again.");
            }
        } else if (Objects.equals(leftOrRight, "right")) {
            if (gameBoard.validMoveRight(user.get(selectedIndex))) {
                System.out.println(printMove(user.get(selectedIndex), leftOrRight));
                gameBoard.addDominoRight(user.remove(selectedIndex));
                myTurn = false;
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
    }


    private static boolean playIfPossible(ComputerPlayer computer, Board gameBoard, Domino d) {
        if (gameBoard.validMoveRight(d)) {
            gameBoard.addDominoRight(computer.remove(d));
            System.out.println(printMove(d,"right"));
            return true;
        } else if (gameBoard.validMoveLeft(d)) {
            gameBoard.addDominoLeft(computer.remove(d));
            System.out.println(printMove(d,"left"));
            return true;
        }
        return false;
    }

    private static boolean availableMove (Board gameBoard, ArrayList<Domino> rack) {
        for (Domino d: rack) {
            if (gameBoard.isEmpty()
                    || d.contains(gameBoard.getFirstValue())
                    || d.contains(gameBoard.getLastValue())
                    || d.contains(0)
                    || gameBoard.getFirstValue() == 0
                    || gameBoard.getLastValue() == 0) {
                return true;
            }
        }
        return false;
    }

    private static String printMenu() {
        return "[p] Play Domino\n[d] Draw from boneyard\n[q] Quit\n";
    }
    private static String printMove(Domino d, String leftOrRight) {
        if (myTurn) {
            return "Playing " + d + " at " + leftOrRight;
        } else {
            return "Computer plays " + d + " at " + leftOrRight;
        }
    }

    private static String printTurn() {
        if (myTurn) {
            return "Human's turn\n";
        } else {
            return "Computer's turn\n";
        }
    }
}
