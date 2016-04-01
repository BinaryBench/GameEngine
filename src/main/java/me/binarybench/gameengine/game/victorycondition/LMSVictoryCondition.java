package me.binarybench.gameengine.game.victorycondition;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bench on 3/31/2016.
 */
public class LMSVictoryCondition extends ListenerComponent {

    private LinkedList<Player> rank = new LinkedList<>();

    private PlayerHolder playerHolder;
    private int endPlayersAmount;
    private PlayerHolder broadcast;
    private Runnable whenDone;

    public LMSVictoryCondition(PlayerHolder playerHolder, int endPlayersAmount, PlayerHolder broadcast, Runnable whenDone)
    {
        this.playerHolder = playerHolder;
        this.endPlayersAmount = endPlayersAmount;
        this.broadcast = broadcast;
        this.whenDone = whenDone;
    }

    public void checkEndGame(int offset)
    {
        int playerCount = playerHolder.getPlayers().size() + offset;

        if (playerCount <= endPlayersAmount)
        {
            whenDone.run();
        }
    }

    public void sendWinners()
    {
        for (Player player : playerHolder)
        {
            rank.add(player);
        }

        int counter = 0;
        broadcast("----------");
        broadcast("");
        for (Player player : getRank())
        {
            counter++;
            broadcast(counter + ". " + ChatColor.YELLOW + player.getName());
            if (counter >= 3)
                break;
        }
        broadcast("");
        broadcast("----------");


    }

    public void broadcast(String message)
    {
        getBroadcast().broadcast(message);
    }


    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }

    public int getEndPlayersAmount()
    {
        return endPlayersAmount;
    }

    public PlayerHolder getBroadcast()
    {
        return broadcast;
    }

    public LinkedList<Player> getRank()
    {
        return rank;
    }
}
