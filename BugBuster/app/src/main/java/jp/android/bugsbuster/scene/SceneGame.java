package jp.android.bugsbuster.scene;

import android.content.Context;
import android.view.MotionEvent;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.android.bugsbuster.*;
import jp.android.bugsbuster.Object;
import jp.android.bugsbuster.game_model.Bomb;
import jp.android.bugsbuster.game_model.Bridge;
import jp.android.bugsbuster.game_model.Enemy;
import jp.android.bugsbuster.game_model.Pool;
import jp.android.bugsbuster.game_model.Stone;
import jp.android.bugsbuster.processing.Animation;
import jp.android.bugsbuster.processing.PImage;
import jp.android.bugsbuster.processing.Text;

public class SceneGame  implements Scene {

    public final static int mapwidth = 15;
    public final static int mapheight = 20;
    public final static int DEADLINE_OFFSET = 1;

    public final static int width = (int) GLESRenderer.SCREEN_WIDTH;
    public final static int height = (int) GLESRenderer.SCREEN_HEIGHT * 836 / 1024;
    public final static int msw = width / mapwidth;
    public final static int msh = height / mapheight;
    public final static int paddingTop = (int) GLESRenderer.SCREEN_HEIGHT * 132 / 1024; //120;//(int)GLESRenderer.SCREEN_HEIGHT*82/640;
    final static int paddingBottom = (int) (GLESRenderer.SCREEN_HEIGHT - height - paddingTop);

    final int Wave=30;
    final int buggType = 8;
    final int MaxLife = 15;
    final int WAVE_ENEMY = 10;
    public final static int obstacleRangeW = 2;
    public final static int obstacleRangeH = 5;

    final int SET_TURN = 0;
    final int ENEMY_TURN = 1;
    final int PAUSE = 2;

    //objeMap
    final int NO_Obje = 0;
    final int NORMAL_Obje = 1;
    final int MOVE_Obje = 2;
    final int BRIDGE_Obje =3;

    final int BombCount = 6;

    int bombCount = 0;


    float damageMap[][] = new float[mapwidth][mapheight];
    int objeMap[][] = new int[mapwidth][mapheight];
    int playerLife = MaxLife;
    int resorceObje=20;
    int score = 0;

    //���̃E�F�[�u��
    int waveCount = 0;
    int enyCount;

    //Frame late
    int reducePoint = 5;

    //�I�u�W�F��I�����Ĉړ������Ă��邩
    boolean nowMove = false;
    //�ړ������錳�̐�
    int moveX = -1;
    int moveY = -1;

    Random rnd = new Random();

    //�`�揇
    PImage game_bg = new PImage(jp.android.bugsbuster.Object.LAYER_0);
    PImage game_header_frame = new PImage(Object.LAYER_3);
    Button play_img = new Button(Object.LAYER_3);
    Button pause_img = new Button(Object.LAYER_3);
    Pool quad_img = new Pool();
    Bridge bridge_img = new Bridge();
    Stone circle_img = new Stone();
    Bomb[] bomb = new Bomb[BombCount];

    //interface TOP
    Text score_txt = new Text(10);
    Text waveCount_txt = new Text(2);
    Text resorceObje_txt = new Text(2);
    PauseMenu pause_menu;

    Enemy[] enemy;
    AppearData appearData = new AppearData();

    //turn's value
    // 0->player
    // 1->enemy attack
    int turn = 0;
    //int counter;

    final int MAX_HP = 3;
    final int MAX_CAKE = 5;
    Animation[] cake = new Animation[MAX_CAKE];
    int[] cakeHP = new int[MAX_CAKE];

    //obstacle AREA set
    Obstacle obstacleSet = new Obstacle();

    Text mWord;
    int a;
    int aDir;
    int counter;

    public void init() {
        //����
        a = 255;
        aDir = -1;
        turn = SET_TURN;
        playerLife = MaxLife;
        resorceObje = 20;
        waveCount = 0;
        nowMove = false;
        counter = 30;
        bombCount = 0;
        for (int i = 0; i < mapwidth; i++) {
            for (int j = 0; j < mapheight; j++) {
                damageMap[i][j] = 0;
                objeMap[i][j] = 0;
            }
        }
        mWord = new Text(32);
        for (int i = 0; i < BombCount; i++) {
            bomb[i] = new Bomb();
        }

        Enemy.initEnemyPattern();

        SceneResult.setResult(false, score);

        for (int i = 0; i < MAX_CAKE; i++) {
            cake[i] = new Animation(Object.LAYER_3);
            cakeHP[i] = MAX_HP;
            cake[i].init(((int) (GLESRenderer.SCREEN_WIDTH) - (int) (48 * (MAX_CAKE + 0.5 - (i)))),
                    (int) (GLESRenderer.SCREEN_HEIGHT - paddingBottom / 2),//(int)(GLESRenderer.SCREEN_HEIGHT-(msh*3.5)),
                    48,
                    48,
                    4, 4, 16, 0);

            cake[i].play(0, 1, false);
        }
        pause_menu = new PauseMenu();

        enemy = new Enemy[WAVE_ENEMY];
        for (int i = 0; i < WAVE_ENEMY; i++) {
            enemy[i] = new Enemy();
        }

        pause_menu.init();
        game_bg.init((int) GLESRenderer.SCREEN_WIDTH / 2,(int) GLESRenderer.SCREEN_HEIGHT / 2,
                (int) GLESRenderer.SCREEN_WIDTH, (int) GLESRenderer.SCREEN_HEIGHT);
        game_header_frame.init((int) GLESRenderer.SCREEN_WIDTH / 2, (int) GLESRenderer.SCREEN_HEIGHT / 2,
                (int) GLESRenderer.SCREEN_WIDTH, (int) GLESRenderer.SCREEN_HEIGHT);

        play_img.init((int) (48 * 1.5f), (int) (GLESRenderer.SCREEN_HEIGHT - paddingBottom / 2), 48, 48);
        play_img.setIdleColor(255, 255, 255, 255);
        pause_img.init((int) (48 * 1.5f), (int) (GLESRenderer.SCREEN_HEIGHT - paddingBottom / 2), 48, 48);
        pause_img.setIdleColor(255, 255, 255, 255);
        pause_img.setShow(false);
        pause_img.setUse(false);

        circle_img.init();
        bridge_img.init();
        quad_img.init();

        SoundManager.playBGM(SoundManager.SOUND_ID_BGM_GAME);
    }

    public void update() {
        if (turn == PAUSE) {
            pause_menu.update();
        }

        if (Input.isMouseTrigger()) {
            mouseCalled();
        }

        if ((turn == ENEMY_TURN) && (enyCount <= 0)) {
            waveCount++;
            resorceObje++;
            turn = SET_TURN;
            //player win
            if (waveCount == Wave) {
                SceneResult.setResult(true, score);
                SceneManger.changeMode(SceneManger.MODE_RESULT);
            }
        } else if (playerLife <= 0) {
            //game over
            SceneResult.setResult(false, score);
            SceneManger.changeMode(SceneManger.MODE_RESULT);
        }

        //not pause
        if (turn != PAUSE) {
            //map and object draw
            //loadMap();	//grid
            loadObject();
            // enemy update
            if (turn == 1) {
                //ENEMY
                loadEnemy();
            }
            //interface update(life and map part draw)
            loadMapAfter();
        }
    }


    @Override
    public void uninit() {

    }

    @Override
    public void updateInput(MotionEvent event) {

    }

    @Override
    public void draw(GL10 gl) {

    }

    @Override
    public void loadTexture(GL10 gl, Context context) {
        pause_menu.loadTexture(gl, context);
        game_bg.loadTexture(gl, context, R.drawable.game_bg);
        game_header_frame.loadTexture(gl, context, R.drawable.game_frame);
        play_img.loadTexture(gl, context, R.drawable.play);
        pause_img.loadTexture(gl, context, R.drawable.pause);
        bridge_img.loadTexture(gl, context, R.drawable.wood);
        Bomb.loadBombTexture(gl, context);

        for (int i = 0; i < MAX_CAKE; i++) {
            cake[i].loadTexture(gl, context, R.drawable.cupcake);
        }

        Enemy.loadEnemyPattern(gl, context);

        bridge_img.loadTexture(gl, context);
        circle_img.loadTexture(gl, context);

        for (int i = 0; i < WAVE_ENEMY; i++) {
            enemy[i].dieEffect.loadTexture(gl, context, R.drawable.water_fx);
        }
    }


    @Override
    public void load() {
    }

    @Override
    public void release() {
    }

    /*Call method when user mouse pressing*/
    void mouseCalled() {
        int cx = convertX((int) Input.getTouchX());
        int cy = convertY((int) Input.getTouchY());

        boolean flg = true;
        if (turn == ENEMY_TURN) {
            for (int i = 0; i < BombCount; i++) {
                if (bomb[i].getAppear()) {
                    //isHit()
                    if (bomb[i].getX() - 1 <= cx && bomb[i].getX() + 1 >= cx && bomb[i].getY() - 1 <= cy && bomb[i].getY() + 1 >= cy) {
                        flg = false;
                        bomb[i].setExplosion(true);
                    }
                }
            }
        }
        if (flg) {
            if (turn != PAUSE && cx >= 0 && cy >= 0 && cx < mapwidth && cy < mapheight) {
                manageObject(cx, cy);
            }
            //wave start
            if (turn == SET_TURN) {
                if (play_img.isHit((int) Input.getTouchX(), (int) Input.getTouchY())) {
                    SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
                    turn = ENEMY_TURN;
                    makeEnemy();

                    //ADD BRIDGE
                    if (waveCount % (Wave / 4) == 5) {
                        //5
                        dropObstacleObje();
                    }

                    //ADD BOMB
                    //if(waveCount%5==4)
                    if (waveCount < 6) {
                        //4 9 14 19 24 29
                        int posi = (int) (rnd.nextInt(WAVE_ENEMY));
                        while (!enemy[posi].isStart()) {
                            posi++;
                            posi = posi % WAVE_ENEMY;
                        }

                        //enemy[posi]
                        //bomb�̍쐬
                        enemy[posi].setBomb(bombCount);
                        bomb[bombCount].setGridPosition(enemy[posi].getX(), enemy[posi].getY());
                        bomb[bombCount].setAppear(true);
                        bombCount++;
                    }
                }
            } else if (turn == ENEMY_TURN) {
                //pause
                if (pause_img.isHit((int) Input.getTouchX(), (int) Input.getTouchY())) {
                    SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
                    turn = PAUSE;
                    pause_menu.show();
                    setPause(true);
                }
            } else if (turn == PAUSE) {
                if (pause_menu.getPressButton(PauseMenu.CONTINUE_BUTTON)) {
                    //continue
                    pause_menu.hide();
                    setPause(false);
                    SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
                    turn = ENEMY_TURN;
                } else if (pause_menu.getPressButton(PauseMenu.TITLE_BUTTON)) {
                    //title
                    SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
                    turn = SET_TURN;
                    SceneManger.changeMode(SceneManger.MODE_TITLE);
                }
            }
        }
    }

    void loadMapAfter() {
        drawHeader();
        drawFooter();
    }

    boolean rangeX(int v) {
        return v >= 0 && v < mapwidth;
    }

    boolean rangeY(int v) {
        return v >= 0 && v < mapheight;
    }

    /*Convert mouse -> grid*/
    int convertX(int x) {
        int cx = x / msw;
        return cx;
    }

    int convertY(int y) {
        int cy = (y - paddingTop) / msh;
        return cy;
    }

    // to count enemy in range
    int enemyCountRange(int minX, int maxX, int minY, int maxY) {
        if (turn == SET_TURN || enyCount <= 0) {
            return 1;
        } else if (turn == ENEMY_TURN) {
            int enyRange = 0;
            for (int e = 0; e < WAVE_ENEMY; e++) {
                if (enemy[e].getX() >= minX && enemy[e].getX() <= maxX) {
                    if (enemy[e].getY() >= minY && enemy[e].getY() <= maxY) {
                        enyRange++;
                    }
                }
            }
            return (1 > enyRange ? 1 : enyRange);
        }
        return 1;
    }

    void dmgMapCreate(int x[], int y[]) {
        int minvX = x[0], maxvX = x[0];
        int minvY = y[0], maxvY = y[0];
        for (int i = 1; i < 4; i++) {
            minvX = (minvX < x[i] ? minvX : x[i]);
            maxvX = (maxvX > x[i] ? maxvX : x[i]);
            minvY = (minvY < y[i] ? minvY : y[i]);
            maxvY = (maxvY > y[i] ? maxvY : y[i]);
        }
        int dx = maxvX - minvX;
        int enyRange = enemyCountRange(minvX, maxvX, minvY, maxvY);
        for (int mx = minvX; mx <= maxvX; mx++) {
            for (int my = minvY; my <= maxvY; my++) {
                float dmg = (60 / dx / enyRange);
                damageMap[mx][my] = damageMap[mx][my] + (1 > dmg ? 1 : dmg);
            }
        }
    }

    void obstacleObjeManage(int ox, int oy) {
        for (int jx = ox; jx <= ox + obstacleRangeW; jx++) {
            for (int jy = oy - obstacleRangeH; jy <= oy; jy++) {
                damageMap[ox][oy] = 0;
            }
        }
    }

    void dropObstacleObje() {
        int bigEria = (int) (rnd.nextInt(4));
        while (!obstacleSet.objeAdd(bigEria) && obstacleSet.getSize() < 4) {
            bigEria = (int) (rnd.nextInt(4));
        }
        int smallEriaX = (int) (rnd.nextInt(((mapwidth - obstacleRangeW * 2) / 2)));
        int smallEriaY = (int) (rnd.nextInt(((mapheight - obstacleRangeH * 2) / 2)));
        //left up
        if (bigEria == 0) {
            objeMap[obstacleRangeW / 2 + smallEriaX][obstacleRangeH / 2 + smallEriaY] = BRIDGE_Obje;
        }
        //right up
        else if (bigEria == 1) {
            objeMap[mapwidth / 2 + smallEriaX][obstacleRangeH / 2 + smallEriaY] = BRIDGE_Obje;
        }
        //left bottom
        else if (bigEria == 2) {
            objeMap[obstacleRangeW / 2 + smallEriaX][mapheight / 2 + smallEriaY] = BRIDGE_Obje;
        }
        //right bottom
        else if (bigEria == 3) {
            objeMap[mapwidth / 2 + smallEriaX][mapheight / 2 + smallEriaY] = BRIDGE_Obje;
        }
    }

    //waveCount�̂͂��߂ɓG�����N���X
    void makeEnemy() {
        int[] typeData = appearData.getApearData(waveCount);
        int position;
        int type;
        enyCount = WAVE_ENEMY;
        for (int i = 0; i < WAVE_ENEMY; i++) {
            type = typeData[i];
            if (type == 4) {
                position = (int) (rnd.nextInt(3)) * 3 + 4;
            } else {
                position = (int) (rnd.nextInt(5)) * 3 + 1;
            }

            if (type == -1) {
                enemy[i].setType(0, position, i * (-1));
                enemy[i].setdieFlg();
                enyCount--;
            } else {
                enemy[i].setType(type, position, i * (-1));
            }
        }
    }


    /*Obje management*/
    void manageObject(int cx, int cy) {
        if ((objeMap[cx][cy] == 0 || objeMap[cx][cy] == 2) && resorceObje > 0) {
            SoundManager.playSound(SoundManager.SOUND_ID_SFX_PUT);
            objeMap[cx][cy] = NORMAL_Obje;
            resorceObje--;
            if (nowMove) {
                if (moveX == cx && moveY == cy) {

                } else {
                    score = (score - reducePoint > 0 ? score - reducePoint : 0);
                    objeMap[moveX][moveY] = NO_Obje;
                }
                nowMove = false;
                moveX = -1;
                moveY = -1;
            }
            loadObject();
        } else if (objeMap[cx][cy] == NORMAL_Obje && !nowMove) {
            SoundManager.playSound(SoundManager.SOUND_ID_SFX_PICK);
            nowMove = true;
            moveX = cx;
            moveY = cy;
            objeMap[cx][cy] = MOVE_Obje;

            resorceObje++;
            loadObject();
        }
    }

    //before loop damage map initialize
    void initLoop() {
        for (int i = 0; i < mapwidth; i++) {
            for (int j = 0; j < mapheight; j++) {
                damageMap[i][j] = 0;
            }
        }
    }

    void loadObject() {
        circle_img.clearPosition();
        bridge_img.clearPosition();
        quad_img.clearPosition();

        initLoop();
        judgeSquear();

        for (int i = 0; i < mapwidth; i++) {
            for (int j = 0; j < mapheight; j++) {
                if (objeMap[i][j] == NORMAL_Obje) {
                    drawObje(i, j);
                } else if (objeMap[i][j] == MOVE_Obje) {
                    moveObje(i, j);
                } else if (objeMap[i][j] == BRIDGE_Obje) {
                    obstacleObjeManage(i, j);
                    drawObstacleObje(i, j);
                }
            }
        }
    }

    void loadEnemy() {
        if (turn != ENEMY_TURN) {
            return;
        }
        for (int i = 0; i < WAVE_ENEMY; i++) {
            if (enemy[i].getArrivalFlg()) {
                continue;
            }
            if (enemy[i].getHP() > 0) {
                if (rangeX((int) enemy[i].getX()) && rangeY((int) enemy[i].getY())) {
                    //�G�Ƀ_���[�W��^����
                    enemy[i].updHP(damageMap[(int) enemy[i].getX()][(int) enemy[i].getY()]);

                    //�G�̎��S
                    if (enemy[i].getHP() <= 0) {
                        SoundManager.playSound(SoundManager.SOUND_ID_SFX_DEAD);
                        enemy[i].setdieFlg();
                        enemy[i].drawDieStart((int) enemy[i].getPosX(), (int) enemy[i].getPosY());
                        enyCount--;
                        if (enemy[i].getBomb() != -1) {
                            bomb[enemy[i].getBomb()].setEnemyDie(true);
                        }
                        SoundManager.playSound(SoundManager.SOUND_ID_SFX_SCORE);
                        score += enemy[i].getScore();
                    }
                }
                //zibunn ga damage wo kurau
                if (enemy[i].getY() >= mapheight + DEADLINE_OFFSET && !enemy[i].getdieFlg()) {
                    enemy[i].setArrivalFlg(true);
                    if (enemy[i].getBomb() != -1) {
                        bomb[enemy[i].getBomb()].setEnemyDie(true);
                        bomb[enemy[i].getBomb()].setAppear(false);
                    }
                    enemy[i].setdieFlg();
                    enyCount--;
                    SoundManager.playSound(SoundManager.SOUND_ID_SFX_EAT);
                    damage();
                }
            }
        }
    }

    /*Judge this four point is square*/
    void judgeSquear() {
        boolean flg = false;
        int dx, dy;
        int px[] = new int[4];
        int py[] = new int[4];
        for (int y = 0; y < mapheight; y++) {
            for (int x = 0; x < mapwidth; x++) {
                if (objeMap[x][y] == NORMAL_Obje) {
                    for (int sy = y; sy < mapheight; sy++) {
                        for (int sx = x; sx < mapwidth; sx++) {
                            if (sx == x && sy == y) continue;
                            if (objeMap[sx][sy] == NORMAL_Obje) {
                                dx = sx - x;
                                dy = sy - y;
                                if (dx > 0 &&
                                        rangeX(x - dy) && rangeY(y + dx) && objeMap[x - dy][y + dx] == NORMAL_Obje &&
                                        rangeX(sx - dy) && rangeY(sy + dx) && objeMap[sx - dy][sy + dx] == NORMAL_Obje) {
                                    px[0] = x;
                                    py[0] = y;
                                    px[1] = sx;
                                    py[1] = sy;
                                    px[2] = (sx - dy);
                                    py[2] = (sy + dx);
                                    px[3] = (x - dy);
                                    py[3] = (y + dx);
                                    //�_���[�W����p�̃}�b�v���쐬
                                    dmgMapCreate(px, py);
                                    //�l�p�`��`��
                                    drawFreeRect(px, py);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void damage() {
        int num = (int) ((MaxLife - playerLife) / 3);
        if (num >= 5) {
            return;
        }
        int frame = (3 * (MAX_HP - cakeHP[num]));
        if (cake[num].GetFrame() >= frame) {
            if (cakeHP[num] > 0) {
                cake[num].play(frame + 1, 3, false);
            }
        }
        cakeHP[num]--;
        playerLife--;
              /*
		      for(int i=0;i<num;i++)
		      {
		          cake[i].SetFrame((MAX_HP-cakeHP[i])*3);
		      }
		      */
    }


    //header draw
    void drawHeader() {
        //interface TOP
        score_txt.print(String.format("%1$08d", score), 140, 32);
        resorceObje_txt.print("" + resorceObje, width - 160, 32);
        waveCount_txt.print(String.format("%1$02d", waveCount), width - 60, 32);
    }

    //footer draw
    void drawFooter() {
        //interface DOWN
        for (int i = 0; i < MAX_CAKE; i++) {
            if (cake[i].GetFrame() >= (cakeHP[i] + 1) * 3) {
                cake[i].setShow(false);
            } else {
                cake[i].setShow(true);
            }
        }
        if (turn == 0) {
            play_img.setShow(true);
            pause_img.setShow(false);
            play_img.setUse(true);
            pause_img.setUse(false);
        } else {
            play_img.setShow(false);
            pause_img.setShow(true);
            play_img.setUse(false);
            pause_img.setUse(true);
        }
    }

    void drawObje(int x, int y) {
        //����
        circle_img.addPosition(x * msw + msw / 2, y * msh + msh / 2 + paddingTop + 4);
    }

    void moveObje(int x, int y) {
        //����(��)
        circle_img.addMovePosition(x * msw + msw / 2, y * msh + msh / 2 + paddingTop + 4);
    }

    void drawObstacleObje(int x, int y) {
        //bridge
        bridge_img.addPosition((x * msw) + (msw * (int) (obstacleRangeW / 2)), (y * msh) + (msh * (int) (obstacleRangeH)));
    }

    void drawFreeRect(int x[], int y[]) {
        //���F�̎l�p
        for (int i = 0; i < 4; i++) {
            x[i] = x[i] * msw + msw / 2;
            y[i] = y[i] * msh + msh / 2 + paddingTop;
        }
        quad_img.addPosition(x[0], y[0], x[1], y[1], x[2], y[2], x[3], y[3]);
    }


    void setPause(boolean flag) {
        //TODO:: Pause MENU
        for (int i = 0; i < WAVE_ENEMY; i++) {
            enemy[i].setUse(!flag);
        }
        for (int i = 0; i < BombCount; i++) {
            bomb[i].setUse(!flag);
        }
    }
}
		    