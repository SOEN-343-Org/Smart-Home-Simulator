package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.soen343.App;
import org.soen343.connection.DBConnection;
import org.soen343.connection.SQLQueriesBuilder;

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
    @FXML private Label invalidLogInLabel;
    @FXML private Label invalidSignUpLabel;

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
        //switch to sign up page
    }

    private static Scene scene;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void btnLogInAction(ActionEvent actionEvent) throws IOException{

        if(!tfUsernameLogIn.getText().isBlank() && !tfPassLogIn.getText().isBlank()){
            //validateLogin();



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
                                //User
                                //go to next place
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                scene = new Scene(loadFXML("DashboardView"), 1200, 900); //TODO: Change scene for our project flow in the future
                                stage.setScene(scene);
                                stage.show();

                            }
                        }
                    }
                }
            }catch (SQLException e){
                Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
                System.exit(0);
            }

        }else {
            invalidLogInLabel.setText("Please enter your username and password");
        }
    }
    public void btnSignUpAction(ActionEvent actionEvent) throws IOException{
        if(!tfUsernameLogIn.getText().isBlank() && !tfPassLogIn.getText().isBlank()){
            validateSignUp();

        }else {
            invalidLogInLabel.setText("Please enter a username and password");
        }
    }

    public void validateSignUp(){
        try {
            String usernameExistsQuery = SQLQueriesBuilder.usernameExists(tfUsernameLogIn.getText());
            ResultSet queryResult = statement.executeQuery(usernameExistsQuery);
            while (queryResult.next()){
                if (queryResult.getInt(1)==1){
                    invalidSignUpLabel.setText("This username is already in use");
                }else {
                    SQLQueriesBuilder.addUser(tfUsernameSignUp.getText(),tfPassSignUp.getText());
                    //go to next place
                }
            }
        }catch (SQLException e){
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    /**public void validateLogin(){
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
                            //User
                            //go to next place
                        }
                    }
                }
            }
        }catch (SQLException e){
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }*/

    public void changeScene(ActionEvent actionEvent) {
        mainController.exitLoginView();
    }
}
