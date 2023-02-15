package dev.crius.cquest.model.requirement.impl.action;

import dev.crius.cquest.api.event.impl.customevents.impl.PlantEvent;
import dev.crius.cquest.model.Quest;

public class PlantQuestRequirement extends ActionQuestRequirement<PlantEvent> {

    private final XMaterial plant;

    public PlantQuestRequirement(Quest quest, XMaterial plant, int progress) {
        super(quest, PlantEvent.class, progress);
        this.plant = plant;
    }

    @Override
    public boolean isUpdatable(PlantEvent event) {
        return event.getPlant().equals(plant);
    }
}
