package sample.client;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import sample.core.Icon;
import sample.server.Server;


public class MyMenuBar extends MenuBar {

    MyMenuBar(Server server, Main app) {
        super();
        // add file menu
        Menu menuFile = new Menu("File");
        //MenuItem是什么?
        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(event -> {
            server.loadGame();
            app.createAndShowGameView();
        });
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(event -> {
            server.saveGame();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("saved.");
            alert.showAndWait();
        });
        menuFile.getItems().addAll(loadItem, saveItem);

        // add settings menu
        Menu menuSetting = new Menu("Settings");
        Menu themeMenu = new Menu("Theme");
        ToggleGroup themeGroup = new ToggleGroup();
        RadioMenuItem snowTheme = new RadioMenuItem("snow");
        snowTheme.setToggleGroup(themeGroup);
        snowTheme.setOnAction(event -> {
            Main.path = "file:assets/img/snow/";
            app.initResources();
        });
        RadioMenuItem grassTheme = new RadioMenuItem("grass");
        grassTheme.setToggleGroup(themeGroup);
        themeMenu.getItems().addAll(snowTheme, grassTheme);
        menuSetting.getItems().add(themeMenu);
        grassTheme.setOnAction(event -> {
            Main.path = "file:assets/img/grass/";
            app.initResources();
        });
        // add help menu
        Menu menuHelp = new Menu("Help");
        menuHelp.setOnAction(event -> {
            helpStage();
        });
        MenuItem howToPlay = new MenuItem("How to Play");
        menuHelp.getItems().add(howToPlay);
        this.getMenus().addAll(menuFile, menuSetting, menuHelp);
    }

    public static void helpStage(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"游戏目标:  \n" +
                "    你的目标是操纵人物, 从起点走到终点  \n  \n" +
                "命令说明:\n" +
                "    h.help: 帮助信息\n" +
                "    w.north: 向北走\n" +
                "    a.west: 向西走\n" +
                "    s.south: 向南走\n" +
                "    d.east: 向东走\n" +
                "    k.kill: 杀死周围的怪物\n" +
                "    p.pick: 捡起周围的宝物\n" +
                "    c.check: 检查得分\n" +
                "    b.back: 返回上一步\n" +
                "    q.quit: 认输\n" +
                "    x.exit: 保存游戏退出");
        alert.showAndWait();
    }
}
