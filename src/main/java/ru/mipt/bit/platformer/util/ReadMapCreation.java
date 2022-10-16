package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ReadMapCreation implements ICreationMapStrategy{
    Player player;
    Collection<Obstacle> obstacles;

    public ReadMapCreation() {
        obstacles = new ArrayList<>();
    }

    @Override
    public void createMap(CreationMapParams params) {

        ArrayList<ArrayList<Character>> map = readMapFile(params.getMapFilePath());
        for (int i = 0;i < map.size();i++){
            for (int j = 0;j < map.get(i).size();j++){
                int x = j;
                int y = map.size() - i + 1;
                Character ch = map.get(i).get(j);
                if (ch.equals('T')){
                    obstacles.add(new Obstacle(new GridPoint2(x,y)));
                } else if (ch.equals('X')) {
                    player = new Player(new GridPoint2(x,y),new GridPoint2(x,y),Rotation.RIGHT);
                }
            }
        }
    }

    private ArrayList<ArrayList<Character>> readMapFile(String mapFilepath) {
        ArrayList<ArrayList<Character>> map = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(mapFilepath));
            String line = reader.readLine();
            while (line != null) {
                ArrayList<Character> mapLine = new ArrayList<>();
                for (int i = 0;i < line.length();i++){
                    mapLine.add(line.charAt(i));
                }
                map.add(mapLine);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Collection<Obstacle> getObstacles() {
        return obstacles;
    }
}
