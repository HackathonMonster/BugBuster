/**

* �A�j���Ǘ��N���X

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         �K���ɂ������

*/
package jp.android.bugsbuster;

import jp.android.bugsbuster.processing.PImage;

public class AnimationInfo {

	enum ANIM_TYPE
	{
		STILL,			//�Î~
		LOOP,			//���[�v
		ONCE,			//���
		REVERSE,		//���(�t)
		REVERSE_LOOP,	//���[�v(�t)
		TWOWAY_LOOP		//�����と���iround�j
	};

	//�A�j���̏��
	private int m_nFrame = 1;							//���݃t���[��
	private int m_nMaxFrame = 1;						//�ő�t���[����
	private int m_nMaxFrameX = 1;						//�ő�t���[����(X���W)
	private int m_nMaxFrameY = 1;						//�ő�t���[����(Y���W)
	
	//�A�j�����
	private boolean m_bStart = false;					//�Đ��t���O
	private int m_nSpeed = 20;							//�Đ����x(�t���[���P��) 
	
	private int m_nCounter = 0;							//�J�E���^�[
	private int m_nStartFrame = 0;						//�Đ��X�^�[�g�t���[��
	private int m_nPlayFrame = 1;						//�Đ��G���h�t���[��
	
	private ANIM_TYPE m_nAnimType = AnimationInfo.ANIM_TYPE.LOOP;	//�J��Ԃ���
	private int m_nAnimDirect = 1;						//�J��Ԃ����
	

	//��{�̊֐�
	public void init(int nFrame, int nMaxX, int nMaxY)
	{
		//����
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

		//����
		m_nCounter = 0;
		
	}
	
	public void update()
	{
		
		//===============================//
		// �A�j���̍X�V					 //
		//===============================//
		if(m_bStart)
		{	
			//�t���[��������ꍇ
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

			////���݂̃t���[���ɐݒ�
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

			//�͈̓`�F�b�N
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
