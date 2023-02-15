package dev.crius.cquest;

import com.github.scropytr.serializationapi.Persist;
import dev.crius.cquest.api.event.listener.CustomEventListener;
import dev.crius.cquest.config.Configuration;
import dev.crius.cquest.config.SQL;
import dev.crius.cquest.config.inventory.QuestGUIConfig;
import dev.crius.cquest.listener.QuestListener;
import dev.crius.cquest.managers.DatabaseManager;
import dev.crius.cquest.managers.QuestManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

@Getter
public final class CQuest extends JavaPlugin {

    private static CQuest instance;
    private Persist persist;

    private Configuration configuration;
    private QuestGUIConfig questGUIConfig;
    private SQL sql;

    private DatabaseManager databaseManager;
    private QuestManager questManager;

    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists()) this.getDataFolder().mkdirs();

        try {
            persist = new Persist(getDataFolder(), Persist.PersistType.YAML);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadConfigs();
        saveConfigs();
        setupManagers();
        questManager.load();
        registerListeners();
    }

    private void loadConfigs() {
        configuration = persist.load(Configuration.class, "config");
        questGUIConfig = persist.load(QuestGUIConfig.class, "quest-gui");
        sql = persist.load(SQL.class, "sql");
    }

    private void saveConfigs() {
        persist.save(configuration, "config");
        persist.save(questGUIConfig, "quest-gui");
        persist.save(sql, "sql");
    }

    @SneakyThrows
    private void setupManagers() {
        databaseManager = new DatabaseManager();
        databaseManager.init();
        questManager = new QuestManager(this);
    }

    private void registerListeners() {
        QuestListener questListener = new QuestListener(this);
        questListener.register();
        getServer().getPluginManager().registerEvents(questListener, this);
        getServer().getPluginManager().registerEvents(new CustomEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
