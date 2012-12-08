package bubbles2v1;
//import oscP5.*;
import processing.core.PApplet;


public class CCVMessages extends PApplet
{


	  public int blobId;
	  public float x,y;
	  public float vecX, vecY;
	  public float motion;
	  
	  public CCVMessages(int BlobId, float Xpos, float Ypos, float VecX, float VecY, float Motion){
	 
	    //bubble id
	    blobId = BlobId;
	    //position x
	    x = Xpos;
	    //position y
	    y = Ypos;
	    //velocity X
	    vecX =  VecX;
	    //velocity Y
	    vecY = VecY;
	    // Motion
	    motion = Motion;

	  }
	  
	    

	

}
