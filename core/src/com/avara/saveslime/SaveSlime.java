package com.avara.saveslime;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SaveSlime extends ApplicationAdapter {

	SpriteBatch batch;
	Texture slime;
	Texture enemySlime;
	Texture background;

	// Slime
	float slimeX = 0;
	float slimeY = 0;
	float slimeWidth;
	float slimeHeight;

	// Screen
	int screenWidth;
	int screenHeight;

	// Game
	public enum GameState {
		idle,
		playing,
		gameOver
	}
	GameState gameState;



	@Override
	public void create () {


		// Initialize
		batch = new SpriteBatch();

		slime           = new Texture("slime.png");
		enemySlime      = new Texture("enemy.png");
		background      = new Texture("background.png");

		// Screen
		screenWidth     = Gdx.graphics.getWidth();
		screenHeight    = Gdx.graphics.getHeight();

		// Slime
		slimeX          = screenWidth/4;
		slimeY          = screenHeight/2;
		slimeWidth      = screenWidth/10;
		slimeHeight     = screenHeight/5;

		// Game
		gameState = GameState.idle;

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, screenWidth, screenHeight);

		switch (gameState){
			case idle:
				break;
			case playing:
				break;
			case gameOver:
				break;
		}
		batch.draw(slime, slimeX, slimeY, slimeWidth, slimeHeight);


		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
