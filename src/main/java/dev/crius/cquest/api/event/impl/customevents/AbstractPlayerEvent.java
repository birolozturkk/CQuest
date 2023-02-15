package dev.crius.cquest.api.event.impl.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class AbstractPlayerEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;

    public AbstractPlayerEvent(Player who) {
        super(who);
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}

