package org.soen343.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    Connection connection;
    Statement statement;
    @FXML
    private TextField tfUsernameSignUp;
    @FXML
    private TextField tfPassSignUp;
    @FXML
    private TextField tfUsernameLogIn;
    @FXML
    private PasswordField tfPassLogIn;
    @FXML
    private Label invalidLogInLabel;
    @FXML
    private Label invalidSignUpLabel;
    @FXML
    private Pane signUpPane;
    @FXML
    private Pane logInPane;

    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("DashboardView" + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    void initializeController() {
    }

    public void initialize() {
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    public void btnSwitchSignUpAction() {
        setFieldsToBlanks();
        signUpPane.toFront();
    }

    public void btnSwitchLogInAction() {
        setFieldsToBlanks();
        logInPane.toFront();
    }

    public void btnLogInAction(ActionEvent actionEvent) {
        if (!tfUsernameLogIn.getText().isBlank() && !tfPassLogIn.getText().isBlank()) {
            validateLogin(actionEvent);
        } else {
            invalidLogInLabel.setText("Please enter your username and password");
        }
    }

    public void btnSignUpAction(ActionEvent actionEvent) {
        if (!tfUsernameSignUp.getText().isBlank() && !tfPassSignUp.getText().isBlank()) {
            validateSignUp(actionEvent);
        } else {
            invalidSignUpLabel.setText("Please enter a username and password");
        }
    }

    public void validateSignUp(ActionEvent actionEvent) {
        try {
            String usernameExistsQuery = SQLQueriesBuilder.usernameExists(tfUsernameSignUp.getText());
            ResultSet queryResult = statement.executeQuery(usernameExistsQuery);
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, usernameExistsQuery);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    invalidSignUpLabel.setText("This username is already in use");
                } else {
                    String addUserQuery = SQLQueriesBuilder.addUser(tfUsernameSignUp.getText(), tfPassSignUp.getText());
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, addUserQuery);
                    statement.executeUpdate(addUserQuery);
                    changeScene(actionEvent);
                    break;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    public void validateLogin(ActionEvent actionEvent) {
        try {
            String usernameExistsQuery = SQLQueriesBuilder.usernameExists(tfUsernameLogIn.getText());
            String userExistsQuery = SQLQueriesBuilder.userExists(tfUsernameLogIn.getText(), tfPassLogIn.getText());

            ResultSet queryResult = statement.executeQuery(usernameExistsQuery);
            //always 1 result
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 0) {
                    invalidLogInLabel.setText("User does not exist. Please sign up");
                } else {
                    queryResult = statement.executeQuery(userExistsQuery);
                    while (queryResult.next()) {
                        if (queryResult.getInt(1) == 0) {
                            invalidLogInLabel.setText("Invalid password. Please try again");
                        } else {
                            invalidLogInLabel.setText("IT WORKED. REMOVE LATER");
                            User.setUsername(tfUsernameLogIn.getText());
                            changeScene(actionEvent);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    public void changeScene(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(loadFXML(), 1200, 900);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
    }

    private void setFieldsToBlanks() {
        tfUsernameSignUp.setText("");
        tfPassSignUp.setText("");
        tfUsernameLogIn.setText("");
        tfPassLogIn.setText("");
        invalidSignUpLabel.setText("");
        invalidLogInLabel.setText("");
    }
}
