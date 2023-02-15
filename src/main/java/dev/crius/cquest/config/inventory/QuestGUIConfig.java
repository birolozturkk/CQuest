package dev.crius.cquest.config.inventory;


public class QuestGUIConfig extends NoItemGUI {

    public QuestItem questItem;

    public static class QuestItem {

        public ItemConfig completedQuest;
        public ItemConfig canCompleteQuest;
        public ItemConfig canNotCompleteQuest;
    }
}