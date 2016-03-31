package me.binarybench.gameengine.component;

/**
 * Created by Exerosis.
 */
public abstract class BaseComponent implements Component {
    private boolean enabled = false;

    @Override
    public boolean enable() {
        if (isEnabled())
            return false;
        onEnable();
        enabled = true;
        return true;
    }

    @Override
    public boolean disable() {
        if (!isEnabled())
            return false;
        onDisable();
        enabled = false;
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    public void onEnable() {

    }


    public void onDisable() {

    }


}