package lk.esoft.kingston.auroraskincare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/MainView.fxml"))));
        stage.setTitle("Aurora Skin Care");
        stage.show();
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}