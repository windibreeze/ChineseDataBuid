import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.Predicate;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Control {
	private static Control control;
	private FileDialog fileDialog;
	private File inputFile;
	private String font[];// ={"宋体","隶书","幼圆","微软雅黑","华文琥珀"};
	private int fontsize = 0;
	private int interval_start = 0;
	private int interval_end = 0;
	private int max_col = 25;
	private List<String> data;
	private Document document;
	private Point point;
	private Dimension dimension;
	private int count=0;
	private File outdir;
	private Control() {
	}

	public static Control getControl() {
		if (control == null)
			control = new Control();
		return control;
	}

	public void openFile(View view) {
		fileDialog = new FileDialog(view, "请选择输入的数据文件");
		fileDialog.setVisible(true);
		;
		String filename = fileDialog.getFile();
		String filedir = fileDialog.getDirectory();
		inputFile = new File(filedir, filename);
		data = FileRead.getStringData(inputFile);
		view.setTip_name(inputFile.getPath());
	}

	public void setFontSize(View view) {
		font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		String value = JOptionPane.showInputDialog(view, "请输入字体数量的上限，最大为" + font.length, "输入字体数量",
				JOptionPane.QUESTION_MESSAGE);
		try {
			fontsize = Integer.valueOf(value);
			if (fontsize > font.length)
				throw new Exception();
			view.setTip_Fontsize(fontsize);
			Control.getControl().init(view);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "请输入字体的数量上限，最大为" + font.length + "\n请不要输入数字以为的字符", "警告！",
					JOptionPane.WARNING_MESSAGE);
			setFontSize(view);
		}
	}

	public void setCharInterval(View view) {
		String value = JOptionPane.showInputDialog(view, "请输入输出的字符的区间，用-分开。如 0-"+String.valueOf(data.size()-1), "输入输出字符区间",
				JOptionPane.QUESTION_MESSAGE);
		String temp[] = value.split("-");
		try {
			interval_start = Integer.valueOf(temp[0]);
			interval_end = Integer.valueOf(temp[1]);
			if (interval_start < 0 || interval_end < interval_start)
				throw new Exception();
			view.setTip_interval(interval_start, interval_end);
			count=interval_start;
			Control.getControl().init(view);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "请输入输出字符的区间。\n按照格式输出，如 1-100 。\n不要输入其他东西", "警告",
					JOptionPane.WARNING_MESSAGE);
			setCharInterval(view);
		}
	}

	public void setCol(View view, JMenuItem item_col) {
		String value = JOptionPane.showInputDialog(view, "请输入最大的列数：", "请输入列数", JOptionPane.QUESTION_MESSAGE);
		try {
			max_col = Integer.valueOf(value);
			if (max_col < 1)
				throw new Exception();
			item_col.setText("设置列数（" + max_col + "）");
			Control.getControl().init(view);
		} catch (Exception e) {
			e.printStackTrace();
			setCol(view, item_col);
		}
		
	}

	public void run(View view) {
		Control.getControl().init(view);
		if(view.isAlwaysOnTop()==false)
			view.setAlwaysOnTop(true);
		try {
			if(count<=interval_end){
				Thread thread=new Thread(new Thread_main(count));
				thread.start();
				thread.join();
				count++;
			}
			else{
				JOptionPane.showMessageDialog(view, "循环已结束");
				view.setAlwaysOnTop(false);
			}
//			SwingUtilities.invokeLater(new Thread_main(i));
//			Thread.sleep(5000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
	}

	public void init(View view) {
		if (inputFile == null) {
			JOptionPane.showMessageDialog(view, "请选择数据文件", "警告", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (data == null)
			data = FileRead.getStringData(inputFile);
		if (font == null)
			font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		if(fontsize==0)
			fontsize = font.length;
		if(interval_end==0)
			interval_end = data.size();
		if(count==0)
			count=interval_start;
		JTextPane textPane=view.getTextpane();
//		point=textPane.getLocation();
		point=textPane.getLocationOnScreen();
		dimension= textPane.getSize();
		document = view.getTextpane().getDocument();
	}

	public void print(int index) {
		String str=data.get(index);
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setFontSize(set, 18);
		try {
			document.remove(0,document.getLength());
			int count = 0;
			for (int i = 1; i < fontsize; i++) {
				StyleConstants.setFontFamily(set, font[i]);
				document.insertString(document.getLength(), str+"  ", set);
				count++;
				if (count >= max_col) {
					document.insertString(document.getLength(), "\n", set);
					count = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void getScreen(int index) {
		String str=data.get(index);
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(point.x,point.y,dimension.width, dimension.height));
			String imgname=Integer.toHexString(str.charAt(0));
			File imgfile = new File(outdir,imgname + ".bmp");
			ImageIO.write(image, "bmp", imgfile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private class Thread_print implements Runnable{
		private int index;
		public Thread_print(int index){
			super();
			this.index=index;
		}
		public void run() {
			Control.getControl().print(index);
//			System.out.println("print  "+index+"__"+data.get(index));
		};
	}
	private class Thread_screen implements Runnable{
		private int index;
		public Thread_screen(int index){
			super();
			this.index=index;
		}
		public void run() {
			Control.getControl().getScreen(index);
//			System.out.println("get screen "+index+"__"+data.get(index));
		};
	}
	
	private class Thread_main implements Runnable{
		private int index;
		public Thread_main(int index){
			super();
			this.index=index;
		}
		public void run() {
			try {
				
				Thread thread=new Thread(new Thread_print(index));
				thread.start();
				thread.join();
				SwingUtilities.invokeLater(new Thread_screen(index));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		};
		
	}

	public void setOutputDir(View view) {
//		fileDialog = new FileDialog(view, "请选择输出的文件夹");
//		fileDialog.setVisible(true);
////		String filename = fileDialog.getFile();
//		String filedir = fileDialog.getDirectory();
//		outdir = new File(filedir);
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("请选择输出的文件夹");
		jfc.setDialogType(JFileChooser.OPEN_DIALOG);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = jfc.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
		   outdir= jfc.getSelectedFile();
		   view.setTitle("结果输出至"+outdir.getAbsolutePath());
		}
		Control.getControl().init(view);
	}
}
