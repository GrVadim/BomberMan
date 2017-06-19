package controller;

import model.*;
import view.View;

import java.util.HashSet;
import java.util.Set;


public class Controller implements EventListener
{
    private View view;
    private Model model;

    public Controller()
    {
        this.view = new View(this);
        this.model = new Model();
        view.init();

        model.restart();
        model.setEventListener(this);
        view.setEventListener(this);
    }

    public static void main(String[] args)
    {
        Controller controller = new Controller();
    }

    @Override
    public void move(Direction direction)
    {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart()
    {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel()
    {
        model.startNextLevel();
        view.update();
    }


    @Override
    public void levelCompleted(int level)
    {
        view.completed(level);
    }

    @Override
    public void PutBomb() {

        GameObjects gameObjects = getGameObjects();
        Player player = gameObjects.getPlayer();

        Set<Bomb> bombs = gameObjects.getBombs();

        Set<Bomb> bombsForDel=new HashSet<>();
        for (Bomb bomb:bombs) {
            if (!bomb.isLive())
                bombsForDel.add(bomb);
        }
        bombs.removeAll(bombsForDel);


        if (bombs.size()<player.getCountBomb())
            gameObjects.addBomb(new Bomb(player.getX(),player.getY(), model,gameObjects));
    }

    public GameObjects getGameObjects(){
        return model.getGameObjects();
    }
}
