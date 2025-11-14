package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private static final String DEFAULT_DEATH_CAUSE = "heart attack";

    private final DeathNote deathNote = new DeathNoteImpl();
    private final String[] testNames = {
        "Diego Mario Alessi Tosi",
        "Pierpaolo Rossi",
        "",
        "Ok",
        "Filippo Ugolini",
        "Samuele Lotti",
    };

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
        for (final String rule : DeathNote.RULES) {
            isValidString(rule);
        }
    }

    @Test
    void testHumanDeath() {
        assertFalse(deathNote.isNameWritten(testNames[0]));

        deathNote.writeName(testNames[0]);

        assertTrue(deathNote.isNameWritten(testNames[0]));
        assertFalse(deathNote.isNameWritten(testNames[1]));
        assertFalse(deathNote.isNameWritten(testNames[2]));
    }

    @Test
    void testCauseOfDeath() {
        assertThrows(IllegalStateException.class, () -> {
            deathNote.writeDeathCause("car accident");
        });

        deathNote.writeName(testNames[1]);
        assertEquals(DEFAULT_DEATH_CAUSE, deathNote.getDeathCause(testNames[1]));

        deathNote.writeName(testNames[3]);
        String newDeathCause = "karting incident";
        assertTrue(deathNote.writeDeathCause(newDeathCause));
        assertEquals(deathNote.getDeathCause(testNames[3]), newDeathCause);
        final long sleepTime = 100;
        assertDoesNotThrow(() -> {
            Thread.sleep(sleepTime);
        });

        newDeathCause = "tripping on stairs";
        assertFalse(deathNote.writeDeathCause(newDeathCause));
        assertNotEquals(deathNote.getDeathCause(testNames[3]), newDeathCause);
    }

    @Test
    void testDetailsOfDeath() {
        assertThrows(IllegalStateException.class, () -> {
            deathNote.writeDetails("very bad accident");
        });

        deathNote.writeName(testNames[4]);
        assertTrue(deathNote.getDeathDetails(DEFAULT_DEATH_CAUSE).isBlank());

        String newDeathDetails = "ran for too long";
        assertTrue(deathNote.writeDetails(newDeathDetails));
        assertEquals(newDeathDetails, deathNote.getDeathDetails(testNames[4]));

        deathNote.writeName(testNames[5]);
        final long sleepTime = 6100;
        assertDoesNotThrow(() -> {
            Thread.sleep(sleepTime);
        });
        newDeathDetails = "checked nvidia stock prices";
        assertFalse(deathNote.writeDetails(newDeathDetails));
    }
}
