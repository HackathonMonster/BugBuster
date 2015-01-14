package jp.android.bugsbuster.game_model;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import jp.android.bugsbuster.*;
import jp.android.bugsbuster.processing.PImage;
import jp.android.bugsbuster.processing.Vector2D;
import jp.android.bugsbuster.scene.SceneGame;


public class Bridge extends PImage {

	private ArrayList<Vector2D> position = new ArrayList<Vector2D>();
	private  int nMax = 0;

	public Bridge()
	{
		super(jp.android.bugsbuster.Object.LAYER_0);
	}

	public void loadTexture(GL10 gl, Context context) {

		loadTexture(gl, context, R.drawable.wood);
	}
	
	public void init()
	{
		super.init(0, 0, SceneGame.msw*SceneGame.obstacleRangeW*9/10, SceneGame.msh*SceneGame.obstacleRangeH*9/10);
		nMax = 0;
	}
	

	//�`��(�����_�[���[�X���b�h���ɓǂݍ��܂Ȃ���I)
	@Override
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
        for(int i=0; (i<nMax) && (i<position.size());i++)		
        {
            Vector2D pos = position.get(i);
            
            if(pos != null)
            {
                //  ���f���r���[�s��̎w��
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                //  ���W�̏���
                gl.glLoadIdentity();

            	gl.glTranslatef(pos.x/GLESRenderer.SCREEN_WIDTH*2, 
            					-pos.y/GLESRenderer.SCREEN_HEIGHT*2, 
            					0);          	
            	
            
	            gl.glColor4f(mColor.r, mColor.g, mColor.b, mColor.a); 
	
				if(vtxFloatBuffer != null)
				{
	            	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vtxFloatBuffer);	// ���_�o�b�t�@��Open GL�ɕR�t��
	            	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	// �`�悷��
				}
            }
			
        }
        
	}

	public void addPosition(int x, int y)
	{
		position.add(new Vector2D(x,y));
		nMax++;
	}


	public void clearPosition()
	{
		position.clear();
		nMax = 0;

	}
}
