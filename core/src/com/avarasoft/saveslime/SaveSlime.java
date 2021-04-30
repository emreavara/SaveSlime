package com.avarasoft.saveslime;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

	// Enemy Slime
	int numberOfEnemies = 4;
	float[] enemyX       = new float[numberOfEnemies];
	float[] enemyOffset1 = new float[numberOfEnemies];
	float[] EnemyOffset2 = new float[numberOfEnemies];

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
	float slimeVelocity = 8;
	float gravity = 0.2f;
	int score = 0;
	BitmapFont gameOverText;
	BitmapFont scoreText;



	@Override
	public void create () {
		// Initialize
		batch = new SpriteBatch();

		slime           = new Texture("slime.png");
		enemySlime      = new Texture("enemy.png");
		background      = new Texture("background.png");
		enemySlime      = new Texture("enemy.png");

		// Screen
		screenWidth     = Gdx.graphics.getWidth();
		screenHeight    = Gdx.graphics.getHeight();

		// Game
		gameState = GameState.idle;

		gameOverText = new BitmapFont();
		gameOverText.setColor(Color.WHITE);
		gameOverText.getData().setScale(5);

		scoreText = new BitmapFont();
		scoreText.setColor(Color.WHITE);
		scoreText.getData().setScale(4);


		// Enemy

		initializeVariables();
	}

	public void initializeVariables(){
		// Slime
		slimeX          = screenWidth/5;
		slimeY          = screenHeight/2;
		slimeWidth      = screenWidth/10;
		slimeHeight     = screenHeight/5;

		// Enemies

		// Game
		score = 0;
		slimeVelocity = 8;

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, screenWidth, screenHeight);
		scoreText.draw(batch, "Score : " + score, screenWidth - 350, 100);
		switch (gameState){
			case idle:
				if(Gdx.input.justTouched()){
					gameState = GameState.playing;
				}
				break;
			case playing:

				if(slimeY > 0){
					if(slimeY > (screenHeight - slimeHeight)){
						slimeVelocity = 0;
						slimeY = screenHeight - slimeHeight;
					} else {
						slimeVelocity += gravity;
						slimeY = slimeY - slimeVelocity;
					}
				} else {
					gameState = GameState.gameOver;
				}
				if(Gdx.input.justTouched()){
						slimeVelocity = -8;
				}
				break;
			case gameOver:
				gameOverText.draw(batch,"Game Over ! \nTap to Play Again ?", screenWidth/3  , screenHeight/2);
				if(Gdx.input.justTouched()){
					initializeVariables();
					gameState = GameState.playing;
				}

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
