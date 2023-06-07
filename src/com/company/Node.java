package com.company;

public class Node implements Comparable {
    public Node parent;
    public int type=1;
    public int x, y;
    public int g=0;
    public int h=0;
    boolean goal=false;

    Node(Node parent, int x, int y,int type) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.type = type;
    }
    // Compare by f value (g + h)
    @Override
    public int compareTo(Object o) {
        Node that = (Node) o;
        return (int)((this.g + this.h) - (that.g + that.h));
    }

    @Override
    public String toString() {
        return " "+this.x+" "+this.y;
    }
}
