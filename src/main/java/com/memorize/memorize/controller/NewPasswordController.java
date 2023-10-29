package com.memorize.memorize.controller;

import com.memorize.memorize.logic.AddPassword;
import com.memorize.memorize.stages.NewPasswordStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NewPasswordController {
    /*
    Nodes
     */
    @FXML
    private Button buttonNewPass;
    @FXML
    private Button closeButton;
    @FXML
    private AnchorPane draggablePane;
    @FXML
    private TextField textSite;
    @FXML
    private TextField textPassword;
    @FXML
    private AnchorPane anchorBackground;
    @FXML
    private AnchorPane anchorPaneAddPassword;
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
    private final DesignUI shiftUI = new DesignUI();
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
        //Styles
        closeButton.getStyleClass().add("buttonClose");
        buttonNewPass.getStyleClass().add("buttonNewPass");
        anchorBackground.getStyleClass().add("anchorBackground");
        anchorPaneAddPassword.getStyleClass().add("anchorPaneAddPassword");
        draggablePane.getStyleClass().add("draggablePane");
        textSite.getStyleClass().add("textSite");
        textPassword.getStyleClass().add("textPassword");
        vboxVisualyze.getStyleClass().add("cell");
        labelChar.getStyleClass().add("labelChar");
        labelSite.getStyleClass().add("labelSite");
        labelPassword.getStyleClass().add("labelPassword");
        /*
        Listeners gerant les textfields et les labels
         */
        //Association des textes et des labels
        textSite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                char letter = newValue.charAt(0);
                labelChar.setText(String.valueOf(letter));
            }
        });
        labelSite.textProperty().bind(textSite.textProperty());
        labelPassword.textProperty().bind(textPassword.textProperty());
        //On limite le nombre de caractères
        textSite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 15) {  // Limite à 15 caractères
                textSite.setText(oldValue);
            }
        });
        textPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 30) {
                textPassword.setText(oldValue);
            }
        });


        //Refresh

        //Set prévisualisation

    }
    /*
    Control
     */
    @FXML
    protected void setPasswordButton(){
        //Set des variables dans AddPassword
        AddPassword addPassword = new AddPassword(labelChar.getText(),labelSite.getText(), labelPassword.getText());
        //Appel de la méthode permettant d'enregistrer les variables extérieurement
        addPassword.addPassword();
        //Set refresh
        mainController.refresh();
    }
    @FXML
    protected void closeBtn(){NewPasswordStage.passClose();}

    private void verifierSiMemeSite(){

    }
}
