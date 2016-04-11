package me.binarybench.gameengine.game.victorycondition;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.common.playerholder.events.PlayerRemoveEvent;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.*;

/**
 * Created by Bench on 3/31/2016.
 */
public class LMSVictoryCondition extends ListenerComponent {

    private List<Player> rank = new ArrayList<>();

    private PlayerHolder playerHolder;
    private int endPlayersAmount;
    private PlayerHolder broadcast;
    private Runnable whenDone;

    public LMSVictoryCondition(PlayerHolder playerHolder, PlayerHolder broadcast, Runnable whenDone)
    {
        this(playerHolder, 1, broadcast, whenDone);
    }

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

        if (playerCount <= getEndPlayersAmount())
        {
            whenDone.run();
        }

    }

    @EventHandler
    public void playerLeave(PlayerRemoveEvent event)
    {
        if (event.getPlayerHolder() != getPlayerHolder())
            return;
        Player player = event.getPlayer();

        rank.remove(player);
        rank.add(event.getPlayer());

        checkEndGame(-1);
    }

    public void sendWinners()
    {
        int counter = 0;
        int score = 1;


        List<Player> players = new ArrayList<>(getPlayerHolder().getPlayers());
        players.removeAll(getRank());

        broadcast("----------");
        broadcast("");

        for (Player player : players)
        {
            counter++;
            print(score, player);
            if (counter >= 3)
                break;
        }
        if (counter < 3)
            for (Player player : getRank())
            {
                counter++;
                score++;
                print(score, player);
                if (counter >= 3)
                    break;
            }

        broadcast("");
        broadcast("----------");
    }

    private void print(int rank, Player player)
    {
        broadcast(rank + ". " + ChatColor.YELLOW + player.getName());
    }

    @Override
    public void onDisable()
    {
        sendWinners();
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

    public List<Player> getRank()
    {
        return rank;
    }
}
