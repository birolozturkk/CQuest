package dev.crius.cquest.hook.money;

import dev.crius.cquest.hook.Hook;
import org.bukkit.OfflinePlayer;

public interface EconomyHook extends Hook {

    default void init(){}
    void add(OfflinePlayer player, double amount);
}
