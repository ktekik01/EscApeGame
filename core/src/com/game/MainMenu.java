package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu implements Screen{

	private GameMain parent;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private Stage stage;
	private ImageButton singlePlayerButton, multiplayerButton,
	profilesButton, settingsButton, quitButton;
	private BitmapFont whiteFont;
	private LabelStyle heading;
	private Texture background, escape;

	public MainMenu(GameMain gameMain) {
		
		setParent(gameMain);
		stage = new Stage(new ScreenViewport());
		
		//adding processor to process inputs to stage
		Gdx.input.setInputProcessor(stage);
		background = new Texture("CB-5.jpg");
		//performing actions of stage
		//stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		table = new Table();
		//table.debug();
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		parent.getBatch().begin();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		
		//Table.drawDebug(stage);
		
		//stage.act(delta);
		parent.getBatch().draw(background,0,0);
		parent.getBatch().end();
		stage.draw();

	}

	@Override
	public void show() {
		
		//importing font
		//whiteFont = new BitmapFont(Gdx.files.internal("assets/whiteTxt.fnt"), false);

		//Gdx.files.internal("UnitGround.png");
		//atlas = new TextureAtlas(Gdx.files.internal("test.pack"));
		//atlas = new TextureAtlas(Gdx.files.internal("test-me!/output/test-me!.pack"));
		//creating skin
		//skin = new Skin(atlas);
		//skin.add(whiteFont);
			
		//creating new table object

		//table.setFillParent(true);//?
		//table.setDebug(true);//?
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//creating Text Buttons
		//TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
		/*
		singlePlayerButton = new TextButton("SinglePlayer", skin);
		multiplayerButton = new TextButton("Multiplayer", skin);
		profilesButton = new TextButton("Profiles", skin);
		settingsButton = new TextButton("Settings", skin);
		quitButton = new TextButton("QUIT", skin);

		 */
		singlePlayerButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("MainMenuButtons/SinglePlayerButton.png"))));
		//singlePlayerButton.pressedOffsetX() = 1;
		multiplayerButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("MainMenuButtons/MultiplayerButton.png"))));

		quitButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("MainMenuButtons/QuitButton.png"))));
		settingsButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("MainMenuButtons/SettingsButton.png"))));
		//setting pixels and positions of buttons
		/**singlePlayerButton.pad(20);
		singlePlayerButton.setPosition(0, 0);
		multiplayerButton.pad(20);
		multiplayerButton.setPosition(0, 0);
		//profilesButton.pad(20);
		//profilesButton.setPosition(0, 0);
		settingsButton.pad(20);
		settingsButton.setPosition(0, 0);
		quitButton.pad(20);
		quitButton.setPosition(0, 0);*/
		
		//adding functionality to the text buttons
		singlePlayerButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e, Actor actor) {
				parent.changeScreen(GameMain.SINGLE_PLAYER_MENU);
			}
		});
		//singlePlayerButton.pressedOffsetX() = 1;
		multiplayerButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e, Actor actor) {
				parent.changeScreen(GameMain.MULTIPLAYER_MENU);
			}
		});
		/*
		profilesButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e, Actor actor) {
				parent.changeScreen(GameMain.PROFILES_MENU);
			}
		});

		 */
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e, Actor actor) {
				parent.changeScreen(GameMain.SETTINGS_MENU);
			}
		});
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e, Actor actor) {
				Gdx.app.exit();
			}
		});

		//adding buttons to the table
		table.add(singlePlayerButton);
		table.row().pad(10, 0, 10, 0);//adding some spaces within row
		table.add(multiplayerButton);
		table.row();
		//table.add(profilesButton).fillX().uniformX();
		//table.row();
		table.add(settingsButton);
		table.row();
		table.add(quitButton);
		table.row();
		
		//adding table to stage
		stage.addActor(table);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	@Override
	public void pause() {

	}
	@Override
	public void resume() {

	}
	@Override
	public void hide() {

	}
	@Override
	public void dispose() {
		stage.dispose();
		parent.dispose();
		background.dispose();
		table.reset();

	}

	public GameMain getParent() {
		return parent;
	}

	public void setParent(GameMain parent) {
		this.parent = parent;
	}
}
