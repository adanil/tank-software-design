package ru.mipt.bit.platformer;

import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Actor;
import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.entity.TileObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRecommentingAI implements AI {

    @Override
    public List<Recommendation> recommend(GameState gameState) {
        ArrayList<Recommendation> commands = new ArrayList<>();
        for (Bot bot : gameState.getBots()) {
            Random ran = new Random();
            int mv = ran.nextInt(5);
            switch (mv) {
                case 0: {
                    commands.add(new Recommendation(bot, Action.MoveEast));
                    break;
                }
                case 1: {
                    commands.add(new Recommendation(bot, Action.MoveWest));
                    break;
                }
                case 2: {
                    commands.add(new Recommendation(bot, Action.MoveNorth));
                    break;
                }
                case 3: {
                    commands.add(new Recommendation(bot, Action.MoveSouth));
                    break;
                }
                case 4: {
                    commands.add(new Recommendation(bot, Action.Shoot));
                    break;
                }
            }
        }
        return commands;
    }
}
