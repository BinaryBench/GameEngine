package me.binarybench.gameengine.game.gamestate;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public enum GameState {
    RESTARTING,

    LOBBY,

    PRE_GAME,

    IN_GAME,

    POST_GAME;

    private static GameState[] gameStates = values();

    public boolean equals(GameState... states)
    {
        for (GameState state : states)
            if (equals(state))
                return true;
        return false;
    }

    public GameState getLast()
    {
        int index = (this.ordinal() - 1);
        return gameStates[index < 0 ? gameStates.length - 1 : index];
    }

    public GameState getNext()
    {
        int index = (this.ordinal() + 1);
        return gameStates[index >= gameStates.length ? 0 : index];
    }

}
