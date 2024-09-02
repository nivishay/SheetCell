package Controller.MenuBar;

import Controller.Main.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class MenuBarController {

    @FXML
    private MenuBar fileMenuBar;
    @FXML
    private MainController MainController;

    public void setMainController(MainController mainController) {
        this.MainController = mainController;
    }

    @FXML
    void openFileChooser(ActionEvent event) {
        // Retrieve the current stage
        Stage stage = (Stage) fileMenuBar.getScene().getWindow();

        // Call the file chooser and get the selected file
        File selectedFile = openXMLFileChooser(stage);

        // Pass the selected file to the MainController
        if (selectedFile != null) {
            MainController.openFileChooser(selectedFile.getAbsolutePath());
        } else {
            System.out.println("File selection canceled.");
        }
    }

    public File openXMLFileChooser(Stage stage) {
        // Create a new FileChooser
        FileChooser fileChooser = new FileChooser();

        // Set the title of the FileChooser dialog
        fileChooser.setTitle("Open XML File");

        // Set the initial directory to the user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Add an extension filter to show only XML files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the open file dialog and get the selected file
        return fileChooser.showOpenDialog(stage);
    }
}


