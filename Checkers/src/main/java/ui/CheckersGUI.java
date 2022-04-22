/**
 * This is the gui version of the Checkers game, it will begin by prompting the user to play either
 * in the gui or text console version of the game.
 *
 * @author William Mastronardi
 * @version 1.0
 *
 * @TODO    Implement logic
 *          Debug to ensure game works properly
 */

package ui;

import core.CheckersLogic;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene; // What happens in the window
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage; // Window

import java.util.Scanner;

import static javafx.geometry.Pos.CENTER;


public class CheckersGUI extends Application {

    private CheckersLogic logic;
    private final int GAME_PERIMETER_LEN = 8;
    private final int PANEL_SCALE = 80;

    /**
     * This main method exists for testing purposes
     * @param args
     */
    public static void main(String[] args) {
            char userIn;
            Scanner kb = new Scanner(System.in);
            System.out.println("Would you like to play in the text console or in the gui?");
            do {
                System.out.println("Enter t for text console or u for gui: ");
                userIn = kb.nextLine().charAt(0);
                if(userIn != 't' && userIn != 'u')
                    System.out.println("Invalid input, try again.");
            }while(userIn != 't' && userIn != 'u');
            switch (userIn) {
                case 't':
                    CheckersTextConsole text = new CheckersTextConsole();
                    break;
                case 'u':
                    launch(args);
                    break;
            }
//        launch(args); //Used for testing
    }


    /**
     * This method is treated as main due to the nature of JavaFx, and it will drive the GUI for the
     * CheckersGUI Class
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        StackPane checkersGameBoard = new StackPane();
        stage.setTitle("Checkers");
        Button p_v_p = new Button("Play against another player");
        Button p_v_cpu = new Button("Play against CPU");
        //Create new layout
        StackPane open_menu = new StackPane();
        initGameBoard(checkersGameBoard);

        //Add in title of game and rules
        Text ruleString = new Text("The goal of this game is to either capture all \n"
                + "of your opponent's pieces. There is no crowning \nor moving backwards in this version, so if"
                + " a player \nhas no moves remaining, then the game will end.");
        ruleString.setTextAlignment(TextAlignment.CENTER);
        Label checkerslbl = new Label("CHECKERS");

        //Change size of label
        checkerslbl.setFont(new Font("Arial", 32));
        ruleString.setFont(new Font("Arial", 14));

        //Add items to layout
        open_menu.getChildren().add(p_v_cpu);
        open_menu.getChildren().add(p_v_p);
        open_menu.getChildren().add(checkerslbl);
        open_menu.getChildren().add(ruleString);

        //Alter Locations
        p_v_p.setTranslateX(100);
        p_v_cpu.setTranslateX(-100);
        p_v_p.setTranslateY(100);
        p_v_cpu.setTranslateY(100);
        checkerslbl.setTranslateY(-100);

        Scene start_menu = new Scene(open_menu, 500, 500);
        stage.setScene(start_menu);
        stage.show();
        //Link clicks to a different scene

        //Link to different scenes
        Scene game = new Scene(checkersGameBoard, 640,640);
        //PvCPU Operator
        p_v_cpu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                logic = new CheckersLogic(true);
                stage.setScene(game);
                stage.show();
            }
        });

        //PVP Operator
        p_v_p.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                logic = new CheckersLogic(false);
                stage.setScene(game);
                stage.show();
            }
        });
    }

    /**
     * This method creates a grispane and fills each cell with a stackpane to display  a seamless ui experience
     * to the user
     * @param checkersGameBoard the gameboard that will be built on throughout this method
     */
    private void initGameBoard(StackPane checkersGameBoard) {
        GridPane board = new GridPane();
        board.prefHeight(640);
        board.prefWidth(640);
        //Add pieces to gridpane
        for(int x = 0; x < GAME_PERIMETER_LEN; x++) {
            for (int y = 0; y < GAME_PERIMETER_LEN; y++) {
                Button button = new Button(x + " " + y);
                if(x < 3)
                    button.setStyle("-fx-background-radius: 5em; " +
                            "-fx-min-width: 70px; " +
                            "-fx-min-height: 70px; " +
                            "-fx-max-width: 70px; " +
                            "-fx-max-height: 70px;" +
                            "-fx-background-color: #000000;" +
                            "-fx-text-fill: transparent");
                if(x > 4)
                    button.setStyle("-fx-background-radius: 5em; " +
                            "-fx-min-width: 70px; " +
                            "-fx-min-height: 70px; " +
                            "-fx-max-width: 70px; " +
                            "-fx-max-height: 70px;" +
                            "-fx-background-color: #ff0000;" +
                            "-fx-text-fill: transparent");
                StackPane cell = new StackPane();
                StackPane piece = new StackPane();


                button.setPrefSize(80,80);

                Rectangle bg = new Rectangle(80, 80);
                if(x % 2 == 0) {
                    if (y % 2 == 1) {
                        if (x < 3) {
                            piece.getChildren().add(button);  //Add Button
                        }
                        if(x > 4) {
                            piece.getChildren().add(button);  //Add Button
                        }
                        bg = new Rectangle(80, 80);
                        bg.setFill(Color.LIGHTCYAN);
                    } else {
                        bg = new Rectangle(80, 80);
                        bg.setFill(Color.PINK);
                    }
                }
                else{
                    if(y % 2 == 0) {
                        if(x > 4) {
                            piece.getChildren().add(button);  //Add Button

                        }
                        if (x < 3) {
                            piece.getChildren().add(button);  //Add Button

                        }
                        bg.setFill(Color.LIGHTCYAN);
                    }
                    else {
                        bg = new Rectangle(80, 80);
                        bg.setFill(Color.PINK);
                    }
                }
                cell.getChildren().add(bg);
                cell.getChildren().add(piece);
                board.add(cell, y, x);
            }
        }

        board.setAlignment(CENTER);
        checkersGameBoard.getChildren().add(board);


    }


}


