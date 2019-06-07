package com.Tick_Tock.PCTIM.Window;

import com.googlecode.lanterna.gui2.*;
import java.util.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.Tick_Tock.PCTIM.*;
import com.Tick_Tock.PCTIM.sdk.*;
import com.Tick_Tock.PCTIM.Utils.*;
import com.googlecode.lanterna.gui2.table.*;

public class GroupListWindow extends BasicWindow
{

	private Panel contentPanel;

	private Button button;

	private ChatWindow chatwindow;

	private Table<String> table;
	public GroupListWindow(String title,ChatWindow _chatwindow){
		super(title);
		this.chatwindow=_chatwindow;
		this.setHints(Arrays.asList(Window.Hint.FIXED_SIZE,Window.Hint.NO_POST_RENDERING));
		this.contentPanel = new Panel(new LinearLayout(Direction.VERTICAL)); // can hold multiple sub-components that will be added to a wind
		this.table= new Table<String>("QQ","群名称","最后消息");
		this.table.setSelectAction(new Runnable() {
				@Override
				public void run() {
					List<String> data = table.getTableModel().getRow(table.getSelectedRow());
					GroupListWindow.this.chatwindow.onupdate(Long.parseLong(data.get(0)),2,data.get(1));
					GroupListWindow.this.getTextGUI().setActiveWindow(GroupListWindow.this.chatwindow);
				}
			});
		this.contentPanel.addComponent(this.table);
		this.setComponent(this.contentPanel);
		
	}

	
	
	public void onmessage(QQMessage message){
		List<List<String>> rows = this.table.getTableModel().getRows();
		int index=0;
		for(List<String> row:rows){
			if(Long.parseLong(row.get(0))==message.Group_uin){
				this.table.getTableModel().setCell(2,index,message.SendName+": "+message.Message);
			}
			index+=1;
		}
	}
	
	
	
	public void setgrouplist(QQUser user)
	{
		if(user.friend_list!=null){
			for(Group_List.Group group:user.group_list.getall_group()){
				this.table.getTableModel().addRow(""+group.group_uin,group.group_name, "");
			}

		}else{

		}
	}
}

