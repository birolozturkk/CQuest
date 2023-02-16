package dev.crius.cquest.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dev.crius.cquest.DatabaseObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@DatabaseTable(tableName = "quest_data")
public class QuestData extends DatabaseObject {

    @DatabaseField(columnName = "id", canBeNull = false, generatedId = true)
    private int id;

    @DatabaseField(columnName = "player_uuid", canBeNull = false)
    private UUID playerUUID;

    @DatabaseField(columnName = "quest_id", canBeNull = false)
    private int questId;

    @DatabaseField(columnName = "requirement_index", canBeNull = false)
    private int requirementIndex;

    @DatabaseField(columnName = "progress", canBeNull = false)
    private int progress;

    public QuestData(UUID playerUUID, int questId, int requirementIndex) {
        this.playerUUID = playerUUID;
        this.questId = questId;
        this.requirementIndex = requirementIndex;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        setChanged(true);
    }

}
