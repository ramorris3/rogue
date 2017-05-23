package com.grumpus.rogue.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.actor.Actor;

/**
 * A number or message that floats up above an actor for a moment.
 * "-5", "Crit!", "Miss!", etc.
 */
public class AttackMessageEffect implements Effect {

    private Actor actor;

    private GlyphLayout layout;
    private GlyphLayout bgLayout;

    private float oy;
    private float oyMax;
    private float timeLeft;

    public AttackMessageEffect(Actor actor, String message, Color color) {
        this.actor = actor;
        layout = new GlyphLayout(RogueGame.font, message,
                color, 0, Align.bottomLeft, false);
        bgLayout = new GlyphLayout(RogueGame.font, message,
                Color.BLACK, 0, Align.bottomLeft, false);
        oy = 0;
        oyMax = 8;
        timeLeft =0.7f;
    }

    @Override
    public void draw(float delta) {
        // update
        if (oy < oyMax) {
            oy = Math.min(oy+2, oyMax);
        }
        timeLeft -= delta;

        // draw
        float x = actor.getCenterX() - (layout.width / 2);
        float y = actor.getY() + oy;

        // draw bg letters (black)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                RogueGame.font.draw(RogueGame.batch, bgLayout, x + i, y + j);
            }
        }

        // draw normal letters (given color)
        RogueGame.font.draw(RogueGame.batch, layout, x, y);
    }

    @Override
    public boolean isFinished() {
        return timeLeft <= 0;
    }
}
