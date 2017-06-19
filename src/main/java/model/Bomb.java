package model;

import view.Field;

import java.awt.*;


public class Bomb extends GameObject implements Runnable
{
    boolean isLive;
    private Model model;
    GameObjects gameObjects;
    public Bomb(int x, int y, Model model, GameObjects gameObjects)
    {
        super(x, y);
        isLive = true;
        this.model = model;
        new Thread(this).start();
        this.gameObjects = gameObjects;
    }

    public boolean isLive() {
        return isLive;
    }

    @Override
    public void draw(Graphics graphics)
    {
        if (isLive){
            graphics.setColor(Color.RED);
            int x1 = getX() - getWidth() / 2;
            int y1 = getY() - getHeight() / 2;
            graphics.draw3DRect(x1, y1, getWidth(), getHeight(), true);
        }
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            model.explosion(this);
            isLive = false;
            gameObjects.delBomb(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
