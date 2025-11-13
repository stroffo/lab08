package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private final DeathNote deathNote = new DeathNoteImpl();

    private void isValidString(final String msg) {
        assertNotNull(msg); // Non-null message
        assertFalse(msg.isBlank()); // Not a blank or empty message
    }

    @Test
    void testZeroOrNegativeRule() {
        try {
            deathNote.getRule(0);
        } catch (final IllegalArgumentException e) {
            isValidString(e.getMessage());
        }

        try {
            deathNote.getRule(-1);
        } catch (final IllegalArgumentException e) {
            isValidString(e.getMessage());
        }
    }

    @Test
    void testNullOrEmptyRules() {
        for (String rule : DeathNote.RULES) {
            isValidString(rule);
        }
    }

    @Test
    void testHumanDeath() {
        throw new AssertionError();
    }

    @Test
    void testCauseOfDeath() {
        throw new AssertionError();
    }

    @Test
    void testDetailsOfDeath() {
        throw new AssertionError();
    }
}
