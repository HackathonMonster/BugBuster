/**

* 2D�|���S���N���X

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         �K���ɂ������

*/
package jp.android.bugsbuster.processing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import jp.android.bugsbuster.*;

public class PImage extends jp.android.bugsbuster.Object {

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

    protected FloatBuffer vtxFloatBuffer = null;	//���_�o�b�t�@
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
        		//X Y Z  (Z��)
        		(float) (-half_sizeX*cos_val)-(half_sizeY*sin_val), (float) (-half_sizeX*sin_val)+(half_sizeY*cos_val),   
        		(float) ( half_sizeX*cos_val)-(half_sizeY*sin_val), (float) ( half_sizeX*sin_val)+(half_sizeY*cos_val), 
        		(float) (-half_sizeX*cos_val)-(-half_sizeY*sin_val), (float) (-half_sizeX*sin_val)+(-half_sizeY*cos_val), 
        		(float) ( half_sizeX*cos_val)-(-half_sizeY*sin_val), (float) ( half_sizeX*sin_val)+(-half_sizeY*cos_val), 
        };

        float[] vertices = //vertex;
        {
        		//X Y Z  (Z��)
        		(-1+posX+(vertex[0]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[1]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+posX+(vertex[2]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[3]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+posX+(vertex[4]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[5]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+posX+(vertex[6]/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+posY+(vertex[7]/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        };
        
        
        
/*
        float[] vertices = //vertex;
        {
        	//X Y Z  (Z��)
            	-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f,  half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
            	 half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f,  half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
            	-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f, -half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
            	 half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f, -half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f, 0,
        		
        };
        */
        /*
        float[] vertices = //vertex;
        {
        		//X Y Z  (Z��)
        		(-1+(-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+(half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+(-half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(-half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        		(-1+(half_sizeX/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(-half_sizeY/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
        };
        
        */
        if(byteBuffer != null)
        {
	        // Java => OpenGL �ɂ������Ă̕ϊ�
	        vtxFloatBuffer = byteBuffer.asFloatBuffer();
	
	        vtxFloatBuffer.put(vertices);
	        vtxFloatBuffer.position(0);
        }
    }
    
    //�`��(�����_�[���[�X���b�h���ɓǂݍ��܂Ȃ���I)
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
        
        // �|���S���̕`��
        {

            //  ���f���r���[�s��̎w��
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //  ���W�̏���
            gl.glLoadIdentity();

            //  ��]
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
            	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vtxFloatBuffer);	// ���_�o�b�t�@��Open GL�ɕR�t��
            	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	// �`�悷��
			}
        }
        

    }
    //�R���X�g���N�^�[
    public PImage(String string, int i)
    {

    }

    public PImage(int layer) {
		super(layer);
	}
    
	public PImage() {

	}
	
	//�F�ݒ�
    public void setColor(int r, int g, int b, int a)
	{
    	mColor.setColor(r,g,b,a);
    }

    //�F�ݒ�
    public void setColor(color color){
    	mColor = color;
    }

	// �X�P�[���ύX�i�ǉ��j
	public void setScl( float sclX, float sclY )
	{
		mSclX = sclX;
		mSclY = sclY;
	}
	
	
    //����
    public void init(int posX, int posY, int sizeX, int sizeY){
    	mPosX = posX;
    	mPosY = posY;
    	mSizeX = sizeX;
    	mSizeY = sizeY;

        byteBuffer = ByteBuffer.allocateDirect((4*3) * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        
    	apply();
    }

    //�e�N�X�`���̃^�C��(�J��Ԃ���)�ݒ�
	public void setTile(float x, float y)
	{
		image.setTile(x, y);
	}

    //�e�N�X�`���̃^�C��(�J��Ԃ���)�ƃI�t�Z�b�g(�X�N���[��)�ݒ�
	public void setTile(float x, float y, float offset_x, float offset_y)
	{
		image.setTile(x, y, offset_x, offset_y);		
	}
	
    //�e�N�X�`���̃I�t�Z�b�g(�X�N���[��)�ݒ�
	public void setOffset(float offset_x, float offset_y)
	{
		image.setOffset(offset_x, offset_y);		
	}

    //��]�p�x�iZ���j�ݒ�
	public void setAngle(float angle)
	{
		mAngle = angle;
	}
	
    //�`��t���O�ݒ�
	public void setShow(boolean flag)
	{
		mShow = flag;
	}

	//�e�N�X�`���̃��\�[�X����ǂݍ���
    public void loadTexture(GL10 gl, Context context, int id)
    {

    	image.loadGLTexture(gl, context, id);
    	    	
    }

	//�e�N�X�`���̃|�C���^�ݒ�
    public void setTexture(Texture texture)
    {
    	image = texture;
    }


    //�X�V
    public void update(){

    	//apply();
    }
    
    //�I��
    public void uninit(){
    }
    
    //�ʒu�ݒ�
    public void setPosition( int fPosX, int fPosY ){

        mPosX = fPosX;
        mPosY = fPosY;
	}

    //�ď���
	@Override
	public void reset() {
		mSclX = 1.0f;
		mSclY = 1.0f;		
	}


	public boolean isHit(int x, int y)
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
