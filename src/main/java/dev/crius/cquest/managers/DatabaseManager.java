package dev.crius.cquest.managers;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.DatabaseTypeUtils;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.logger.NullLogBackend;
import com.j256.ormlite.support.ConnectionSource;
import dev.crius.cquest.CQuest;
import dev.crius.cquest.config.SQL;
import dev.crius.cquest.repository.impl.ActiveQuestRepository;
import dev.crius.cquest.repository.impl.CompletedQuestRepository;
import dev.crius.cquest.repository.impl.QuestDataRepository;
import lombok.Getter;

import java.io.File;
import java.sql.SQLException;

@Getter
public class DatabaseManager {

    private ConnectionSource connectionSource;

    private QuestDataRepository questDataRepository;
    private ActiveQuestRepository activeQuestRepository;
    private CompletedQuestRepository completedQuestRepository;

    public void init() throws SQLException {
        LoggerFactory.setLogBackendFactory(new NullLogBackend.NullLogBackendFactory());

        SQL sqlConfig = CQuest.getInstance().getSql();
        String databaseURL = getDatabaseURL(sqlConfig);

        this.connectionSource = new JdbcConnectionSource(
                databaseURL,
                sqlConfig.username,
                sqlConfig.password,
                DatabaseTypeUtils.createDatabaseType(databaseURL)
        );

        this.questDataRepository = new QuestDataRepository(connectionSource);
        this.activeQuestRepository = new ActiveQuestRepository(connectionSource);
        this.completedQuestRepository = new CompletedQuestRepository(connectionSource);
    }

    /**
     * Database connection String used for establishing a connection.
     *
     * @return The database URL String
     */
    private String getDatabaseURL(SQL sqlConfig) {
        switch (sqlConfig.driver) {
            case MYSQL:
                return "jdbc:" + sqlConfig.driver.name().toLowerCase() + "://" + sqlConfig.host + ":" + sqlConfig.port + "/" + sqlConfig.database + "?useSSL=" + sqlConfig.useSSL;
            case SQLITE:
                return "jdbc:sqlite:" + new File(CQuest.getInstance().getDataFolder(), sqlConfig.database + ".db");
            default:
                throw new UnsupportedOperationException("Unsupported driver (how did we get here?): " + sqlConfig.driver.name());
        }
    }

}
