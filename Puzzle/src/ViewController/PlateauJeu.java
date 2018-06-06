/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import Grille.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * @author markk
 */
public class PlateauJeu extends Application {

    boolean isStarted, gameOver;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Alerte !");
        alert.setContentText("Le jeu est terminé");
        Plateau plateau = new Plateau(10, 10);
        //Creation du menu
        MenuBar menuBar = new MenuBar();
        // --- Menu Jeu
        Menu menuJeu = new Menu("Jouer");
        MenuItem commencer = new Menu("Commencer");
        MenuItem sauvegarder = new Menu("Sauvegarder");
        MenuItem charger = new Menu("Charger");
        MenuItem quitter = new Menu("Quitter");
        menuJeu.getItems().addAll(commencer, sauvegarder, charger, quitter);
        // --- Menu Score
        Menu menuScore = new Menu("Meilleurs Scores");
        // --- Menu Aide
        Menu menuAide = new Menu("Aide");
        MenuItem regleJeu = new Menu("Rèles de jeu");
        MenuItem aPropos = new Menu("A propos");
        menuAide.getItems().addAll(regleJeu, aPropos);
        menuBar.getMenus().addAll(menuJeu, menuScore, menuAide);
        BackgroundImage myBI = new BackgroundImage(new Image(new File("ressources\\background.png").toURI().toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(160, 540, false, false, true, true));
        //GridPane2//
        //*********//
        PanView puzzleView = new PanView(10, 10);
        plateau.setNaturalLanguageColors(puzzleView.getNaturalLanguageColors());

        puzzleView.getgPane2().setPrefWidth(300);
        puzzleView.getgPane2().setPrefHeight(520);
        puzzleView.getgPane2().requestFocus();
        //
        //*********//
        //GridPane3//
        //*********//              
        GridPane gPane3 = new GridPane();
        //gPane3.setGridLinesVisible(true);
        gPane3.setPrefWidth(170);
        gPane3.setPrefHeight(540);
        //gPane3.setStyle("-fx-background-color: #333cc4;");
        gPane3.setBackground(new Background(myBI));
        VBox btnPanel = new VBox(5);
        Button startGame = new Button("Jouer");

        startGame.setPrefWidth(60);
        //startGame.setPadding(new Insets(10, 0, 10, 0));
        HBox songPanel = new HBox(5);
        Image muteImage = new Image(new File("ressources\\mute.png").toURI().toString());
        Image songImage = new Image(new File("ressources\\song.png").toURI().toString());
        Image pauseImage = new Image(new File("ressources\\pause.png").toURI().toString());
        Button song = new Button("", new ImageView(songImage));
        song.setMaxWidth(20);
        song.setPrefWidth(20);
        song.setPrefHeight(20);
        Button mute = new Button("", new ImageView(muteImage));
        //mute.setPadding(new Insets(10, 0, 10, 0));
        mute.setMaxWidth(20);
        mute.setPrefWidth(20);
        mute.setPrefHeight(20);
        Button pause = new Button("", new ImageView(pauseImage));

        pause.setMaxWidth(20);
        pause.setPrefWidth(20);
        pause.setPrefHeight(20);
        btnPanel.getChildren().add(startGame);
        songPanel.getChildren().add(song);
        songPanel.getChildren().add(mute);
        songPanel.getChildren().add(pause);

        GridPane.setMargin(btnPanel, new Insets(10, 0, 10, 0));
        btnPanel.setAlignment(Pos.CENTER);
        songPanel.setAlignment(Pos.CENTER);
        gPane3.add(btnPanel, 0, 7, 4, 4);
        gPane3.add(songPanel, 0, 11, 4, 1);
        gPane3.setAlignment(Pos.CENTER);
        gPane3.setPadding(new Insets(10));
        //BorderPane
        BorderPane border = new BorderPane();
        border.setCenter(puzzleView.getgPane2());
        border.setRight(gPane3);
        border.setTop(menuBar);
        Scene scene = new Scene(border, 480, 290);
        primaryStage.setTitle("Puzzle");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(new File("ressources\\puzzle-logo.jpg").toURI().toString()));
        primaryStage.show();
        startGame.setOnAction((ActionEvent e) -> {
            PuzzleGameCore.play(plateau, puzzleView);
        });
        //
        setObserver(plateau, puzzleView);
    }

    private void setObserver(Plateau plateau, PanView puzzleView) {
        plateau.addObserver((Observable o, Object arg) -> {
            if (arg instanceof Case) {
                Case tmp = (Case) arg;
                if (!puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()]) {
                    puzzleView.getRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()].setFill(convertColor(puzzleView.getNaturalLanguageColors()[tmp.getPosition().getX()][tmp.getPosition().getY()]));
                    puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = true;
                } else if (puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()]) {
                    puzzleView.getRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()].setFill(Color.BLACK);
                    puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = false;
                }
            }
        });
    }

    public Color convertColor(Agent.Color couleur) {
        switch (couleur) {
            case CYAN:
                return Color.CYAN;
            case RED:
                return Color.RED;
            case GREEN:
                return Color.GREEN;
            case PURPLE:
                return Color.PURPLE;
            case ORANGE:
                return Color.ORANGE;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
        }
        return Color.BLANCHEDALMOND;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setTimelineStop(Timeline t) {
        t.stop();
    }
}
