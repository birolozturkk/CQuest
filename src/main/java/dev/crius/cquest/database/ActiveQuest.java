package dev.crius.cquest.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dev.crius.cquest.DatabaseObject;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@DatabaseTable(tableName = "active_quests")
public class ActiveQuest extends DatabaseObject {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "player_uuid", canBeNull = false, unique = true)
    private UUID playerUUID;

    @DatabaseField(columnName = "quest_id", canBeNull = false)
    private int questId;

    public ActiveQuest(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public ActiveQuest(UUID playerUUID, int questId) {
        this.playerUUID = playerUUID;
        this.questId = questId;
    }
}
