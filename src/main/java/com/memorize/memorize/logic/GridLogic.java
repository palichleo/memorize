package com.memorize.memorize.logic;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class GridLogic {
    private int size;
    private VBox[][] vBoxes;

    public void resetAndCreate(GridPane grille){
        //SIZE
        setSIZE();
        //CLEAR
        grille.getChildren().clear();
        grille.getColumnConstraints().clear();
        grille.getRowConstraints().clear();
        //CONSTRAINTS
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        //CREATE
        for (int i = 0; i<size; i++){
            grille.getRowConstraints().add(rowConstraints);
            grille.getColumnConstraints().add(columnConstraints);
            for (int j = 0; j<size; j++){
                grille.add(vBoxes[i][j], i, j);
                vBoxes[i][j].getStyleClass().add("cell");
            }
        }
    }
    public VBox getVbox(int row, int col){
        return vBoxes[row][col];
    }
    public void setVboxesChild(int row, int line, char car){ //CHAR CAR est le caractere symbolique du site web
        Platform.runLater(() -> {

            Label caractere = new Label();
            caractere.setText("" + car);

            vBoxes[row][line].getChildren().add(caractere);
        });
    }

    private void setSIZE(){
        int setSize = (int) Math.sqrt(9);
        this.size = setSize;
        this.vBoxes = new VBox[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                vBoxes[i][j] = new VBox();
            }
        }
    }
}
