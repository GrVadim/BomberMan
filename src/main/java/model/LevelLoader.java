package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;


public class LevelLoader {
    private Path levels;
    Model model;
    public LevelLoader(Path levels,GameObjects gameObjects,Model model) {
        this.levels = levels;
        this.model = model;
    }
    public GameObjects getLevel(int level) {
        int levl = level % 60;
        if (levl == 0) {
            levl = 60;
        }
        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Set<Monstr> monsters = new HashSet<>();
        Player player = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(levels.toString()));
            int x0 = Model.FIELD_SELL_SIZE / 2;
            int y0 = Model.FIELD_SELL_SIZE / 2;
            while (!reader.readLine().contains("Maze: " + levl));
            reader.readLine();
            int x = Integer.parseInt(reader.readLine().split(" ")[2]);
            int y = Integer.parseInt(reader.readLine().split(" ")[2]);
            reader.readLine();
            reader.readLine();
            reader.readLine();
            for (int i = 0; i < y; i++) {
                String line = reader.readLine();
                for (int j = 0; j < x; j++)
                    switch (line.charAt(j)) {
                        case 'X':
                            walls.add(new Wall(x0 + j * Model.FIELD_SELL_SIZE, y0 + i * Model.FIELD_SELL_SIZE));
                            break;
                        case '@':
                            player = new Player(x0 + j * Model.FIELD_SELL_SIZE, y0 + i * Model.FIELD_SELL_SIZE);
                            break;
                        case '*':
                            boxes.add(new Box(x0 + j * Model.FIELD_SELL_SIZE, y0 + i * Model.FIELD_SELL_SIZE));
                            break;
                        case '&':
                            boxes.add(new Box(x0 + j * Model.FIELD_SELL_SIZE, y0 + i * Model.FIELD_SELL_SIZE));
                            break;
                        case '.':
                            homes.add(new Home(x0 + j * Model.FIELD_SELL_SIZE, y0 + i * Model.FIELD_SELL_SIZE));
                            break;
                        case 'm':
                            monsters.add(new Monstr(x0 + j * Model.FIELD_SELL_SIZE, y0 + i * Model.FIELD_SELL_SIZE,model));
                            break;
                    }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new GameObjects(walls, boxes, homes, monsters, player);
    }
}