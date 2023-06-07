package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int sizeOfMaze;
        int woods;
        int rocks;
        int startX = 0;
        int startY = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter the n of Maze: ");
        sizeOfMaze = scanner.nextInt();

        System.out.println("enter the startX: ");
        startX = scanner.nextInt();
        System.out.println("enter the startY: ");
        startY = scanner.nextInt();


        System.out.println("enter the n of Woods: ");
        woods = scanner.nextInt();
        System.out.println("enter the n of Rocks: ");
        rocks = scanner.nextInt();

        System.out.println("enter the n of Goals: ");
        int Goals = scanner.nextInt();



        Node[][] maze = new Node[sizeOfMaze][sizeOfMaze];
        Node[][] mazeCopy=new Node[sizeOfMaze][sizeOfMaze];
        for (int i = 0; i < sizeOfMaze; i++) {
            for (int j = 0; j < sizeOfMaze; j++) {
                maze[i][j] = new Node(null, i, j, 1);
            }
        }

        while (woods != 0) {
            int x = rand(sizeOfMaze);
            int y = rand(sizeOfMaze);
            if (!(startX == x && startY == y) && maze[x][y].type==1) {
                maze[x][y].type = 2;
                woods--;
            }
        }

        while (rocks != 0) {
            int x = rand(sizeOfMaze);
            int y = rand(sizeOfMaze);
            if (!(startX == x && startY == y)  && maze[x][y].type==1) {
                maze[x][y].type = 5;
                rocks--;
            }
        }

        ArrayList<Node> nodes = new ArrayList<>();
        while (Goals != 0) {
            int x = rand(sizeOfMaze);
            int y = rand(sizeOfMaze);
            if (!(startX == x && startY == y)  && maze[x][y].type == 1 && !maze[x][y].goal) {
                nodes.add(maze[x][y]);
                maze[x][y].goal=true;
                Goals--;
            }
        }

        for (int i = 0; i <maze.length ; i++) {
            for (int j = 0; j <maze.length ; j++) {
                switch (maze[i][j].type){
                    case 1 :
                        if (maze[i][j].goal){
                            System.out.print(" G ");
                        }else System.out.print(" - ");
                        break;
                    case 2:
                        System.out.print(" W ");
                        break;
                    case 5:
                        System.out.print(" R ");
                        break;
                }


            }
            System.out.println();
        }

        for (int i = 0; i <maze.length ; i++) {
            for (int j = 0; j <maze[i].length ; j++) {
                mazeCopy[i][j]=new Node(null,i,j,maze[i][j].type);
                if (maze[i][j].goal){
                    mazeCopy[i][j].goal=true;
                }
            }
        }

        AStarAlgo aStarAlgo = new AStarAlgo(maze,maze[startX][startY],nodes);
        aStarAlgo.findPath();
        for (int i = 0; i <aStarAlgo.path.size() ; i++) {
            Collections.reverse(aStarAlgo.path.get(i));
            for (int j = 0; j <aStarAlgo.path.get(i).size() ; j++) {
                System.out.println(aStarAlgo.path.get(i).get(j));
            }
            System.out.println();
        }

        Gui_main.run(sizeOfMaze,sizeOfMaze,mazeCopy,aStarAlgo.path,startX,startY);
    }

    private static int rand(int length) {
        return new Random().nextInt(length);
    }




}
