package me.binarybench.gameengine.game;

/**
 * Created by BinaryBench on 3/19/2016.
 */
public class SingleGameComponent extends BaseGameComponent {

    private Game game;

    public SingleGameComponent(Game game)
    {
        this.game = game;
        startGame(game);
    }

    @Override
    public void endGame()
    {
        super.endGame();
        startGame(game);
    }
}
