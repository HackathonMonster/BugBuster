package jp.android.bugsbuster;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;



public class Pool extends PImage {
	

	private ArrayList<Vector2D> position = new ArrayList<Vector2D>();
	private  int nMax = 0;
	
	Pool()
	{
		super(Object.LAYER_0);
	}
	
	public void init()
	{
		super.init(0, 0, 30, 30);
		this.setTile(0.5f, 1, 0, 0);
		nMax = 0;
	}
	

	//描画(レンダーラースレッド内に読み込まなきゃ！)
	@Override
    public void draw(GL10 gl){

    	if(mShow == false)
    	{
    		return;
    	}
    	
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
       //gl.glCullFace(GL10.GL_CULL_FACE);
		
        ////////////////
        image.setTexture(gl, 0, 0, 1, 1);
        //////////////////////////////
        
        // ポリゴンの描画
        for(int i=0; (i<nMax*4) && (i<position.size());i+=4)		
        {   
            Vector2D pos0 = position.get(i);
            Vector2D pos1 = position.get(i+1);
            Vector2D pos2 = position.get(i+2);
            Vector2D pos3 = position.get(i+3);
            
            	if((pos0 != null) && (pos1 != null) && (pos2 != null) && (pos3 != null))
	           {
	                //  モデルビュー行列の指定
	                gl.glMatrixMode(GL10.GL_MODELVIEW);
	                //  座標の初期化
	                gl.glLoadIdentity();
	
	                
	                float[] vertices = //vertex;
	                {
	                		//X Y Z  (Z順)
	                		(-1+(pos0.x/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(pos0.y/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	                		(-1+(pos1.x/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(pos1.y/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	                		(-1+(pos2.x/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(pos2.y/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	                		(-1+(pos3.x/(float)GLESRenderer.SCREEN_WIDTH*2.f)), (-1+(pos3.y/(float)GLESRenderer.SCREEN_HEIGHT*2.f)),    	0.0f,
	                };
	                
	                
	                if(byteBuffer != null)
	                {
	        	        // Java => OpenGL にあたっての変換
	        	        vtxFloatBuffer = byteBuffer.asFloatBuffer();
	        	
	        	        vtxFloatBuffer.put(vertices);
	        	        vtxFloatBuffer.position(0);
	                }
	                
		            gl.glColor4f(0/255.0f,126/255.0f,255/255.0f,70/255.0f); 
		
					if(vtxFloatBuffer != null)
					{
		            	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vtxFloatBuffer);	// 頂点バッファをOpen GLに紐付け
		            	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	// 描画する
					}
	            }
	        
        }
        
	}

	public void addPosition(int x1, int y1,int x2, int y2,int x3, int y3,int x4, int y4)
	{
		//Z順
		position.add(new Vector2D(x1,(int)GLESRenderer.SCREEN_HEIGHT-y1));
		position.add(new Vector2D(x2,(int)GLESRenderer.SCREEN_HEIGHT-y2));
		position.add(new Vector2D(x4,(int)GLESRenderer.SCREEN_HEIGHT-y4));
		position.add(new Vector2D(x3,(int)GLESRenderer.SCREEN_HEIGHT-y3));
		nMax++;
	}

	public void clearPosition()
	{
		position.clear();
		nMax = 0;

	}
}
