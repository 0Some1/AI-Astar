package com.company;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JLabel;

public class Gui_main {

    JFrame frame;
    JButton buttons[][];
    int rows=100;
    int cols=100;
    Node[][] maze;
    ArrayList<ArrayList<Node>> paths;
    int startX;
    int startY;
    JLabel cost;
    int costs=0;

    /**
     * Launch the application.
     */
    public Gui_main(int rows, int cols, Node[][] maze, ArrayList<ArrayList<Node>> paths, int x, int y) {
        this.rows = rows;
        this.cols = cols;
        this.maze = maze;
        this.paths = paths;
        this.startX = x;
        this.startY = y;
        initialize();
    }

    /**
     * Create the application.\
     */
    public static void run(int rows, int cols, Node[][] maze, ArrayList<ArrayList<Node>> paths, int x, int y) {
        try {
            Gui_main window = new Gui_main(rows, cols, maze, paths, x, y);
            window.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("FTFL");
        frame.setBounds(100, 100, 1002, 810);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 750, 750);
        frame.getContentPane().add(panel);
        panel.setLayout(new GridLayout(this.rows, this.cols, 0, 0));

        JButton btnNewButton = new JButton("Start");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //next step do :
				Gui_main.this.new ChangeIcon().start();
            }
        });
        btnNewButton.setBounds(816, 464, 85, 21);
        frame.getContentPane().add(btnNewButton);
        
        JLabel lblNewLabel = new JLabel("Cost:   ");
        lblNewLabel.setBounds(774, 171, 45, 13);
        frame.getContentPane().add(lblNewLabel);
        
         cost = new JLabel("");
        cost.setBounds(836, 171, 45, 13);
        frame.getContentPane().add(cost);

        this.buttons = new JButton[this.rows][this.cols];

        int i;
        int j;
        for (i = 0; i < this.buttons.length; ++i) {
            for (j = 0; j < this.buttons[i].length; ++j) {
                this.buttons[i][j] = new JButton();
                this.buttons[i][j].setActionCommand(i + " " + j);
                this.buttons[i][j].setSize(20, 20);
                if (maze[i][j].type == 1) {
                    if (maze[i][j].goal) {
                        this.buttons[i][j].setBackground(Color.red);
                    } else if (i == startX && j == startY) {
                        this.buttons[i][j].setBackground(Color.green);
                    } else {
                        this.buttons[i][j].setBackground(Color.lightGray);
                    }
                } else if (maze[i][j].type == 2) {
                    this.buttons[i][j].setBackground(new Color(115, 78, 16, 255));

                } else if (maze[i][j].type == 5) {
                    this.buttons[i][j].setBackground(new Color(96, 107, 168, 255));
                }
                panel.add(this.buttons[i][j]);
            }

        }
    }

    class ChangeIcon extends Thread {
        public void run() {
            for (int i = 0; i < paths.size(); i++) {
                for (int j = 0; j < paths.get(i).size(); j++) {
                    Node node = paths.get(i).get(j);
                    buttons[node.x][node.y].setBackground(Color.pink);
					try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					buttons[node.x][node.y].setBackground(Color.green);
					try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if(maze[node.x][node.y].type==1){
                        costs++;
                    }else if (maze[node.x][node.y].type==2){
					    costs+=2;
                    }else if (maze[node.x][node.y].type==5){
                        costs+=5;
                    }
					cost.setText( costs +" ");

				}
            }
        }
    }
}
