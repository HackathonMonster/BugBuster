/**

* オブジェクトクラス

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

public abstract class Object {

	protected int layer = LAYER_2;
	public static final int LAYER_0 = 0;
	public static final int LAYER_1 = 1;
	public static final int LAYER_2 = 2;
	public static final int LAYER_3 = 3;
	public static final int LAYER_MAX = 4;
	protected boolean mUse = true;
	protected boolean mDelete = false;
	public static Lock lock = new Lock();

	public void setUse(boolean flag){ mUse = flag; }
	
	public static ArrayList[] objectList;
	
	static public void initList()
	{
		objectList = new ArrayList[LAYER_MAX];
		for(int i= 0; i<LAYER_MAX; i++)
		{
			objectList[i] = new ArrayList<Object>();
		}
	}	
	
	static public void updateList()
	{

		synchronized (lock)
		{
		
			for(int i= 0; i<LAYER_MAX; i++)
			{
				for(Object obj: (ArrayList<Object>)objectList[i]) 
				{
					
					if(!obj.mDelete)
					{
						obj.update();
					}
				}
			}
	
			for(int i= 0; i<LAYER_MAX; i++)
			{
				for(Object obj: (ArrayList<Object>)objectList[i]) 
				{
	
					
						if(obj.mDelete)
						{
							objectList[i].remove(obj);
							
						}
				}
			}
		}
	
	}
	
	static public void drawList(GL10 gl)
	{

		synchronized (lock)
		{
			for(int i= 0; i<LAYER_MAX; i++)
			{
				for(Object obj: (ArrayList<Object>)objectList[i]) 
				{
	
					if(!obj.mDelete)
					{
						obj.draw(gl);
					}
				}
			}
		}
	}

	//オブジェクトリストクリア
	static public void clearList()
	{

		for(int i= 0; i<LAYER_MAX; i++)
		{
			objectList[i].clear();
		}
		
	}
	
	public Object(int layer)
	{
		this.layer = layer;
		objectList[layer].add(this);
	}

	public Object()
	{
		objectList[layer].add(this);
	}

	//初期化
	abstract public void reset();

	//更新
	abstract public void update();

	//描画
	abstract public void draw(GL10 gl);

	//描画読み込む
	abstract public void loadTexture(GL10 gl, Context context, int id);

	public void delete()
	{
		mDelete = true;
	}
	

	public void setLayer( int layer ) 
	{
		objectList[this.layer].remove( this );
		this.layer = layer;
		objectList[this.layer].add( this );
	}
	
}
