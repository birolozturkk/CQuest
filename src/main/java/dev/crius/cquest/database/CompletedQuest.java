package dev.crius.cquest.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dev.crius.cquest.DatabaseObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@DatabaseTable(tableName = "completed_quests")
public class CompletedQuest extends DatabaseObject {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "player_uuid", canBeNull = false)
    private UUID playerUUID;

    @DatabaseField(columnName = "quest_id", canBeNull = false)
    private int questId;

    public CompletedQuest(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public CompletedQuest(UUID playerUUID, int questId) {
        this.playerUUID = playerUUID;
        this.questId = questId;
    }
}
