module lk.ijse.esoft.auroraskincare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens lk.esoft.kingston.auroraskincare.view_controller to javafx.fxml;
    opens lk.esoft.kingston.auroraskincare to javafx.fxml;
    opens lk.esoft.kingston.auroraskincare.model to javafx.base;
    exports lk.esoft.kingston.auroraskincare;
}