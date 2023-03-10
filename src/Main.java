import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 *  Welcome to Dominos!
 */
public class Main extends Application {
    private static boolean myTurn = true;
    private static boolean finished = false;
    private static String leftOrRight = null;

    /**
     * The start method contains the logic for the buttons and most of the drawing logic
     * each button utilizes functions that were already used in the commandline version and
     * also uses the same classes as the commandline version.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) {
        Boneyard boneyard = new Boneyard();
        HumanPlayer user = new HumanPlayer(boneyard);
        ComputerPlayer computer = new ComputerPlayer(boneyard);
        Board gameBoard = new Board();
        BorderPane root = new BorderPane();
        Button flipBtn = makeButtonGood(new Button());
        Button drawBtn = makeButtonGood(new Button());
        Button placeLeft = makeButtonGood(new Button());
        Button placeRight = makeButtonGood(new Button());
        Label computerText = makeLabelGood(new Label(computer.toString()));
        Label boneyardText = makeLabelGood(new Label(boneyard.toString()));
        Label dominoesText = makeLabelGood(new Label("Dominos!"));

        VBox sideHolder = new VBox();
        HBox topHolder = new HBox();

        topHolder.setSpacing(40);
        topHolder.setAlignment(Pos.CENTER);
        sideHolder.setSpacing(10);
        dominoesText.setFont(Font.font("Helvetica", FontWeight.BOLD,50));

        stage.setTitle("Dominos!");
        flipBtn.setText("Flip");
        drawBtn.setText("Draw");
        placeRight.setText("Place Right");
        placeLeft.setText("Place Left");

        placeLeft.setPrefHeight(2000);
        placeRight.setPrefHeight(2000);

        topHolder.getChildren().addAll(computerText, dominoesText, boneyardText);
        sideHolder.getChildren().addAll(flipBtn, placeLeft, drawBtn);

        root.setLeft(sideHolder);
        root.setRight(placeRight);
        root.setTop(topHolder);
        root.setCenter(gameBoard.drawBoard());
        root.setBottom(user.drawRack(root));
        root.setStyle("-fx-background-color: lightgray");

        stage.setScene(new Scene(root, 1600,600));
        stage.show();

        // checks if there are not any possible moves, or your rack is empty and also if the
        // boneyard is empty, if so allows you to draw a domino then checks if the game is over.
        drawBtn.setOnAction(event -> {
            if ((user.getRack().size() == 0 && !boneyard.isEmpty())
                    || (!boneyard.isEmpty() && !availableMove(gameBoard, user.getRack()))) {
                user.add(boneyard.draw());

                if (!checkWin(boneyard, user, computer, gameBoard, root)) {
                    root.setCenter(null);
                    root.setBottom(null);

                    boneyardText.setText(null);
                    boneyardText.setText(boneyard.toString());

                    root.setCenter(gameBoard.drawBoard());
                    root.setBottom(user.drawRack(root));
                }
            }
        });
        // uses same rotate domino method used in commandline version
        //flips the selected domino
        flipBtn.setOnAction(event -> {
            for (Domino d: user.getRack()) {
                if (d.isSelected()) {
                    d.rotate();
                }
            }
            root.setBottom(null);
            root.setBottom(user.drawRack(root));
        });
        // checks if selected domino can be placed in the left spot and places it if possible
        // if domino is placed checks win condition
        placeLeft.setOnAction(event -> {
            for (Domino d: user.getRack()) {
                if (d.isSelected() && gameBoard.validMoveLeft(d)) {
                    d.removeSelected();
                    gameBoard.addDominoLeft(user.remove(d));

                    checkWinComputerMove(boneyard, user, computer, gameBoard, root, computerText, boneyardText);
                    break;
                }
            }
        });

        // checks if selected domino can be placed in the right spot and places it if possible
        // if domino is placed checks win condition
        placeRight.setOnAction(event -> {
            for (Domino d: user.getRack()) {
                if (d.isSelected() && gameBoard.validMoveRight(d)) {
                    d.removeSelected();
                    gameBoard.addDominoRight(user.remove(d));

                    checkWinComputerMove(boneyard, user, computer, gameBoard, root, computerText, boneyardText);
                    break;
                }
            }
        });
    }

    /**
     * main method contains the logic for the javafx version of the dominos game
     * @param args args
     */
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
                System.out.print(computer.toString() + boneyard + gameBoard + user + printTurn() + "[p] Play Domino\n[d] Draw from boneyard\n[q] Quit\n");
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

    /**
     *  checks if a game over condition is met, and if so calls the clear and print method and sets the end screen.
     * @param boneyard boneyard dominos
     * @param user the players rack
     * @param computer the computers rack
     * @param gameBoard current game board
     * @param root javafx borderpane
     * @return returns true if the game is over and false otherwise
     */
    private boolean checkWin(Boneyard boneyard, HumanPlayer user, ComputerPlayer computer, Board gameBoard, BorderPane root) {
        if (checkWin (2, boneyard, computer, user, gameBoard) == 1) {
            clearAndPrint(user, computer, root);
            root.setCenter(makeLabelGood(new Label("You won! :)")));
            return true;
        } else if (checkWin (2, boneyard, computer, user, gameBoard) == 2) {
            clearAndPrint(user, computer, root);
            root.setCenter(makeLabelGood(new Label("Computer won ):")));
            return true;
        }
        return false;
    }

    /**
     * clears the javafx pane and prints out scores to commandline
     * @param user the players rack
     * @param computer the computers rack
     * @param root javafx borderpane
     */
    private void clearAndPrint(HumanPlayer user, ComputerPlayer computer, BorderPane root) {
        clearRoot(root);
        root.setStyle("-fx-background-color: lightgray");
        final int usersScore = calculateScore(user.getRack());
        final int computerScore = calculateScore(computer.getRack());

        System.out.println("Computer's score: " + computerScore);
        System.out.println("Player's score: " + usersScore);
    }

    /**
     * first checks if a win condition is met, if not then keeps attempting to place a computer domino
     * and draws if no move is possible until either a move is found or the a game over condition is met
     * @param boneyard boneyard dominos
     * @param user player rack
     * @param computer computer rack
     * @param gameBoard current game board
     * @param root root borderpane
     * @param computerText number of computer dominos text to be updated
     * @param boneyardText number of dominos in boneyard text to be updated
     */
    private void checkWinComputerMove(Boneyard boneyard, HumanPlayer user, ComputerPlayer computer, Board gameBoard, BorderPane root, Label computerText, Label boneyardText) {
        boolean over = false;
        if (checkWin (1, boneyard, computer, user, gameBoard) == 1) {
            clearAndPrint(user, computer, root);
            over = true;
            root.setCenter(makeLabelGood(new Label("You won! :)")));
        } else if (checkWin (1, boneyard, computer, user, gameBoard) == 2) {
            clearAndPrint(user, computer, root);
            over = true;
            root.setCenter(makeLabelGood(new Label("Computer won ):")));
        }
        if (!over) {
            guiComputerMove(boneyard, user, computer, gameBoard, root);
            checkWin(boneyard, user, computer, gameBoard, root);

            boneyardText.setText(null);
            boneyardText.setText(boneyard.toString());
            computerText.setText(null);
            computerText.setText(computer.toString());
        }
    }

    /**
     * keeps checking and playing if a computer move is possible, used in both commandline and javafx version
     * @param boneyard current boneyard
     * @param computer computer rack
     * @param gameBoard current game board
     */
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

    /**
     *  check win for the commandline version of the game
     * @param lastPlayed the last player to have played 1 for player, 2 for computer.
     * @param b boneyard
     * @param c computer rack
     * @param h player rack
     * @param g current board
     * @return returns 2 if the computer wins, 1 if the player wins, or 0 if neither has won yet.
     */
    private int checkWin(int lastPlayed, Boneyard b, ComputerPlayer c, HumanPlayer h, Board g) {
        if (b.isEmpty() && (c.getRack().size() == 0 || h.getRack().size() == 0
                || !availableMove(g, h.getRack()) || !availableMove(g, c.getRack()))) {
            int hscore = calculateScore(h.getRack());
            int cscore = calculateScore(c.getRack());
            if (hscore > cscore) {
                return 2;
            } else if (hscore < cscore) {
                return 1;
            } else {
                return lastPlayed;
            }
        }
        return 0;
    }

    /**
     * used to clear javafx borderpane completely at end of game
     * @param root borderpane holding everything
     */
    private void clearRoot (BorderPane root) {
        root.setCenter(null);
        root.setBottom(null);
        root.setTop(null);
        root.setLeft(null);
        root.setRight(null);
    }

    /**
     * used to do some javafx logic then uses the same computer move logic used for the commandline version.
     * @param boneyard current boneyard
     * @param user player rack
     * @param computer computer rack
     * @param gameBoard current gameboard
     * @param root javafx borderpane
     */
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

    /**
     * adds numbers of all dominos of given domino rack
     * @param rack either player or computer rack of dominos
     * @return the score of adding all domino faces in that rack
     */
    private static int calculateScore (ArrayList<Domino> rack) {
        int score = 0;
        for (Domino d: rack) {
            score = score + d.getFirst();
            score = score + d.getSecond();
        }
        return score;
    }

    /**
     * Commandline version of asking questions and recieving / error testing inputs given when play move is selected
     * @param scnr scanner in
     * @param user player rack
     * @param gameBoard current gameboard
     * @param selectedIndex the index of the domino to be played
     */
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


    /**
     * plays the computer move and prints out the move played
     * @param computer computer player
     * @param gameBoard current board
     * @param d the domino to be played
     * @return true if move is played false otherwise
     */
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

    /**
     * checks if there is an available move used in both javafx and commandline version
     * @param gameBoard current game board
     * @param rack the rack to check for moves for using the game board
     * @return true if there's an available move and false otherwise
     */
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

    /**
     * prints the move played
     * @param d domino being played
     * @param leftOrRight is it being played on the "left" or "right"
     * @return string of the move for commandline version
     */
    private static String printMove(Domino d, String leftOrRight) {
        if (myTurn) {
            return "Playing " + d + " at " + leftOrRight;
        } else {
            return "Computer plays " + d + " at " + leftOrRight;
        }
    }

    /**
     * prints the current turn
     * @return string stating who is playing
     */
    private static String printTurn() {
        if (myTurn) {
            return "Human's turn\n";
        } else {
            return "Computer's turn\n";
        }
    }

    /**
     * used to turn buttons for javafx into appealing looking buttons
     * @param button button to make beautiful
     * @return a gorgeous button
     */
    private Button makeButtonGood(Button button) {
        button.setPrefSize(140,50);
        button.setBackground(Background.fill(Color.rgb(255,255,255)));
        button.setBorder(Border.stroke(Color.BLACK));
        button.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        return button;
    }

    /**
     * used to turn labels for javafx into appealing looking labels
     * @param label label to make beautiful
     * @return a gorgeous label
     */
    private Label makeLabelGood(Label label) {
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,25));
        label.setTextFill(Color.BLACK);
        return label;
    }
}
