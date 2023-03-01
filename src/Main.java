import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    private static boolean myTurn = true;
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Boneyard boneyard = new Boneyard();
        HumanPlayer user = new HumanPlayer(boneyard);
        ComputerPlayer computer = new ComputerPlayer(boneyard);
        Board gameBoard = new Board();

        System.out.print("Dominos!\n" + computer + boneyard + "\n\n" + user + printTurn() + printMenu());
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    private static String printMenu() {
        return "[p] Play Domino\n[d] Draw from boneyard\n[q] Quit\n";
    }

    private static String printTurn() {
        if (myTurn) {
            return "Human's turn\n";
        } else {
            return "Computer's turn\n";
        }
    }
}
