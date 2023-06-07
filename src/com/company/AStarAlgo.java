package com.company;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarAlgo {
    private PriorityQueue<Node> open=new PriorityQueue<>();
    private  ArrayList<Node> closed=new ArrayList<>();
    public   ArrayList<ArrayList<Node>> path=new ArrayList<>();
    private  Node[][] maze;
    private  Node now;
    private  Node start;
    private  ArrayList<Node> goals;


    public AStarAlgo(Node[][] maze, Node start, ArrayList<Node> goals) {
        this.maze = maze;
        this.start = start;
        this.goals = goals;
    }

    public  void findPath (){
        while (goals.size()>0){
            open.add(start);
            while (!open.isEmpty()){
                now=open.poll();
                closed.add(now);
                now.type=1;
                addNeighborsToOpenList();
                Node oneOfGoals=openContainsGoal();
                if (oneOfGoals!=null){
                    savePath(oneOfGoals);
                    open.clear();
                    closed.clear();
                    start=oneOfGoals;
                    goals.remove((Object) oneOfGoals);
                    clearParents();
                }
            }
        }
    }

    private void clearParents() {
        for (int i = 0; i <maze.length ; i++) {
            for (int j = 0; j <maze.length ; j++) {
                maze[i][j].parent=null;
            }
        }
    }

    private void savePath(Node oneOfGoals) {
        Node node=oneOfGoals;
        ArrayList<Node> nodes=new ArrayList<>(); // this is the path that we are going to save
        while (node!=null){
            nodes.add(node);
            node=node.parent;
        }
        path.add(nodes);
    }

    private void addNeighborsToOpenList() {
        ArrayList<Node> neighbors=getNeighbors();
        for (int i = 0; i <neighbors.size() ; i++) {
            Node neighbor= neighbors.get(i);
            if (closed.contains(neighbor)){
                continue;
            }else if (!open.contains(neighbor)){
                neighbor.parent=now;
                neighbor.g=now.g + neighbor.type;
                neighbor.h= getHeuristic(neighbor);
                open.add(neighbor);
            }else {
                int fNew= (now.g + neighbor.type + getHeuristic(neighbor));
                if (fNew < neighbor.g + neighbor.h){
                    neighbor.parent=now;
                    neighbor.g= now.g + neighbor.type;
                    neighbor.h= getHeuristic(neighbor);
                }
            }
        }

    }

    private int getHeuristic(Node neighbor) {
        int min=Integer.MAX_VALUE;
        for (int i = 0; i <goals.size() ; i++) {
            Node goal=goals.get(i);
            int dist= getManhattan(neighbor,goal);
            if (min>dist){
                min=dist;
            }
        }
        return  min;
    }

    private int getManhattan(Node neighbor, Node goal) {
        return Math.abs(neighbor.x-goal.x)+Math.abs(neighbor.y-goal.y);
    }

    private ArrayList<Node> getNeighbors() {
        ArrayList<Node> nodes=new ArrayList<>();
        if(0 <= now.x-1 && now.x-1  < maze.length){
            nodes.add(maze[now.x-1][now.y]);
        }
        if(0 <= now.y-1 && now.y-1  < maze.length){
            nodes.add(maze[now.x][now.y-1]);
        }
        if(0 <= now.x+1 && now.x+1  < maze.length){
            nodes.add(maze[now.x+1][now.y]);
        }
        if(0 <= now.y+1 && now.y+1  < maze.length){
            nodes.add(maze[now.x][now.y+1]);
        }
        return nodes;
    }

    private Node openContainsGoal(){
        for (int i = 0; i <goals.size() ; i++) {
            if (open.contains(goals.get(i))){
                return goals.get(i);
            }
        }
        return null;
    }

}
