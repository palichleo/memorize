package com.memorize.memorize.controller;

import com.memorize.memorize.logic.GridLogic;
import com.memorize.memorize.stages.NewPasswordStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NewPasswordController {
    /*
    Nodes
     */
    @FXML
    protected Button buttonNewPass;
    @FXML
    private Button closeButton;
    @FXML
    protected AnchorPane draggablePane;
    @FXML
    private TextField textSite;
    @FXML
    private TextField textPassword;
    @FXML
    private AnchorPane anchorBackground;
    @FXML
    private VBox vboxVisualyze;
    @FXML
    private Label labelChar;
    @FXML
    private Label labelSite;
    @FXML
    private Label labelPassword;
    /*
   Classes
    */
    private final ShiftUI shiftUI = new ShiftUI();
    public MainController mainController;
    public void setMain(MainController controller){
        this.mainController = controller;
    }
    /*
    Initialization
     */
    @FXML
    protected void initialize(){
        shiftUI.paneDragSettings(draggablePane);

        vboxVisualyze.getStyleClass().add("cell");
        closeButton.getStyleClass().add("buttonClose");
        buttonNewPass.getStyleClass().add("buttonNewPass");
        anchorBackground.getStyleClass().add("anchorBackground");
        draggablePane.getStyleClass().add("draggablePane");
        textSite.getStyleClass().add("textSite");
        textPassword.getStyleClass().add("textPassword");

        //Refresh
//Set prévisualisation

    }
    /*
    Control
     */
    @FXML
    protected void setPasswordButton(){
        //Set des variables





        //Set refresh

        mainController.refresh();
    }
    @FXML
    protected void closeBtn(){NewPasswordStage.passClose();}

    private void verifierSiMemeSite(){

    }
}
