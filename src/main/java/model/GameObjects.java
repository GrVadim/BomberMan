package model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


public class GameObjects
{
    private Set<Wall> walls;
    private Set<Box> boxes;
    private Set<Home> homes;
    private Set<Monstr> monsters;
    private Set<Bomb> bombs;
    private Set<ExplosionObject> explosionObjects = new LinkedHashSet<>();
    private Player player;

    public Set<Wall> getWalls()
    {
        return walls;
    }

    public Set<Monstr> getMonsters()
    {
        return monsters;
    }

    public Set<Box> getBoxes()
    {
        return boxes;
    }

    public Set<Bomb> getBombs()
    {
        return bombs;
    }

    public Set<Home> getHomes()
    {
        return homes;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void addBomb(Bomb bomb)
    {
        bombs.add(bomb);
    }

    public void addExplosionObject(ExplosionObject explosionObject)
    {
        explosionObjects.add(explosionObject);
    }

    public GameObjects(Set<Wall> walls, Set<Box> boxes, Set<Home> homes, Set<Monstr> monsters,Player player)
    {
        this.walls = walls;
        this.boxes = boxes;
        this.homes = homes;
        this.monsters = monsters;
        this.player = player;
        bombs = new HashSet<>();
    }

    public Set<GameObject> getAll(){
        Set<GameObject> gameObjects= new LinkedHashSet<>();
        gameObjects.addAll(walls);
        gameObjects.addAll(boxes);
        gameObjects.addAll(homes);
        gameObjects.addAll(monsters);
        gameObjects.addAll(bombs);
        gameObjects.addAll(explosionObjects);
        gameObjects.add(player);


        return gameObjects;
    }

    public void delMonstr (Monstr monstr){
        monsters.remove(monstr);
        monstr.setLive(false);
    }
    public void delExplosionObject (ExplosionObject explosionObject){
        explosionObjects.remove(explosionObject);
    }
    public void delBox (Box box){
        boxes.remove(box);
    }

    public void delBomb (Bomb bomb){
        bombs.remove(bomb);
    }
}
