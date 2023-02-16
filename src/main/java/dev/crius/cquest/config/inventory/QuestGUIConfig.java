package dev.crius.cquest.config.inventory;


import java.util.List;

public class QuestGUIConfig extends NoItemGUI {

    public QuestItem questItem;

    public static class QuestItem {


        public List<Integer> slots;
        public ItemConfig completedQuest;
        public ItemConfig canCompleteQuest;
        public ItemConfig canNotCompleteQuest;
    }
}