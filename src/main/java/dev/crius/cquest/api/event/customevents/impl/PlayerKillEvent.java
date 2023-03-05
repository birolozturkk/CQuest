package dev.crius.cquest.api.event.customevents.impl;

import dev.crius.cquest.api.event.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@Getter
public class PlayerKillEvent extends AbstractPlayerEvent {

    private final Player victim;
    private final EntityDamageEvent.DamageCause cause;

    public PlayerKillEvent(Player killer, Player victim, EntityDamageEvent.DamageCause cause) {
        super(killer);
        this.victim = victim;
        this.cause = cause;
    }
}
