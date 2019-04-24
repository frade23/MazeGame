package sample.core;

import javafx.util.Duration;
import sample.client.Main;
import sample.server.Server;

import java.io.Serializable;
import java.util.ArrayList;

import static sample.core.Icon.*;

public class Person implements Serializable, Music{
    public int[] pos = {1, 1}; // y,x
    public ArrayList<Integer> columnY = new ArrayList<>();
    public ArrayList<Integer> rowX = new ArrayList<>();
    private int theMarkOfHero = 10000;
    private int theStepNumberOfHero = 0;
    private int theNumberOfKillMonster = 0;
    private int theNumberOfPickUpTreasure = 0;
    private int theBackStepNumberOfHero = 0;
    private int temp;

    //向上移动人物
    public void moveUp(Icon[][] map){
        if(map[pos[0] - 1][pos[1]] != WALL){
            if (map[pos[0]][pos[1]] == HERO){
                map[pos[0]][pos[1]] = EMPTY;
            }
            mediaPlayer2.seek(Duration.ZERO);
            Music.mediaPlayer2.play();
            rowX.add(pos[0]);
            columnY.add(pos[1]);
            pos[0] -= 1;
            theMarkOfHero -= 10;
            theStepNumberOfHero++;

        }
    }
    //向下移动人物
    public void moveDown(Icon[][] map){

        if(map[pos[0] + 1][pos[1]] != WALL){
            if (map[pos[0]][pos[1]] == HERO){
                map[pos[0]][pos[1]] = EMPTY;
            }
            mediaPlayer2.seek(Duration.ZERO);
            Music.mediaPlayer2.play();
            rowX.add(pos[0]);
            columnY.add(pos[1]);
            pos[0] += 1;
            theMarkOfHero -= 10;
            theStepNumberOfHero++;
        }
    }
    //向左移动人物
    public void moveLeft(Icon[][] map){
        if(map[pos[0]][pos[1] - 1] != WALL){
            if (map[pos[0]][pos[1]] == HERO){
                map[pos[0]][pos[1]] = EMPTY;
            }
            mediaPlayer2.seek(Duration.ZERO);
            Music.mediaPlayer2.play();
            rowX.add(pos[0]);
            columnY.add(pos[1]);
            pos[1] -= 1;
            theMarkOfHero -= 10;
            theStepNumberOfHero++;
        }
    }
    //向右移动人物
    public void moveRight(Icon[][] map){

        if(map[pos[0]][pos[1] +1] != WALL ){
            if (map[pos[0]][pos[1]] == HERO){
                map[pos[0]][pos[1]] = EMPTY;
            }
            mediaPlayer2.seek(Duration.ZERO);
            Music.mediaPlayer2.play();
            rowX.add(pos[0]);
            columnY.add(pos[1]);
            pos[1] += 1;
            theMarkOfHero -= 10;
            theStepNumberOfHero++;
        }
    }
    //回退
    public void moveBack(Icon[][] map, Server server){
        if(rowX.size() > 0) {
            if(temp != 0 &&  rowX.size() == temp){
                Main.a--;
                server.createGame(Main.a);
                for(int i = 0; i < 2; i++) {
                pos[0] = rowX.get(rowX.size() - 1);
                pos[1] = columnY.get(columnY.size() - 1);
                if (map[pos[0]][pos[1]] != MONSTER && map[pos[0]][pos[1]] != TREASURE) {
                    map[pos[0]][pos[1]] = EMPTY;
                }
                rowX.remove(rowX.size() - 1);
                columnY.remove(columnY.size() - 1);
                theBackStepNumberOfHero++;
                mediaPlayer2.seek(Duration.ZERO);
                Music.mediaPlayer2.play();
                //                   mark += 10;
                }
            }else {
                pos[0] = rowX.get(rowX.size() - 1);
                pos[1] = columnY.get(columnY.size() - 1);
                if (map[pos[0]][pos[1]] != MONSTER && map[pos[0]][pos[1]] != TREASURE) {
                    map[pos[0]][pos[1]] = EMPTY;
                }
                rowX.remove(rowX.size() - 1);
                columnY.remove(columnY.size() - 1);
                theBackStepNumberOfHero++;
                mediaPlayer2.seek(Duration.ZERO);
                Music.mediaPlayer2.play();
            }
        }

    }
    //捡宝物
    public void pickUpTreasure(Icon[][] map){
        if (map[pos[0]][pos[1] + 1] == TREASURE){
            map[pos[0]][pos[1] + 1] = EMPTY;
            mediaPlayer4.seek(Duration.ZERO);
            Music.mediaPlayer4.play();
            theMarkOfHero += 80;
            theNumberOfPickUpTreasure++;
        }
        if (map[pos[0]][pos[1] - 1] == TREASURE){
            map[pos[0]][pos[1] - 1] = EMPTY;
            mediaPlayer4.seek(Duration.ZERO);
            Music.mediaPlayer4.play();
            theMarkOfHero += 80;
            theNumberOfPickUpTreasure++;
        }

        if (map[pos[0] + 1][pos[1]] == TREASURE){
            map[pos[0] + 1][pos[1]] = EMPTY;
            mediaPlayer4.seek(Duration.ZERO);
            Music.mediaPlayer4.play();
            theMarkOfHero += 80;
            theNumberOfPickUpTreasure++;
        }

        if (map[pos[0] - 1][pos[1]] == TREASURE){
            map[pos[0] - 1][pos[1]] = EMPTY;
            mediaPlayer4.seek(Duration.ZERO);
            Music.mediaPlayer4.play();
            theMarkOfHero += 80;
            theNumberOfPickUpTreasure++;
        }
    }
    //杀怪
    public void killMonster(Icon[][] map, Monster[] monsters){
        for (int i = 0; i < monsters.length; i++){
            if (pos[0] == monsters[i].getSiteMX1() &&pos[1] + 1 == monsters[i].getSiteMY1()){
                map[pos[0]][pos[1] + 1] = EMPTY;
                mediaPlayer5.seek(Duration.ZERO);
                Music.mediaPlayer5.play();
                monsters[i].setSiteMX1(0);
                monsters[i].setSiteMY1(0);
                theMarkOfHero += 100;
                theNumberOfKillMonster++;
            }
            if (pos[0] == monsters[i].getSiteMX1() &&pos[1] - 1 == monsters[i].getSiteMY1()){
                map[pos[0]][pos[1] - 1] = EMPTY;
                mediaPlayer5.seek(Duration.ZERO);
                Music.mediaPlayer5.play();
                monsters[i].setSiteMX1(0);
                monsters[i].setSiteMY1(0);
                theMarkOfHero += 100;
                theNumberOfKillMonster++;
            }
            if (pos[0] + 1 == monsters[i].getSiteMX1() &&pos[1] == monsters[i].getSiteMY1()){
                map[pos[0] + 1][pos[1]] = EMPTY;
                mediaPlayer5.seek(Duration.ZERO);
                Music.mediaPlayer5.play();
                monsters[i].setSiteMX1(0);
                monsters[i].setSiteMY1(0);
                theMarkOfHero += 100;
                theNumberOfKillMonster++;
            }
            if (pos[0] - 1 == monsters[i].getSiteMX1() &&pos[1] == monsters[i].getSiteMY1()){
                map[pos[0] - 1][pos[1]] = EMPTY;
                mediaPlayer5.seek(Duration.ZERO);
                Music.mediaPlayer5.play();
                monsters[i].setSiteMX1(0);
                monsters[i].setSiteMY1(0);
                theMarkOfHero += 100;
                theNumberOfKillMonster++;
            }
        }
    }

    //死亡触发
    public boolean killHero(Monster[] monsters, Icon[][] map){
        for (int i = 0; i < monsters.length; i++){
            if (monsters[i] != null && pos[0] == monsters[i].getSiteMX1() &&pos[1] == monsters[i].getSiteMY1()){
                map[pos[0]][pos[1]] = MONSTER;
                mediaPlayer1.seek(Duration.ZERO);
                Music.mediaPlayer1.play();
                monsters[i].setSiteMX1(pos[0]);
                monsters[i].setSiteMY1(pos[1]);
                return true;
            }
        }
        return false;
    }
    //判断是否到达终点
    public boolean isEnd(Icon[][] map){
        if (pos[0] == map.length - 2D && pos[1] == map[0].length - 2){
            mediaPlayer3.seek(Duration.ZERO);
            Music.mediaPlayer3.play();
            temp = rowX.size() + 1;
            return true;
        }
        else return false;
    }

    //足迹
    public void printFootPrint(int n, Icon[][] map){
        if (columnY.size() > n &&  map[rowX.get(rowX.size() - n -1)][columnY.get(columnY.size() - n -1)] == FOOTPRINT){
            map[rowX.get(rowX.size() - n -1)][columnY.get(columnY.size() - n -1)] = EMPTY;
        }
        for (int i = 0; i < n; i++){
            if (columnY.size() > i && map[rowX.get(rowX.size() - i - 1)][columnY.get(columnY.size() - i -1)] == EMPTY)
                map[rowX.get(rowX.size() - i - 1)][columnY.get(columnY.size() -i - 1)] = FOOTPRINT;
        }

//        if (columnY.size() > 1 && map[rowX.get(rowX.size() - 2)][columnY.get(columnY.size() - 2)] == EMPTY){
//            map[rowX.get(rowX.size() - 2)][columnY.get(columnY.size() - 2)] = FOOTPRINT;
//        }
    }
    public int getTheMarkOfHero(){
        return theMarkOfHero;
    }

    public int getTheStepNumberOfHero() {
        return theStepNumberOfHero;
    }

    public int getTheNumberOfKillMonster() {
        return theNumberOfKillMonster;
    }

    public int getTheNumberOfPickUpTreasure() {
        return theNumberOfPickUpTreasure;
    }

    public int getTheBackStepNumberOfHero() {
        return theBackStepNumberOfHero;
    }

    public int getX(){
        return pos[0];
    }
    public int getY(){
        return pos[1];
    }


}
