package jp.android.bugsbuster;

import jp.android.bugsbuster.processing.PImage;

public class Bar {

	  final color FOREGROUND_MAX = new color(0,255,0);
	  final color FOREGROUND_MIN = new color(255,0,0);
	  final color BACKGROUND = new color(64,64,64);
	  final color BORDER = new color(255,255,255);
	  final float MEAN_HP = 0.20f;
	  final float BAR_SIZE_X = 80;
	  final float BAR_SIZE_Y = 10;
	  final float BORDER_SIZE = 2;

	  private PImage mBgImage = new PImage(Object.LAYER_2);
	  private PImage mValueImage = new PImage(Object.LAYER_2);
	  
	  color c;
	  float xpos;
	  float ypos;
	  int HP;
	  int nextHP;
	  int maxHP;
	  float scale;

	  // The Constructor is defined with arguments.
      public Bar(int max) {
	    c = FOREGROUND_MAX;
	    xpos = 0;
	    ypos = 0;
	    maxHP = max;
	    HP = maxHP;
	    nextHP = HP;

	    mBgImage.init(0, 0, 100, 1);
	    mValueImage.init(0, 0, 100, 1);
	  }

	  public void resetHP(int max)
	  {
	    maxHP = max;
	    HP = maxHP;
	    nextHP = HP;
	    

	    mValueImage.setSize((int)(max),(int)(BAR_SIZE_Y));
	    mValueImage.apply();
	  }  
	  
	  public void setHP(int showHP,
                        float showX,
                        float showY)
	  {
	    xpos = showX;
	    ypos = showY;
	    nextHP = showHP;
	  }
	  
	  public void update() {
	    
	    //slowly decreasing
	    float diff = nextHP-HP;
	    if(Math.abs(diff) < 1)
	    {
	      HP = nextHP;
	    }
	    else
	    {
	      HP += diff/20.f;
	    }
	    
	    
	    //bakcground
		mBgImage.setColor(BACKGROUND);
		mBgImage.setPosition((int)xpos,(int)ypos);
		mBgImage.setSize((int)(BAR_SIZE_X+(2*BORDER_SIZE)),(int)(BAR_SIZE_Y+(2*BORDER_SIZE)));
	   
		mBgImage.apply();
		
	    if(HP < (maxHP*MEAN_HP))
	    {
	      c = FOREGROUND_MIN;      
	    }
	    else
	    {
	      c = FOREGROUND_MAX;      
	    }
	    
	    //HP bar
		mValueImage.setColor(c);
	    float showHP = (HP/(float)maxHP)*BAR_SIZE_X;
	    mValueImage.setPosition((int)(xpos-(BAR_SIZE_X/2.f)+(showHP/2.f)),(int)ypos);
	    mValueImage.setSize((int)(showHP),(int)(BAR_SIZE_Y));

	    mValueImage.apply();
	  }
	  
	  public void setShow(boolean flag)
	  {
		  mValueImage.setShow(flag);
		  mBgImage.setShow(flag);
		  
	  }

	public int getValue() {
		return (int)HP;
	}
}
