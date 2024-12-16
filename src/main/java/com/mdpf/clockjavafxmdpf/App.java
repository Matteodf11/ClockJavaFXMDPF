package com.mdpf.clockjavafxmdpf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ButtonType btnyes = new ButtonType("YES");
        ButtonType btnno = new ButtonType("NO");
        Button button = new Button("Exit");
        Alert a = new Alert(AlertType.NONE);
        a.getButtonTypes().addAll(btnno, btnyes);
        VBox vboxcontainer = new VBox();
        vboxcontainer.setAlignment(Pos.CENTER);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("HH:mm:ss");
        Label lbClock = new Label();
        stage.setTitle("Clock by MDPF");
         

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               
                a.setAlertType(AlertType.CONFIRMATION);
                a.setTitle("Are you sure you want to exit?");
                a.setContentText("Do you want to exit?");
                a.setHeaderText("You are going to close the app");

                a.showAndWait().ifPresent(response -> {
                    if (response == btnyes) {
                        System.exit(0);
                    }
                });
            }
        };

        button.setOnAction(event);

        var imagenreloj = new Image("clock-mdpf.png");
        var imgView = new ImageView(imagenreloj);
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);
        imgView.setPreserveRatio(true);
        imgView.setCache(true);

        var imagenicono = new Image("clock-icon-mdpf.png");
        var imgViewIcon = new ImageView(imagenicono);

        button.setGraphic(imgViewIcon);

        lbClock.setFont(new Font("Comic-Sans", 50));
        lbClock.setTextFill(Color.CHOCOLATE);

        vboxcontainer.getChildren().addAll(imgView, lbClock, button);

        var scene = new Scene(vboxcontainer, 400, 400);
        stage.getIcons().add(imagenicono);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        Thread clockThread = new Thread(() -> {
            while (true) {
                try {

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    lbClock.setText(formateador.format(LocalDateTime.now()));
                });
            }
        });

        clockThread.setDaemon(true);
        clockThread.start();

    }

    public static void main(String[] args) {
        launch();
    }

}
