package dev.crius.cquest.repository.impl;

import com.j256.ormlite.support.ConnectionSource;
import dev.crius.cquest.SortedList;
import dev.crius.cquest.model.ActiveQuest;
import dev.crius.cquest.model.CompletedQuest;
import dev.crius.cquest.repository.Repository;

import java.sql.SQLException;
import java.util.*;

public class CompletedQuestRepository extends Repository<CompletedQuest, Integer> {

    private final SortedList<CompletedQuest> completedQuests = new SortedList<>(
            Comparator.comparing(CompletedQuest::getPlayerUUID));

    public CompletedQuestRepository(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, CompletedQuest.class, Comparator.comparing(CompletedQuest::getId));
        completedQuests.addAll(getEntries());
    }

    @Override
    public void addEntry(CompletedQuest activeQuest) {
        super.addEntry(activeQuest);
        completedQuests.add(activeQuest);
    }

    public List<CompletedQuest> getCompletedQuests(UUID playerUUID) {
        int index = Collections.binarySearch(completedQuests, new CompletedQuest(playerUUID),
                Comparator.comparing(CompletedQuest::getPlayerUUID));
        if (index < 0) return Collections.emptyList();

        int currentIndex = index - 1;
        List<CompletedQuest> result = new ArrayList<>();
        result.add(completedQuests.get(index));

        while (true) {
            if (currentIndex < 0) break;
            CompletedQuest completedQuest = completedQuests.get(currentIndex);
            if (playerUUID.equals(completedQuest.getPlayerUUID())) {
                result.add(completedQuests.get(currentIndex));
                currentIndex--;
            } else {
                break;
            }
        }

        currentIndex = index + 1;

        while (true) {
            if (currentIndex >= completedQuests.size()) break;
            CompletedQuest completedQuest = completedQuests.get(currentIndex);
            if (playerUUID.equals(completedQuest.getPlayerUUID())) {
                result.add(completedQuests.get(currentIndex));
                currentIndex++;
            } else {
                break;
            }
        }
        return result;
    }
}
