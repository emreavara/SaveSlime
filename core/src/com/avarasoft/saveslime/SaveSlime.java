package com.avarasoft.saveslime;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SaveSlime extends ApplicationAdapter {

	SpriteBatch batch;
	Texture slime;
	Texture enemySlime1;
	Texture enemySlime2;
	Texture enemySlime3;
	Texture enemySlime4;
	Texture background;
	Texture burstSlime;
	Random random;

	Circle slimeCircle;
	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;
	Circle[] enemyCircle4;


	// Slime
	float slimeX = 0;
	float slimeY = 0;
	float slimeWidth;
	float slimeHeight;
	float jumpVelocity;

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
	int passedEnemy;

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
	int numberOfCollision = 0;

	Boolean collision1 = false;
	Boolean collision2 = false;
	Boolean collision3 = false;
	Boolean collision4 = false;



	@Override
	public void create () {
		// Initialize
		batch           = new SpriteBatch();

		slime           = new Texture("slime.png");
		background      = new Texture("background.png");
		enemySlime1     = new Texture("enemy.png");
		enemySlime2     = new Texture("enemy.png");
		enemySlime3     = new Texture("enemy.png");
		enemySlime4     = new Texture("enemy.png");
		burstSlime      = new Texture("burst.png");
		slimeCircle     = new Circle();
		enemyCircle1    = new Circle[numberOfEnemies];
		enemyCircle2    = new Circle[numberOfEnemies];
		enemyCircle3    = new Circle[numberOfEnemies];
		enemyCircle4    = new Circle[numberOfEnemies];

		random          = new Random();


		// Screen
		screenWidth     = Gdx.graphics.getWidth();
		screenHeight    = Gdx.graphics.getHeight();

		// Game
		gameState = GameState.idle;

		gameOverText    = new BitmapFont();
		gameOverText.setColor(Color.WHITE);
		gameOverText.getData().setScale(5);

		scoreText       = new BitmapFont();
		scoreText.setColor(Color.WHITE);
		scoreText.getData().setScale(4);

		initializeVariables();

	}

	public void initializeVariables(){
		// Slime
		slimeX          = screenWidth/5;
		slimeY          = screenHeight/2;
		slimeWidth      = screenWidth/10;
		slimeHeight     = screenHeight/5;
		jumpVelocity    = -8;

		// Enemy
		distanceBetweenEnemies = screenWidth/2;
		enemySlimeWidth = 2*slimeWidth/5;
		enemySlimeHeight = 2*slimeHeight/5;
		passedEnemy = 0;

		for(int i = 0; i<numberOfEnemies; i++){
			enemyX[i] = screenWidth + i*distanceBetweenEnemies;

			enemyOffset1[i] = repositionEnemiesOnYAxis();
			enemyOffset2[i] = repositionEnemiesOnYAxis();
			enemyOffset3[i] = repositionEnemiesOnYAxis();
			enemyOffset4[i] = repositionEnemiesOnYAxis();

			enemyCircle1[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
			enemyCircle4[i] = new Circle();
		}



		// Game
		score              = 0;
		slimeVelocity      = 8;
		enemySlimeVelocity = 3;
		numberOfCollision  = 0;

	}
	public float repositionEnemiesOnYAxis(){
		return slimeHeight + (random.nextFloat()) * (screenHeight/2);
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, screenWidth, screenHeight);
		scoreText.draw(batch, "Score : " + score, screenWidth - 350, 100);

		switch (gameState){
			case idle:
				batch.draw(slime, slimeX, slimeY, slimeWidth, slimeHeight);
				if(Gdx.input.justTouched()){
					gameState = GameState.playing;
				}
				break;
			case playing:
				batch.draw(slime, slimeX, slimeY, slimeWidth, slimeHeight);
				if(enemyX[passedEnemy] < (slimeX - (slimeWidth/2))){
					score++;
					collision1 = false;
					collision2 = false;
					collision3 = false;
					collision4 = false;
					if(passedEnemy < (numberOfEnemies -1)){
						passedEnemy++;
					} else {
						passedEnemy = 0;
					}

				}

				for(int i = 0; i<numberOfEnemies; i++){
					if(enemyX[i] < -enemySlimeWidth){
						enemyX[i]       = numberOfEnemies*distanceBetweenEnemies;
						enemyOffset1[i] = repositionEnemiesOnYAxis();
						enemyOffset2[i] = repositionEnemiesOnYAxis();
						enemyOffset3[i] = repositionEnemiesOnYAxis();
						enemyOffset4[i] = repositionEnemiesOnYAxis();
						/*
						enemyOffset1[i] = slimeHeight + (random.nextFloat()) * (screenHeight/2);
						enemyOffset2[i] = slimeHeight + (random.nextFloat()) * (screenHeight/2);
						enemyOffset3[i] = screenHeight/2 + (random.nextFloat()) * (screenHeight/2 - slimeHeight);
						enemyOffset4[i] = screenHeight/2 + (random.nextFloat()) * (screenHeight/2 - slimeHeight);
						 */
					} else {
						enemyX[i] = enemyX[i] - enemySlimeVelocity;
					}
					batch.draw(enemySlime1, enemyX[i], enemyOffset1[i], enemySlimeWidth, enemySlimeHeight);
					batch.draw(enemySlime2, enemyX[i], enemyOffset2[i], enemySlimeWidth, enemySlimeHeight);
					batch.draw(enemySlime3, enemyX[i], enemyOffset3[i], enemySlimeWidth, enemySlimeHeight);
					batch.draw(enemySlime4, enemyX[i], enemyOffset4[i], enemySlimeWidth, enemySlimeHeight);

					enemyCircle1[i] = new Circle(enemyX[i] + enemySlimeWidth/2, enemyOffset1[i] + enemySlimeHeight/2, 2*enemySlimeWidth/5);
					enemyCircle2[i] = new Circle(enemyX[i] + enemySlimeWidth/2, enemyOffset2[i] + enemySlimeHeight/2, 2*enemySlimeWidth/5);
					enemyCircle3[i] = new Circle(enemyX[i] + enemySlimeWidth/2, enemyOffset3[i] + enemySlimeHeight/2, 2*enemySlimeWidth/5);
					enemyCircle4[i] = new Circle(enemyX[i] + enemySlimeWidth/2, enemyOffset4[i] + enemySlimeHeight/2, 2*enemySlimeWidth/5);
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
						slimeVelocity = jumpVelocity;
				}
				break;
			case gameOver:
				batch.draw(burstSlime, slimeX, slimeY, slimeWidth, slimeHeight);
				gameOverText.draw(batch,"Game Over ! \nTap to Play Again ?", screenWidth/3  , screenHeight/2);

				if(Gdx.input.justTouched()){
					initializeVariables();
					gameState = GameState.playing;
				}

				break;
		}

		batch.end();

		slimeCircle = new Circle(slimeX + slimeWidth/2, slimeY + slimeHeight/2, 2*slimeWidth/5);

		for(int i = 0; i<numberOfEnemies; i++){
			if(Intersector.overlaps(slimeCircle, enemyCircle1[i]) && !collision1){
				numberOfCollision++;
				slimeWidth  = slimeWidth + slimeWidth/10;
				slimeHeight = slimeHeight + slimeHeight/10;
				collision1  = true;
			} else if(Intersector.overlaps(slimeCircle, enemyCircle2[i]) && !collision2){
				numberOfCollision++;
				slimeWidth  = slimeWidth + slimeWidth/10;
				slimeHeight = slimeHeight + slimeHeight/10;
				collision2  = true;
			} else if(Intersector.overlaps(slimeCircle, enemyCircle3[i]) && !collision3){
				numberOfCollision++;
				slimeWidth  = slimeWidth + slimeWidth/10;
				slimeHeight = slimeHeight + slimeHeight/10;
				collision3  = true;
			} else if(Intersector.overlaps(slimeCircle, enemyCircle4[i]) && !collision4){
				numberOfCollision++;
				slimeWidth  = slimeWidth + slimeWidth/10;
				slimeHeight = slimeHeight + slimeHeight/10;
				collision4  = true;
			}
		}
		if(numberOfCollision > 3){
			gameState = GameState.gameOver;

		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
