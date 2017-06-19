package model;

import java.awt.*;


public class Wall extends CollisionObject
{
    public Wall(int x, int y)
    {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.GRAY);
        int x1 = getX() - getWidth() / 2;
        int y1 = getY() - getHeight() / 2;
        graphics.drawRect(x1, y1, getWidth(), getHeight());
    }

    @Override
    public boolean isCollision(GameObject gameObject, Direction direction)
    {
        return super.isCollision(gameObject, direction);
    }
}
