package sample.core;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public interface Music {
    String path1 = new File("music/heromusic/fail.mp3").toURI().toString();
    Media failMusic = new Media(path1);
    MediaPlayer mediaPlayer1 = new MediaPlayer(failMusic);

    String path2 = new File("music/heromusic/mouseOn.mp3").toURI().toString();
    Media move = new Media(path2);
    MediaPlayer mediaPlayer2 = new MediaPlayer(move);

    String path3 = new File("music/heromusic/victory.mp3").toURI().toString();
    Media victory = new Media(path3 );
    MediaPlayer mediaPlayer3 = new MediaPlayer(victory);

    String path4 = new File("music/heromusic/get.mp3").toURI().toString();
    Media getTreasure = new Media(path4);
    MediaPlayer mediaPlayer4 = new MediaPlayer(getTreasure);

    String path5 = new File("music/heromusic/click1.mp3").toURI().toString();
    Media kill = new Media(path5);
    MediaPlayer mediaPlayer5 = new MediaPlayer(kill);

    String path6 = new File("music/heromusic/click1.mp3").toURI().toString();
    Media click1 = new Media(path6);
    MediaPlayer mediaPlayer6 = new MediaPlayer(click1);

    String path7 = new File("music/BGM.mp3").toURI().toString();
    Media BGM = new Media(path7);
    MediaPlayer mediaPlayer7 = new MediaPlayer(BGM);

}
