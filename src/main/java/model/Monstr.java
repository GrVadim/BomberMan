package model;

import java.awt.*;


public class Monstr  extends CollisionObject implements Movable, Runnable
{   GameObjects gameObjects;
    Model model;
    boolean isLive;

    public void setLive(boolean live) {
        isLive = live;
    }

    public Monstr(int x, int y, Model model)
    {
        super(x, y);
        this.gameObjects = gameObjects;
        this.model = model;
        new Thread(this).start();
        isLive = true;
    }
    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.RED);
        int leftUpperCornerX = getX() - getWidth() / 2;
        int leftUpperCornerY = getY() - getHeight() / 2;
        int rightUpperCornerX = getX() + getWidth() / 2;
        int rightUpperCornerY = leftUpperCornerY;
        int leftLowerCornerX = leftUpperCornerX;
        int leftLowerCornerY = getY() + getHeight() / 2;
        int rightLowerCornerX = rightUpperCornerX;
        int rightLowerCornerY = leftLowerCornerY;
        graphics.drawOval(leftUpperCornerX, leftUpperCornerY, getWidth(), getHeight());

    }
    @Override
    public void move(int x, int y)
    {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    @Override
    public void run() {
        while (isLive){
            try {
                Thread.sleep(200);
                model.moveMonster(this);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}