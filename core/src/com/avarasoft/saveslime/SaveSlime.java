package com.avarasoft.saveslime;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class SaveSlime extends ApplicationAdapter {

	SpriteBatch batch;
	Texture slime;
	Texture enemySlime1;
	Texture enemySlime2;
	Texture enemySlime3;
	Texture enemySlime4;
	Texture background;
	Random random;

	// Slime
	float slimeX = 0;
	float slimeY = 0;
	float slimeWidth;
	float slimeHeight;

	// Enemy Slime
	int numberOfEnemies = 4;
	float[] enemyX       = new float[numberOfEnemies];
	float[] enemyOffset1 = new float[numberOfEnemies];
	float[] enemyOffset2 = new float[numberOfEnemies];
	float[] enemyOffset3 = new float[numberOfEnemies];
	float[] enemyOffset4 = new float[numberOfEnemies];
	float distanceBetweenEnemies;
	float enemySlimeWidth;
	float enemySlimeHeight;

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
	float enemySlimeVelocity = 0;
	float gravity = 0.2f;
	int score = 0;
	BitmapFont gameOverText;
	BitmapFont scoreText;



	@Override
	public void create () {
		// Initialize
		batch = new SpriteBatch();

		slime           = new Texture("slime.png");
		background      = new Texture("background.png");
		enemySlime1     = new Texture("enemy.png");
		enemySlime2     = new Texture("enemy.png");
		enemySlime3     = new Texture("enemy.png");
		enemySlime4     = new Texture("enemy.png");
		random = new Random();

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




		initializeVariables();
		enemyLoop();

	}

	public void initializeVariables(){
		// Slime
		slimeX          = screenWidth/5;
		slimeY          = screenHeight/2;
		slimeWidth      = screenWidth/10;
		slimeHeight     = screenHeight/5;

		// Enemy
		distanceBetweenEnemies = screenWidth/2;
		enemySlimeWidth = 2*slimeWidth/5;
		enemySlimeHeight = 2*slimeHeight/5;


		// Game
		score = 0;
		slimeVelocity = 8;
		enemySlimeVelocity = 3;

	}
	public void enemyLoop(){
		for(int i = 0; i<numberOfEnemies; i++){
			enemyX[i] = screenWidth + i*distanceBetweenEnemies;
			enemyOffset1[i] =(random.nextFloat()) * (screenHeight/2 - enemySlimeHeight);
			enemyOffset2[i] =(random.nextFloat()) * (screenHeight/2 - enemySlimeHeight);
			enemyOffset3[i] =screenHeight/2 + (random.nextFloat()) * (screenHeight/2 - enemySlimeHeight);
			enemyOffset4[i] =screenHeight/2 + (random.nextFloat()) * (screenHeight/2 - enemySlimeHeight);
		}
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
				for(int i = 0; i<numberOfEnemies; i++){
					if(enemyX[i] < -enemySlimeWidth){
						enemyX[i]= numberOfEnemies*distanceBetweenEnemies;
					} else {
						enemyX[i] = enemyX[i] - enemySlimeVelocity;
					}
					batch.draw(enemySlime1, enemyX[i], enemyOffset1[i], enemySlimeWidth, enemySlimeHeight);
					batch.draw(enemySlime2, enemyX[i], enemyOffset2[i], enemySlimeWidth, enemySlimeHeight);
					batch.draw(enemySlime3, enemyX[i], enemyOffset3[i], enemySlimeWidth, enemySlimeHeight);
					batch.draw(enemySlime4, enemyX[i], enemyOffset4[i], enemySlimeWidth, enemySlimeHeight);
				}
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
					enemyLoop();
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
