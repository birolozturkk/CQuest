package dev.crius.cquest.api.event.customevents.impl;

import dev.crius.cquest.api.event.customevents.AbstractPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@Getter
public class MobKillEvent extends AbstractPlayerEvent {

    private final Mob victim;
    private final EntityDamageEvent.DamageCause cause;

    public MobKillEvent(Player killer, Mob victim, EntityDamageEvent.DamageCause cause) {
        super(killer);
        this.victim = victim;
        this.cause = cause;
    }
}
