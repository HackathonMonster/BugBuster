/**

* �e�N�X�`���[�N���X

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         �K���ɂ������

*/
package jp.android.bugsbuster;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Texture {

	private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	private float texture[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 0.0f,		// bottom right	(V3)
			0.0f, 1.0f,		// top left		(V2)
			1.0f, 1.0f		// top right	(V4)
	};
	/** The texture pointer */
    public int[] textures = new int[1];

	public Texture()
	{
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();

	}

	public void setTile(float x, float y)
	{
		// Mapping coordinates for the vertices (0.0~1.0)
		texture[0] = 0.0f*x;	texture[1] = 0.0f*y;		// bottom left	(V1)
		texture[2] = 1.0f*x;	texture[3] = 0.0f*y;		// bottom right	(V3)
		texture[4] = 0.0f*x;	texture[5] = 1.0f*y;		// top left		(V2)
		texture[6] = 1.0f*x;	texture[7] = 1.0f*y;		// top right	(V4)
		
	}
	
	public void setTile(float x, float y, float offset_x, float offset_y)
	{		
		// Mapping coordinates for the vertices (0.0~1.0)
		texture[0] = (0.0f*x)+offset_x;	texture[1] = (0.0f*y)+offset_y;		// bottom left	(V1)
		texture[2] = (1.0f*x)+offset_x;	texture[3] = (0.0f*y)+offset_y;		// bottom right	(V3)
		texture[4] = (0.0f*x)+offset_x;	texture[5] = (1.0f*y)+offset_y;		// top left		(V2)
		texture[6] = (1.0f*x)+offset_x;	texture[7] = (1.0f*y)+offset_y;		// top right	(V4)
		
	}
	

	public void setOffset(float offset_x, float offset_y)
	{
		// Mapping coordinates for the vertices (0.0~1.0)
		texture[0] += offset_x;	texture[1] += offset_y;		// bottom left	(V1)
		texture[2] += offset_x;	texture[3] += offset_y;		// bottom right	(V3)
		texture[4] += offset_x;	texture[5] += offset_y;		// top left		(V2)
		texture[6] += offset_x;	texture[7] += offset_y;		// top right	(V4)
		
	}
	
	public void loadGLTexture(GL10 gl, Context context, int id) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
													 id);

		// generate one texture pointer
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		// Clean up
		bitmap.recycle();
	}

	public void setTexture(int[] image)
	{
		this.textures = image;
	}
	
	public void setTexture(GL10 gl, float startU, float startV, float endU, float endV)
	{
		//�e�N�X�`���@�\ON
		gl.glEnable(GL10.GL_TEXTURE_2D);

		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDisable(GL10.GL_LIGHTING);
		
		gl.glEnable(GL10.GL_BLEND);


		////////////////////
		textureBuffer.put(texture);
		textureBuffer.position(0);
		///////////////////////
		
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		

		// Point to our vertex buffer
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
	}
	
}









