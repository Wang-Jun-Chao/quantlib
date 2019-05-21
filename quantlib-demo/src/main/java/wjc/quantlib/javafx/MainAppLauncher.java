package wjc.quantlib.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-21 10:22
 **/
public class MainAppLauncher extends Application {
    public static void main(String[] args) {
        Application.launch(MainAppLauncher.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainApp.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("JavaFX Graph Example");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
