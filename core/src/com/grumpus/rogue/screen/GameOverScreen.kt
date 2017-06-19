package com.grumpus.rogue.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.viewport.FitViewport
import com.grumpus.rogue.RogueGame

/** Simple screen for after player dies */
class GameOverScreen(val game: RogueGame) : ScreenAdapter() {
    // cam and viewport
    val camera = OrthographicCamera()
    val viewport = FitViewport(
            RogueGame.VIEW_WIDTH.toFloat(),
            RogueGame.VIEW_HEIGHT.toFloat(),
            camera
    )

    // TODO: and end-game stats/analytics
    val gameOverLayout = GlyphLayout(RogueGame.font, "GAME OVER")
    val nextLayout = GlyphLayout(RogueGame.font, "Press SPACE to retry, ESC to quit")

    /**
     * Render screen, handle key pressed
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
                gameOverLayout,
                RogueGame.VIEW_WIDTH / 2 - gameOverLayout.width / 2,
                RogueGame.VIEW_HEIGHT / 2f + 16)

        RogueGame.font.draw(RogueGame.batch,
                nextLayout,
                RogueGame.VIEW_WIDTH / 2 - nextLayout.width / 2,
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