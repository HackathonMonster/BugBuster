/**

* 2Dポリゴンクラス

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

public class PImage extends Object {

    protected int mPosX   = 0;
    protected int mPosY   = 0;
    protected float mSclX = 1.0f;
    protected float mSclY = 1.0f;
    protected int mSizeX  = 0;
    protected int mSizeY  = 0;
    protected float mAngle  = 0;
    protected boolean mShow  = true;
    protected color mColor = new color(255,255,255,255);
    public Texture image = new Texture();

    public int getPosX(){return mPosX;}
    public int getPosY(){return mPosY;}
    public int getSizeX(){return mSizeX;}
    public int getSizeY(){return mSizeY;}

    protected FloatBuffer vtxFloatBuffer = null;	//頂点バッファ
    protected ByteBuffer byteBuffer = null;

    public void apply()
    {

    	float  half_sizeX = (mSizeX/2.f) * mSclX;
    	float  half_sizeY = (mSizeY/2.f) * mSclY;
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
        
        
        
/*
        float[] vertices = //vertex;
        {
        	//X Y Z  (Z順)
            	-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f,  half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
            	 half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f,  half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
            	-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f, -half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
            	 half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f, -half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
        		
        };
        */
        /*
        float[] vertices = //vertex;
        {
        		//X Y Z  (Z順)
        		(-1+(-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+(half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+(-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(-half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+(half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(-half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        };
        
        */
        if(byteBuffer != null)
        {
	        // Java => OpenGL にあたっての変換
	        vtxFloatBuffer = byteBuffer.asFloatBuffer();
	
	        vtxFloatBuffer.put(vertices);
	        vtxFloatBuffer.position(0);
        }
    }
    
    //描画(レンダーラースレッド内に読み込まなきゃ！)
    public void draw(GL10 gl){

    	if(mShow == false)
    	{
    		return;
    	}
    	
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        ////////////////
        image.setTexture(gl, 0, 0, 1, 1);
        //////////////////////////////
        
        // ポリゴンの描画
        {

            //  モデルビュー行列の指定
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //  座標の初期化
            gl.glLoadIdentity();

            //  回転
            //gl.glRotatef(mRotate_x, 0, 1, 0);
            //gl.glRotatef(mRotate_y, 1, 0, 0);
           /* 
            gl.glTranslatef((mPosX/GLESRenderer.SCREEN_WIDTH*2), 
            				-mPosY/GLESRenderer.SCREEN_HEIGHT*2, 
            				0);
            */
            gl.glColor4f(mColor.r, mColor.g, mColor.b, mColor.a); 

			if(vtxFloatBuffer != null)
			{
            	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vtxFloatBuffer);	// 頂点バッファをOpen GLに紐付け
            	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	// 描画する
			}
        }
        

    }
    //コンストラクター
    public PImage(String string, int i)
    {

    }

    public PImage(int layer) {
		super(layer);
	}
    
	public PImage() {

	}
	
	//色設定
    public void setColor(int r, int g, int b, int a)
	{
    	mColor.setColor(r,g,b,a);
    }

    //色設定
    public void setColor(color color){
    	mColor = color;
    }

	// スケール変更（追加）
	public void setScl( float sclX, float sclY )
	{
		mSclX = sclX;
		mSclY = sclY;
	}
	
	
    //初期化
    public void init(int posX, int posY, int sizeX, int sizeY){
    	mPosX = posX;
    	mPosY = posY;
    	mSizeX = sizeX;
    	mSizeY = sizeY;

        byteBuffer = ByteBuffer.allocateDirect((4*3) * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        
    	apply();
    }

    //テクスチャのタイル(繰り返し回数)設定
	public void setTile(float x, float y)
	{
		image.setTile(x, y);
	}

    //テクスチャのタイル(繰り返し回数)とオフセット(スクロール)設定
	public void setTile(float x, float y, float offset_x, float offset_y)
	{
		image.setTile(x, y, offset_x, offset_y);		
	}
	
    //テクスチャのオフセット(スクロール)設定
	public void setOffset(float offset_x, float offset_y)
	{
		image.setOffset(offset_x, offset_y);		
	}

    //回転角度（Z軸）設定
	public void setAngle(float angle)
	{
		mAngle = angle;
	}
	
    //描画フラグ設定
	public void setShow(boolean flag)
	{
		mShow = flag;
	}

	//テクスチャのリソースから読み込む
    public void loadTexture(GL10 gl, Context context, int id)
    {

    	image.loadGLTexture(gl, context, id);
    	    	
    }

	//テクスチャのポインタ設定
    public void setTexture(Texture texture)
    {
    	image = texture;
    }


    //更新
    public void update(){

    	//apply();
    }
    
    //終了
    public void uninit(){
    }
    
    //位置設定
    public void setPosition( int fPosX, int fPosY ){

        mPosX = fPosX;
        mPosY = fPosY;
	}

    //再初期化
	@Override
	public void reset() {
		mSclX = 1.0f;
		mSclY = 1.0f;		
	}


	boolean isHit(int x, int y)
	{
		int start_x = (int) (mPosX-(mSizeX/2.f));
		int start_y = (int) (mPosY-(mSizeY/2.f));
		int end_x = (int) (mPosX+(mSizeX/2.f));
		int end_y = (int) (mPosY+(mSizeY/2.f));

		if((x > start_x) && (x <= end_x) &&
		   (y > start_y) && (y < end_y) )
		{	
			return true;
		}

		return false;
	}
	

	public void setSize(int x, int y) {
		mSizeX = x;
		mSizeY = y;		
		apply();
	}
	
	public void setPosition(float x, float y) {
		setPosition((int)x, (int)y);
	}

	public void setSize(float x, float y) {
		setSize((int)x, (int)y);
	}
}
