import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x= new int[GAME_UNITS];
    final int[] y= new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;//Score
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            for(int i=0;i<bodyParts;i++){
                if(i==0){ //head of snake
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else{
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollision(){
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i]) && (y[0]==y[i])){   //checks head-body collision
                running=false;
                break;
            }
        }
        if(x[0]<0){running = false;} //checks left wall collision
        if(x[0]>SCREEN_WIDTH){running = false;}  //checks right wall collision
        if(y[0]<0){running = false;}  //checks top wall collision
        if(y[0]>SCREEN_HEIGHT){running = false;}  //checks down wall collision

        if(!running){timer.stop();}
    }
    public void gameOver(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT -> direction = (direction!='R')?'L':'R';
                case KeyEvent.VK_RIGHT -> direction = (direction!='L')?'R':'L';
                case KeyEvent.VK_UP -> direction = (direction!='D')?'U':'D';
                case KeyEvent.VK_DOWN -> direction = (direction!='U')?'D':'U';
            }
        }
    }
}
