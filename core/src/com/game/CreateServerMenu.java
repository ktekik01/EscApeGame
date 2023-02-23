package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CreateServerMenu implements Screen{

	private GameMain parent;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private Stage stage;
	private ImageButton createServerButton, startGameButton;
	private BitmapFont whiteFont;
	private LabelStyle heading;
	private Texture background;

	public CreateServerMenu(GameMain gameMain) {

		setParent(gameMain);
		stage = new Stage(new ScreenViewport());

		//adding processor to process inputs to stage
		Gdx.input.setInputProcessor(stage);
		//background = new Texture("MenuBackground.png");
		//performing actions of stage
		background = new Texture("CB-5.jpg");
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));//?
		stage.draw();
		table = new Table();
	}
	@Override
	public void show() {

		//importing font
		//whiteFont = new BitmapFont(Gdx.files.internal("assets/whiteTxt.fnt"), false);

		//atlas = new TextureAtlas(Gdx.files.internal("badlogic.jpg"));
		//creating skin
		//skin = new Skin(atlas);

		//skin.add(whiteFont);

		//creating new table object

		table.setFillParent(true);//?
		//table.setDebug(true);//?
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//creating the buttons
		createServerButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("CreateServer/create.png"))));
		startGameButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("CreateServer/create.png"))));
		//adding functionality to buttons
		createServerButton.addListener(new ClickListener() {
				//TODO creating server with this button

		});
		startGameButton.addListener(new ClickListener() {
			//TODO creating server with this button

		});

		//adding buttons to the table
		table.add(createServerButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);//adding some spaces within row

		//adding table to stage
		stage.addActor(table);

	}
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		parent.getBatch().begin();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		parent.getBatch().draw(background, 0, 0);
		//Table.drawDebug(stage);
		//stage.act(delta);
		//parent.getBatch().draw(background,0,0);
		parent.getBatch().end();
		stage.draw();
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
		//background.dispose();
	}

	public GameMain getParent() {
		return parent;
	}

	public void setParent(GameMain parent) {
		this.parent = parent;
	}

}