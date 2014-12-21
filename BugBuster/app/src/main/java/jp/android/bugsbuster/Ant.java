package jp.android.bugsbuster;

public class Ant extends PImage{
	
	  final int START_POS_X = (int)(50);
	  final int START_POS_Y = (int)(50);
	  
	  AnimationInfo anim = new AnimationInfo();
	  float posX;
	  float posY;
	  float angle;
	  float moveAngle;

	  
	@Override
	public void reset() {
		super.reset();
		
		  int type = (int) Math.random()*2;
	      angle = 0;
	      anim.init(0, 2, 1);
	      anim.play(0,2,true);
	      
	      switch(type)
	      {
	        case 0:
	        posX = GLESRenderer.SCREEN_WIDTH/2;
	        posY = START_POS_Y;
	        moveAngle = (float) (3*Math.PI/6.f);
	        break;
	        case 1:
	        posX = GLESRenderer.SCREEN_WIDTH/2;
	        posY = -START_POS_Y;
	        moveAngle = (float) (-5*Math.PI/6.f);
	        break;
	      }

	      setSize(48, 48);
	      setPosition(posX, posY);
	      setAngle(angle);
		
	      anim.setSpeed(10);
	}


	@Override
	public void update() {

		  float offset = (float) (-Math.PI/6.f+(Math.sin(angle)*Math.PI/6.f));
	      float moveX = (float) (Math.sin(moveAngle+offset)*3.f);
	      float moveY = (float) (Math.cos(moveAngle+offset)*3.f);
	  
	      posX += moveX;
	      posY += moveY;
	        
	      if(posX < 0)
	      {
	        posX = 0;
	        moveAngle = (float) (5*Math.PI/6.f);
	        
	      }
	      if(posX > GLESRenderer.SCREEN_WIDTH)
	      {
	         posX = GLESRenderer.SCREEN_WIDTH;
	         moveAngle = (float) (-Math.PI/6.f);
	        
	      } 
	      if(posY < 0)
	      {
	        posY = 0;
	        moveAngle = (float) (Math.PI/6.f);
	        
	      }
	      if(posY > GLESRenderer.SCREEN_HEIGHT)
	      {
	         posY = GLESRenderer.SCREEN_HEIGHT;
		     moveAngle = (float) (-5*Math.PI/6.f);
	      }

	      angle += (float) (Math.PI/450.f);
	      
	      setPosition(posX, posY);
	      
	      float rot_offset = (float) (-Math.PI/6.f+(Math.sin(angle)*Math.PI/6.f));
	      setAngle(moveAngle+rot_offset); 
	      
	      anim.update();
	      anim.applyFrame(this);
	      
	      apply();
	}
	  
}
