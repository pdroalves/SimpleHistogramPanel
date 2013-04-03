package myDrawer;
import myTypeData.GenType;

import javax.swing.JFrame;

import myTypeData.GenTypeHistogram;

public class Drawer {

	JFrame jfrm;
	GenTypeHistogram h;
	private int height = 420;
	private int width = 420;

	public Drawer(){
		jfrm = new JFrame();
		jfrm.setSize(width, height);
		h = new GenTypeHistogram();
		h.addType(new GenType("B1",10));
		h.addType(new GenType("B2",15));
		h.addType(new GenType("B3",7));
		h.addType(new GenType("B4",25));
		h.addType(new GenType("B5",11));
		h.addType(new GenType("B6",16));
		h.addType(new GenType("B7",1));
		h.addType(new GenType("B8",19));
		h.addType(new GenType("B9",12));
		h.addType(new GenType("B10",5));
		h.addType(new GenType("B11",3));
		h.addType(new GenType("B12",75));
		h.addType(new GenType("B12",160));
		h.addType(new GenType("B12",1600000));
		h.setBarNames(true);
		h.enableLinearize(true);
		jfrm.add(h.getPanel());
		jfrm.setVisible(true);
	}	
}
