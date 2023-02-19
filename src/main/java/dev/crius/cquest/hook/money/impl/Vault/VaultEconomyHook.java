package dev.crius.cquest.hook.money.impl.Vault;

import dev.crius.cquest.hook.money.EconomyHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomyHook implements EconomyHook {

    private Economy economy = null;

    public VaultEconomyHook() {
        init();
    }

    @Override
    public void init() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return;

        economy = rsp.getProvider();
    }


    @Override
    public void add(OfflinePlayer player, double amount) {
        economy.depositPlayer(player, amount);
    }

}
