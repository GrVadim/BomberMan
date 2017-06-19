package controller;

import model.Direction;


public interface EventListener
{
    public void move(Direction direction);

    public void restart();

    public void startNextLevel();

    public void levelCompleted(int level);

    public void PutBomb();
}
