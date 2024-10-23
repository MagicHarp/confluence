package org.confluence.mod.api.event;

import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import org.confluence.mod.common.data.saved.GamePhase;

public class GamePhaseInitializeEvent extends Event implements IModBusEvent {
    private final GamePhase[] original;
    private GamePhase[] neoValues;

    public GamePhaseInitializeEvent(GamePhase[] original) {
        this.original = original;
        this.neoValues = original;
    }

    public GamePhase[] getOriginal() {
        return original;
    }

    public void setNeoValues(GamePhase[] neoValues) {
        this.neoValues = neoValues;
    }

    public GamePhase[] getNeoValues() {
        return neoValues;
    }
}
