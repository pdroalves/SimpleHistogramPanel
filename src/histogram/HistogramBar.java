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
import java.util.Random;

public abstract class HistogramBar{
	private String barName;
	private int value;
	private int xBar;
	private int yBar;
	private int widthBar;
	private int heightBar;
	private Color color;
	
	public HistogramBar(){
		barName = new String();
		value = 0;
		xBar = 0;
		yBar = 0;
		widthBar = 0;
		heightBar = 0;
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();	
		color = new Color(r,g,b);		
	}
	
	public HistogramBar(String n,int v,int x,int y,int width,int height){
		barName = n;
		value = v;
		xBar = x;
		yBar = y;
		widthBar = width;
		heightBar = height;
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();	
		color = new Color(r,g,b);
	}
	
	public HistogramBar(int x,int y,int width,int height,Color c){
		xBar = x;
		yBar = y;
		widthBar = width;
		heightBar = height;
		color = c;
	}
	
	public void setName(String n){
		barName = n;
	}
	
	public String getName(){
		return barName;
	}
	
	public void setValue(int n){
		value = n;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setX(int x){
		xBar = x;
	}

	public void setY(int y){
		yBar = y;
	}
	
	public void setWidth(int width){
		widthBar = width;
	}
	
	public void setHeight(int height){
		heightBar = height;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setRandomColor(){
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();	
		color = new Color(r,g,b);
	}

}
