package com.grumpus.rogue.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.viewport.FitViewport
import com.grumpus.rogue.RogueGame

/** Simple "Quit to main menu?" screen */
class PauseScreen(val game: RogueGame, val playScreen: PlayScreen): ScreenAdapter() {
    // cam and viewport
    val camera = OrthographicCamera()
    val viewport = FitViewport(
            RogueGame.VIEW_WIDTH.toFloat(),
            RogueGame.VIEW_HEIGHT.toFloat(),
            camera
    )

    // text layouts
    val quitLayout = GlyphLayout(RogueGame.font, "Quit to main menu?\n\nAll progress will be lost.")
    val keysLayout = GlyphLayout(RogueGame.font, "(Y)es or (N)o")

    /** Render screen and handle key pressed */
    override fun render(delta: Float) {
        // check for any keypresses
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)
                or Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            game.goToScreen(playScreen, true)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            game.goToScreen(TitleScreen(game), false)
        }

        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // update cam
        camera.update()
        RogueGame.batch.projectionMatrix = camera.combined

        RogueGame.batch.begin()

        // draw border
        RogueGame.border.draw(RogueGame.batch, 0f, 0f,
                RogueGame.VIEW_WIDTH.toFloat(),
                RogueGame.VIEW_HEIGHT.toFloat())

        // draw text layouts
        RogueGame.font.draw(RogueGame.batch,
                quitLayout,
                RogueGame.VIEW_WIDTH / 2 - quitLayout.width / 2,
                RogueGame.VIEW_HEIGHT / 2f + 16)

        RogueGame.font.draw(RogueGame.batch,
                keysLayout,
                RogueGame.VIEW_WIDTH / 2 - keysLayout.width / 2,
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