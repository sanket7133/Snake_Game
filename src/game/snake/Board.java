package game.snake;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class Board extends JPanel implements ActionListener {
    private Image apple,dot,head;
    public static int count=0;
    private int dots;
    private final int DOT_SIZE=10,ALL_DOTS=900;
    private int apple_x,apple_y,Random=29;

    private boolean leftDir=false;
    private boolean rightDir=true;
    private boolean inGame=true;
    private boolean upDir=false;
    private boolean downDir=false;

     private Timer timer;
    private final int x[]=new int[ALL_DOTS];
    private final int y[]=new int[ALL_DOTS];
    JButton button;
    JLabel l;


    Board(){

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));

        setFocusable(true);
        LoadImages();

        initGame();
    }
    public void LoadImages(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("game/snake/icons/apple.png"));
        apple=i1.getImage();

        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("game/snake/icons/dot.png"));
        dot=i2.getImage();

        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("game/snake/icons/head.png"));
        head= i3.getImage();
    }

    public void  initGame()
    {
        dots=3;

        for (int z=0; z<dots;z++){
            x[z]=50- z* DOT_SIZE;
            y[z]=50;
        }

        locateApple();

        timer=new Timer(140,this);
        timer.start();

    }

    public void  locateApple(){
        double r=  Math.random()* Random;
        apple_x =(int)r*DOT_SIZE;
        apple_y=(int)r*DOT_SIZE;
        //System.out.println(r);

    }

    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        if(button==ae.getSource()){

            new Snake().setVisible(true);

        }
       repaint();

    }
    public void paintComponent( Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(inGame){
            score(g);
            g.drawImage(apple,apple_x,apple_y,this);
            for (int z=0;z<dots;z++){
                if(z==0){
                    g.drawImage(head,x[z],y[z],this);
                }
                else
                    g.drawImage(dot,x[z],y[z],this);
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else
            gameover(g);
    }
    public void gameover(Graphics g){
        String msg="GAME OVER";
        Font font=new Font("SAN_SERIF", Font.BOLD,14);
        FontMetrics matrix= getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(300-matrix.stringWidth(msg))/2,150);
        button=new JButton("PLAY AGAIN");
        button.setFont(new Font("SAN_SERIF", Font.BOLD,10));
        button.setBounds(120,170,100,30);
        button.addActionListener(this);
        add(button);
    }


    public void score(Graphics g){
       int sc=(dots-3)*10;
       String scr="Score : ";
        Font font=new Font("SAn_SERIF", Font.ITALIC,14);
       g.setColor(Color.WHITE);
       g.setFont(font);
     g.drawString(scr+sc,10,10);
    }

     public void checkCollision(){

        for(int z=dots;z>0;z--){
            if((z>4) && (x[0]==x[z]) && (y[0]==y[z])){
                inGame=false;
            }
        }
         if (x[0]>=300) {
           inGame=false;
         }
         if (y[0]>=300) {
             inGame=false;
         }
         if (x[0]<=0) {
             inGame=false;
         }
         if (y[0]<=0) {
             inGame=false;
         }
         if (!inGame){
             timer.stop();
         }
     }
    public void checkApple(){
        if(x[0]==apple_x && y[0]==apple_y){
            dots++;
            locateApple();
        }
    }
    public void move(){
        for (int z=dots;z>0;z--){
            x[z]=x[z-1];
            y[z]=y[z-1];
        }
        if(leftDir){
            x[0]-=DOT_SIZE;
        }
        if(rightDir){
            x[0]+=DOT_SIZE;
        }

        if(upDir){
            y[0]-=DOT_SIZE;
        }
        if(downDir){
            y[0]+=DOT_SIZE;
        }
    }
    private class TAdapter extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            int key =e.getKeyCode();
            if(key== KeyEvent.VK_LEFT && (!rightDir)){
                leftDir=true;
                upDir=false;
                downDir=false;

            }
            if(key== KeyEvent.VK_RIGHT && (!leftDir)){
                rightDir=true;
                upDir=false;
                downDir=false;

            }if(key== KeyEvent.VK_DOWN && (!upDir)){
                leftDir=false;
                rightDir=false;
                downDir=true;

            }
            if(key== KeyEvent.VK_UP && (!downDir)){
                leftDir=false;
                upDir=true;
                rightDir=false;

            }
        }
    }
}
