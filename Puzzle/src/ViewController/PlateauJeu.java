/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;


import java.io.File;

import Grille.*;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author markk
 */
public class PlateauJeu extends Application {

    boolean isStarted, gameOver;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundImage myBI = new BackgroundImage(new Image(new File("ressources\\background.png").toURI().toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(160, 540, false, false, true, true));
        //GridPane2//
        //*********//
        PanView puzzleView = new PanView(10, 10);


        puzzleView.getgPane2().setPrefWidth(300);
        puzzleView.getgPane2().setPrefHeight(520);
        puzzleView.getgPane2().requestFocus();
        //FinalState GridPane//
        PanView finalState = new PanView(10, 10);
        finalState.getgPane2().setPrefWidth(300);
        finalState.getgPane2().setPrefHeight(520);
        finalState.getgPane2().requestFocus();
        //*********//
        //GridPane3//
        //*********//              
        GridPane gPane3 = new GridPane();
        //gPane3.setGridLinesVisible(true);
        gPane3.setPrefWidth(170);
        gPane3.setPrefHeight(520);
        //gPane3.setStyle("-fx-background-color: #333cc4;");
        gPane3.setBackground(new Background(myBI));
        VBox btnPanel = new VBox(5);
        Button startGame = new Button("Jouer");
        Label agentNumberInputLabel = new Label();
        agentNumberInputLabel.setText("Nombre d'agents :");
        agentNumberInputLabel.setFont(Font.font(null, FontWeight.BOLD,13));
        agentNumberInputLabel.setStyle(" -fx-background-color: white");
        agentNumberInputLabel.setPrefHeight(10);
        TextArea agentNumberInput = new TextArea();
        agentNumberInput.setMaxWidth(20);
        agentNumberInput.setPrefHeight(10);
        //rows count
        Label rowsNumberInputLabel = new Label();
        rowsNumberInputLabel.setText("Nombre de lignes :");
        rowsNumberInputLabel.setFont(Font.font(null, FontWeight.BOLD,13));
        rowsNumberInputLabel.setStyle(" -fx-background-color: white");
        rowsNumberInputLabel.setPrefHeight(10);
        TextArea rowsNumberInput = new TextArea();
        rowsNumberInput.setMaxWidth(20);
        rowsNumberInput.setPrefHeight(10);
        //////////
        //columns count
        Label columnsNumberInputLabel = new Label();
        columnsNumberInputLabel.setText("Nombre de colonnes :");
        columnsNumberInputLabel.setFont(Font.font(null, FontWeight.BOLD,13));
        columnsNumberInputLabel.setStyle(" -fx-background-color: white");
        columnsNumberInputLabel.setPrefHeight(10);
        TextArea columnsNumberInput = new TextArea();
        columnsNumberInput.setMaxWidth(20);
        columnsNumberInput.setPrefHeight(10);
        /////////
        startGame.setPrefWidth(60);
        startGame.setPrefWidth(60);
        btnPanel.getChildren().add(startGame);
        btnPanel.getChildren().add(rowsNumberInputLabel);
        btnPanel.getChildren().add(rowsNumberInput);
        btnPanel.getChildren().add(columnsNumberInputLabel);
        btnPanel.getChildren().add(columnsNumberInput);
        btnPanel.getChildren().add(agentNumberInputLabel);
        btnPanel.getChildren().add(agentNumberInput);

        GridPane.setMargin(btnPanel, new Insets(10, 0, 10, 0));
        btnPanel.setAlignment(Pos.CENTER);
        gPane3.add(btnPanel, 0, 7, 4, 4);
        gPane3.setAlignment(Pos.CENTER);
        gPane3.setPadding(new Insets(10));
        //BorderPane
        BorderPane border = new BorderPane();
        border.setCenter(gPane3);
        border.setRight(puzzleView.getgPane2());
        border.setLeft(finalState.getgPane2());
        Scene scene = new Scene(border, 780, 260);
        primaryStage.setTitle("Puzzle");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(new File("ressources\\puzzle-logo.jpg").toURI().toString()));
        primaryStage.show();
        startGame.setOnAction((ActionEvent e) -> {

            if(!agentNumberInput.getText().equals("") && !rowsNumberInput.getText().equals("") && !columnsNumberInput.getText().equals("")){
                int nbAgents = Integer.parseInt(agentNumberInput.getText());
                int rowsNumber = Integer.parseInt(rowsNumberInput.getText());
                int columnsNumber = Integer.parseInt(columnsNumberInput.getText());
                if(  nbAgents> 0 && rowsNumber > 0 && columnsNumber> 0){
                    if(nbAgents > rowsNumber*columnsNumber){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Nombres d'agents incorrect !");
                        alert.setHeaderText("Alerte !");
                        alert.setContentText(""+nbAgents+" doit être < à "+rowsNumber +"* "+columnsNumber );
                        alert.showAndWait();
                    }else PuzzleGameCore.play(puzzleView, finalState,nbAgents,rowsNumber,columnsNumber);
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Valeurs manquantes !");
                    alert.setHeaderText("Alerte !");
                    alert.setContentText("une valeur est < 0");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Entrez les valeurs!");
                alert.setHeaderText("Alerte !");
                alert.setContentText("Champs vides");
                alert.showAndWait();
            }
        });
        //
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setTimelineStop(Timeline t) {
        t.stop();
    }
}
