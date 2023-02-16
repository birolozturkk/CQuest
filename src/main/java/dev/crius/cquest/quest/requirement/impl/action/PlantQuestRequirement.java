package dev.crius.cquest.quest.requirement.impl.action;

import com.cryptomorin.xseries.XMaterial;
import dev.crius.cquest.api.event.impl.customevents.impl.PlantEvent;
import dev.crius.cquest.quest.Quest;

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
