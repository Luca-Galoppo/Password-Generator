module com.example.passwordgenerator {
    requires javafx.fxml;
    requires javafx.controls;

    opens com.example.passwordgenerator to javafx.fxml;
    exports com.example.passwordgenerator;
}