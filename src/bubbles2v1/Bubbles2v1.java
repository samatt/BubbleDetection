package bubbles2v1;

import java.util.HashMap;
import java.util.Iterator;
import ddf.minim.*;
import bubbles2v1.CCVMessages;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PFont;

//import 
@SuppressWarnings("serial")
public class Bubbles2v1 extends PApplet {

	PFont font;
	OscP5 oscP5;
	Minim minim;
	AudioSnippet[] pops = new AudioSnippet[10];
	HashMap<Integer, CCVMessages> blobsHash = new HashMap<Integer, CCVMessages>();

	public void setup() {
		size(640, 640);
		// start oscP5, telling it to listen for incoming messages at port 5001
		oscP5 = new OscP5(this, 3333);
		font = loadFont("SynchroLET-60.vlw");
		textFont(font, 20);
		background(0);
		minim = new Minim(this);
	
		for(int i =0; i<pops.length;i++){
		pops[i] = minim.loadSnippet("Pop"+i+".mp3");
		}

	}

	public void draw() {
		for (int i = 0; i < pops.length; i++) {
			if (!pops[i].isPlaying()) {
				pops[i].rewind();
			}
		}

	}

	public void keyPressed() {

		Iterator<CCVMessages> i = blobsHash.values().iterator();
		println("new");
		while (i.hasNext()) {
			CCVMessages checkBlob = (CCVMessages) i.next();

			println("Blob Id" + " " + checkBlob.blobId + " " + checkBlob.x
					+ " " + checkBlob.y);
		}

	}

	public void mouseClicked() {

	}

	void oscEvent(OscMessage theOscMessage) {

		String firstValue = theOscMessage.get(0).stringValue();
		
		
		if (firstValue.equals("fseq")) {
			// this type of OSC message tells you the frame count.
			// we don't really use it for our application
		} else if (firstValue.equals("set")) {

			// The set message contains a info about an individual blob id.
			// The message format is as follows
			// Set s x y X Y m
			// sessionID position velocity motion

			// println(firstValue);
			// session id

			int secondValue = theOscMessage.get(1).intValue();
			// println(secondValue);
			// position x
			float Value = theOscMessage.get(2).floatValue();
			// position y
			float Value1 = theOscMessage.get(3).floatValue();
			// velocity X
			float Value2 = theOscMessage.get(4).floatValue();
			// velocity Y
			float Value3 = theOscMessage.get(5).floatValue();
			// Motion
			float Value4 = theOscMessage.get(6).floatValue();
			println("Blob Data: "+ secondValue + Value + Value1 +Value2 + Value3 + Value4);
			CCVMessages thisBlob = new CCVMessages(secondValue, Value, Value1,
					Value2, Value3, Value4);
			// if(!blobsHash.containsKey(thisBlob.blobId)){
			blobsHash.put(thisBlob.blobId, thisBlob);
			
			// }

		} else if (firstValue.equals("alive")) {
			// alive message format:
			// 'alive' session ids
			// The problem with this message is that it doesnt tell you how many
			// session ids to expect
			// for now arbitrarily running through a loop a 100 times to get all
			// the available Ids.
			// Max number of blobs detected by CCV is configurable. config.xml
			// in the CCV folder.
			//IMPORTANT: Sometimes see an OscP5 error because blob.x and blob.y values are not coming in
			// need to put a check in to ensure a number is coming in before playing a pop.
			// Isnt a critical issue as the code still runs fine, but it will make it more robust.
			
			int[] idList = new int[100];
			for (int i = 1; i < 100; i++) {
				if (theOscMessage.get(i) != null) {
					int Value = theOscMessage.get(i).intValue();
					idList[i] = Value;
					//println(Value);
				}
			}

			// This iterator runs through the HashMap of current bubbles that
			// are 'alive'and compares them to the new list that was just
			// received in the alive message.
			// If the HashMap has an Id that doesn't exist in the new idList it
			// can be inferred that the
			// bubble no longer exists.
			Iterator<CCVMessages> i = blobsHash.values().iterator();

			while (i.hasNext()) {
				CCVMessages checkBlob = (CCVMessages) i.next();
				if (compareIds(idList, checkBlob.blobId)) {
				
				} 
				else {
					background(0);
					textAlign(LEFT, CENTER);
					text("POP!" + checkBlob.blobId, (checkBlob.x * 320),
							(checkBlob.y * 320));
					println(checkBlob.blobId);
					//This line ensure that a pop is not moade when bubbles fall
					//of the screen.
					if (checkBlob.y > 0.9 || checkBlob.x > 0.9
							|| checkBlob.x < 0.01 || checkBlob.y < 0.01) {
					println("ignored:" + checkBlob.blobId);
					} 
					else {
						float r = random(0,39);
						pops[(int)r].play();
					}

					println("removed:" + checkBlob.blobId);
					i.remove();

				}
			}
		}

	}

	boolean compareIds(int[] idList, int blobId) {

		for (int i = 0; i < idList.length; i++) {

			if (idList[i] == blobId) {

				return true;
			}
		}

		return false;
	}

}
