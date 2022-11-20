package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;

import java.util.Collection;

/*
* Port
*/
public interface CommandGenerator {
    Collection<Command> generateCommands(Tank player, Level level);
}
