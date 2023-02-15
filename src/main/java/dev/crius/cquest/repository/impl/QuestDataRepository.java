package dev.crius.cquest.repository.impl;

import com.j256.ormlite.support.ConnectionSource;
import dev.crius.cquest.SortedList;
import dev.crius.cquest.model.Quest;
import dev.crius.cquest.model.QuestData;
import dev.crius.cquest.repository.Repository;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class QuestDataRepository extends Repository<QuestData, Integer> {

    private final SortedList<QuestData> questDataEntries = new SortedList<>(Comparator.comparing(QuestData::getPlayerUUID)
            .thenComparing(QuestData::getQuestId)
            .thenComparing(QuestData::getRequirementIndex));

    public QuestDataRepository(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, QuestData.class, Comparator.comparing(QuestData::getId));
        questDataEntries.addAll(getEntries());
    }

    @Override
    public void addEntry(QuestData questData) {
        super.addEntry(questData);
        questDataEntries.add(questData);
    }

    public Optional<QuestData> getQuestData(UUID playerUUID, int questId, int requirementIndex) {
        return questDataEntries.getEntry(new QuestData(playerUUID, questId, requirementIndex));
    }
}
