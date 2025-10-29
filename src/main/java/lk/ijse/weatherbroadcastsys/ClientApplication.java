package lk.ijse.weatherbroadcastsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Client.fxml"));
        Scene clientScene = new Scene(root);
        stage.setScene(clientScene);
        stage.setTitle("Client");
        stage.show();
    }
}
