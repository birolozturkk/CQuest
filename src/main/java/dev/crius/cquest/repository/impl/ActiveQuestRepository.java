package dev.crius.cquest.repository.impl;

import com.j256.ormlite.support.ConnectionSource;
import dev.crius.cquest.SortedList;
import dev.crius.cquest.model.ActiveQuest;
import dev.crius.cquest.model.QuestData;
import dev.crius.cquest.repository.Repository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public class ActiveQuestRepository extends Repository<ActiveQuest, Integer> {

    private final SortedList<ActiveQuest> activeQuests = new SortedList<>(
            Comparator.comparing(ActiveQuest::getPlayerUUID));

    public ActiveQuestRepository(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ActiveQuest.class, Comparator.comparing(ActiveQuest::getId));
        activeQuests.addAll(getEntries());
    }

    @Override
    public void addEntry(ActiveQuest activeQuest) {
        super.addEntry(activeQuest);
        activeQuests.add(activeQuest);
    }

    public Optional<ActiveQuest> getActiveQuest(UUID playerUUID) {
        return activeQuests.getEntry(new ActiveQuest(playerUUID));
    }
}
