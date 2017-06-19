package view;

import controller.EventListener;
import model.Direction;
import model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Field extends JPanel implements Runnable
{
    private EventListener eventListener;
    private View view;

    public Field(View view)
    {
        this.view = view;

        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);

        setFocusable(true);
        new Thread(this).start();
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        for (GameObject gameObject : view.getGameObjects().getAll()){
            gameObject.draw(g);
        }
    }

    public void setEventListener(EventListener eventListener)
    {
        this.eventListener = eventListener;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    public class KeyHandler extends KeyAdapter
    {

        @Override
        public void keyPressed(KeyEvent e)
        {
            super.keyPressed(e);
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT: eventListener.move(Direction.LEFT); break;
                case KeyEvent.VK_RIGHT: eventListener.move(Direction.RIGHT); break;
                case KeyEvent.VK_UP: eventListener.move(Direction.UP); break;
                case KeyEvent.VK_DOWN: eventListener.move(Direction.DOWN); break;
                case KeyEvent.VK_R: eventListener.restart(); break;
                case KeyEvent.VK_SPACE: eventListener.PutBomb(); break;
            }
        }
    }
}
