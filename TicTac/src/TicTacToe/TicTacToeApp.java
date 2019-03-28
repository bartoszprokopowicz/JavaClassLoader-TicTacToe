package TicTacToe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/tictactoe.fxml"));
        BorderPane root = loader.load();

        TicTacToeController controller = loader.getController();

        controller.setData();

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        primaryStage.sizeToScene();
        controller.setStage(primaryStage);
        primaryStage.show();

    }
}
