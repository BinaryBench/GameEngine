package me.binarybench.gameengine.component;

/**
 * Created by Exerosis.
 */
public abstract class ComponentBase implements Component {
    private boolean enabled = false;

    @Override
    public void enable() {
        if (isEnabled())
            return;
        onEnable();
        enabled = true;
    }

    @Override
    public void disable() {
        if (!isEnabled())
            return;
        onDisable();
        enabled = false;
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