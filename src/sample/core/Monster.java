package sample.core;

import sample.server.Server;

import static sample.core.Icon.*;

public class Monster {

    //定义怪物的坐标
    private int siteMX1;
    private int siteMY1;
    public Monster(Icon[][] map){
        boolean canPut = true;
        while (canPut){
            int i = (int)(Math.random() * map.length);

            for (int j = (int)(Math.random()*map[0].length); j < map[0].length; j++){
                if (map[i][j] == EMPTY && i != 1){
                    map[i][j] = MONSTER;
                    siteMX1 = i;
                    siteMY1 = j;
                    canPut = false;
                    break;
                }
            }
        }
    }


    //定义方法改变怪物坐标
    public void move(Icon[][] map) {
        int siteMX2 = siteMX1;
        int siteMY2 = siteMY1;
        map[siteMX1][siteMY1] = EMPTY;
        while(siteMX2 == siteMX1 && siteMY2 == siteMY1){
            int direction = (int)(Math.random() * 4);
            switch (direction){
                case 0: if (!map[siteMX2 - 1][siteMY2].equals(TREASURE) && !map[siteMX2 - 1][siteMY2].equals(WALL) && !map[siteMX2 - 1][siteMY2].equals(END))
                    siteMX1--;
                    break;
                case 2: if (!map[siteMX2 + 1][siteMY2].equals(TREASURE) && !map[siteMX2 + 1][siteMY2].equals(WALL) && !map[siteMX2 + 1][siteMY2].equals(END))
                    siteMX1++;
                    break;
                case 1: if (!map[siteMX2][siteMY2 - 1].equals(TREASURE) && !map[siteMX2][siteMY2 - 1].equals(WALL) && !map[siteMX2][siteMY2 - 1].equals(END))
                    siteMY1--;
                    break;
                case 3: if (!map[siteMX2][siteMY2 + 1].equals(TREASURE) && !map[siteMX2][siteMY2 + 1].equals(WALL) && !map[siteMX2][siteMY2 + 1].equals(END))
                    siteMY1++;
                    break;
            }
        }
        //打印怪物
        map[siteMX1][siteMY1] = MONSTER;
    }


    //得到怪物的横坐标
    public int getSiteMX1() {
           return siteMX1;
   }

    //得到怪物的纵坐标
    int getSiteMY1() {
           return siteMY1;
      }

    void setSiteMX1(int siteMX1) {
        this.siteMX1 = siteMX1;
    }

    void setSiteMY1(int siteMY1) {
        this.siteMY1 = siteMY1;
    }
}

