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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {

    private GameMain parent;
    private Table table;
    private Stage stage;
    private ImageButton extraLifeButton, restartButton,
            mainMenuButton;
    private Label heading;
    private Texture background;

    public GameOverScreen(GameMain gameMain) {
        setParent(gameMain);
        stage = new Stage(new ScreenViewport());
        //adding processor to process inputs to stage
        Gdx.input.setInputProcessor(stage);
        //background = new Texture("CB-5.jpg");

        //performing actions of stage
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
        table = new Table();
        table.setFillParent(true);//?
       // table.setDebug(true);//?
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //creating the buttons
        mainMenuButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("GameOverScreen/mainMenu.png"))));
        restartButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("GameOverScreen/restart.png"))));
        extraLifeButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("GameOverScreen/useExtraLife.png"))));

        //adding functionality to buttons
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor actor) {
                parent.changeScreen(GameMain.MAIN_MENU);
            }
        });
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor actor) {
                parent.changeScreen(GameMain.GAME_SCREEN);
                //GameScreen.setIsGameEnd(false);
            }
        });
        extraLifeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Profile.getExtraLife().activate();
                
            }
        });

        //adding buttons to the table
        table.add(mainMenuButton).fillX().uniformX();//from the database best
        table.row();
        table.add(restartButton).fillX().uniformX();//from the database best
        table.row();
        table.add(extraLifeButton).fillX().uniformX();//from the database second
        table.row();

        //adding table to stage
        stage.addActor(table);

    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        parent.getBatch().begin();
        //parent.getBatch().draw(background, 0, 0);
        parent.getBatch().end();
        //Table.drawDebug(stage);
        //stage.act(delta);
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
        this.parent = parent;
    }
}