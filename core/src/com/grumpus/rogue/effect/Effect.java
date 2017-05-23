package com.grumpus.rogue.effect;

/**
 * An effect is any temporary visual not inherently tied to the UI.
 * Damage numbers, weapon slash animations, puffs of smoke, etc. are all effects.
 */
public interface Effect {

    /** Update and draw this effect */
    void draw(float delta);

    /** Find out whether or not to keep drawing this effect */
    boolean isFinished();

}
