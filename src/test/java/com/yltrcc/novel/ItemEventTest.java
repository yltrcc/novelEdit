package com.yltrcc.novel;

import java.awt.FlowLayout;

import java.awt.event.ItemEvent;

import java.awt.event.ItemListener;



import javax.swing.JComboBox;

import javax.swing.JFrame;

import javax.swing.JLabel;



public class ItemEventTest {

	private JFrame frame;

	private JLabel proLabel,cityLabel;

	private JComboBox proBox,cityBox;

	private String[] proStr;

	private String[][] cityStr;

	

	

	public ItemEventTest(String[] proStr,String[][] cityStr){

		frame=new JFrame("Item Event Test！");

		proLabel=new JLabel("省份：");

		cityLabel=new JLabel("城市：");

		

		this.proStr=proStr;

		this.cityStr=cityStr;

		proBox=new JComboBox(proStr);

		cityBox=new JComboBox(cityStr[0]);

		frame.setLayout(new FlowLayout());

		

		frame.add(proLabel);

		frame.add(proBox);

		frame.add(cityLabel);

		frame.add(cityBox);

	}

	

	public void addEventHandler(){

		proBox.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent arg0) {

				int index=proBox.getSelectedIndex();

				cityBox.removeAllItems();

				for(int i=0;i<cityStr[index].length;i++){

					cityBox.addItem(cityStr[index][i]);

				}

			}

		});

	}

	

	public void showMe(){

		addEventHandler();

		frame.setSize(400,300);

		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	

	public static void main(String[] args){

		String[] proStr={"山东","山西","河北","河南","江苏","浙江"};

		String[][] cityStr={

				{"济南","烟台","青岛","潍坊","泰安"},

				{"太原","大同","平遥","临汾","晋中"},

				{"石家庄","保定","唐山","邯郸","秦皇岛"},

				{"郑州","安阳","洛阳","开封","南阳"},

				{"南京","苏州","扬州","常州","无锡"},

				{"杭州","温州","宁波","金华","义乌"}

		};

		new ItemEventTest(proStr,cityStr).showMe();

	}



}
