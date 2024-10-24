package lk.esoft.kingston.auroraskincare.db;

import javafx.scene.control.Alert;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class SingletonConnection {

    private static final SingletonConnection INSTANCE = new SingletonConnection();
    private Connection CONNECTION;

    private SingletonConnection() {
        try {
            CONNECTION = DriverManager.getConnection
                    ("jdbc:postgresql://localhost:12500/aurora_skin_care",
                            "postgres", "yasith12345");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to establish database connection, try restarting")
                    .showAndWait();
            System.exit(1);
        }
    }

    public static SingletonConnection getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return CONNECTION;
    }

}

