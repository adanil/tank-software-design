package ru.mipt.bit.platformer;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandType;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.commands.Shoot;
import ru.mipt.bit.platformer.entity.*;
import ru.mipt.bit.platformer.util.MoveVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AIAdapter {
    AI ai;
    Collection<Tank> enemies;
    Tank player;
    Collection<Obstacle> obstacles;
    Level level;

    public AIAdapter( Collection<Tank> enemies, Tank player, Collection<Obstacle> obstacles, Level level) {
        ai = new RandomRecommendingAI();
        this.enemies = enemies;
        this.player = player;
        this.obstacles = obstacles;
        this.level = level;
    }

    private GameState generateGameState(){
        List<Bot> botsAdapted = new ArrayList<>();
        List<org.awesome.ai.state.immovable.Obstacle> obstacleAdapted = new ArrayList<>();

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
        Player playerAdapted = playerBuilder.build();


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
            botsAdapted.add(botBuilder.build());
        }

        for (Obstacle obstacle : obstacles){
            org.awesome.ai.state.immovable.Obstacle o = new org.awesome.ai.state.immovable.Obstacle(obstacle.getCoordinates().x,obstacle.getCoordinates().y);
            obstacleAdapted.add(o);
        }


        GameState.GameStateBuilder gmBuilder = GameState.builder();

        gmBuilder.bots(botsAdapted);
        gmBuilder.player(playerAdapted);
        gmBuilder.obstacles(obstacleAdapted);
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
                        if (player.validateCommand(CommandType.SHOOT))
                            commands.add(new Shoot(new Bullet(player), level, player));
                        break;
                    }
                    case MoveEast: {
                        if (player.validateCommand(CommandType.MOVE))
                            commands.add(new MoveCommand(player,MoveVector.RIGHT, TileObject.PLAYER,level));
                        break;
                    }
                    case MoveWest: {
                        if (player.validateCommand(CommandType.MOVE))
                            commands.add(new MoveCommand(player,MoveVector.LEFT, TileObject.PLAYER,level));
                        break;
                    }
                    case MoveNorth: {
                        if (player.validateCommand(CommandType.MOVE))
                            commands.add(new MoveCommand(player,MoveVector.UP, TileObject.PLAYER,level));
                        break;
                    }
                    case MoveSouth: {
                        if (player.validateCommand(CommandType.MOVE))
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
                                if (bot.validateCommand(CommandType.SHOOT))
                                    commands.add(new Shoot(new Bullet(bot), level,bot));
                                break;
                            }
                            case MoveEast: {
                                if (bot.validateCommand(CommandType.MOVE))
                                    commands.add(new MoveCommand(bot,MoveVector.RIGHT, TileObject.BOT,level));
                                break;
                            }
                            case MoveWest: {
                                if (bot.validateCommand(CommandType.MOVE))
                                    commands.add(new MoveCommand(bot,MoveVector.LEFT, TileObject.BOT,level));
                                break;
                            }
                            case MoveNorth: {
                                if (bot.validateCommand(CommandType.MOVE))
                                    commands.add(new MoveCommand(bot,MoveVector.UP, TileObject.BOT,level));
                                break;
                            }
                            case MoveSouth: {
                                if (bot.validateCommand(CommandType.MOVE))
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
