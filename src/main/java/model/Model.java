package model;

import controller.EventListener;

import java.nio.file.Paths;
import java.util.Set;


public class Model {
    public static final int FIELD_SELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("src/main/java/res/levels.txt"), gameObjects, this);

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        restartLevel(++currentLevel);
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();

        if (checkMonstersCollision(player, direction)) restart();

        if (checkWallCollision(player, direction)) return;
        if (checkBoxCollision(direction, player)) return;
        int newX = 0, newY = 0;
        switch (direction) {
            case LEFT:
                newX -= Model.FIELD_SELL_SIZE;
                break;
            case RIGHT:
                newX += Model.FIELD_SELL_SIZE;
                break;
            case UP:
                newY -= Model.FIELD_SELL_SIZE;
                break;
            case DOWN:
                newY += Model.FIELD_SELL_SIZE;
                break;
        }
        player.move(newX, newY);
        checkCompletion();
    }

    public void moveMonster(Monstr monstr) {

        int d = determineDirection(monstr);
        Direction direction = Direction.UP;
        switch (d) {
            case 1:
                direction = Direction.RIGHT;
                break;
            case 2:
                direction = Direction.DOWN;
                break;
            case 3:
                direction = Direction.UP;
                break;
            case 4:
                direction = Direction.LEFT;
                break;
        }

        if (checkMonstersCollision(gameObjects.getPlayer(), direction)) restart();

        if (checkWallCollision(monstr, direction)) return;
        if (checkBoxCollision(direction, monstr)) return;
        int newX = 0, newY = 0;
        switch (direction) {
            case LEFT:
                newX -= Model.FIELD_SELL_SIZE;
                break;
            case RIGHT:
                newX += Model.FIELD_SELL_SIZE;
                break;
            case UP:
                newY -= Model.FIELD_SELL_SIZE;
                break;
            case DOWN:
                newY += Model.FIELD_SELL_SIZE;
                break;
        }
        monstr.move(newX, newY);
        checkCompletion();
    }

    private int determineDirection(Monstr monstr) {

        Player player = gameObjects.getPlayer();

        int px = player.getX();
        int py = player.getY();
        int mx = monstr.getX();
        int my = monstr.getY();

        if (px > mx) {
            if (py > my) {
                if ((px - mx) < (py - my))
                    return 1;
                else
                    return 2;
            } else {
                if ((px - mx) > (py - my))
                    return 1;
                else
                    return 3;
            }
        } else {
            if (py > my) {
                if ((px - mx) < (py - my))
                    return 4;
                else
                    return 2;
            } else {
                if ((px - mx) > (py - my))
                    return 1;
                else
                    return 4;
            }
        }
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        Set<Wall> walls = gameObjects.getWalls();
        for (Wall wall : walls) {
            if (gameObject.isCollision(wall, direction)) return true;
        }
        return false;
    }

    public boolean checkMonstersCollision(CollisionObject gameObject, Direction direction) {
        Set<Monstr> monsters = gameObjects.getMonsters();
        for (Monstr monstr : monsters) {
            if (gameObject.isCollision(monstr, direction)) return true;
        }
        return false;
    }

    public boolean checkExplosionCollision(CollisionObject gameObject) {

        Set<Wall> walls = gameObjects.getWalls();
        Set<Box> boxes = gameObjects.getBoxes();
        Set<Monstr> monster = gameObjects.getMonsters();

        for (Wall wall : walls) {
            if ((gameObject.getX() == wall.getX()) && (gameObject.getY() == wall.getY())) {
                return true;
            }
        }

        for (Box box : boxes) {
            if ((gameObject.getX() == box.getX()) && (gameObject.getY() == box.getY())) {
                gameObjects.delBox(box);
                return false;
            }
        }
        for (Monstr monstr : monster) {
            if ((gameObject.getX() == monstr.getX()) && (gameObject.getY() == monstr.getY())) {
                gameObjects.delMonstr(monstr);
                return false;
            }
        }

        return false;
    }

    public boolean checkBoxCollision(Direction direction, GameObject gameObject) {
        CollisionObject moveObject;
        if (gameObject instanceof Player)
            moveObject = (Player) gameObject;
        else
            moveObject = (Monstr) gameObject;

        int newX = 0, newY = 0;
        // если стена
        if (checkWallCollision(moveObject, direction)) return true;
        // если ящик
        Set<Box> boxes = gameObjects.getBoxes(); // первый ящик
        for (Box box : boxes) {
            if (moveObject.isCollision(box, direction)) {
                // если за ним стена
                if (checkWallCollision(box, direction)) return true;
                // если за ним ящик
                Set<Box> boxesBack = gameObjects.getBoxes(); // ящики за ящиком
                for (Box boxBack : boxesBack) {
                    if (box.isCollision(boxBack, direction))
                        return true;
                }
                // если  столкновение произошло но сзади ничего нет двигаем
                switch (direction) {
                    case LEFT:
                        newX -= Model.FIELD_SELL_SIZE;
                        break;
                    case RIGHT:
                        newX += Model.FIELD_SELL_SIZE;
                        break;
                    case UP:
                        newY -= Model.FIELD_SELL_SIZE;
                        break;
                    case DOWN:
                        newY += Model.FIELD_SELL_SIZE;
                        break;
                }
                box.move(newX, newY);
                return false;
            }
        }
        return false;
    }

    public void checkCompletion() {
        Set<Home> homes = gameObjects.getHomes();
        Player player = gameObjects.getPlayer();
        for (Home home : homes) {
            if (home.getX() == player.getX() && home.getY() == player.getY())
                eventListener.levelCompleted(currentLevel);
        }
    }

    public void explosion(Bomb bomb) {


        ExplosionObject explL = new ExplosionObject(bomb.getX() - 20, bomb.getY(), gameObjects);
        ExplosionObject explR = new ExplosionObject(bomb.getX() + 20, bomb.getY(), gameObjects);
        ExplosionObject explT = new ExplosionObject(bomb.getX(), bomb.getY() + 20, gameObjects);
        ExplosionObject explD = new ExplosionObject(bomb.getX(), bomb.getY() - 20, gameObjects);
        ExplosionObject explC = new ExplosionObject(bomb.getX(), bomb.getY() - 20, gameObjects);

        Player player = gameObjects.getPlayer();
        if (((explL.getX() == player.getX()) && (explL.getY() == player.getY()))
                || ((explR.getX() == player.getX()) && (explR.getY() == player.getY()))
                || ((explT.getX() == player.getX()) && (explT.getY() == player.getY()))
                || ((explD.getX() == player.getX()) && (explD.getY() == player.getY()))
                || ((explC.getX() == player.getX()) && (explC.getY() == player.getY()))) {
            restart();
            return;
        }

        if (!checkExplosionCollision(explL))
            gameObjects.addExplosionObject(explL);
        if (!checkExplosionCollision(explR))
            gameObjects.addExplosionObject(explR);
        if (!checkExplosionCollision(explT))
            gameObjects.addExplosionObject(explT);
        if (!checkExplosionCollision(explD))
            gameObjects.addExplosionObject(explD);
        if (!checkExplosionCollision(explC))
            gameObjects.addExplosionObject(explC);
    }
}
