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
	private int startX = 50;
	private int startY = 0;
	private String histName;
	Font defaultFont = new Font("Arial", Font.BOLD, 13);
	private FontMetrics fm;

	public SimpleHistogramPanel(){
	}

	public SimpleHistogramPanel(int x,int y){
		this.setPreferredSize(new Dimension(x,y));
	}
	
	public SimpleHistogramPanel(String name){
		histName  = name;
	}

	public SimpleHistogramPanel(String name,int x,int y){
		this.setPreferredSize(new Dimension(x,y));
		histName  = name;
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
	}	

	private int getTotalEvents(){
		return data.size();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		int sizeX = this.getParent().getWidth();
		int sizeY = this.getParent().getHeight();
		
		// Paint histogram name
		if(histName != null){
			setFontMetrics(getFontMetrics(defaultFont));
			int txtWidth = getFontMetrics().stringWidth(histName);
			int txtHeight = getFontMetrics().getHeight();
			int x = (int)(Math.ceil(sizeX/2 - txtWidth/2));
			int y = txtHeight;
			startY = startY + y;
			g.drawString(histName, x, y);
		}

		histWidth = sizeX-startX-10;
		histHeight = sizeY-startY*2;
		
		if(barNames){
			histHeight =- 20;
		}

		drawOutsideBorders(g);
		drawYMarks(g);
		drawBars(g);
	}
	

	private void drawOutsideBorders(Graphics g){
		g.drawRect(startX, startY, histWidth, histHeight);		
	}

	private void drawYMarks(Graphics g){
		if(data.size() > 0){
			int yMax = max.getValue();
			double yGap;
			if(yMax > 0){
				yGap = histHeight/yMax;
				
				// Desenha marcadores a esquerda
				for(int i = 0;i <= yMax;i++){
					int xYMark = startX-20;
					int yYMark = (int)(startY+i*yGap+7);
					g.drawString(String.valueOf(yMax - i), xYMark,yYMark);
					g.drawLine(startX, yYMark-5, startX-2, yYMark-5);
					g.setColor(new Color(bgLines[0],bgLines[1],bgLines[2]));
					g.drawLine(startX, yYMark-5,startX+histWidth,yYMark-5);
					g.setColor(Color.black);
				}
			}
		}
	}

	private void drawXMarks(int x,int y,Graphics g){
		g.drawLine(x, y, x, y+5);
		g.setColor(new Color(bgLines[0],bgLines[1],bgLines[2]));
		g.drawLine(x,y,x,y-histHeight);
		g.setColor(Color.black);

	}

	private void drawBars(Graphics g){
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
				drawBar(e,startX+i*barWidth+xGap,startY+histHeight-barHeight,barWidth,barHeight,g);
			}
		}
	}

	private void drawBar(HistogramBar t,int x,int y,int width,int height,Graphics gr){
		Color c = t.getColor();
		Color barBorder = new Color(0,0,0);

		drawXMarks((int)(Math.ceil(x + width/2)), y + height,gr);
		gr.setColor(c);
		gr.fillRect(x, y, width, height);
		gr.setColor(barBorder);
		gr.drawRect(x, y, width, height);
		if(barNames){
			gr.drawString(t.getName(), (int)(Math.ceil(x + width/3)), y+height+20);
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
