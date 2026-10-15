module com.example.simuladorprobabilidadescartas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simuladorprobabilidadescartas to javafx.fxml;
    exports com.example.simuladorprobabilidadescartas;
}