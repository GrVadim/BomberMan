package model;

import java.awt.*;


public class Player extends CollisionObject implements Movable
{
    private int countBomb;
    public Player(int x, int y)
    {
        super(x, y);
        countBomb = 1;
    }

    public void setCountBomb(int countBomb) {
        this.countBomb = countBomb;
    }

    public int getCountBomb() {

        return countBomb;
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.YELLOW);
        int leftUpperCornerX = getX() - getWidth() / 2;
        int leftUpperCornerY = getY() - getHeight() / 2;
        graphics.drawOval(leftUpperCornerX, leftUpperCornerY, getWidth(), getHeight());
    }
    @Override
    public void move(int x, int y)
    {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }
}
