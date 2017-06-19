package model;

import java.awt.*;

public class ExplosionObject extends CollisionObject implements Runnable
{
    GameObjects gameObjects;

    public ExplosionObject(int x, int y, GameObjects gameObjects) {
        super(x, y);
        new Thread(this).start();
        this.gameObjects = gameObjects;
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.red);
        int x1 = getX() - getWidth() / 2;
        int y1 = getY() - getHeight() / 2;
        graphics.draw3DRect(x1, y1, getWidth(), getHeight(),false);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            gameObjects.delExplosionObject(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
