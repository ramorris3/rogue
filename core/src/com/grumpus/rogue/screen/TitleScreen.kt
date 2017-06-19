package com.grumpus.rogue.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.viewport.FitViewport
import com.grumpus.rogue.RogueGame

/** Opening title/game start screen */
class TitleScreen(val game: RogueGame): ScreenAdapter() {
    // cam and viewport
    val camera = OrthographicCamera()
    val viewport = FitViewport(
            RogueGame.VIEW_WIDTH.toFloat(),
            RogueGame.VIEW_HEIGHT.toFloat(),
            camera
    )

    // TODO: Replace title glyph layout with pixel art
    // glyph layouts
    val titleLayout = GlyphLayout(RogueGame.font, "RAVENSGARD v0.1")
    val startLayout = GlyphLayout(RogueGame.font, "Press SPACE to start")

    /**
     * Renders title screen, waits for key press to start
     */
    override fun render(delta: Float) {
        // check for keypress
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.goToScreen(PlayScreen(game), false)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }

        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // update camera
        camera.update()
        RogueGame.batch.projectionMatrix = camera.combined

        RogueGame.batch.begin()

        // draw border
        RogueGame.border.draw(RogueGame.batch, 0f, 0f,
                RogueGame.VIEW_WIDTH.toFloat(),
                RogueGame.VIEW_HEIGHT.toFloat())

        // draw title
        RogueGame.font.draw(RogueGame.batch,
                titleLayout,
                RogueGame.VIEW_WIDTH / 2 - titleLayout.width / 2,
                RogueGame.VIEW_HEIGHT / 2f + 16)

        // draw start text
        RogueGame.font.draw(RogueGame.batch,
                startLayout,
                RogueGame.VIEW_WIDTH / 2 - startLayout.width / 2,
                32f)

        RogueGame.batch.end()
    }

    /**
     * Resize viewport and letterbox
     */
    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
    }
}