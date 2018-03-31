package draughts.ui.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import draughts.gamecore.Board;
import draughts.gamecore.Square;
import draughts.gamecore.PieceType;


public class DraughtsGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board();
        GridPane boardView = new BoardView(board).makeBoardView();


        Button update = new Button("Make King");
        EventHandler<ActionEvent> handler = e -> {
            board.setPieceType(new Square(0, 0), PieceType.WHITE_KING);
            System.out.println(board.getPieceType(new Square(0, 0)));
        };
        update.setOnAction(handler);
        boardView.addColumn(9, update);


        Button update2 = new Button("Remove");
        EventHandler<ActionEvent> handler2 = e -> {
            board.setPieceType(new Square(0, 0), PieceType.NONE);
            System.out.println(board.getPieceType(new Square(0, 0)));
        };
        update2.setOnAction(handler2);
        boardView.addColumn(10, update2);


        Scene scene = new Scene(boardView, 1500, 1200);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Draughts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
