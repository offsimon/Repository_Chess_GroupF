module com.example.pcgf {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pcgf to javafx.fxml;
    exports com.example.pcgf;
}