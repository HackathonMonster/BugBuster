package jp.android.bugsbuster;

public class Animation extends PImage {

	  AnimationInfo anim = new AnimationInfo();


	    public Animation() {
			super();
		}

	    public Animation(int layer) {
			super(layer);
		}
	    
	    //èâä˙âª
	    public void init(int posX, int posY, int sizeX, int sizeY,
	    				 int maxX, int maxY, int maxFrame, int frame){
	    	anim.init(frame, maxX, maxY);
	    	super.init(posX, posY, sizeX, sizeY);

	    	anim.applyFrame(this);
	    }
		
	    @Override
		public void update() {
	    	anim.update();
	    	anim.applyFrame(this);
		}

		public void play(int start, int num, boolean bLoop) {
			anim.play(start,num,bLoop);
		}

		public int GetFrame() {

			return anim.getFrame();
		}

		public void SetFrame(int frame) {
			anim.setFrame(frame);
			
		}

		public void setPlaySpeed(int speed) {
			anim.setSpeed(speed);
			
		}
}
