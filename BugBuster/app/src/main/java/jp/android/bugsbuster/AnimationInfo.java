/**

* アニメ管理クラス

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

public class AnimationInfo {

	enum ANIM_TYPE
	{
		STILL,			//静止
		LOOP,			//ループ
		ONCE,			//一回
		REVERSE,		//一回(逆)
		REVERSE_LOOP,	//ループ(逆)
		TWOWAY_LOOP		//初→後→初（round）
	};

	//アニメの情報
	private int m_nFrame = 1;							//現在フレーム
	private int m_nMaxFrame = 1;						//最大フレーム数
	private int m_nMaxFrameX = 1;						//最大フレーム数(X座標)
	private int m_nMaxFrameY = 1;						//最大フレーム数(Y座標)
	
	//アニメ情報
	private boolean m_bStart = false;					//再生フラグ
	private int m_nSpeed = 20;							//再生速度(フレーム単位) 
	
	private int m_nCounter = 0;							//カウンター
	private int m_nStartFrame = 0;						//再生スタートフレーム
	private int m_nPlayFrame = 1;						//再生エンドフレーム
	
	private ANIM_TYPE m_nAnimType = AnimationInfo.ANIM_TYPE.LOOP;	//繰り返し方
	private int m_nAnimDirect = 1;						//繰り返し方向
	

	//基本の関数
	public void init(int nFrame, int nMaxX, int nMaxY)
	{
		//初期化
		m_nCounter = 0;
		m_nStartFrame = 0;
		m_nPlayFrame = m_nMaxFrame;

		m_nFrame = nFrame;
		m_nMaxFrame = nMaxX*nMaxY;
		m_nMaxFrameX = nMaxX;
		m_nMaxFrameY = nMaxY;
		
	}

	public void reset()
	{

		//初期化
		m_nCounter = 0;
		
	}
	
	public void update()
	{
		
		//===============================//
		// アニメの更新					 //
		//===============================//
		if(m_bStart)
		{	
			//フレームがある場合
			if(m_nMaxFrame > 1)
			{
				m_nCounter++;
				m_nCounter = m_nCounter%m_nSpeed;

				if((m_nCounter%m_nSpeed)==0)
				{
					switch(m_nAnimType)
					{
									
					case STILL:
						m_bStart = false;
						break;
					case LOOP:
						m_nFrame++;
						if(m_nFrame >= (m_nStartFrame+m_nPlayFrame))
						{
							m_nFrame = m_nStartFrame;
						}
						break;
					case ONCE:
						if(m_nFrame < (m_nStartFrame+m_nPlayFrame-1))
						{
							m_nFrame++;
						}
						else
						{
							m_bStart = false;
						}
						break;
					case REVERSE:
						if(m_nFrame > 0)
						{
							m_nFrame--;
						}
						else
						{
							m_bStart = false;
						}
						break;
					case REVERSE_LOOP:
						m_nFrame--;
						if(m_nFrame < 0)
						{
							m_nFrame = m_nMaxFrame-1;
						}
						break;					
					case TWOWAY_LOOP:
						m_nFrame += m_nAnimDirect;
						if(m_nFrame <= 0) 
						{
							m_nFrame = 0;
							m_nAnimDirect = 1;
						}
						if(m_nFrame >= m_nMaxFrame-1)
						{
							m_nFrame = m_nMaxFrame-1;
							m_nAnimDirect = -1;

						}
						break;
					}
				}		
			}

			////現在のフレームに設定
			//setTextureUV(m_nFrame);
		}

	}
		
	
	public void start( boolean flag ){ m_bStart = flag; }
	public void playFrame( int nStart, int nFrame)
	{
		if((m_nStartFrame != nStart) || (m_nPlayFrame != nFrame))
		{
			m_nStartFrame = nStart;
			m_nPlayFrame = nFrame;

			//範囲チェック
			if(m_nStartFrame >= m_nMaxFrame)
			{
				m_nStartFrame = m_nMaxFrame-1;
			}

			if(m_nStartFrame < 0)
			{
				m_nStartFrame = 0;
			}

			if((m_nStartFrame+m_nPlayFrame) > m_nMaxFrame)
			{
				m_nPlayFrame = m_nMaxFrame-m_nStartFrame;
			}
			
			if(m_nPlayFrame <= 0)
			{
				m_nPlayFrame = 1;
			}

			m_nFrame = m_nStartFrame;

			//setTextureUV(m_nStartFrame);
			m_bStart = true;
			
			
		}
		
	}
	public void setType( ANIM_TYPE type ){ m_nAnimType = type; }
	public void setSpeed( int speed ){ m_nSpeed = speed; }

	public int getFrame(){ return m_nFrame; }
	public int getMaxFrame(){ return m_nMaxFrame; }
	public int getMaxFrameX(){ return m_nMaxFrameX; }
	public int getMaxFrameY(){ return m_nMaxFrameY; }

	public boolean isPlaying(){ return m_bStart; }

	public void applyFrame(PImage mImage) {
		float fSizeX = 1/(float)m_nMaxFrameX;
		float fSizeY = 1/(float)m_nMaxFrameY;
		mImage.setTile(fSizeX, fSizeY, 
						(m_nFrame%m_nMaxFrameX)*fSizeX, 
						((int)(m_nFrame/m_nMaxFrameX))*fSizeY);
	}

	public void play(int start, int num, boolean loop) {
		playFrame(start, num);
		
		if(loop)
		{
			setType(ANIM_TYPE.LOOP);
		}
		else
		{

			setType(ANIM_TYPE.ONCE);
		}
	}

	public void setFrame(int frame) {

		m_nFrame = frame;
	}
}
