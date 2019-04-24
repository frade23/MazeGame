package sample.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.core.*;
import sample.core.Icon;
import sample.server.Server;

import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.Map;
import javafx.scene.text.Text;

import javax.swing.*;

public class Main extends Application {
    private static final double TILE_SIZE = 30;
    private static Server server;
    private ImageView[][] mapTiles;
    private GridPane map;
    private Stage stage;
    private Map<Icon, Image> imageMap = new HashMap<>();
    private Person person = new Person();
    public static int a;

    static String path = "file:assets/img/grass/";

    public static void main(String[] args) {
        if(args.length == 2)
            a = 0;
        else if(args.length != 4)
            a = 5;
        else if(args[3].equals("1"))
            a = 5;
        else if(args[3].equals("2"))
            a = 6;
        else if(args[3].equals("3"))
            a = 7;
        else if(args[3].equals("4"))
            a = 8;
        server = new Server();
        server.createGame(a);
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.person = server.getPerson();
        primaryStage.setTitle("迷宫游戏");
        this.stage = primaryStage;

        // prepare resources
        initGameFrame();
        initResources();
        addKeyControls();

        // show game view
        createAndShowGameView();
    }

    void createAndShowGameView() {
        Icon[][] icons = server.getGameView();
        int numRows = icons.length;
        int numCols = icons[0].length;
        mapTiles = new ImageView[numRows][numCols];
        map.getChildren().removeAll(map.getChildren());
        for (int i = 0; i < numRows; i++) {
            // maze contents
            for (int j = 0; j < numCols; j++) {
                mapTiles[i][j] = newImageView(icons[i][j]);
                map.add(mapTiles[i][j], j + 1, i + 1);
            }
        }
        this.stage.show();
        map.requestFocus();
    }

    private void updateGameView() {
        Icon[][] icons = server.getGameView();
        int numRows = icons.length;
        int numCols = icons[0].length;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                mapTiles[i][j].setImage(iconToImage(icons[i][j]));
            }
        }
    }


    private ImageView newImageView(Icon icon) {
        ImageView imageView = new ImageView(iconToImage(icon));
        imageView.setFitWidth(TILE_SIZE);
        imageView.setFitHeight(TILE_SIZE);
        return imageView;
    }

    //枚举转为图像
    private Image iconToImage(Icon icon) {
        Image image = imageMap.get(icon);
        if (image == null)
            throw new IllegalArgumentException(icon.toString());
        return image;
    }

    //增加关键操作
    private void addKeyControls() {
        map.setOnKeyPressed(e -> {
            Direction d = null;
            switch (e.getCode()) {
                case W:
                    d = Direction.NORTH;
                    break;
                case A:
                    d = Direction.WEST;
                    break;
                case S:
                    d = Direction.SOUTH;
                    break;
                case D:
                    d = Direction.EAST;
                    break;
                case B:
                    d = Direction.BACK;
                    break;
                case K:
                    d = Direction.KILL;
                    break;
                case P:
                    d = Direction.PICK;
                    break;
                case H:
                    d = Direction.HELP;
                    break;
                case C:
                    d = Direction.CHECK;
                    break;
                case Q:
                    d = Direction.QUIT;
                    break;
                case X:
                    d = Direction.SAVE;
                    break;

            }
            if (d != null && !person.isEnd(server.getMap()) && !person.killHero(server.getMonster(), server.getMap())) {
                server.movePlayer(d);
            }
            if(person.isEnd(server.getMap())){
                if(a >= 5){
                    JOptionPane.showMessageDialog(null, "恭喜你通过该关卡", "消息", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
                server.createGame(a += 1);
                server.refresh();
            }
            else if (a == 4){
                JOptionPane.showMessageDialog(null, "恭喜你通关故事模式, 游戏即将退出", "消息", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            // repaint gui
            updateGameView();
        });
    }

    //初始化游戏框架
    private void initGameFrame() {
        VBox vBox = new VBox();
        MenuBar menuBar = new MyMenuBar(server, this);
        map = new GridPane();
        vBox.getChildren().addAll(menuBar, map);
        this.stage.setScene(new Scene(vBox));
        Music.mediaPlayer7.setCycleCount(-1);
        Music.mediaPlayer7.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event ->{
            if(a == 3 || a == 8)
            {
                server.moveAllMonsters();
                updateGameView();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if(person.killHero(server.getMonster(), server.getMap())){
                killHeroStage();
            }
        }));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
    }
//    初始化资源
    void initResources() {

        Image WALL_IMAGE = new Image(path + "wall.png");
        Image SPACE_IMAGE = new Image(path + "space.png");
        Image HERO_IMAGE = new Image(path + "hero.png");
        Image END_IMAGE = new Image(path + "end.png");
        Image FOOTPRINT_IMAGE = new Image(path + "footprint.png");
        Image MONSTER_IMAGE = new Image(path + "monster.png");
        Image TREASURE_IMAGE = new Image(path + "treasure.png");

        // add to image map
        imageMap.put(Icon.EMPTY, SPACE_IMAGE);
        imageMap.put(Icon.WALL, WALL_IMAGE);
        imageMap.put(Icon.HERO, HERO_IMAGE);
        imageMap.put(Icon.FOOTPRINT, FOOTPRINT_IMAGE);
        imageMap.put(Icon.MONSTER, MONSTER_IMAGE);
        imageMap.put(Icon.END, END_IMAGE);
        imageMap.put(Icon.TREASURE, TREASURE_IMAGE);
    }


    private void killHeroStage() {
        JOptionPane.showMessageDialog(null, "你已经死亡即将退出游戏", "消息", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public Stage getStage() {
        return stage;
    }

    //排行榜
    public void rankList()throws Exception{
        String ranking = "";
        String itself = "";

        try{
            ArrayList<Integer> score = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            File file = new File("res/rankingList");
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.write(server.getPerson().getTheMarkOfHero());
//            dataOutputStream.writeUTF(server.getPerson().getName);
//            itself += server.getPerson().getName + " " + server.getPerson().getTheMarkOfHero();
            dataOutputStream.close();

            DataInputStream scanner = new DataInputStream(new FileInputStream(file));

                while (dataOutputStream.size() != 0){
                    score.add(scanner.readInt());
                    name.add(scanner.readUTF());
                }

            int[] temp = new int[score.size()];
            for (int i = 0; i < score.size(); i++){
                temp[i] = score.get(i);
            }
            Arrays.sort(temp);

            for (int i = 0; i < 3; i++){
                ranking += "\n" + name.get(score.indexOf(temp[temp.length - i - 1])) + "  " + temp[temp.length - i - 1];
            }
            scanner.close();
        }
        catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, itself + "\n" + "排名" + "\n" + ranking);
        alert.showAndWait();
    }
//    public void logInStage(Stage primaryStage){
//        StackPane stackPane = new StackPane();
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        //hBox1
//        HBox hBox1 = new HBox();
//        hBox1.setAlignment(Pos.CENTER);
//        hBox1.setPadding(new Insets(5, 5, 5, 5));
//
//        //hBox2
//        HBox hBox2 = new HBox();
//        hBox2.setAlignment(Pos.CENTER);
//        hBox2.setPadding(new Insets(5, 5, 5, 5));
//
//        //hBox3
//        HBox hBox3 = new HBox();
//        hBox3.setAlignment(Pos.CENTER);
//        hBox3.setPadding(new Insets(5, 5, 5, 5));
//
//        //in hBox1
//        javafx.scene.text.Text text = new javafx.scene.text.Text("用户名: ");
//        text.setFont(Font.font("宋体",20));
//        TextField textField = new TextField();
//
//        hBox1.getChildren().addAll(text, textField);
//
//        //in hBox2
//        Button button1 = new Button("登录");
//        button1.setOnAction(event -> {
//
//        });
//        Button button2 = new Button("退出");
//        button2.setOnAction(event -> {
//            System.exit(0);
//        });
//        hBox2.getChildren().addAll(button1, button2);
//
//        //in hBox3
//
//        javafx.scene.text.Text text1 = new javafx.scene.text.Text("足迹数: ");
//        text.setFont(Font.font("宋体",20));
//        TextField textField1 = new TextField();
//
//        hBox3.getChildren().addAll(text1, textField1);
//
//        Image image = new Image("file:image/logIn5.jpg");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(600);
//        imageView.setFitWidth(1000);
//        vBox.getChildren().addAll( hBox1, hBox2, hBox3);
//
//        stackPane.getChildren().addAll(imageView, vBox);
//
//        Scene scene = new Scene(stackPane, 1000, 600);
//        primaryStage.setTitle("登录界面");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }
//


//    public void startStage(Stage primaryStage){
//        Pane pane = new Pane();
//
//        Image image = new Image("file:image/logIn5.jpg");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(1280);
//        imageView.setFitHeight(960);
//
//        Button button1 = new Button("开始游戏");
//        button1.setPrefHeight(50);
//        button1.setPrefWidth(140);
//        button1.setLayoutX(500);
//        button1.setLayoutY(460);
//
//        Button button2 = new Button("继续游戏");
//        button2.setPrefHeight(50);
//        button2.setPrefWidth(140);
//        button2.setLayoutX(500);
//        button2.setLayoutY(520);
//
//        Button button3 = new Button("退出游戏");
//        button3.setPrefHeight(50);
//        button3.setPrefWidth(140);
//        button3.setLayoutX(500);
//        button3.setLayoutY(635);
//        button3.setOnAction(event -> {
//            System.exit(0);
//        });
//
//        pane.getChildren().addAll(imageView, button1, button2, button3);
//
//        Scene scene = new Scene(pane);
//        primaryStage.setTitle("主菜单");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();
//
//
//
////        VBox vBox = new VBox();
////
////        HBox hBox1 = new HBox();
////        hBox1.setAlignment(Pos.CENTER);
////        hBox1.setPadding(new Insets(5, 5, 5, 5));
////
////        //hBox2
////        HBox hBox2 = new HBox();
////        hBox2.setAlignment(Pos.CENTER);
////        hBox2.setPadding(new Insets(5, 5, 5, 5));
////
////        //hBox3
////        HBox hBox3 = new HBox();
////        hBox3.setAlignment(Pos.CENTER);
////        hBox3.setPadding(new Insets(20, 5, 5, 5));
//    }

//    public void patternStage(Stage primaryStage){
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
//        button1.setLayoutY(480);
//
//        Button button2 = new Button("沙盒模式");
//        button2.setPrefHeight(50);
//        button2.setPrefWidth(140);
//        button2.setLayoutX(500);
//        button2.setLayoutY(540);
//
//        Button button3 = new Button("返回主菜单");
//        button3.setPrefHeight(50);
//        button3.setPrefWidth(140);
//        button3.setLayoutX(500);
//        button3.setLayoutY(635);
//        button3.setOnAction(event -> {
//            startStage(primaryStage);
//        });
//
//        pane.getChildren().addAll(imageView, button1, button2, button3);
//
//        Scene scene = new Scene(pane);
//        primaryStage.setTitle("模式选择界面");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }
//
//    public void sandboxStage(Stage primaryStage){
//        Pane pane = new Pane();
//
//        Image image = new Image("file:image/logIn5.jpg");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(1280);
//        imageView.setFitHeight(960);
//
//        Button button1 = new Button("第一关");
//        button1.setPrefHeight(50);
//        button1.setPrefWidth(140);
//        button1.setLayoutX(510);
//        button1.setLayoutY(445);
//
//        Button button2 = new Button("第二关");
//        button2.setPrefHeight(50);
//        button2.setPrefWidth(140);
//        button2.setLayoutX(510);
//        button2.setLayoutY(495);
//
//        Button button3 = new Button("第三关");
//        button3.setPrefHeight(50);
//        button3.setPrefWidth(140);
//        button3.setLayoutX(510);
//        button3.setLayoutY(545);
//        button3.setOnAction(event -> {
//            startStage(primaryStage);
//        });
//
//        Button button4 = new Button("第四关");
//        button4.setPrefHeight(50);
//        button4.setPrefWidth(140);
//        button4.setLayoutX(510);
//        button4.setLayoutY(595);
//
//        Button button5 = new Button("返回上一界面");
//        button5.setPrefHeight(50);
//        button5.setPrefWidth(140);
//        button5.setLayoutX(510);
//        button5.setLayoutY(685);
//
//
//        pane.getChildren().addAll(imageView, button1, button2, button3, button4, button5);
//
//        Scene scene = new Scene(pane);
//        primaryStage.setTitle("模式选择界面");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }
//
//    public void storyStage(Stage primaryStage){
//        StackPane stackPane = new StackPane();
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        //hBox1
//        HBox hBox1 = new HBox();
//        hBox1.setAlignment(Pos.CENTER);
//        hBox1.setPadding(new Insets(5, 5, 5, 5));
//
//        //hBox2
//        HBox hBox2 = new HBox();
//        hBox2.setAlignment(Pos.CENTER);
//        hBox2.setPadding(new Insets(5, 5, 5, 5));
//
//        //in hBox1
//        javafx.scene.text.Text text = new javafx.scene.text.Text("用户名: ");
//        text.setFont(Font.font("宋体",20));
//        TextField textField = new TextField();
//
//        hBox1.getChildren().addAll(text, textField);
//
//        //in hBox2
//        Button button1 = new Button("开始游戏");
//        button1.setOnAction(event -> {
//
//        });
//        Button button2 = new Button("返回上一界面");
//        button2.setOnAction(event -> {
//            System.exit(0);
//        });
//        hBox2.getChildren().addAll(button1, button2);
//
//        Image image = new Image("file:image/logIn5.jpg");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(600);
//        imageView.setFitWidth(1000);
//        vBox.getChildren().addAll( hBox1, hBox2);
//
//        stackPane.getChildren().addAll(imageView, vBox);
//
//        Scene scene = new Scene(stackPane, 1000, 600);
//        primaryStage.setTitle("故事模式");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }
}
