/**

* 画面遷移管理

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

public class Transition {

	private static final int CHANGE_VAL = 15;
    protected int mPosX   = 0;
    protected int mPosY   = 0;
    protected int mSizeX  = 0;
    protected int mSizeY  = 0;
    protected float mAngle  = 0;
    protected color mColor = new color();
    public Texture image = new Texture();

	private int mAlpha = 255;
	private boolean mStart = false;
	private boolean mFadeIn = false;
	
	public Transition()
	{
		mAlpha = 0;
		mStart = false;
		
	}

	public void startFadeIn()
	{
		mAlpha = 255;
		mFadeIn = true;
		mStart = true;
	}
	
	public void startFadeOut()
	{
		mAlpha = 0;
		mFadeIn = false;
		mStart = true;
	}	
	
	public void setAlpha(int val)
	{
		if(val > 255)
		{
			val = 255;
		}
		mAlpha = val;
	}
	
	public void stop()
	{
		mStart = false;
	}
	
	public boolean isEndFadeIn()
	{
		if(mFadeIn && (mAlpha <= 0))
		{
			return true;
		}
		return false;
	}
	
	public boolean isEndFadeOut()
	{
		if(!mFadeIn && (mAlpha >= 255))
		{
			return true;
		}
		
		return false;
	}
	
	public void reset() {
		
		mStart = false;
		
    	mPosX = (int) (GLESRenderer.SCREEN_WIDTH/2);
    	mPosY = (int) (GLESRenderer.SCREEN_HEIGHT/2);
    	mSizeX = (int) GLESRenderer.SCREEN_WIDTH;
    	mSizeY = (int) GLESRenderer.SCREEN_HEIGHT;
    	
		//色設定
		mColor.setColor(0,0,0,0);
	}

	public void update() {
	
		if(mStart)
		{
			if(mFadeIn)
			{
				mAlpha -= CHANGE_VAL;
				
				if(mAlpha <= 0)
				{
					mAlpha = 0;
				}
			}
			else
			{
				mAlpha += CHANGE_VAL;
				
				if(mAlpha >= 255)
				{
					mAlpha = 255;
				}
			}

			//色設定
			mColor.setColor(0,0,0,mAlpha);
		}
		
	}

	public void draw(GL10 gl) {

		if(mStart)
		{
			// Point to our buffers
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

	        ////////////////
	        image.setTexture(gl, 0, 0, 1, 1);
	        //////////////////////////////
	        
	        // ポリゴンの描画
	        {
	        	
	            float[] vertices = //vertex;
	            {
	            		//X Y Z  (Z順)
	            		-1, 1, 0,
	            		1, 1, 0,
	            		-1, -1, 0,
	            		1, -1, 0,
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

	public void loadTexture(GL10 gl, Context context, int id) {

    	image.loadGLTexture(gl, context, id);
    	    	
	}

}
