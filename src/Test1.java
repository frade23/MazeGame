import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Test1 extends Application{
    @Override
    public void start(Stage primaryStage){
//        Pane pane = new Pane();
//
//        Image image = new Image("file:image/logIn5.jpg");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(1280);
//        imageView.setFitHeight(960);
//
//        Button button1 = new Button("故事模式");
//        button1.setPrefHeight(50);
//        button1.setPrefWidth(140);
//        button1.setLayoutX(500);
//        button1.setLayoutY(500);
//
//        Button button2 = new Button("沙盒模式");
//        button2.setPrefHeight(50);
//        button2.setPrefWidth(140);
//        button2.setLayoutX(500);
//        button2.setLayoutY(550);
//
//        Button button3 = new Button("返回主菜单");
//        button3.setPrefHeight(50);
//        button3.setPrefWidth(140);
//        button3.setLayoutX(470);
//        button3.setLayoutY(635);
//        button3.setOnAction(event -> {
//        });
//
//        pane.getChildren().addAll(imageView, button1, button2, button3);
//
//        Scene scene = new Scene(pane);
//        primaryStage.setTitle("模式选择界面");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        //hBox1
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setPadding(new Insets(5, 5, 5, 5));

        //hBox2
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPadding(new Insets(5, 5, 5, 5));

        //in hBox1
        javafx.scene.text.Text text = new javafx.scene.text.Text("用户名: ");
        text.setFont(Font.font("宋体",20));
        TextField textField = new TextField();

        hBox1.getChildren().addAll(text, textField);

        //in hBox2
        Button button1 = new Button("开始游戏");
        button1.setOnAction(event -> {

        });
        Button button2 = new Button("返回上一界面");
        button2.setOnAction(event -> {
            System.exit(0);
        });
        hBox2.getChildren().addAll(button1, button2);

        Image image = new Image("file:image/logIn5.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(600);
        imageView.setFitWidth(1000);
        vBox.getChildren().addAll( hBox1, hBox2);

        stackPane.getChildren().addAll(imageView, vBox);

        Scene scene = new Scene(stackPane, 1000, 600);
        primaryStage.setTitle("登录界面");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
