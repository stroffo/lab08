package it.unibo.deathnote.impl;

import java.util.List;

import it.unibo.deathnote.api.DeathNote;

public final class DeathNoteImpl implements DeathNote {

    private List<DeathNoteEntry> entries = List.of();
    private DeathNoteEntry latestEntry;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Cannot find any valid rule at index " + ruleNumber + ".");
        } 

        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        var newEntry = new DeathNoteEntry();
        newEntry.name = name;
        
        entries.add(newEntry);
        latestEntry = newEntry;

        throw new UnsupportedOperationException("Unimplemented method 'writeName'");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        throw new UnsupportedOperationException("Unimplemented method 'writeDeathCause'");
    }

    @Override
    public boolean writeDetails(String details) {
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

    private class DeathNoteEntry {
        private String name = "";
        private String deathCause = "";
        private String deathDetails = "";

        private void flush() {
            name = "";
            deathCause = "";
            deathDetails = "";
        }
    }
}
