package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.customevents.impl.PlantEvent;
import dev.crius.cquest.quest.Quest;

public class PlantRequirement extends ActionQuestRequirement<PlantEvent> {

    private final XMaterial plant;

    public PlantRequirement(Quest quest, XMaterial plant, int progress) {
        super(quest, PlantEvent.class, progress);
        this.plant = plant;
    }

    @Override
    public boolean isUpdatable(PlantEvent event) {
        return event.getPlant().equals(plant);
    }
}
