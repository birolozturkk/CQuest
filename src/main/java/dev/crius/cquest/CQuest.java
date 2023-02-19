package dev.crius.cquest;

import com.github.scropytr.serializationapi.Persist;
import dev.crius.cquest.api.event.listener.CustomEventListener;
import dev.crius.cquest.commands.CQuestCommand;
import dev.crius.cquest.commands.QuestCommand;
import dev.crius.cquest.config.Configuration;
import dev.crius.cquest.config.SQL;
import dev.crius.cquest.hook.money.EconomyHook;
import dev.crius.cquest.hook.money.impl.Vault.VaultEconomyHook;
import dev.crius.cquest.listener.QuestListener;
import dev.crius.cquest.managers.BossBarManager;
import dev.crius.cquest.managers.DatabaseManager;
import dev.crius.cquest.managers.QuestManager;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

@Getter
public final class CQuest extends JavaPlugin {

    private static CQuest instance;
    private Persist persist;

    private Configuration configuration;
    private SQL sql;

    private DatabaseManager databaseManager;
    private QuestManager questManager;
    private BossBarManager bossBarManager;
    private BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(this);

    private BukkitAudiences adventure;

    private EconomyHook economyHook;

    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists()) this.getDataFolder().mkdirs();

        try {
            persist = new Persist(getDataFolder(), Persist.PersistType.YAML);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.adventure = BukkitAudiences.create(this);

        loadConfigs();
        saveConfigs();
        setupManagers();
        setupEconomy();
        questManager.load();
        registerListeners();
        commandManager.registerCommand(new QuestCommand(this));
        commandManager.registerCommand(new CQuestCommand(this));
    }

    public void loadConfigs() {
        configuration = persist.load(Configuration.class, "config");
        sql = persist.load(SQL.class, "sql");
    }

    private void saveConfigs() {
        persist.save(configuration, "config");
        persist.save(sql, "sql");
    }

    @SneakyThrows
    private void setupManagers() {
        databaseManager = new DatabaseManager();
        databaseManager.init();
        questManager = new QuestManager(this);
        bossBarManager = new BossBarManager(this);
    }

    private void registerListeners() {
        QuestListener questListener = new QuestListener(this);
        questListener.register();
        getServer().getPluginManager().registerEvents(questListener, this);
        getServer().getPluginManager().registerEvents(new CustomEventListener(), this);
    }

    private void setupEconomy() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        if (pluginManager.getPlugin("Vault") != null)
            economyHook = new VaultEconomyHook();
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

    }

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public void log(String message) {
        log(message, Level.INFO);
    }

    public void log(String message, Level level) {
        this.getLogger().log(level, message);
    }


    public static CQuest getInstance() {
        return instance;
    }
}
