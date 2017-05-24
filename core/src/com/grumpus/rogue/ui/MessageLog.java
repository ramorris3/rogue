package com.grumpus.rogue.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.utils.Align;
import com.grumpus.rogue.RogueGame;

/**
 * Message log is fixed to the bottom of the screen,
 * And logs the player and enemies' actions.
 */
public class MessageLog {

    private GlyphLayout[] messageLayouts;
    private NinePatch border;

    public MessageLog() {
        messageLayouts = new GlyphLayout[5]; // only save the last few messageLayouts
        border = new NinePatch(
                new Texture("img/ui/border.png"),
                16, 16, 16, 16);
    }

    /** Add a message to the message log. */
    public void add(String message, Color color) {
        // capitalize the message
        if (message.length() > 0) {
            message = message.substring(0, 1).toUpperCase() + message.substring(1);
        } else {
            Gdx.app.log(this.getClass().getSimpleName(), "WARNING! Empty message passed to message log.");
        }

        // add message to front of the list, shifting and replacing the others.
        for (int i = messageLayouts.length - 1; i >= 0; i--) {
            if (i > 0) {
                messageLayouts[i] = messageLayouts[i - 1];
            } else {
                messageLayouts[i] = new GlyphLayout(RogueGame.font, message,
                        color, 0, Align.bottomLeft, false);
            }
        }
    }

    /** Most recent messageLayouts are drawn first, going up */
    public void draw() {
        // draw nine-patch border
        border.draw(RogueGame.batch, 0, 0, RogueGame.VIEW_WIDTH, 80);

        // draw messageLayouts
        int x = RogueGame.TILE_SIZE / 2;
        int y = RogueGame.TILE_SIZE;
        for (GlyphLayout layout : messageLayouts) {
            if (layout != null) {
                RogueGame.font.draw(RogueGame.batch, layout, x, y);
                y += layout.height * 2;
            }
        }
    }

}
