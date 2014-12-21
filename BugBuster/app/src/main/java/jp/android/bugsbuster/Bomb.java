package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Bomb extends PImage {

	static Animation bombImage = new Animation();
	static PImage explosionImage = new PImage();
	
	 final int BombAnim = 10; 
	  
	  private boolean appear;
	  
	   private float x=0,y=0; 
	   private boolean explosion=false;
	   private boolean enemyDie=false;
	   private int expCount=0;
	   
	   
	   Bomb(){
		   super(Object.LAYER_2);
		   
	      expCount=0;
	      explosion=false;
	      enemyDie=false;
	      appear=false;
	   }
	   


		public static void loadBombTexture(GL10 gl, Context context) {

			  bombImage.loadTexture(gl, context, R.drawable.bomb);
			  explosionImage.loadTexture(gl, context, R.drawable.bomb_fx);
		}


		   void setGridPosition(float x,float y){
		      this.x=x;
		      this.y=y;
		   }
		   float getX(){
		     return this.x;
		   }
		   float getY(){
		     return this.y;
		   }
		   
		   
		   void setAppear(boolean appear){
		     this.appear = appear;
		   }
		   
		   boolean getAppear(){
		      return appear;
		   }
		   
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
	     if(!enemyDie && !explosion){
	         
	      }
	     
	     if(explosion)
	     {

	        expCount++;
	 	    
	 	    if(expCount>BombAnim)
	 	    {
	 	      setAppear(false);
	 	    }
	     }
	}

	@Override
	public void draw(GL10 gl) {
		if(appear)
		{
			// Point to our buffers
						gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
						gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				
				        ////////////////
					       if(!explosion){
					    	   bombImage.image.setTexture(gl, 0, 0, 1, 1);
					    	   bombImage.anim.applyFrame(this);
					       }else{

						 	    //explosionImage.setPosition(x-expCount*5,y-expCount*5);
						 	    //explosionImage.setSize(expCount*10,expCount*10);
						 	    //explosionImage.apply();
						 	   explosionImage.image.setTexture(gl, 0, 0, 1, 1);
					       }
				        //////////////////////////////
				        
				        // ポリゴンの描画
				
				        //  モデルビュー行列の指定
				        gl.glMatrixMode(GL10.GL_MODELVIEW);
				        //  座標の初期化
				        gl.glLoadIdentity();
				        
				
				    	gl.glTranslatef(((x+0.5f)*SceneGame.msw)/GLESRenderer.SCREEN_WIDTH*2, 
				    					-(y*SceneGame.msh+SceneGame.paddingTop)/GLESRenderer.SCREEN_HEIGHT*2, 
				    					0);          	
				    	

					       if(explosion){
					    	   gl.glScalef(expCount, expCount, 1);
					       }
				    	
				        gl.glColor4f(mColor.r, mColor.g, mColor.b, mColor.a); 
				
						if(vtxFloatBuffer != null)
						{
				        	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vtxFloatBuffer);	// 頂点バッファをOpen GLに紐付け
				        	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	// 描画する
						}
		}
	}



	@Override
	public void loadTexture(GL10 gl, Context context, int id) {
		// TODO Auto-generated method stub
		
	}
	

	   void setEnemyDie(boolean enemyDie){
	       this.enemyDie=enemyDie;
	   }
	   
	   boolean getEnemyDie(){
	      return enemyDie;
	   }
	   
	   void setExplosion(boolean explosion){
	       this.explosion=explosion;
	   }
	   
	   boolean getExplosion(){
	      return explosion;
	   }
	   

}
