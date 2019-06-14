package com.Tick_Tock.PCTIM.sdk;
import com.Tick_Tock.PCTIM.Message.*;


public interface API {
	public void SendGroupMessage(MessageFactory factory);
	
	public void SendFriendMessage(MessageFactory factory);
	
	public Group_List getgrouplist();
	
	public Friend_List getfriendlist();
}
