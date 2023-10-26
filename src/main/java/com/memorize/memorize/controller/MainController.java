package com.memorize.memorize.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainController {
    /*
    Nodes
     */
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonShift;
    @FXML
    private Pane draggablePane;
    @FXML
    private GridPane gridPane;
    /*
    Classes
     */
    private final ShiftUI shiftUI = new ShiftUI();
    /*
    Initialization
     */
    @FXML
    protected void initialize() {
        shiftUI.paneDragSettings(draggablePane);
        buttonClose.getStyleClass().add("close-button");
        buttonShift.getStyleClass().add("buttonShift");
    }
    /*
    Control
     */
    @FXML
    protected void buttonClose() {
        System.exit(0);
    }
    @FXML
    protected void handleButtonAction() {
        shiftUI.nodeShift(gridPane, buttonShift, 1600, 1000, "left");
    }
}
