package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandGenerator;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.commands.Shoot;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Obstacle;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.TileObject;
import ru.mipt.bit.platformer.util.MoveVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AIAdapter {
    NotRecommendingAI ai;
    Collection<Tank> enemies;
    Tank player;
    Collection<Obstacle> obstacles;
    Level level;

    public AIAdapter( Collection<Tank> enemies, Tank player, Collection<Obstacle> obstacles, Level level) {
        ai = new NotRecommendingAI();
        this.enemies = enemies;
        this.player = player;
        this.obstacles = obstacles;
        this.level = level;
    }

    private GameState generateGameState(){
        List<Bot> bots_adapted = new ArrayList<>();
        List<org.awesome.ai.state.immovable.Obstacle> obstacle_adapted = new ArrayList<>();

        Player.PlayerBuilder playerBuilder = Player.builder();
        playerBuilder.source(player);
        playerBuilder.x(player.getCurrentCoordinates().x);
        playerBuilder.y(player.getDestinationCoordinates().y);
        playerBuilder.destX(player.getDestinationCoordinates().x);
        playerBuilder.destY(player.getDestinationCoordinates().y);
        switch (player.getRotation()){
            case UP:{
                playerBuilder.orientation(Orientation.N);
                break;
            }
            case DOWN:{
                playerBuilder.orientation(Orientation.S);
                break;
            }
            case LEFT:{
                playerBuilder.orientation(Orientation.W);
                break;
            }
            case RIGHT:{
                playerBuilder.orientation(Orientation.E);
                break;
            }
        }
        Player player_adapted = playerBuilder.build();


        for (Tank tank : enemies){
            Bot.BotBuilder botBuilder = Bot.builder();
            botBuilder.source(tank);
            botBuilder.x(tank.getCurrentCoordinates().x);
            botBuilder.y(tank.getCurrentCoordinates().y);
            botBuilder.destX(tank.getDestinationCoordinates().x);
            botBuilder.destY(tank.getDestinationCoordinates().y);
            switch (tank.getRotation()){
                case UP:{
                    botBuilder.orientation(Orientation.N);
                    break;
                }
                case DOWN:{
                    botBuilder.orientation(Orientation.S);
                    break;
                }
                case LEFT:{
                    botBuilder.orientation(Orientation.W);
                    break;
                }
                case RIGHT:{
                    botBuilder.orientation(Orientation.E);
                    break;
                }
            }
            bots_adapted.add(Bot.builder().build());
        }

        for (Obstacle obstacle : obstacles){
            org.awesome.ai.state.immovable.Obstacle o = new org.awesome.ai.state.immovable.Obstacle(obstacle.getCoordinates().x,obstacle.getCoordinates().y);
            obstacle_adapted.add(o);
        }


        GameState.GameStateBuilder gmBuilder = GameState.builder();

        gmBuilder.bots(bots_adapted);
        gmBuilder.player(player_adapted);
        gmBuilder.obstacles(obstacle_adapted);
        gmBuilder.levelWidth(level.getWidth());
        gmBuilder.levelHeight(level.getHeight());

        return gmBuilder.build();
    }

    public Collection<Command> generateCommands() {
        ArrayList<Command> commands = new ArrayList<>();

        GameState gameState = generateGameState();
        List<Recommendation> recs = ai.recommend(gameState);

        for (Recommendation rec : recs){
            if (rec.getActor().getSource() == player) {
                switch (rec.getAction()) {
                    case Shoot: {
                        commands.add(new Shoot());
                        break;
                    }
                    case MoveEast: {
                        commands.add(new MoveCommand(player,MoveVector.RIGHT, TileObject.PLAYER,level));
                        break;
                    }
                    case MoveWest: {
                        commands.add(new MoveCommand(player,MoveVector.LEFT, TileObject.PLAYER,level));
                        break;
                    }
                    case MoveNorth: {
                        commands.add(new MoveCommand(player,MoveVector.UP, TileObject.PLAYER,level));
                        break;
                    }
                    case MoveSouth: {
                        commands.add(new MoveCommand(player,MoveVector.DOWN, TileObject.PLAYER,level));
                        break;
                    }
                }
            }
            else {
                for (Tank bot : enemies){
                    if (rec.getActor().getSource() == bot) {
                        switch (rec.getAction()) {
                            case Shoot: {
                                commands.add(new Shoot());
                                break;
                            }
                            case MoveEast: {
                                commands.add(new MoveCommand(bot,MoveVector.RIGHT, TileObject.BOT,level));
                                break;
                            }
                            case MoveWest: {
                                commands.add(new MoveCommand(bot,MoveVector.LEFT, TileObject.BOT,level));
                                break;
                            }
                            case MoveNorth: {
                                commands.add(new MoveCommand(bot,MoveVector.UP, TileObject.BOT,level));
                                break;
                            }
                            case MoveSouth: {
                                commands.add(new MoveCommand(bot,MoveVector.DOWN, TileObject.BOT,level));
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }

        return commands;
    }
}
