package me.binarybench.gameengine.game;

import me.binarybench.gameengine.game.events.GameStartEvent;
import me.binarybench.gameengine.game.events.GameEndEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public abstract class BaseGameComponent implements GameComponent {

    private Game game;

    @Override
    public void endGame()
    {
        System.out.println("Game Ended");
        Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(this));
        this.game = null;
    }

    protected boolean startGame(Game game)
    {
        Validate.notNull(game, "Game cannot be null!");

        if (this.game != null)
            return false;

        this.game = game;
        game.start(this);
        Bukkit.getPluginManager().callEvent(new GameStartEvent(this));
        return true;
    }

    @Override
    public Game getGame()
    {
        return game;
    }
}
