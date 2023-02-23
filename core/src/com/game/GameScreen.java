package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;

public class GameScreen implements Screen, ContactListener {

    // Screen sizes
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int PPM = 100;
    public static final int INCREMENT_SCORE = 30;

    // TO DETECT COLLISIONS
    public static final short CHARACTER = 1;
    public static final short COIN = 2;
    public static final short GROUND = 3;
    public static final short COLUMN = 4;
    public static final short LOW_CEILING = 5;
    public static final short DESTROYED = 6;

    // Properties
    private final GameMain gameMain;
    private final World world;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Array<Sprite> backgrounds;
    private final Sprite sky;
    private final Texture cityTexture;
    private final Character character;
    private final Gorilla gorilla;
    private final ArrayList<ComplexGround> map;
    private final ArrayList <Coin> coins;
    private final ArrayList<Column> columns;
    private final ArrayList<LowCeiling> ceilings;
    private int mapLength;
    private final Label scoreLabel;
    private final Label coinLabel;
    private int gameScore;
    private int coinAmount;
    private static boolean isGameEnd;
    private final OrthographicCamera debugCamera;
    private final Box2DDebugRenderer debugRenderer;
    private final Stage stage;

    // Timer properties
    protected float backgroundSlidingVar;
    private float timeSecond;
    private float timePeriod;
    private final float maxSpeed;
    private float time;

    // Constructor
    public GameScreen(GameMain gameMain) {
        this.gameMain = gameMain;
        this.camera = new OrthographicCamera(WIDTH, HEIGHT);
        this.camera.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
        this.viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        this.backgrounds = new Array<>();
        this.sky = new Sprite(new Texture("city_sky-background.png"));
        this.cityTexture = new Texture("city_background.png");
        this.world = new World(new Vector2(0, -140f), true);
        this.gorilla = new Gorilla(new Texture("Gifs/Gorilla/0.gif"), world, 100, 250);
        this.character = new Character(new Texture("Gifs/0.gif"),world, 200 + (int) gorilla.getWidth(), 250);
        this.backgroundSlidingVar = 3f;
        this.timeSecond = 0f;
        this.time = 0f;
        this.timePeriod = 10f;
        this.maxSpeed = 12f;
        isGameEnd = false;
        this.stage = new Stage(viewport, gameMain.getBatch());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Alien Eclipse.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont font = generator.generateFont(parameter);
        this.gameScore = 0;
        this.coinAmount = 0;
        this.scoreLabel = new Label("Score" + gameScore, new Label.LabelStyle(font, Color.GOLD) );
        this.coinLabel =  new Label("Coins" + coinAmount, new Label.LabelStyle(font, Color.GOLD) );

        Table coinAndScoreTable = new Table();
        coinAndScoreTable.top().right();
        coinAndScoreTable.setFillParent(true);
        coinAndScoreTable.add(scoreLabel);
        coinAndScoreTable.row();

        coinAndScoreTable.add(new Image(new Texture("Coin.png")));
        coinAndScoreTable.add(coinLabel);
        stage.addActor(coinAndScoreTable);

        world.setContactListener(this);
        createBackGrounds();
        map = new ArrayList<>();
        coins = new ArrayList<>();
        columns = new ArrayList<>();
        ceilings = new ArrayList<>();

        mapGenerator();
        distributeCoins();
        distributeColumnsAndCeilings();
        gorilla.relocateGorilla(map.get(0).grounds.get(0));
        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, (float)GameScreen.WIDTH /GameScreen.PPM,
                (float)GameScreen.HEIGHT / GameScreen.PPM);
        debugCamera.position.set(WIDTH/2F, HEIGHT/2F,0 );
        debugRenderer = new Box2DDebugRenderer();
    }// end constructor

    // Methods
    private void update() {
        moveBackgrounds();
        moveMap();

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            if(!Profile.getShield().isActive()){
                Profile.getShield().activate();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            if(!Profile.getWing().isActive()){
                Profile.getWing().activate();
            }
        }

        if(character.getX() < 0 || character.getY() < 0){
            isGameEnd = true;
            if(Profile.getExtraLife().getExtraLifeCount() > 0){
                if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                    System.out.println("Bastı");
                    Profile.getExtraLife().activate();
                    character.moveToTheUp(character.getY() + 10*PPM);
                }
            }
        }

        character.moveCharacter();
        gorilla.imitate(character);
        for (Coin c: coins) {moveGameObject(c);}
        for (Column col: columns) {moveGameObject(col);}
        for (LowCeiling ceiling: ceilings) {moveGameObject(ceiling);}
        for (Coin c: coins) {
            if (c.getFix().getUserData() == "Remove") {
                c.moveToTheEnd(c.getX() + mapLength);
                c.getFix().setUserData("Coin");
            }
        }
        gameScore += INCREMENT_SCORE;
        scoreLabel.setText("Score :" + gameScore);
        if(isGameEnd){
            gameMain.changeScreen(GameMain.GAME_OVER_SCREEN);
        }
    }

    private void moveGameObject (GameObject obj) {
        obj.update(new Vector2(-3,0), GameScreen.PPM);
        if (obj.getX() < -obj.getWidth())
            obj.moveToTheEnd(mapLength + obj.getX());
    }

    public void mapGenerator() {
        mapLength = 0;
        for (int i = 0; i < 100; i++ ) {
            ComplexGround complex;
            if (i == 0) {
                complex = new ComplexGround(mapLength, world, 6);
            }
            else {
                int y = 3 + (int) (3 * Math.random());
                complex = new ComplexGround(mapLength, world, y);
            }
            map.add(complex);
            mapLength = mapLength + complex.getWidth() + (int) complex.grounds.get(0).getWidth();
        }
    }

    public void distributeCoins() {
        for (ComplexGround complexGround : map) {
            for (int j = 0; j < complexGround.grounds.size(); j++) {
                for (int k = 0; k < 5; k++) {
                    float y = complexGround.grounds.get(j).getY();
                    float height = complexGround.grounds.get(j).getHeight();
                    int z1 = (int) (y + height) + (int) (4 * Math.random()) * Ground.HEIGHT_DIFFERENCE;
                    int z2 = (int) (complexGround.grounds.get(0).getWidth() * Math.random() + complexGround.grounds.get(j).getX());
                    Coin coin = new Coin(new Texture("Coin.png"), world, z2, z1);
                    coins.add(coin);
                }
            }
        }
    }

    public void builtColumnOrCeiling(ComplexGround complexGround, Texture ceilingText, Texture columnText, int groundChooser) {
        int columnOrCeiling = (int) ((Math.random() * 2));
        float z2 = complexGround.grounds.get(groundChooser).getX() + (float) (Math.random() * complexGround.grounds.get(groundChooser).getWidth());
        float yPosition = complexGround.grounds.get(groundChooser).getHeight() + complexGround.grounds.get(groundChooser).getY();
        if (groundChooser == 0 || groundChooser == complexGround.grounds.size() - 1) {
            if (columnOrCeiling == 1) {
                if (complexGround.grounds.get(groundChooser).getX() + complexGround.grounds.get(groundChooser).getWidth() - z2 < columnText.getWidth())
                    z2 = z2 - columnText.getWidth();
                Column column = new Column(columnText, world, (int) z2, (int) yPosition);
                columns.add(column);
            }
        } else {
            if (columnOrCeiling == 0) {
                if (complexGround.grounds.get(groundChooser).getX() + complexGround.grounds.get(groundChooser).getWidth() - z2 < columnText.getWidth())
                    z2 = z2 - columnText.getWidth();
                Column column = new Column(columnText, world, (int) z2, (int) yPosition);
                columns.add(column);
            } else {
                if (complexGround.grounds.get(groundChooser).getX() + complexGround.grounds.get(groundChooser).getWidth() - z2 < ceilingText.getWidth())
                    z2 = z2 - ceilingText.getWidth();
                yPosition = yPosition + character.getHeight();
                LowCeiling ceiling = new LowCeiling(ceilingText, world, (int) z2, (int) yPosition);
                ceilings.add(ceiling);
            }
        }
    }

    public void distributeColumnsAndCeilings() {
        int l= 0;
        for (ComplexGround complexGround : map) {
            int k = complexGround.grounds.size();
            if ( l > 2) {
                Texture ceilingText = new Texture("lowCeiling.png");
                Texture columnText = new Texture("Column.png");


                if (k == 3) {
                    int groundChooser = (int) (Math.random() * k);
                    builtColumnOrCeiling(complexGround, ceilingText, columnText, groundChooser);
                }

                if (k == 4) {
                    int chooser1 = (int) (Math.random() * k);
                    int chooser2;
                    do {
                        chooser2 = (int) (Math.random() * k);
                    }
                    while (Math.abs(chooser1 - chooser2) < 2);

                    builtColumnOrCeiling(complexGround, ceilingText, columnText, chooser1);
                    builtColumnOrCeiling(complexGround, ceilingText, columnText, chooser2);
                }

                if (k == 5 || k == 6) {

                    int chooser1;
                    int chooser2;
                    int chooser3;
                    do {
                        chooser1 = (int) (Math.random() * k);
                        chooser2 = (int) (Math.random() * k);
                        chooser3 = (int) (Math.random() * k);
                    }
                    while (Math.abs(chooser1 - chooser3) < 2 || Math.abs(chooser2 - chooser3) < 2 || Math.abs(chooser1 - chooser2) < 2);

                    builtColumnOrCeiling(complexGround, ceilingText, columnText, chooser1);
                    builtColumnOrCeiling(complexGround, ceilingText, columnText, chooser2);
                    builtColumnOrCeiling(complexGround, ceilingText, columnText, chooser3);
                }
            }
            l++;
        }
    }

    public void moveMap() {
        for (ComplexGround complex : map) {
            complex.moveComplexGround();
            if (complex.grounds.get(0).getX() < -complex.getWidth())
                complex.moveToTheEnd(mapLength + complex.grounds.get(0).getX());
        }
    }

    public void drawMap() {
        for (ComplexGround complex : map) {
            complex.drawComplexGround(gameMain.getBatch());
        }
        for (Coin c: coins) {
            c.draw(gameMain.getBatch());
        }
        for (Column col: columns) {
            col.draw(gameMain.getBatch());
        }
        for (LowCeiling ceiling: ceilings) {
            ceiling.draw(gameMain.getBatch());
        }
    }

    private void createBackGrounds() {
        for (int i = 0; i < 3; i++) {
            Sprite city = new Sprite(cityTexture);
            city.setPosition(i * city.getWidth(), 0);
            backgrounds.add(city);
        }
    }

    private void moveBackgrounds() {
        for (Sprite s : backgrounds) {
            float x1 = s.getX() - backgroundSlidingVar;
            s.setPosition(x1, s.getY());
            if (s.getX() + WIDTH + (s.getWidth() / 2f) < camera.position.x) {
                float x2 = s.getX() + s.getWidth() * 3;
                s.setPosition(x2, s.getY());
            }
        }
    }

    private void drawBackgrounds(SpriteBatch batch) {
        batch.draw(sky, 0, 0);
        for (Sprite s : backgrounds) {
            batch.draw(s, s.getX(), s.getY());
        }
    }

    /**
     * increaseSlidingSpeedByTime(): void
     * In a certain period, the speed of the sliding background is increased 0.5f
     * The speed increases up until the maximum value defined, which is 16f
     * When the initial speed reaches the maximum value, the counter is stopped
     * Speed remains at the maximum value for the rest of the game
     */
    private void increaseSlidingSpeedByTime() {
        timeSecond += Gdx.graphics.getDeltaTime();
        if (timeSecond > timePeriod) {
            timeSecond -= timePeriod;
            if (backgroundSlidingVar < maxSpeed) backgroundSlidingVar += 0.5f;
            else timePeriod = 0f;
        }
    }

    private void animateCharacterTimer() {
        float period = 0.09f;
        time += Gdx.graphics.getDeltaTime();
        if (time > period) {
            character.animate();
            gorilla.animate();
            time -= period;
        }
    }

    // Methods implemented from Screen
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update();
        animateCharacterTimer();
        ScreenUtils.clear(1, 0, 0, 1);
        gameMain.getBatch().begin();
        drawBackgrounds(gameMain.getBatch());
        increaseSlidingSpeedByTime();
        character.drawCharacter(gameMain.getBatch());
        gorilla.drawGorilla(gameMain.getBatch());
        drawMap();
        gameMain.getBatch().end();
        //debugRenderer.render(world, debugCamera.combined);
        gameMain.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    public static boolean isGameEnd() {
        return isGameEnd;
    }

    public void setIsGameEnd(boolean set) {
        character.setPosition(230, character.getY());
        isGameEnd = set;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        this.stage.dispose();
        this.gameMain.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fix1, fix2;
        if ( contact.getFixtureA().getUserData() == "Character" || contact.getFixtureB().getUserData() == "Character" ) {
            if (contact.getFixtureA().getUserData() == "Character") {
                fix1 = contact.getFixtureA();
                fix2 = contact.getFixtureB();
            } else {
                fix1 = contact.getFixtureB();
                fix2 = contact.getFixtureA();
            }
            if (fix1.getUserData() == "Character" && fix2.getUserData() == "Coin") {
                System.out.println("Coin is collected");
                fix2.setUserData("Remove");
                coinAmount++;
                coinLabel.setText(" : " + coinAmount);
            }
            if (fix1.getUserData() == "Character" && ((fix2.getUserData() == "Column") || (fix2.getUserData() == "Low Ceiling"))) {
                if (Profile.getShield().isActive()) {
                    fix2.setSensor(true);
                }
            }
            if (fix1.getUserData() == "Character" && fix2.getUserData() == "Ground") {
                System.out.println("On the ground");
                fix2.setUserData("TouchedGround");
                for (ComplexGround complex : map) {
                    for (Ground ground : complex.grounds) {
                        if (ground.getFix().getUserData() == "TouchedGround") {
                            if (ground.getY() + ground.getHeight() - ground.getHeight() / 2 <= character.getY()) {
                                character.setAbleToJump(true);
                            }
                            ground.getFix().setUserData("Ground");
                        }
                    }
                }
            }
            if ((fix1.getUserData() == "Character" && fix2.getUserData() == "Gorilla")) {
                System.out.println("Gorilla caught!");
                isGameEnd = true;
            }
        }
        else {
            if (contact.getFixtureA().getUserData() == "Gorilla") {
                fix1 = contact.getFixtureA();
                fix2 = contact.getFixtureB();
            } else {
                fix1 = contact.getFixtureB();
                fix2 = contact.getFixtureA();
            }
            if (fix1.getUserData() == "Gorilla" && ((fix2.getUserData() == "Column") || (fix2.getUserData() == "Low Ceiling")) ) {
                fix2.setSensor(true);
                System.out.println("Gorilla hit the obstacle");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}// end class
