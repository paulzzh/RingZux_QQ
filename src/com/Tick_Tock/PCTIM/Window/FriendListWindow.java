package com.Tick_Tock.PCTIM.Window;
import com.googlecode.lanterna.gui2.*;
import java.util.*;
import com.Tick_Tock.PCTIM.*;
import com.googlecode.lanterna.gui2.table.*;
import com.Tick_Tock.PCTIM.sdk.*;
import com.Tick_Tock.PCTIM.Utils.*;
import com.googlecode.lanterna.gui2.dialogs.*;

public class FriendListWindow extends BasicWindow
{

	private Panel contentPanel;

	private ChatWindow chatwindow;

	private Button button;

	private Table<String> table;

	
	public FriendListWindow(String title,ChatWindow _chatwindow){
		super(title);
		this.chatwindow = _chatwindow;
		this.setHints(Arrays.asList(Window.Hint.FIXED_SIZE,Window.Hint.NO_POST_RENDERING));
		this.contentPanel = new Panel(new LinearLayout(Direction.VERTICAL)); // can hold multiple sub-components that will be added to a wind
		this.table= new Table<String>("QQ","好友名称","最后消息");
		this.table.setSelectAction(new Runnable() {
				@Override
				public void run() {
					List<String> data = table.getTableModel().getRow(table.getSelectedRow());
					FriendListWindow.this.chatwindow.onupdate(Long.parseLong(data.get(0)),1,data.get(1));
					FriendListWindow.this.getTextGUI().setActiveWindow(FriendListWindow.this.chatwindow);
				}
			});
		this.contentPanel.addComponent(this.table);
		this.setComponent(this.contentPanel);
		
	}

	

	
public void onmessage(QQMessage message){
	List<List<String>> rows = this.table.getTableModel().getRows();
	int index=0;
	for(List<String> row:rows){
		if(Long.parseLong(row.get(0))==message.Sender_Uin){
			this.table.getTableModel().setCell(2,index,message.Message);
		}
		index+=1;
	}
}
	
	
	public void setfriendlist(final QQUser user){
		
		if(user.friend_list!=null){
			for(Friend_List.Friend members:user.friend_list.members){
				this.table.getTableModel().addRow(""+members.friend_uin,members.friend_name, "");
			}
			
		}else{
			
		}
	
	}


	




	
	




}
