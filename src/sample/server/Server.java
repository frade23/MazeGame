package sample.server;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.client.Main;
import sample.client.MyMenuBar;
import sample.core.*;

import java.io.*;

import static sample.core.Icon.EMPTY;
import static sample.core.Icon.TREASURE;

public class Server {

    private Icon[][] map;
    private Person person = new Person();
    private Monster[] monster = new Monster[8];

    public void createGame(int a) {
        if(a == 0 || a == 5)
            map = new Map().map1;
        else if(a == 1 || a == 6) {
            map = new Map().map2;
            addTreasure();
        }
        else if(a == 2 || a == 7) {
            map = new Map().map3;
            addTreasure();
            addMonster();
        }
        else if(a == 3 || a == 8) {
            map = new Map().map4;
            addTreasure();
            addMonster();
        }

    }

    public Icon[][] getGameView() {
        Icon[][] tmp = new Icon[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                tmp[i][j] = map[i][j];
            }
        }
        tmp[person.getX()][person.getY()] = Icon.HERO;
        return tmp;
    }

    public void saveGame() {
        try {
            checkDirectory("save");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./save/1.save"));
            objectOutputStream.writeObject(map);
            objectOutputStream.writeObject(person);
//            for (int i = 0; i < 7; i++){
//                objectOutputStream.writeObject(monster[i]);
//            }

            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkDirectory(String name) {
        File file = new File(name);
        if (!file.exists() || file.isFile())
            while (!file.mkdir()) {
                System.out.println("can not create directory: " + name);
            }
    }

    public void loadGame() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./save/1.save"));
            try {
                map = (Icon[][]) objectInputStream.readObject();
                person = (Person) objectInputStream.readObject();
//                for (int i = 0; i < 7; i++){
//                    monster[i] = (Monster) ois.readObject();
//                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //移动人物, 人物操作
    public void movePlayer(Direction d) {
        switch (d) {
            case NORTH:
                person.moveUp(map);
                break;
            case SOUTH:
                person.moveDown(map);
                break;
            case EAST:
                person.moveRight(map);
                break;
            case WEST:
                person.moveLeft(map);
                break;
            case BACK:
                person.moveBack(map, this);
                break;
            case PICK:
                person.pickUpTreasure(map);
                break;
            case KILL:
                person.killMonster(map, monster);
                break;
            case HELP:
                MyMenuBar.helpStage();
                break;
            case CHECK:
               checkStage();
                break;
            case QUIT:
                Text text1 = new Text("确定退出游戏吗?");
                Stage stage1 = new Stage();
                start(stage1, text1);
                break;
            case SAVE:
                Text text2 = new Text("确定保存游戏退出吗?");
                Stage stage2 = new Stage();
                start1(stage2, text2);
                break;

        }
        person.printFootPrint(6, map);
    }

    public void addMonster(){
        for (int i = 0; i < monster.length; i++){
            monster[i] = new Monster(map);
        }
    }
    public void moveAllMonsters(){
        for (int i = 0; i < monster.length; i++){
            if(monster[i].getSiteMX1() != 0 && monster[i].getSiteMX1() != person.getX() && monster[i].getSiteMX1() != person.getY())
            monster[i].move(map);
        }
    }
    public void addTreasure(){
        System.out.println(1);
        for (int k = 0; k < 8; k++){
            boolean canPut = true;
            while (canPut){
                int i = (int)(Math.random() * map.length);

                for (int j = (int)(Math.random()*map[0].length); j < map[0].length; j++){
                    if (map[i][j] == EMPTY && i != 1){
                        map[i][j] = TREASURE;
                        canPut = false;
                        break;
                    }
                }
            }
        }
    }

    public void refresh(){
        person.columnY.add(person.pos[1]);
        person.rowX.add(person.pos[0]);
        person.pos = new int[]{1, 1};
    }

    public void checkStage(){
        if (Main.a > 4){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION,"您当前得分为: " + person.getTheMarkOfHero() + "\n" +
                    "您当前所走步数为: " + person.getTheStepNumberOfHero() + "\n" +
                    "您当前后退步数为: " + person.getTheBackStepNumberOfHero() + "\n" +
                    "您当前杀怪数为: " + person.getTheNumberOfKillMonster() + "\n" +
                    "您当前所捡宝物数为: " + person.getTheNumberOfPickUpTreasure() + "\n" +
                    "您当前所在关卡: 第" + (Main.a - 4) + "关");
            alert1.showAndWait();
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION,"您当前得分为: " + person.getTheMarkOfHero() + "\n" +
                    "您当前所走步数为: " + person.getTheStepNumberOfHero() + "\n" +
                    "您当前后退步数为: " + person.getTheBackStepNumberOfHero() + "\n" +
                    "您当前杀怪数为: " + person.getTheNumberOfKillMonster() + "\n" +
                    "您当前所捡宝物数为: " + person.getTheNumberOfPickUpTreasure() + "\n" +
                    "您当前所在关卡: 第" + (Main.a + 1) + "关");
            alert1.showAndWait();
        }
    }


    private void start(Stage primaryStage, Text text){
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.TOP_CENTER);
        VBox vBox = new VBox();
        Button button1 = new Button("确定");
        Button button2 = new Button("取消");

        hBox1.getChildren().addAll(text);
        hBox2.getChildren().addAll(button1, button2);
        vBox.getChildren().addAll(hBox1, hBox2);

        button1.setOnAction(event -> {
            System.exit(0);
        });
        button2.setOnAction(event -> {
            primaryStage.close();
        });

        Scene scene = new Scene(vBox, 250, 60);
        primaryStage.setTitle("提示");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void start1(Stage primaryStage, Text text){
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.TOP_CENTER);
        VBox vBox = new VBox();
        Button button1 = new Button("确定");
        Button button2 = new Button("取消");

        hBox1.getChildren().addAll(text);
        hBox2.getChildren().addAll(button1, button2);
        vBox.getChildren().addAll(hBox1, hBox2);

        button1.setOnAction(event -> {
            saveGame();
            System.exit(0);
        });
        button2.setOnAction(event -> {
            primaryStage.close();
        });

        Scene scene = new Scene(vBox, 250, 60);
        primaryStage.setTitle("提示");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public Icon[][] getMap() {
        return map;
    }

    public void setMap(Icon[][] map) {
        this.map = map;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Monster[] getMonster() {
        return monster;
    }

    public void setMonster(Monster[] monster) {
        this.monster = monster;
    }

}



//    public static void main(String[] args) throws IOException {
//        // output map
//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./map/4.map")));
//        Icon[][] map = Map.map4;
//        bw.write(map.length + " " + map[0].length + "\n");
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[i].length; j++) {
//                int num = 0;
//                switch (map[i][j]) {
//                    case WALL:
//                        num = 1;
//                        break;
//                    case EMPTY:
//                        num = 0;
//                        break;
//                    case TREASURE:
//                        num = 2;
//                        break;
//                    case MONSTER:
//                        num = 3;
//                        break;
//                    case END:
//                        num = 4;
//                        break;
//                    case HERO:
//                        num = 5;
//                        break;
//                }
//                bw.write(num + " ");
//            }
//            bw.write("\n");
//        }
//        bw.close();
//    }