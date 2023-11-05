package com.memorize.memorize.controller;

import com.memorize.memorize.logic.AddPassword;
import com.memorize.memorize.stages.NewPasswordStage;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
    private TextField textUsername;
    @FXML
    private AnchorPane anchorBackground;
    @FXML
    private AnchorPane paneBehindAdd;
    @FXML
    private AnchorPane paneBack;
    @FXML
    private StackPane StackPaneVisualyze;
    @FXML
    private Label labelChar;
    @FXML
    private Label labelSite;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelPassword;
    /*
   Classes
    */
    private final DesignUI designUI = new DesignUI();
    public MainController mainController;
    public void setMain(MainController controller){
        this.mainController = controller;
    }
    /*
    Initialization
     */
    @FXML
    protected void initialize(){
        designUI.paneDragSettings(draggablePane);
        //Styles
        closeButton.getStyleClass().add("buttonClose");
        buttonNewPass.getStyleClass().add("buttonNewPass");
        anchorBackground.getStyleClass().add("anchorBackground");
        paneBehindAdd.getStyleClass().add("paneBehindAdd");
        draggablePane.getStyleClass().add("draggablePane");
        paneBack.getStyleClass().add("paneBack");
        textSite.getStyleClass().add("textField");
        textPassword.getStyleClass().add("textField");
        textUsername.getStyleClass().add("textField");
        StackPaneVisualyze.getStyleClass().add("cell");

        designUI.setStyleInsideStackPane(labelChar, labelSite, labelUsername, labelPassword, StackPaneVisualyze);

        /*
        Listeners gerant les textfields et les labels
         */
        //Association des textes et des labels
        textSite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                char letter = newValue.charAt(0);
                labelChar.setText(String.valueOf(letter).toUpperCase());
            }
        });
        labelSite.textProperty().bind(textSite.textProperty());
        labelUsername.textProperty().bind(textUsername.textProperty());
        labelPassword.textProperty().bind(textPassword.textProperty());
        //On limite le nombre de caractères
        textSite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 30) {  // Limite à 30 caractères
                textSite.setText(oldValue);
            }
        });
        textUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 30) {
                textUsername.setText(oldValue);
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
