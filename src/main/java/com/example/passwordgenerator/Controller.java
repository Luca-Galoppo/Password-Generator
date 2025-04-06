package com.example.passwordgenerator;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.*;


public class Controller {

    final char[] lowerCaseAlphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    final char[] upperCaseAlphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    final char[] specialChar = {'!','@','#','$','%','^','&','*','(',')','-','_','=','+',',','<','>','.',':',';','{','"','}','[',']','|'};
    final char[] numbers = {'1','2','3','4','5','6','7','8','9','0'};
    //@FXML
    //public ComboBox comboBoxSpecial;
    @FXML
    private Label sliderValueLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Slider slider = new Slider();
    @FXML
    private Button modeButton;
    private boolean darkMode = true;
    private Scene scene;
    public CheckBox checkBox;
    public Button generatePasswordButton;

    public void initialize() {
        scene = modeButton.getScene();
        if (slider != null && sliderValueLabel != null) {
            slider.setMajorTickUnit(2);
            slider.setSnapToTicks(true);
            slider.setShowTickLabels(true);
            slider.setMinorTickCount(1);
        }
        //comboBoxSpecial.setDisable(!checkBox.isSelected());
        //checkBox.setOnAction(event -> initializeComboBox());
        passwordLabel.setOnMouseClicked(this::handleLabelClick);
    }

//    @FXML
//    public void initializeComboBox(){
//        comboBoxSpecial.setDisable(!checkBox.isSelected());
//        for (char c : specialChar) {
//            comboBoxSpecial.getItems().add(c);
//        }
//    }

    @FXML
    private void generatePassword() {
        passwordLabel.setText(onGeneratePassword());
    }

    public String onGeneratePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < slider.getValue(); i++) {
            int selectChar = random.nextInt(checkBox.isSelected() ? 4 : 3);
            char character = 0;

            switch (selectChar) {
                case 0:
                    character = lowerCaseAlphabet[random.nextInt(lowerCaseAlphabet.length)];
                    break;
                case 1:
                    character = upperCaseAlphabet[random.nextInt(upperCaseAlphabet.length)];
                    break;
                case 2:
                    character = numbers[random.nextInt(numbers.length)];
                    break;
                case 3:
                    if(checkBox.isSelected()) character = specialChar[random.nextInt(specialChar.length)];
                    break;
                default:
                    character = ' ';
            }
            password.append(character);
        }
        return password.toString();
    }

    public void toggleMode() {
        darkMode = !darkMode;
        if (darkMode) {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
            scene.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
            modeButton.setText("Dark Mode");
        } else {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
            scene.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
            modeButton.setText("Light Mode");
        }

    }

    private void handleLabelClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(passwordLabel.getText());
            clipboard.setContent(content);
            passwordLabel.setStyle("-fx-background-color: lightblue;");

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            javafx.application.Platform.runLater(() -> {
                                passwordLabel.setStyle("");
                            });
                        }
                    },
                    500
            );
        }
    }

}