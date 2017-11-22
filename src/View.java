import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;

public class View extends JFrame {
	private JTextPane textPane;
	private JMenuItem tip_filename;
	private JMenuItem tip_interval;
	private JMenuItem tip_font_size;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public View() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,780,450);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u5F00\u59CB");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u6253\u5F00\u6587\u4EF6");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getControl().openFile(View.this);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u5F00\u59CB\u8FD0\u884C");
		mntmNewMenuItem_3.setSize(1, 10);
		menuBar.add(mntmNewMenuItem_3);
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getControl().run(View.this);
			}
		});
		
		JMenu mnNewMenu_1 = new JMenu("\u8BBE\u7F6E");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u8BBE\u7F6E\u8F93\u51FA\u533A\u95F4");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getControl().setCharInterval(View.this);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u8BBE\u7F6E\u5B57\u4F53\u6570\u91CF\u4E0A\u9650");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getControl().setFontSize(View.this);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem item_col = new JMenuItem("\u8BBE\u7F6E\u884C\u6570\uFF0825\uFF09");
		item_col.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getControl().setCol(View.this,item_col);
			}
		});
		mnNewMenu_1.add(item_col);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("\u8BBE\u7F6E\u8F93\u51FA\u6587\u4EF6\u5939");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getControl().setOutputDir(View.this);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_2 = new JMenu("\u5173\u4E8E");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("\u4FE1\u606F");
		mnNewMenu_2.add(mntmNewMenuItem_4);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		textPane = new JTextPane();
		textPane.setFont(new Font("宋体", Font.PLAIN, 18));
		textPane.setEditable(false);
		getContentPane().add(textPane, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		tip_filename = new JMenuItem("");
		toolBar.add(tip_filename);
		
		tip_interval = new JMenuItem("");
		toolBar.add(tip_interval);
		
		tip_font_size = new JMenuItem("");
		toolBar.add(tip_font_size);
	}

	public void setTip_name(String path) {
//		tip_filename.setText("现在打开的文件是： "+path);
		tip_filename.setText(path);
	}

	public void setTip_Fontsize(int fontsize) {
		tip_font_size.setText("最大字体数量为"+fontsize);
	}

	public void setTip_interval(int interval_start, int interval_end) {
		tip_interval.setText("输出的字符的区间为 "+interval_start+"-"+interval_end);
	}

	public JTextPane getTextpane() {
		return textPane;
	}


}
