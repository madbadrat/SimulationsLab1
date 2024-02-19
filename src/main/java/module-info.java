module ru.vorotov.simulationslab1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.vorotov.simulationslab1 to javafx.fxml;
    exports ru.vorotov.simulationslab1;
}