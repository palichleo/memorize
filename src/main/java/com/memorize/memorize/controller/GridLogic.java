package com.memorize.memorize.controller;

import com.memorize.memorize.logic.security.PathManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class GridLogic {
    private int size;
    private StackPane[][] stackPanes;
    private final PathManager pathManager = new PathManager();
    private final DesignUI designUI = new DesignUI();

    public GridLogic(){
        //apres
    }

    protected void resetAndCreate(GridPane grille){
        setSIZE();
        createStackPane();
        //CLEAR
        grille.getChildren().clear();
        grille.getColumnConstraints().clear();
        grille.getRowConstraints().clear();
        //CONSTRAINTS
        ColumnConstraints columnConstraints = new ColumnConstraints();

        columnConstraints.setPercentWidth(100.0 / size); // Définir le pourcentage de largeur
        columnConstraints.setFillWidth(true); // Permettre le remplissage de la largeur
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100.0 / size); // Définir le pourcentage de hauteur
        rowConstraints.setFillHeight(true); // Permettre le remplissage de la hauteur

        //CREATE
        for (int i = 0; i<size; i++){
            grille.getRowConstraints().add(rowConstraints);
            grille.getColumnConstraints().add(columnConstraints);
            for (int j = 0; j<size; j++){
                grille.add(stackPanes[i][j], i, j);
                try {setstackPanesChild(i, j);} catch (PathManager.FileTooSmallException ignored){}
                stackPanes[i][j].getStyleClass().add("cell");
                stackPanes[i][j].setAlignment(Pos.CENTER);
            }
        }
    }
    protected void setstackPanesChild(int row, int line) throws PathManager.FileTooSmallException {

        String siteName = "abcd.com"; //pathManager.getInformation(row+line+1, 0);
        String pass = "abcdpass"; //pathManager.getInformation(row+line+1, 1);
        String user = "abcduser"; //pathManager.getInformation(row+line+1, 2);

        char letter = siteName.charAt(0); Label character = new Label(String.valueOf(letter).toUpperCase()); //Style Char
        Label site = new Label(siteName); //SiteName
        Label username = new Label(user); //UserName
        Label password = new Label(pass); //Password

        designUI.setStyleInsideStackPane(character, site, username, password, stackPanes[row][line]);

        stackPanes[row][line].getChildren().add(character);
        stackPanes[row][line].getChildren().add(site);
        stackPanes[row][line].getChildren().add(username);
        stackPanes[row][line].getChildren().add(password);


    }

    private void setSIZE(){
        this.size = (int) Math.sqrt(9);
    }
    private void createStackPane(){
        this.stackPanes = new StackPane[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                stackPanes[i][j] = new StackPane();
            }
        }
    }
}