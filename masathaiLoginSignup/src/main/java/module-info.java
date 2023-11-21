module sample.masathailoginsignup {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens sample.masathailoginsignup to javafx.fxml;
    exports sample.masathailoginsignup;
}