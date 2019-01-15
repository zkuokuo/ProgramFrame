package com.nibiru.framelib.player.utils;

public class Constants {
	public static final float CENTER_Z = -4.5f;
	public static final int THUMBNAIL_WIDTH = 310;
	public static final int THUMBNAIL_HEIGHT = 230;
	
	public static final int SEGMENT_THUMBNAIL_WIDTH = 304;
	public static final int SEGMENT_THUMBNAIL_HEIGHT = 154;
	
	public static final float BOTTOM_BUTTON_WIDTH = 66 / 100f / 1.5f;
	public static final float BOTTOM_BUTTON_HEIGHT = 42 / 100f / 1.5f;
	
	public static final float BOTTOM_BUTTON_WIDTH1 = 42 / 100f / 1.5f;
	public static final float BOTTOM_BUTTON_HEIGHT1 = 42 / 100f / 1.5f;
	
	public static final float BUTTON_DELETE = 30 / 100f / 2f;
	
	public static final float BUTTON_BACK_SIZE = 50 / 100f / 1.6f;
	
	public static final float BUTTON_PLAY_WIDTH = 149 / 100f / 2.5f;
	public static final float BUTTON_PLAY_HEIGHT = 218 / 100f / 2.5f;
	public static final float BUTTON_PLAY = 76 / 200f / 1.6f;
	public static final float BUTTON_PREVIEW_WIDTH = 149 / 100f / 2.5f;
	public static final float BUTTON_PREVIEW_HEIGHT = 218 / 100f / 2.5f;
	
	//播放界面
	public static final float MODE_TYPE_BG_WIDTH = 604 / 100f / 1.5f;
	public static final float MODE_TYPE_BG_HEIGHT = 334 / 100f / 1.5f;
	public static final float BTN_CONFIRM_WIDTH = 126 / 100f / 1.5f;
	public static final float BTN_CONFIRM_HEIGHT = 46 / 100f / 1.5f;
	public static final float BTN_PLAY_LOOP_WIDTH = 126 / 100f / 1.5f;
	public static final float BTN_PLAY_LOOP_HEIGHT = 46 / 100f / 1.5f;
	public static final float BTN_DECODE_WIDTH = 126 / 100f / 1.5f;
	public static final float BTN_DECODE_HEIGHT = 46 / 100f / 1.5f;


	public static final int LAYER_DELETEICON = 3;
	public static final int LAYER_ONEPLAY = 0;
	public static final int LAYER_TWOPLAY = 1;
	public static final int LAYER_TWOPRIVIEW = 2;

	//所有的传值标识
	public static final String INTENT_WHICHSCENE = "VideoSegmentScene";
	public static final String INTENT_WHICHPART = "videoSegment_0";
	public static final String INTENT_MODULETAG = "module_tag";
	public static final String INTENT_ONPAUSE = "onPause";
	public static final String INTENT_ONSTOP = "onStop";
	public static final String INTENT_CURRENTPROCESS = "currentprocess";
	public static final String INTENT_BRIGHTNESS = "brightness";
	public static final String INTENT_VOICE = "voice";
	public static final String INTENT_ISSCREENON = "isscreenon";
	public static final String INTENT_ONSTART = "onstart";
	public static final String INTENT_SEEKTO = "seekto";
	public static final String INTENT_JINGHUAMODULE = "jinghuamodule";
	public static final String INTENT_TFAMOUNT = "amount";
	public static final String INTENT_TFSTART = "tfstart";
	public static final String INTENT_TFFINISH = "tffinish";
	public static final String INTENT_HIDECONTROLLER = "hidecontroller";
	public static final String INTENT_CLARIFY = "clarify";
	public static final String INTENT_FISHEYESIZE = "fisheyesize";
	public static final String INTENT_FISHEYEPOSITION = "fisheyeposition";

	public static final int MESSAGE_PAUSE = 0;
	public static final int MESSAGE_STOP = 1;
	public static final int MESSAGE_CURRENTPROCESS = 2;
	public static final int MESSAGE_BRIGHTNESS = 3;
	public static final int MESSAGE_VOICE = 4;
	public static final int MESSAGE_ISSCREENON = 5;
	public static final int MESSAGE_START = 6;
	public static final int MESSAGE_SEEKTO = 7;
	public static final int MESSAGE_JINGHUAMODULE = 8;
	public static final int MESSAGE_TFAMOUNT = 9;
	public static final int MESSAGE_TFSTART = 10;
	public static final int MESSAGE_TFFINISH = 11;
	public static final int MESSAGE_HIDECONTROLLER = 12;
	public static final int MESSAGE_CLARIFY = 13;
	public static final int MESSAGE_FISHEYESIZE = 14;
	public static final int MESSAGE_FISHEYEPOSITION = 15;

	public static final boolean ISTOUCHINACHINA = false;

	public static boolean ISREFRESHTFCARD = false;

}
