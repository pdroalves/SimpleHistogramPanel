import javax.swing.SwingUtilities;
import myDrawer.Drawer;

public class Principal {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				new Drawer();
			}		
		});
	}

}
