package TicTacToe;

import Annotations.Difficulty;
import Annotations.Strategy;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TicTacToeController {

    @FXML
    public ComboBox<String> difficultyComboBox;
    public ComboBox<String> sizeComboBox;
    public ComboBox<String> strategyComboBox;
    public GridPane gameBoard;
    public BorderPane borderPane;
    public Button loadClass;
    public MenuItem AIFirstButton;

    private Stage stage;

    private Board board;

    private ClassReader reader;
    private ArrayList<Method> arrayMethods;
    private ArrayList<Annotation> arrayAnnotationsMethods;
    private ArrayList<Annotation> arrayAnnotationsClasses;


    private Class AIClass;
    private Method AIMethod;
    private Object AIObject;

    public void setData(){
        reader = new ClassReader();
        sizeComboBox.getItems().clear();
        sizeComboBox.getItems().addAll("5x5","6x6","7x7");
        arrayMethods = new ArrayList<>();
        arrayAnnotationsMethods = new ArrayList<>();
        arrayAnnotationsClasses = new ArrayList<>();
    }

    public void handleSizeComboAction(ActionEvent actionEvent) {
        String size = sizeComboBox.getValue();
        switch(size)
        {
            case "5x5":
                System.out.println("5x5");
                changeSize5x5();
                board = new Board(5);
                break;
            case "6x6":
                System.out.println("6x6");
                board = new Board(6);
                changeSize6x6();
                break;
            case "7x7":
                System.out.println("7x7");
                board = new Board(7);
                changeSize7x7();
                break;
        }
    }

    private void handlePressButton(ActionEvent actionEvent) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        //System.out.println("Wcisniety przycisk");
        Button b = (Button) actionEvent.getSource();
        //b.setText("X");
        int i = Integer.parseInt(b.getId());
        String player = board.getTurn().toString();
        if(board.move(i)) {
            b.setText(player);
            AIMove();
        }
        if(board.isGameOver()){
            System.out.println(board.getWinner());
            board.reset();
            handleSizeComboAction(actionEvent);
            AIFirstButton.setDisable(false);
        }
    }

    private void AIMove() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if(!board.isGameOver()){
            //Object ob = AIClass.newInstance();
            //int i = RandomAI.AI.run(board);
            int i = (int) AIMethod.invoke(AIObject, board);
            ArrayList<Node> nodes = new ArrayList<>(gameBoard.getChildren());
            Button b = (Button) nodes.get(i);
            b.setText(board.getTurn().toString());
            board.move(i);
        }
    }

    @SuppressWarnings("Duplicates")
    private void changeSize5x5(){
        Set<Node> deleteNodes = new HashSet<>(gameBoard.getChildren());
        gameBoard.getChildren().removeAll(deleteNodes);
        int id = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                Button button = new Button();
                button.setPrefWidth(100.0);
                button.setPrefHeight(100.0);
                button.setId(String.valueOf(id));
                button.setOnAction(actionEvent -> {
                    try {
                        handlePressButton(actionEvent);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
                gameBoard.add(button,i,j);
                id++;
            }
        }
        stage.sizeToScene();
    }

    private void changeSize6x6(){
        Set<Node> deleteNodes = new HashSet<>(gameBoard.getChildren());
        gameBoard.getChildren().removeAll(deleteNodes);
        int id = 0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                Button button = new Button();
                button.setPrefWidth(100.0);
                button.setPrefHeight(100.0);
                button.setId(String.valueOf(id));
                button.setOnAction(actionEvent -> {
                    try {
                        handlePressButton(actionEvent);
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
                gameBoard.add(button,i,j);
                id++;
            }
        }
        stage.sizeToScene();
    }

    private void changeSize7x7(){
        Set<Node> deleteNodes = new HashSet<>(gameBoard.getChildren());
        gameBoard.getChildren().removeAll(deleteNodes);
        int id = 0;
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                Button button = new Button();
                button.setPrefWidth(100.0);
                button.setPrefHeight(100.0);
                button.setId(String.valueOf(id));
                button.setOnAction(actionEvent -> {
                    try {
                        handlePressButton(actionEvent);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
                gameBoard.add(button,i,j);
                id++;
            }
        }
        stage.sizeToScene();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadMethods(@NotNull Class c) throws NoSuchMethodException {
        //loadAnnotations(c);
        arrayMethods = new ArrayList<>();
        arrayAnnotationsMethods = new ArrayList<>();
        difficultyComboBox.getItems().clear();
        ObservableList<String> lista2 = difficultyComboBox.getItems();

        Method m;
        m = c.getMethod("easy", Board.class);
        arrayMethods.add(m);
        loadAnnotationsMethods(m);
        m = c.getMethod("hard", Board.class);
        arrayMethods.add(m);
        loadAnnotationsMethods(m);

        Difficulty d = (Difficulty) arrayAnnotationsMethods.get(0);
        lista2.add(d.difficultyName());
        d = (Difficulty) arrayAnnotationsMethods.get(1);
        lista2.add(d.difficultyName());
        difficultyComboBox.setItems(lista2);
    }

    private void loadAnnotationsMethods(Method m){
        arrayAnnotationsMethods.add(m.getDeclaredAnnotation(Difficulty.class));
        System.out.println("EYYY");
    }

    private void loadAnnotationsClasses(Class c){
        ObservableList<String> lista = strategyComboBox.getItems();
        Strategy s = (Strategy) c.getDeclaredAnnotation(Strategy.class);
        arrayAnnotationsClasses.add(s);
        lista.add(s.strategyName());
        strategyComboBox.setItems(lista);
        System.out.println("EYYY");
    }

    public void handleLoadClassAction(ActionEvent actionEvent) throws NoSuchMethodException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            Class c = reader.loadClass(file.toPath());
            loadAnnotationsClasses(c);
            loadMethods(c);
            AIClass = c;
        }

    }

    public void handleUnloadClassAction(ActionEvent actionEvent) {
        reader.getArrayClass().clear();
        strategyComboBox.getItems().clear();
        difficultyComboBox.getItems().clear();
        arrayMethods.clear();
        arrayAnnotationsClasses.clear();
        arrayAnnotationsMethods.clear();
        AIClass = null;
        AIMethod = null;
        AIObject = null;
        System.gc();

    }

    public void handleSwitchClassAction(ActionEvent actionEvent) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        int i = strategyComboBox.getSelectionModel().getSelectedIndex();
        if(i > -1){
            AIClass = reader.getArrayClass().get(i);
            AIObject = AIClass.newInstance();
            loadMethods(AIClass);
            System.out.println("XXX");
            //board.reset();
        }
    }
    public void handleSwitchMethodAction(ActionEvent actionEvent){
        int i = difficultyComboBox.getSelectionModel().getSelectedIndex();
        if(i > -1){
            AIMethod = arrayMethods.get(i);
        }
    }

    public void firstAIAction(ActionEvent actionEvent) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        AIMove();
        AIFirstButton.setDisable(true);
    }
}
