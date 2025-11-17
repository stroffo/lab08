package it.unibo.deathnote.impl;

import java.util.LinkedList;
import java.util.List;

import it.unibo.deathnote.api.DeathNote;

public final class DeathNoteImpl implements DeathNote {
    private static final String DEFAULT_DEATH_CAUSE = "heart attack";

    private List<DeathNoteEntry> entries = new LinkedList<>();
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
        if (name == null) throw new NullPointerException();
        
        if (latestEntry != null) {
            entries.add(latestEntry.clone());
        } 

        latestEntry = new DeathNoteEntry(name);
        latestEntry.deathCause = DEFAULT_DEATH_CAUSE;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (cause == null || latestEntry == null) {
            throw new IllegalStateException();
        }

        if (latestEntry.getTimeElapsed() < 40 ) {
            latestEntry.deathCause = cause;
            return true;
        }

        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        var entry = this.getEntryByName(name);

        if (entry == null) {
            throw new IllegalArgumentException();
        }

        return entry.deathCause;
    }

    @Override
    public String getDeathDetails(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(final String name) {
        if (latestEntry == null) return false;

        if (latestEntry.name == name) return true;
        
        for (DeathNoteEntry entry : entries) {
            if (entry.name == name) return true;
        }

        return false;
    }

    private DeathNoteEntry getEntryByName(final String name) {
        if (latestEntry.name == name) {
            return latestEntry;
        }

        for (DeathNoteEntry entry : entries) {
            if (entry.name == name) {
                return entry;
            }
        }

        return null;
    }

    private class DeathNoteEntry implements Cloneable {
        private final String name;
        private String deathCause = "";
        private String deathDetails = "";
        private long nameWriteTime;

        public DeathNoteEntry(final String name) {
            this.name = name;
            nameWriteTime = System.currentTimeMillis();
        }

        public long getTimeElapsed() {   
            return System.currentTimeMillis() - nameWriteTime;
        }

        /**
         * Get a shallow copy of the entry
         * @return a shallow copy of this object
         */
        protected DeathNoteEntry clone() {
            var obj = new DeathNoteEntry(this.name);
            obj.deathCause = this.deathCause;
            obj.deathDetails = this.deathDetails;
            obj.nameWriteTime = this.nameWriteTime;
            return obj;    
        }
    }
}
