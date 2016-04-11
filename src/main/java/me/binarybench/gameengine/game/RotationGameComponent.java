package me.binarybench.gameengine.game;

/**
 * Created by BinaryBench on 3/19/2016.
 */
public class RotationGameComponent extends BaseGameComponent {

    private Game game;

    private int counter = 0;
    private Game[] games;

    public RotationGameComponent(Game... games)
    {
        this.games = games;
        startGame(games[counter]);
    }

    @Override
    public void endGame()
    {
        super.endGame();
        counter++;
        if (counter >= games.length)
            counter = 0;
        startGame(games[counter]);
    }
}
