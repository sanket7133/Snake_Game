package game.snake;

import javax.swing.*;

public class Snake extends JFrame{
   public int count=0;
    Snake(){
        Board b= new Board();
        add(b);
        pack();
        setLocationRelativeTo(null);
        setTitle("Snake Game");
        setResizable(false);
        //System.out.println(b.count);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
         int count=0;
        new Snake().setVisible(true);

    }
}
