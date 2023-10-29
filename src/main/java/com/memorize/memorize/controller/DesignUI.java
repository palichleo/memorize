package com.memorize.memorize.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DesignUI {
    private final double[] xOffset = {0};
    private final double[] yOffset = {0};
    private final TranslateTransition translateTransition = new TranslateTransition();
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    protected void paneDragSettings(Pane paneDrag){
        paneDrag.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        paneDrag.setOnMouseDragged(event -> {
            double newPosX = event.getScreenX() - xOffset[0];
            double newPosY = event.getScreenY() - yOffset[0];
            paneDrag.getScene().getWindow().setX(newPosX);
            paneDrag.getScene().getWindow().setY(newPosY);
        });
    }
    protected void nodeShift(Node cible, Node conflit, int px, int duration, String direction) {
        translateTransition.setDuration(Duration.millis(duration)); //duration
        translateTransition.setNode(cible); //node

        switch (direction) {
            case "left" ->
                //déplacement vers la gauche
                    translateTransition.setToX(cible.getTranslateX() - px);
            case "right" ->
                //déplacement vers la droite
                    translateTransition.setToX(cible.getTranslateX() + px);
            case "up" ->
                //déplacement vers le haut
                    translateTransition.setToY(cible.getTranslateY() - px);
            case "down" ->
                //déplacement vers le bas
                    translateTransition.setToY(cible.getTranslateY() + px);
        }

        translateTransition.play();

        if (conflit != null){
            disabledNodeDuration(conflit, duration);
        }
    }
    protected Timeline getTimeline(PasswordField pass) {
        return getTimeline(pass, null);
    }
    protected Timeline getTimeline(PasswordField pass, PasswordField pass2) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    // Convertir RGB en chaîne hexadécimale
                    String colorHex = String.format("#%02X%02X%02X", red, green, blue);
                    // Appliquer la couleur au style
                    String combinedStyle = "-fx-border-color: " + colorHex + ";";
                    pass.setStyle(combinedStyle);
                    if (pass2 != null) {
                        pass2.setStyle(combinedStyle);
                    }
                    // Incrémenter les composantes RGB
                    blue++;
                    green++;
                    red++;
                    if (blue > 100) blue = 0;
                    if (green > 100) green = 0;
                    if (red > 100) red = 0;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }
    private void disabledNodeDuration(Node conflit, int duration){
        conflit.setDisable(true); //désactive le noeud conflictuel

        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), e -> conflit.setDisable(false));
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);

        timeline.play();
    }
}
