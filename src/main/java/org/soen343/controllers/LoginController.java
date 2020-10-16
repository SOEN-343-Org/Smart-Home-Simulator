package org.soen343.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.soen343.App;
import org.soen343.connection.DBConnection;
import org.soen343.connection.SQLQueriesBuilder;
import org.soen343.models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController extends Controller {
    @FXML private Button btnSignUp;
    @FXML private TextField tfUsernameSignUp;
    @FXML private TextField tfPassSignUp;
    @FXML private TextField tfUsernameLogIn;
    @FXML private PasswordField tfPassLogIn;
    @FXML private Button btnLogIn;
    @FXML private Button btnSwitchSignUp;
    @FXML private Button btnSwitchLogIn;
    @FXML private Label invalidLogInLabel;
    @FXML private Label invalidSignUpLabel;
    @FXML private Pane signUpPane;
    @FXML private Pane logInPane;
    DashboardController mainController;
    Connection connection;
    Statement statement;

    public void initialize(){
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
        }catch (SQLException e){
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    public void btnSwitchSignUpAction(ActionEvent actionEvent){
        tfUsernameSignUp.setText("");
        tfPassSignUp.setText("");
        tfUsernameLogIn.setText("");
        tfPassLogIn.setText("");
        invalidSignUpLabel.setText("");
        invalidLogInLabel.setText("");
        signUpPane.toFront();
    }
    public void btnSwitchLogInAction(ActionEvent actionEvent){
        tfUsernameSignUp.setText("");
        tfPassSignUp.setText("");
        tfUsernameLogIn.setText("");
        tfPassLogIn.setText("");
        invalidSignUpLabel.setText("");
        invalidLogInLabel.setText("");
        logInPane.toFront();
    }

    public void btnLogInAction(ActionEvent actionEvent){
        if(!tfUsernameLogIn.getText().isBlank() && !tfPassLogIn.getText().isBlank()){
            validateLogin();
        }else {
            invalidLogInLabel.setText("Please enter your username and password");
        }
    }

    public void btnSignUpAction(ActionEvent actionEvent){
        if(!tfUsernameSignUp.getText().isBlank() && !tfPassSignUp.getText().isBlank()){
            validateSignUp();
        }else {
            invalidSignUpLabel.setText("Please enter a username and password");
        }
    }

    public void validateSignUp(){
        try {
            String usernameExistsQuery = SQLQueriesBuilder.usernameExists(tfUsernameSignUp.getText());
            ResultSet queryResult = statement.executeQuery(usernameExistsQuery);
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, usernameExistsQuery);
            while (queryResult.next()){
                if (queryResult.getInt(1)==1){
                    invalidSignUpLabel.setText("This username is already in use");
                }else {
                    String addUserQuery = SQLQueriesBuilder.addUser(tfUsernameSignUp.getText(),tfPassSignUp.getText());
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, addUserQuery);
                    statement.executeUpdate(addUserQuery);
                    //CHANGE SCENES HERE TO DASHBOARDVIEW
                    break;
                }
            }
        }catch (SQLException e){
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    public void validateLogin(){
        try {
            String usernameExistsQuery = SQLQueriesBuilder.usernameExists(tfUsernameLogIn.getText());
            String userExistsQuery = SQLQueriesBuilder.userExists(tfUsernameLogIn.getText(), tfPassLogIn.getText());

            ResultSet queryResult = statement.executeQuery(usernameExistsQuery);
            //always 1 result
            while(queryResult.next()){
                if(queryResult.getInt(1)==0){
                    invalidLogInLabel.setText("User does not exist. Please sign up");
                }else{
                    queryResult = statement.executeQuery(userExistsQuery);
                    while (queryResult.next()){
                        if(queryResult.getInt(1)==0){
                            invalidLogInLabel.setText("Invalid password. Please try again");
                        }else {
                            invalidLogInLabel.setText("IT WORKED. REMOVE LATER");
                            User.setUsername(tfUsernameLogIn.getText());
                            changeScene();
                            //CHANGE SCENE HERE TO DASHBOARDVIEW
                        }
                    }
                }
            }
        }catch (SQLException e){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    //not sure
    public void changeScene(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("DashboardView.fxml"));
            Stage dashboardStage = new Stage();
            dashboardStage.initStyle(StageStyle.UNDECORATED);
            dashboardStage.setScene(new Scene(root,1200, 900));
            dashboardStage.show();

        }catch (IOException e){

        }
    }

}
