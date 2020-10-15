package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.soen343.models.Model;

public class DashboardController extends Controller {


    @FXML
    private HouseLayoutController houseLayoutController;
    @FXML
    private SmartHomeCoreController smartHomeCoreController;
    @FXML
    private LoginController loginController;
    @FXML
    private IndividualController individualController;

    @FXML
    private AnchorPane login;

    @FXML
    private AnchorPane individual;

    @FXML
    public void initialize() {
        // Sets the model for all controllers
        this.setModel(new Model());
        //loginController.setModel(model);
        houseLayoutController.setModel(model);
        smartHomeCoreController.setModel(model);


        individualController.setModel(model);
        individualController.setMainController(this);
        individualController.init();


        //loginController.setMain(this);

        houseLayoutController.drawLayout();


    }

    public void setIndividualViewVisibility(boolean vis){
        individual.setVisible(vis);
    }

    public void login() {
        login.setVisible(false);
    }
}
