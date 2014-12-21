package jp.android.bugsbuster;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Enemy extends PImage {

	Random rnd = new Random();
	public final static int ENEMY_PATTERN_RED_ANT 		= 0;
	public final static int ENEMY_PATTERN_BLACK_ANT 	= 1;
	public final static int ENEMY_PATTERN_SPIDER 		= 2;
	public final static int ENEMY_PATTERN_KABUTO 		= 3;
	public final static int ENEMY_PATTERN_LADY 			= 4;
	public final static int ENEMY_PATTERN_CORCROACH 	= 5;
	public final static int ENEMY_PATTERN_FLY 			= 6;
	public final static int ENEMY_PATTERN_DANGORO 		= 7;
	public final static int MAX_ENEMY_PATTERN 			= 8;
	static Animation[] enemy_pattern;
	Animation dieEffect;

	final int MAX_HP[] = {
		100, 	//ENEMY_PATTERN_RED_ANT 	
		120, 	//ENEMY_PATTERN_BLACK_ANT 
		170, 	//ENEMY_PATTERN_SPIDER 
		250, 	//ENEMY_PATTERN_KABUTO 
		330, 	//ENEMY_PATTERN_LADY
		360, 	//ENEMY_PATTERN_CORCROACH
		280, 	//ENEMY_PATTERN_FLY 	
		3000, 	//ENEMY_PATTERN_DANGORO
	};
			
	static private Texture images = new Texture();

	  private int type;
	  private float hp;
	  private float avg;
	  private int score;
	  private int direction;
	  private int angle;
	  private float x=0,y=0;
	  private boolean dieFlg=false;
	  private boolean starFlg=false;
	  Bar bar = new Bar(100);

	  private boolean arrivalFlg=false;
	  private int dieFrame=0;
	  private int bomb = -1;
	  
	  Enemy()
	  {
		  super(LAYER_1);
		  super.init(0, 0, SceneGame.msw, SceneGame.msh);

		  dieEffect = new Animation(); //8		
		  dieEffect.setLayer(Object.LAYER_2);
		  dieEffect.init(0,0,60,60,4,2,8,7);
		  dieEffect.setPlaySpeed(3);
	  }

  void setType(int type,int x,int y){
	  
    switch(type){
      //red_ant
      case ENEMY_PATTERN_RED_ANT:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=1;
        this.score=10;
        this.x=x;
        this.y=y;
        this.direction=1;
        this.angle=0;
        break;
      //black_ant
      case ENEMY_PATTERN_BLACK_ANT:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=1;
        this.score=30;
        this.x=x;
        this.y=y;
        break;
      //spider
      case ENEMY_PATTERN_SPIDER:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=3;
        this.score=50;
        this.x=x;
        this.y=y;
        break;
      //kabuto
      case ENEMY_PATTERN_KABUTO:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=3;
        this.score=100;
        this.x=x;
        this.y=y;
        break;
      //lady
      case ENEMY_PATTERN_LADY:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=3;
        this.score=200;
        this.x=x;
        this.y=y;
        break;
      //gokiburi
      case ENEMY_PATTERN_CORCROACH:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=3;
        this.score=500;
        this.x=x;
        this.y=y;
        this.direction=1;
        break;
      //fly
      case ENEMY_PATTERN_FLY:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=3;
        this.score=1000;
        this.x=x;
        this.y=y;
        break;
      //dangomushi
      case ENEMY_PATTERN_DANGORO:
        this.type=type;
        this.hp=MAX_HP[type];
        this.avg=1;
        this.score=1000;
        this.x=x;
        this.y=y;
        default:break;
    }
    reset();
    
    bar.resetHP(100);
    bar.update();
    
	starFlg = true;
	setShow(true);
	mPosX = (int)((x+0.5f)*SceneGame.msw);
	mPosY = (int)(y*SceneGame.msh+SceneGame.paddingTop);
  }
  
  public void reset(){
	starFlg = false;

    dieFlg = false;
    dieFrame=0;
    arrivalFlg = false;
    bomb = -1;
  }
  
  
  public void update(){

	   if(mUse && starFlg)  
	   {
		   	  if(hp > 0)
			  {
		   		
				    switch(type){
				      case ENEMY_PATTERN_RED_ANT: 
				        this.y+=avg*0.05f;
				        break;
				      case ENEMY_PATTERN_BLACK_ANT:
				        this.y+=avg*0.08f;
				        break;
				      case ENEMY_PATTERN_SPIDER:
				    	if(y>1.0){
				    		this.x+=(int)(-2+(rnd.nextInt(((2-(-2))+1))))*0.4f;
				    	}
				        this.y+=avg*0.025f;
				        this.x=constrain(this.x, 0, 14);
				        break;
				        //sin orbital
				      case ENEMY_PATTERN_KABUTO:
				        this.y+=0.05;
				        if(y>1.0){
				        	this.x+=Math.sin(radians(angle))*0.2;
				        	angle+=10;
				        }
				        this.x=constrain(this.x, 0, 14);
				        break;
				        //circle orbital
				      case ENEMY_PATTERN_LADY: 

				          if(y>1.0){
						        this.x+=Math.cos(radians(angle))*0.3;
						        this.y+=Math.sin(radians(angle))*0.3+0.05;
						        angle+=10;
				          }else{
				        	  	this.y+=0.05;
				          }
				        break;
				        //naname wall bound
				      case ENEMY_PATTERN_CORCROACH: 
				          this.y+=0.05;
				          if(y>1.0){
				        	  this.x+=avg*direction*0.1;
				          }
				          if((this.x >=14) || (this.x <= 0)) {
				            direction=-direction; 
				          }
				        break;
				        //barabara movement
				      case ENEMY_PATTERN_FLY:
				    	if(y>1.0){
				    		this.x+=(-1+(rnd.nextInt(3)*0.02f));
				    		this.y+=( 1+(rnd.nextInt((1-1+1)))*0.01f)*0.07f;
				    	}else{
				    		this.y+=( 1+(rnd.nextInt((1-1+1)))*0.01f)*0.07f;
				    	}
				        this.x=constrain(x, 0, 14);
				        break;
				      case ENEMY_PATTERN_DANGORO:
				        this.y+=(rnd.nextInt(2))*0.04f;
				        break;
				      default:break;
				     }
				    

					mPosX = (int)((x+0.5f)*SceneGame.msw);
					mPosY = (int)(y*SceneGame.msh+SceneGame.paddingTop);

					bar.setHP((int)(hp/(float)MAX_HP[type]*100),((x+0.5f)*SceneGame.msw), 
			    					(y*SceneGame.msh+SceneGame.paddingTop-30));          	
			    					     
			  }
		   	  else
		   	  {
				   starFlg = false;
		   	  }

			   setShow(true);
	   }
	   else
	   {
		   setShow(false);
	   }
     
		
		if(starFlg)
		{

			bar.update();
			bar.setShow(true);
		}
		else
		{
			bar.setShow(false);
		}
  }
  private float constrain(float num, int low, int high) {

	  if(num < low)
	  {
		  num = low;
	  }
	  else if(num > high)
	  {
		  num = high;
	  }
	return num;
}
private float radians(int angle) {
	return (float) (angle * Math.PI / 180.f);
}
void setX(float x){
    this.x = x;
  }
  void setY(float y){
    this.y = y;
  }
  float getX(){
    return this.x;
  }
  float getY(){
    return this.y;
  }
  void updHP(float dmg){
    this.hp-=dmg*0.1;
  }
  void setHP(int hp){
	    this.hp=hp;
  }
  int getHP(){
    return (int)this.hp;
  }
  int getScore(){
    return score;
  }
  void setArrivalFlg(boolean flg){
	    this.arrivalFlg=flg;
	  }
	  boolean getArrivalFlg(){
	    return arrivalFlg;
	  }
	  
	  
	  void setBomb(int bomb){
	    this.bomb = bomb;
	  }
	  int getBomb(){
	    return bomb;
	  }
  
  //die effect
  void drawDieStart(float pos_x,float pos_y){
      dieEffect.play(0,0,false);
      dieEffect.play(0,8,false);

	  dieEffect.setShow(true);
      dieEffect.setPosition(pos_x, pos_y);  
      dieEffect.apply();
  	
  }
  
  void setdieFlg(){
    setX(-1);
    setY(-1);
    hp = 0;
    this.dieFlg = true;
    starFlg = false;
  }
  
  boolean getdieFlg(){
    return this.dieFlg;
  }
  
	  static public void initEnemyPattern() {

		  enemy_pattern = new Animation[MAX_ENEMY_PATTERN];
		  for(int i=0; i<MAX_ENEMY_PATTERN; i++)
		  {
			  enemy_pattern[i] = new Animation();
			  enemy_pattern[i].init(0, 0, SceneGame.msw, SceneGame.msh, 8, 4, 24, 0);
		      enemy_pattern[i].setSize(64,64);
		      enemy_pattern[i].setPlaySpeed(3);
		      
		      if(i == ENEMY_PATTERN_DANGORO)
		      {
		    	  enemy_pattern[i].play((i*2), 10, true);
		      }
		      else
		      {
		    	  enemy_pattern[i].play((i*2), 2, true);
		      }
		      
		  }


	  }
	  
	static public void loadEnemyPattern(GL10 gl, Context context) {
		  for(int i=0; i<MAX_ENEMY_PATTERN; i++)
		  {
			  enemy_pattern[i].loadTexture(gl, context, R.drawable.enemy);
		  }
	}
	
	@Override
	public void draw(GL10 gl) {
		if(mShow == false)
    	{
    		return;
    	}
		
    	if(hp > 0)
    	{
			// Point to our buffers
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	
	        ////////////////
			enemy_pattern[type].anim.applyFrame(this);
			enemy_pattern[type].image.setTexture(gl, 0, 0, 1, 1);
	        //////////////////////////////
	        
	        // �|���S���̕`��
	
	        //  ���f���r���[�s��̎w��
	        gl.glMatrixMode(GL10.GL_MODELVIEW);
	        //  ���W�̏���
	        gl.glLoadIdentity();
	        
	
	    	gl.glTranslatef(((x+0.5f)*SceneGame.msw)/GLESRenderer.SCREEN_WIDTH*2, 
	    					-(y*SceneGame.msh+SceneGame.paddingTop)/GLESRenderer.SCREEN_HEIGHT*2, 
	    					0);          	
	    	

	    	
	        gl.glColor4f(mColor.r, mColor.g, mColor.b, mColor.a); 
	
			if(vtxFloatBuffer != null)
			{
	        	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vtxFloatBuffer);	// ���_�o�b�t�@��Open GL�ɕR�t��
	        	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	// �`�悷��
			}
			
    	}
                  
		 
	}

	public boolean isStart() {
		return starFlg;
	}
}
