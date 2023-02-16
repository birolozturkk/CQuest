package dev.crius.cquest.quest;

import dev.crius.cquest.config.inventory.QuestGUIConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Getter
public class QuestPage {

    private QuestGUIConfig gui;
    private Map<Integer, Quest> quests;
}
