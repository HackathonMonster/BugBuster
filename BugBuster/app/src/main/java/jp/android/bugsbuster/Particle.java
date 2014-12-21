/**

* パティクールクラス

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Particle extends Object {

	private boolean mUse = false;
	private boolean mShow = false;
	private int mLife = 0;
	private float mPosX = 0;
	private float mPosY = 0;
    protected int mSizeX  = 0;
    protected int mSizeY  = 0;
	private float mSpeedX = 0;
	private float mSpeedY = 0;
	private float mAccX = 0;
	private float mAccY = 0;
    protected float mAngle  = 0;

    private Texture mTexture;
    
    protected color mColor = new color(255,255,255,255);

	public void setTexture(Texture image)
	{
		mTexture = image;
	}
	
	public void setAccel(float x, float y)
	{
		mAccX = x;
		mAccY = y;
		
	}
	
	public boolean isShow()
	{
		return mUse;
	}
	
	public void setShow(boolean show)
	{
		mShow = show;
	}

	public boolean isUse()
	{
		return mUse;
	}
	
	public void setUse(boolean use)
	{
		mUse = use;
	}
	
	public Particle()
	{
		super(LAYER_1);
	}
	
	public void setParticle(int life, float pos_x, float pos_y, int sizeX, int sizeY, float speedX, float speedY)
	{
		mLife = life;
		mPosX = pos_x;
		mPosY = pos_y;
    	mSizeX = sizeX;
    	mSizeY = sizeY;
		mSpeedX = speedX;
		mSpeedY = speedY;

		mAngle = 0;
		mUse = true;
		mShow = true;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		
		if(mUse)
		{
			if(mLife > 0)
			{
				mLife--;

				mPosX += mAccX;
				mPosY += mAccY;
				
				mPosX += mSpeedX;
				mPosY += mSpeedY;
			}
			else
			{

				mUse = false;
				mShow = false;
			}
		}
	}

	@Override
	public void draw(GL10 gl) {

		if(mShow)
		{
			// Point to our buffers
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	
	        ////////////////
			if(mTexture != null)
			{
				mTexture.setTexture(gl, 0, 0, 1, 1);
			}
	        //////////////////////////////
	        
	        // ポリゴンの描画
	        {
	        	
	        	float  half_sizeX = (mSizeX/2.f);
	        	float  half_sizeY = (mSizeY/2.f);
	        	float  posX = mPosX/(float)GLESRenderer.SCREEN_WIDTH*2.f;
	        	float  posY = (GLESRenderer.SCREEN_HEIGHT-mPosY)/(float)GLESRenderer.SCREEN_HEIGHT*2.f;
	        	
	            float cos_val = (float) Math.cos(mAngle);
	            float sin_val = (float) Math.sin(mAngle);
	
	            
	            
	            float[] vertex = {
	            		//X Y Z  (Z順)
	            		(float) (-half_sizeX*cos_val)-(half_sizeY*sin_val), (float) (-half_sizeX*sin_val)+(half_sizeY*cos_val),   
	            		(float) ( half_sizeX*cos_val)-(half_sizeY*sin_val), (float) ( half_sizeX*sin_val)+(half_sizeY*cos_val), 
	            		(float) (-half_sizeX*cos_val)-(-half_sizeY*sin_val), (float) (-half_sizeX*sin_val)+(-half_sizeY*cos_val), 
	            		(float) ( half_sizeX*cos_val)-(-half_sizeY*sin_val), (float) ( half_sizeX*sin_val)+(-half_sizeY*cos_val), 
	            };
	            
	            float[] vertices = //vertex;
	            {
	            		//X Y Z  (Z順)
	            		(-1+posX+(vertex[0]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[1]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	            		(-1+posX+(vertex[2]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[3]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	            		(-1+posX+(vertex[4]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[5]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	            		(-1+posX+(vertex[6]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[7]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	            };
	            
	            // Java => OpenGL にあたっての変換
	            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
	            byteBuffer.order(ByteOrder.nativeOrder());
	            FloatBuffer fb = byteBuffer.asFloatBuffer();
	
	            //  モデルビュー行列の指定
	            gl.glMatrixMode(GL10.GL_MODELVIEW);
	            //  座標の初期化
	            gl.glLoadIdentity();
	
	            //  回転
	            //gl.glRotatef(mRotate_x, 0, 1, 0);
	            //gl.glRotatef(mRotate_y, 1, 0, 0);
	            
	            gl.glColor4f(mColor.r, mColor.g, mColor.b, mColor.a); 
	            
	            fb.put(vertices);
	            fb.position(0);
	
	            //gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);	// 頂点バッファの有効化
	            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb);	// 頂点バッファをOpen GLに紐付け
	            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	// 描画する
	        }             
        }
	}

    //色設定
    public void setColor(int r, int g, int b, int a)
	{
    	mColor.setColor(r,g,b,a);
    }

    //色設定
    public void setColor(color color){
    	mColor = color;
    };
    

	
	@Override
	public void loadTexture(GL10 gl, Context context, int id) {
		// TODO Auto-generated method stub
		
	}

}
