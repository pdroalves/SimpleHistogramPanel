//    This file is part of SimpleHistogramPanel.
//
//    SimpleHistogramPanel is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    SimpleHistogramPanel is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public License
//    along with SimpleHistogramPanel.  If not, see <http://www.gnu.org/licenses/>.

package histogram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JPanel;

public class SimpleHistogramPanel extends JPanel{

	private ArrayList<HistogramBar> data;
	private HistogramBar max;
	private boolean barNames = false;
	private int[] bgLines = {223,223,223};
	private int histWidth;
	private int histHeight;
	private int defaultStartX = 50;
	private int defaultStartY = 5;
	private int startX = defaultStartX;
	private int startY = defaultStartY;
	private String histName;
	Font defaultFont = new Font("Arial", Font.BOLD, 13);
	private FontMetrics fm;
	private ActionHighlightHBar ahhb;
	private Graphics gr;
	private ArrayList<HistogramBar> barsToMark;
	
	public SimpleHistogramPanel(){
		super();
		ahhb = new ActionHighlightHBar(this);
		this.addMouseMotionListener(ahhb);
		barsToMark = new ArrayList<HistogramBar>();
	}

	public SimpleHistogramPanel(int x,int y){
		super();
		this.setPreferredSize(new Dimension(x,y));
		ahhb = new ActionHighlightHBar(this);
		this.addMouseMotionListener(ahhb);
		barsToMark = new ArrayList<HistogramBar>();
	}
	
	public SimpleHistogramPanel(String name){
		histName  = name;
		ahhb = new ActionHighlightHBar(this);
		this.addMouseMotionListener(ahhb);
		barsToMark = new ArrayList<HistogramBar>();
	}

	public SimpleHistogramPanel(String name,int x,int y){
		this.setPreferredSize(new Dimension(x,y));
		histName  = name;
		ahhb = new ActionHighlightHBar(this);
		this.addMouseMotionListener(ahhb);
		barsToMark = new ArrayList<HistogramBar>();
	}
	
	public void setName(String name){
		histName = name;
	}
	
	public String getName(){
		return histName;
	}
	
	public void setBarNames(boolean b){
		barNames = b;
	}

	private HistogramBar getMax(){
		Iterator<HistogramBar> iterator = data.iterator();
		HistogramBar max = data.get(0);

		while(iterator.hasNext()){
			HistogramBar e = iterator.next();
			if(e.getValue() > max.getValue()){
				max = e;
			}
		}
		return max;
	}
	
	public void setData(ArrayList<HistogramBar> d){
		data = d;
		max = getMax();
		ahhb.setData(data);
	}	

	public ArrayList<HistogramBar> getData(){
		return data;
	}
	private int getTotalEvents(){
		return data.size();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		gr = g;
		int sizeX = this.getParent().getWidth();
		int sizeY = this.getParent().getHeight();
		
		// Paint histogram name
		if(histName != null){
			setFontMetrics(getFontMetrics(defaultFont));
			int txtWidth = getFontMetrics().stringWidth(histName);
			int txtHeight = getFontMetrics().getHeight();
			int x = (int)(Math.ceil(sizeX/2 - txtWidth/2));
			int y = txtHeight;
			startY = defaultStartY + y;
			gr.drawString(histName, x, y);
		}

		histWidth = sizeX-startX-10;
		
		if(barNames){
			histHeight = sizeY-startY*2 - 20;
		}else{
			histHeight = sizeY-startY*2;
		}

		drawOutsideBorders();
		drawYMarks();
		drawBars();
	}
	

	private void drawOutsideBorders(){
		gr.drawRect(startX, startY, histWidth, histHeight);		
	}

	private void drawYMarks(){
		if(data.size() > 0){
			int yMax = max.getValue();
			double yGap;
			if(yMax > 0){
				yGap = histHeight/yMax;
				int maxP = (int)(Math.ceil(Math.log10(yMax)));
				
				// Draw y-axis elements
				for(int i = maxP;i >= 0;i--){
					int mark = (int)(Math.pow(10, maxP-i));
					int xYMark = startX-20;
					int yYMark = (int)(startY+mark*yGap+7);
					
					// Draw numbers
					//gr.drawString(String.valueOf((int)(yMax - Math.pow(10, mark))), xYMark,yYMark);

					// Draw small line at y-axis
					gr.drawLine(startX, yYMark-5, startX-2, yYMark-5);
					
					// Draw gray line from y-axis to histogram end line
					gr.setColor(new Color(bgLines[0],bgLines[1],bgLines[2]));
					gr.drawLine(startX, yYMark-5,startX+histWidth,yYMark-5);
					gr.setColor(Color.black);
				}
				
				// Draw y-axis marks on selected bars
				if(barsToMark.size() > 0){
					Iterator<HistogramBar> iterator = barsToMark.iterator();
					while(iterator.hasNext()){
						HistogramBar b = iterator.next();
						gr.drawLine(startX,b.getY(),b.getX(),b.getY());
						
						// Draw numbers
						gr.drawString(String.valueOf(b.getValue()), startX-20,b.getY());
					}
				}
			}
		}
	}

	private void drawXMarks(int x,int y){
		gr.drawLine(x, y, x, y+5);
		gr.setColor(new Color(bgLines[0],bgLines[1],bgLines[2]));
		gr.drawLine(x,y,x,y-histHeight);
		gr.setColor(Color.black);

	}
	
	public void drawMark(HistogramBar b){
		if(!barsToMark.contains(b))
			barsToMark.add(b);
		System.out.println(b.getName());
		this.repaint();
	}
	
	public void dontDrawMark(HistogramBar b){
		while(barsToMark.contains(b)){
			barsToMark.remove(b);
		}
	}

	private void drawBars(){
		int totalEvents = getTotalEvents();
		int xGap = (int)(Math.ceil(histWidth*0.05));
		int yMax = getMax().getValue();
		if(yMax > 0){
			int barWidth = (int)(Math.ceil((histWidth - xGap*2)/ totalEvents));
			int barHeight = 0;
			HistogramBar e;
			Iterator<HistogramBar> iterator = data.iterator();
			
			// Desenha barras
			for(int i=0;iterator.hasNext();i++){
				e = iterator.next();
				barHeight = (int)Math.ceil(histHeight*e.getValue()/yMax);			
				drawBar(e,startX+i*barWidth+xGap,startY+histHeight-barHeight,barWidth,barHeight);
			}
		}
	}

	private void drawBar(HistogramBar t,int x,int y,int width,int height){
		Color c = t.getColor();
		Color barBorder = new Color(0,0,0);

		drawXMarks((int)(Math.ceil(x + width/2)), y + height);
		gr.setColor(c);
		gr.fillRect(x, y, width, height);
		gr.setColor(barBorder);
		gr.drawRect(x, y, width, height);
		if(barNames){
			setFontMetrics(getFontMetrics(defaultFont));
			int txtWidth = getFontMetrics().stringWidth(t.getName());
			int txtHeight = getFontMetrics().getHeight();
			int xName = (int)(Math.ceil(x + width/2 - txtWidth/2));
			int yName = y+height + txtHeight;	
			gr.drawString(t.getName(), xName, yName);
		}
		t.setX(x);
		t.setY(y);
		t.setWidth(width);
		t.setHeight(height);
	}

	public FontMetrics getFontMetrics() {
		return fm;
	}

	public void setFontMetrics(FontMetrics fm) {
		this.fm = fm;
	}


}
