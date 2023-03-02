import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {
    private static boolean myTurn = true;
    private static boolean finished = false;
    private int selectedIndex = -1;
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String input = null;

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
            while (myTurn) {
                System.out.print(computer.toString() + boneyard + gameBoard + user + printTurn() + printMenu());
                input = scnr.next();
                switch (input) {
                    case ("p") -> {
                        System.out.println("Which Domino?");
                        input = scnr.next();
                        if (Integer.parseInt(input) > user. || Integer.parseInt(input) < 0)

                        System.out.println("Left or Right? (l/r)");
                        input = scnr.next();

                        if (Objects.equals(input, "l")) {

                        } else {

                        }
                    }
                    case ("d") -> {
                        if (!boneyard.isEmpty()) {
                            user.add(boneyard.draw());
                            System.out.println("Domino drawn");
                            myTurn = false;
                        } else {
                            System.out.println("Boneyard is empty!");
                        }
                    }
                    case ("q") -> {
                        System.out.println("Thanks for playing!");
                        System.exit(0);
                    }
                }
            }

            System.out.print(printTurn());

            while (!myTurn) {
                for (Domino d : computer.getRack()) {
                    if (playIfPossible(computer, gameBoard, d)) {
                        myTurn = true;
                        break;
                    }
                    d.rotate();
                    if (playIfPossible(computer, gameBoard, d)) {
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
        }
        //game is over
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

    @Override
    public void start(Stage primaryStage) throws Exception {

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
