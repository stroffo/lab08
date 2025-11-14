package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private final DeathNote deathNote = new DeathNoteImpl();
    private final String[] testNames =  {
        "Diego Mario Alessi Tosi",
        "Pierpaolo Rossi",
        "",
        "Ok",
        "Filippo Ugolini",
        "Samuele Lotti"
    };
    private final String defaultDeathCause = "heart attack";

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
        assertEquals(defaultDeathCause, deathNote.getDeathCause(testNames[1]));
        
        deathNote.writeName(testNames[3]);
        String newDeathCause = "karting incident";
        Boolean validCause = deathNote.writeDeathCause(newDeathCause);
        assertTrue(validCause);
        assertEquals(deathNote.getDeathCause(testNames[3]), newDeathCause);
        assertDoesNotThrow(() -> {
            Thread.sleep(100L);
        });

        newDeathCause = "tripping on stairs";
        validCause = deathNote.writeDeathCause(newDeathCause);
        assertFalse(validCause);
        assertNotEquals(deathNote.getDeathCause(testNames[3]), newDeathCause);
    }

    @Test
    void testDetailsOfDeath() {
        assertThrows(IllegalStateException.class, () -> {
            deathNote.writeDetails("very bad accident");
        });
        
        deathNote.writeName(testNames[4]);
        assertTrue(deathNote.getDeathDetails(defaultDeathCause).isBlank());

        String newDeathDetails = "ran for too long";
        assertTrue(deathNote.writeDetails(newDeathDetails));
        assertEquals(newDeathDetails, deathNote.getDeathDetails(testNames[4]));

        deathNote.writeName(testNames[5]);
        assertDoesNotThrow(() -> {
            Thread.sleep(6100L);
        });
        newDeathDetails = "checked nvidia stock prices";
        assertFalse(deathNote.writeDetails(newDeathDetails));
    }
}
