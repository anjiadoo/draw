package mnnu.ad.draw;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.mr.util.DrawImageUtil;
import com.mr.util.FrameGetShape;
import com.mr.util.ShapeWindow;
import com.mr.util.Shapes;

/**
 * 画图主窗体
 * 
 * @author anjiadoo
 *
 */
public class DrawPictureFrame extends JFrame implements FrameGetShape { // 继承窗体类, 实现了新接口

	BufferedImage image = new BufferedImage(1000, 650, BufferedImage.TYPE_INT_BGR);// 创建一个8位BGR颜色分量的图像
	Graphics gs = image.getGraphics();                   // 获得图像的绘图对象
	Graphics2D g = (Graphics2D) gs;                      // 将绘图对象转换成Graphics2D类型
	DrawPictureCanvas canvas = new DrawPictureCanvas();  // 创建画布对象
	Color foreColor = Color.BLACK;                       // 定义前景色
	Color backgroundColor = Color.WHITE;                  // 定义背景色
	int x = -1;     // 上一次鼠标绘制点的横坐标
	int y = -1;     // 上一次鼠标绘制点的纵坐标
	boolean rubber = false;                              // 橡皮标识变量
	boolean drawShape = false;                           // 画图形标识变量

	// 声明工具栏和工具栏按钮	
	private JToolBar toolBar;                            // 工具栏
	private JButton  eraserButton;                       // 橡皮按钮
	private JToggleButton strokeButton1;                 // 细线按钮
	private JToggleButton strokeButton2;                 // 粗线按钮
	private JToggleButton strokeButton3;                 // 较粗按钮
	private JButton backgroundButton;                    // 背景色按钮
	private JButton foregrounButton;                     // 前景色按钮
	private JButton clearButton;                         // 清除按钮
	private JButton savebButton;                         // 保存按钮
	private JButton shapeButton;                         // 图形按钮
	Shapes shape;                                        // 绘画的图形
	
	// 声明菜单栏和菜单项
	private JMenuBar menuBar;                            // 菜单栏
	private JMenuItem strokeMenuItem1;                   // 细线菜单
	private JMenuItem strokeMenuItem2;                   // 粗线菜单
	private JMenuItem strokeMenuItem3;                   // 较粗菜单
	private JMenuItem clearMenuItem;                     // 清除菜单
	private JMenuItem foregroundMenuItem;                // 前景色菜单
	private JMenuItem backgroundMenuItem;                // 背景色菜单
	private JMenuItem eraserMenuItem;                    // 橡皮菜单
	private JMenuItem exitMenuItem;                      // 退出菜单
	private JMenuItem saveMenuItem;                      // 保存菜单
	private JMenuItem shuiyinItem;                       // 水印菜單
	private String shuiyin = "";                         // 水印內容
	
	// 关联简笔画窗体
	private PictureWindow picWindow;                     // 简笔画展示窗体
	private JButton showPicBut;                          // 展开简笔画按钮

	/**
	 * 构造方法，添加组件的初始化方法,监听方法
	 */
	public DrawPictureFrame() {
		setResizable(false);                             // 窗体不能改变大小
		setTitle("画板(水印内容[" + shuiyin +"])");         // 设置标题
		Toolkit tk=Toolkit.getDefaultToolkit();          // 获得系统默认的组件工具包用于修改程序图标
	    Image image=tk.createImage("src/image/icon/画板.png");
		setIconImage(image);                             // 修改程序图标
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 窗体关闭则停止程序
		setBounds(500, 100, 1000, 650);                   // 设置窗口位置和宽高
		init();                                          // 组件初始化
		addListener();                                   // 添加组件监听
	}

	/**
	 * 组件初始化
	 */
	private void init() {
		g.setColor(backgroundColor);                  // 用背景色设置绘图对象的颜色
		g.fillRect(0, 0, 1000, 650);                   // 用背景色填充整个画布
		g.setColor(foreColor);                        // 用前景色设置绘图对象的颜色
		canvas.setImage(image);                       // 设置画布的图像
		getContentPane().add(canvas);                 // 将画布添加到窗体容器默认布局的中部位置
		
		// 工具栏和工具栏按钮
		toolBar = new JToolBar();                     // 初始化工具栏
		getContentPane().add(toolBar, BorderLayout.NORTH); // 工具栏添加到窗口最北位置
		toolBar.setBackground(Color.LIGHT_GRAY);      // 设置工具栏背景色
		
		showPicBut = new JButton();
		showPicBut.setToolTipText("展开简笔画");        // 鼠标悬停提示
		showPicBut.setIcon(new ImageIcon("src/image/icon/展开.png"));//设置按钮图标
		toolBar.add(showPicBut);
		
		savebButton = new JButton();
		savebButton.setToolTipText("保存");
		savebButton.setIcon(new ImageIcon("src/image/icon/保存.png"));
		toolBar.add(savebButton);                     // 将按钮添加到工具栏
		toolBar.addSeparator();                       // 添加分割线
		
		strokeButton1 = new JToggleButton();
		strokeButton1.setToolTipText("细线");
		strokeButton1.setIcon(new ImageIcon("src/image/icon/像素1.png"));
		strokeButton1.setSelected(true);              // 细线按钮处于被选中位置
		toolBar.add(strokeButton1);
		
		strokeButton2 = new JToggleButton();
		strokeButton2.setToolTipText("粗线");
		strokeButton2.setIcon(new ImageIcon("src/image/icon/像素2.png"));
		toolBar.add(strokeButton2);
		
		strokeButton3 = new JToggleButton();
		strokeButton3.setToolTipText("较粗");
		strokeButton3.setIcon(new ImageIcon("src/image/icon/像素3.png"));
		toolBar.add(strokeButton3);
		
		ButtonGroup strokeGroup = new ButtonGroup();  // 画笔粗细按钮，保证同时只有一个按钮被选中
		strokeGroup.add(strokeButton1);               // 添加到一个组里面
		strokeGroup.add(strokeButton2);
		strokeGroup.add(strokeButton3);
		toolBar.addSeparator();                       // 添加分割线
		
		backgroundButton = new JButton();
		backgroundButton.setToolTipText("背景颜色");
		backgroundButton.setIcon(new ImageIcon("src/image/icon/背景色.png"));
		toolBar.add(backgroundButton);
		foregrounButton = new JButton();
		foregrounButton.setToolTipText("前景颜色");
		foregrounButton.setIcon(new ImageIcon("src/image/icon/前景色.png"));
		toolBar.add(foregrounButton);
		toolBar.addSeparator();
		
		shapeButton = new JButton();
		shapeButton.setToolTipText("图形");
		shapeButton.setIcon(new ImageIcon("src/image/icon/形状.png"));
		toolBar.add(shapeButton);
		clearButton = new JButton();
		clearButton.setToolTipText("清除");
		clearButton.setIcon(new ImageIcon("src/image/icon/清除.png"));
		toolBar.add(clearButton);
		eraserButton = new JButton();
		eraserButton.setToolTipText("橡皮");
		eraserButton.setIcon(new ImageIcon("src/image/icon/橡皮.png"));
		toolBar.add(eraserButton);
		
		// 菜单栏和菜单项
		menuBar = new JMenuBar();                      // 创建菜单栏
		setJMenuBar(menuBar);                          // 窗体载入菜单栏
		
		JMenu systemMenu = new JMenu("系统");           // 初始化菜单对象，并添加文本
		menuBar.add(systemMenu);                       // 菜单栏添加菜单对象
		shuiyinItem = new JMenuItem("水印");            // 初始化菜单项对象，并添加文本
		systemMenu.add(shuiyinItem);                   // 菜单添加菜单项
		saveMenuItem = new JMenuItem("保存");           // 初始化菜单项对象，并添加文本
		systemMenu.add(saveMenuItem);                  // 菜单添加菜单项
		systemMenu.addSeparator();                     // 添加分割线
		exitMenuItem = new JMenuItem("退出");
		systemMenu.add(exitMenuItem);
		
		JMenu strokeMenu = new JMenu("线型");
		menuBar.add(strokeMenu);
		strokeMenuItem1 = new JMenuItem("细线");
		strokeMenu.add(strokeMenuItem1);
		strokeMenuItem2 = new JMenuItem("粗线");
		strokeMenu.add(strokeMenuItem2);
		strokeMenuItem3 = new JMenuItem("较粗");
		strokeMenu.add(strokeMenuItem3);
		
		JMenu colorMenu = new JMenu("颜色");
		menuBar.add(colorMenu);
		foregroundMenuItem = new JMenuItem("前景颜色");
		colorMenu.add(foregroundMenuItem);
		backgroundMenuItem = new JMenuItem("背景颜色");
		colorMenu.add(backgroundMenuItem);
		
		JMenu editMenu= new JMenu("编辑");
		menuBar.add(editMenu);
		clearMenuItem = new JMenuItem("清除");
		editMenu.add(clearMenuItem);
		eraserMenuItem = new JMenuItem("橡皮");
		editMenu.add(eraserMenuItem);
		
		// 创建简笔画面板，并将本类作为父类
		picWindow = new PictureWindow(DrawPictureFrame.this);

	}// init() end
	
	/**
	 * 为组件添加动作监听
	 */
	private void addListener() {
		canvas.addMouseMotionListener(new MouseMotionListener() { // 为画板添加鼠标移动事件监听
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (rubber) {
					// 设置鼠标指针的形状为图片
					Toolkit kit = Toolkit.getDefaultToolkit();// 获得系统默认的组件工具包
					// 利用工具包获取图片
					Image image1 = kit.createImage("src/image/icon/鼠标橡皮.png");
					// 利用工具包创建一个自定义的光标对象
					// 参数为图片，光标热点和光标描述字符串
					Cursor c = kit.createCustomCursor(image1, new Point(0, 25), "clear");
					setCursor(c);// 使用自定义光标
				} else {
					// 设置鼠标指针的形状为图片
					Toolkit kit = Toolkit.getDefaultToolkit();// 获得系统默认的组件工具包
					// 利用工具包获取图片
					Image image2 = kit.createImage("src/image/icon/画笔.png");
					// 利用工具包创建一个自定义的光标对象
					// 参数为图片，光标热点和光标描述字符串
					Cursor c = kit.createCustomCursor(image2, new Point(0, 25), "clear");
					setCursor(c);// 使用自定义光标
					
					/* 设置鼠标指针的形状为十字光标(系统自带)
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));*/
				}
			}
			
			@Override
			public void mouseDragged(final MouseEvent e) { // 当鼠标拖拽时
				if(x > 0 && y >0) {                   // 如果x 和 y 存在鼠标记录
					if (rubber) {                     // 如果橡皮标识为true，表示使用橡皮
						g.setColor(backgroundColor);  // 绘图工具使用背景色
						g.fillOval(x, y, 10, 10);     // 在鼠标滑过的位置画填充的圆形
					} else {
						g.drawLine(x, y, e.getX(), e.getY());  // 在鼠标滑过的位置画直线
					}
				} //if end
			    x = e.getX();           // 上一次鼠标绘制点的横坐标
			    y = e.getY();           // 上一次鼠标绘制点的纵坐标
			    canvas.repaint();       // 更新画布
		    } // mouseDragged() end
		});
		
		canvas.addMouseListener(new MouseAdapter() { // 为画板添加鼠标单击事件监听
			
			@Override
			public void mouseReleased(final MouseEvent arg0) { // 当按键抬起时
				x = -1;       // 将记录上一次鼠标绘制点的横坐标恢复成-1
				y = -1;       // 将记录上一次鼠标绘制点的纵坐标恢复成-1
			}
			
			@Override
			public void mousePressed(MouseEvent e) {  // 当按下键时
				if (drawShape) {
					switch (shape.getType()) {
					case Shapes.YUAN:
						// 计算坐标，让鼠标处于图形的中心位置
						int yuanX = e.getX() - shape.getWidth();
						int yuanY = e.getY() - shape.getHeigth();
						// 创建圆形图形，并指定坐标和宽高
						Ellipse2D yuan = new Ellipse2D.Double(yuanX, yuanY, shape.getWidth(), shape.getHeigth());
						g.draw(yuan);       // 画图工具画此圆形
						break;
						
					case Shapes.FANG:
						// 计算坐标，让鼠标处于图形的中心位置
						int fangX = e.getX() - shape.getWidth();
						int fangY = e.getY() - shape.getHeigth();
						// 创建方形图形，并指定坐标和宽高
						Rectangle2D fang = new Rectangle2D.Double(fangX, fangY, shape.getWidth(), shape.getHeigth());
						g.draw(fang);      // 画图工具画次方形
						break;
						
					default:
						break;
					}
					canvas.repaint();      // 更新画布
					drawShape = false;     // 画图形标识变量为false，说明现在鼠标画的是线条，而不是图形
				}
			}
		});
		
		toolBar.addMouseMotionListener(new MouseMotionListener() { // 工具栏添加鼠标移动监听
			
			@Override
			public void mouseMoved(final MouseEvent arg0) {
				// 设置鼠标指针的形状为默认光标
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));	
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		strokeButton1.addActionListener(new ActionListener() { // "细线"按钮添加动作监听

			@Override
			public void actionPerformed(final ActionEvent e) {
				// 声明画笔的属性，粗细为1像素，线条末端无修饰，折线处呈尖角
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // 画图工具使用此画笔
			}
		});
		
		strokeButton2.addActionListener(new ActionListener() { // "粗线"按钮添加动作监听

			@Override
			public void actionPerformed(final ActionEvent e) {
				// 声明画笔的属性，粗细为2像素，线条末端无修饰，折线处呈尖角
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // 画图工具使用此画笔
			}
		});
		
		strokeButton3.addActionListener(new ActionListener() { // "较粗"按钮添加动作监听

			@Override
			public void actionPerformed(final ActionEvent e) {
				// 声明画笔的属性，粗细为3像素，线条末端无修饰，折线处呈尖角
				BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // 画图工具使用此画笔
			}
		});
		
		backgroundButton.addActionListener(new ActionListener() { // 背景颜色按钮添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent e) { // 单击时
				
				//打开选择颜色对话框，参数依次为：父窗体，标题，默认选中的颜色(青色)
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if (bgColor != null) {
					backgroundColor = bgColor;// 将选中的颜色赋给背景色变量
				}
				//背景色按钮也更换为这种背景颜色
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);  // 绘图工具使用背景色
				g.fillRect(0, 0, 1000, 650);   // 画一个背景颜色的方形填满整个画布
				g.setColor(foreColor);        // 绘图工具使用前景色
				canvas.repaint();             // 更新画布
			}
		});
		
		foregrounButton.addActionListener(new ActionListener() { // 前景颜色按钮添加动作监听

			@Override
			public void actionPerformed(final ActionEvent arg0) { // 单击时
				
				// 打开选择颜色对话框，参数依次为：父窗体，标题，默认选中的颜色(青色)
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if (fColor != null) {        // 如果选中的颜色不是空的
					foreColor = fColor;      // 将选中的颜色赋给前景色变量
				}
				// 前景色按钮也更换为这种背景颜色
				foregrounButton.setBackground(foreColor);
				g.setColor(foreColor);       // 绘图工具使用前景色
			}
		});
		
		clearButton.addActionListener(new ActionListener() { // 为清除按钮添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // 单击时
				g.setColor(backgroundColor);  // 绘图工具使用背景色
				g.fillRect(0, 0, 1000, 650);   // 画一个背景色的方形填满整个画布
				g.setColor(foreColor);        // 绘图工具使用前景色
				canvas.repaint();             // 刷新画布
			}
		});
		
		eraserButton.addActionListener(new ActionListener() { // 为橡皮按钮添加动作监听 
			
			@Override
			public void actionPerformed(final ActionEvent atg0) { // 单击时
				// 单击工具栏上的橡皮按钮，使用橡皮
				if (eraserButton.getToolTipText().equals("橡皮")) {
					rubber = true;              // 设置橡皮标识为true
					// 改变按钮上显示的图标为"画笔"
					eraserButton.setToolTipText("画图");
					eraserButton.setIcon(new ImageIcon("src/image/icon/画笔.png"));
				} else { // 单击工具栏上的画图按钮，使用画笔
					rubber = false;             // 设置橡皮标识符为false
					// 改变按钮上显示的图标为"橡皮"
					eraserButton.setToolTipText("橡皮");
					eraserButton.setIcon(new ImageIcon("src/image/icon/橡皮.png"));
					g.setColor(foreColor);      // 设置绘图对象的前景色
				}
			}
		});
		
		shapeButton.addActionListener(new ActionListener() { // 为图形按钮添加动作监听
			
			@Override
			public void actionPerformed(ActionEvent e) { // 单击时
				// 创建图形选择组件
				ShapeWindow shapeWindow = new ShapeWindow(DrawPictureFrame.this);
				int shapeButtonWidth = shapeButton.getWidth();// 获取图像按钮宽度
				int shapeWindowWidth = shapeWindow.getWidth();// 获取图形按钮高度
				int shapeButtonX = shapeButton.getX();        // 获取图形按钮横坐标
				int shapeButtonY = shapeButton.getY();        // 获取图形按钮纵坐标
				
				// 计算图形组件横坐标，让组件与"图形"按钮居中对齐
				int shapeWindowX = getX() + shapeButtonX
						- (shapeWindowWidth - shapeButtonWidth) / 2;
				// 计算图形组件纵坐标，让组件显示在"图形"按钮下方
				int shapeWindowY = getY() + shapeButtonY +80;
				// 设置图形组件坐标位置
				shapeWindow.setLocation(shapeWindowX, shapeWindowY);
				shapeWindow.setVisible(true);      // 图形组件可见
			}
		});
	    
		savebButton.addActionListener(new ActionListener() { // 为保存按钮添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // 单击时
				addWatermark(); // 添加水印
				DrawImageUtil.saveImage(DrawPictureFrame.this, image); // 打印图片
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() { // 为退出菜单项添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent e) { // 单击时
				System.exit(0);// 程序关闭
			}
		});
		
		eraserMenuItem.addActionListener(new ActionListener() { // 为橡皮菜单项添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent atg0) { // 单击时
				// 单击工具栏上的橡皮按钮，使用橡皮
				if (eraserButton.getToolTipText().equals("橡皮")) {
					rubber = true;              // 设置橡皮标识为true
					eraserButton.setToolTipText("画图");
					eraserButton.setIcon(new ImageIcon("src/image/icon/画笔.png"));
				} else { // 单击工具栏上的画图按钮，使用画笔
					rubber = false;             // 设置橡皮标识符为false
					eraserButton.setToolTipText("橡皮");
					eraserButton.setIcon(new ImageIcon("src/image/icon/橡皮.png"));
					g.setColor(foreColor);      // 设置绘图对象的前景色
				}
			}
		});
		
		clearMenuItem.addActionListener(new ActionListener() { // 为清除菜单项添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // 单击时
				g.setColor(backgroundColor);  // 绘图工具使用背景色
				g.fillRect(0, 0, 1000, 650);   // 画一个背景色的方形填满整个画布
				g.setColor(foreColor);        // 绘图工具使用前景色
				canvas.repaint();             // 刷新画布
			}
		});
		
		strokeMenuItem1.addActionListener(new ActionListener() { // 为“细线”菜单项添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// 声明画笔的属性，粗细为1像素，线条末端无修饰，折线处呈尖角
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // 画图工具使用此画笔
			}
		});
		
		strokeMenuItem2.addActionListener(new ActionListener() { // 为“粗线”菜单项添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// 声明画笔的属性，粗细为2像素，线条末端无修饰，折线处呈尖角
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // 画图工具使用此画笔
			}
		});
		
		strokeMenuItem3.addActionListener(new ActionListener() { // 为“较粗”菜单项添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// 声明画笔的属性，粗细为3像素，线条末端无修饰，折线处呈尖角
				BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // 画图工具使用此画笔
			}
		});
		
		foregroundMenuItem.addActionListener(new ActionListener() { // 前景颜色按钮添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // 单击时
				
				// 打开选择颜色对话框，参数依次为：父窗体，标题，默认选中的颜色(青色)
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if (fColor != null) {        // 如果选中的颜色不是空的
					foreColor = fColor;      // 将选中的颜色赋给前景色变量
				}
				// 前景色按钮也更换为这中背景颜色
				foregrounButton.setForeground(foreColor);
				g.setColor(foreColor);       // 绘图工具使用前景色
			}
		});
		
		backgroundMenuItem.addActionListener(new ActionListener() { // 背景颜色按钮添加动作监听
			
			@Override
			public void actionPerformed(final ActionEvent e) { // 单击时
				
				//打开选择颜色对话框，参数依次为：父窗体，标题，默认选中的颜色(青色)
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if (bgColor != null) {
					backgroundColor = bgColor;// 将选中的颜色赋给背景色变量
				}
				//背景色按钮也更换为这中背景颜色
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);  // 绘图工具使用背景色
				g.fillRect(0, 0, 1000, 650);   // 画一个背景颜色的方形填满整个画布
				g.setColor(foreColor);        // 绘图工具使用前景色
				canvas.repaint();             // 更新画布
			}
		});
		
		saveMenuItem.addActionListener(new ActionListener() { // 为保存按钮添加动作监听

				@Override
				public void actionPerformed(final ActionEvent arg0) { // 单击时
					addWatermark(); // 添加水印
					DrawImageUtil.saveImage(DrawPictureFrame.this, image); // 打印图片
				}
		});
		
		shuiyinItem.addActionListener(new ActionListener() { // 为水印菜单项添加动作监听
			
			@Override
			public void actionPerformed(ActionEvent e) { // 单击时
				// 弹出输入对话框
				shuiyin = JOptionPane.showInputDialog(DrawPictureFrame.this, "添加什么水印内容！");
				if (null == shuiyin) {
					shuiyin = "";
				} else {
					setTitle("画板(水印内容[" + shuiyin +"])");// 修改窗体标题
				}
			}
		});
		
		showPicBut.addActionListener(new ActionListener() { // 为简笔画按钮添加动作监听
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isVisible = picWindow.isVisible();// 获取简笔画窗体可见状态
				if (isVisible) {
					showPicBut.setToolTipText("展开简笔画");
					showPicBut.setIcon(new ImageIcon("src/image/icon/展开.png"));
					picWindow.setVisible(false);
				} else {
					showPicBut.setToolTipText("隐藏简笔画");
					showPicBut.setIcon(new ImageIcon("src/image/icon/隐藏.png"));
					// 重新指定简笔画展示窗体的显示位置
					// 横坐标 = 主窗体横坐标 - 简笔画窗体宽度 -5
					// 纵坐标 = 主窗体纵坐标
					picWindow.setLocation(getX() - picWindow.getWidth() - 5, getY());
					picWindow.setVisible(true);// 设置简笔画窗体可见
				}
			}
		});
	}
	/**
	 * 恢复简笔画按钮的文本，此方法供简笔画面板的"隐藏"按钮调用
	 */
	public void initShowPicBut() {
		showPicBut.setToolTipText("展开简笔画");
		showPicBut.setIcon(new ImageIcon("src/image/icon/展开.png"));
	}
	
	/**
	 * 添加水印
	 */
	private void addWatermark() {
		if (!"".equals(shuiyin.trim())) {                // 如果水印字段不是空字符串
			g.rotate(Math.toRadians(-30));               // 将图片旋转-30度
			Font font = new Font("楷体", Font.BOLD, 50);  // 设置字体
			g.setFont(font);                             // 载入字体
			g.setColor(Color.GRAY);                      // 使用灰色
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f); // 设置透明效果
			g.setComposite(alpha);                       // 使用透明效果
			g.drawString(shuiyin, 380, 800);             // 绘制文字
			canvas.repaint();                            // 刷新画板
			g.rotate(Math.toRadians(30));                // 将图片再璇转回来
			alpha = AlphaComposite.SrcOver.derive(1f);   // 不透明效果
			g.setComposite(alpha);                       // 使用不透明效果
			g.setColor(foreColor);                       // 画笔恢复之前颜色
		}
	}
	
	/**
	 * FrameGetShape接口实现类，用于获得图形空间返回的被选中的图形
	 */
	public void getShape(Shapes shapes) {
		this.shape = shapes; // 将返回的图形对象赋值给类的全局变量
		drawShape = true;    // 画图形标识变量为true，说明现在鼠标画的是图形，而不是线条
	}
	
	/**
	 * 程序运行主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DrawPictureFrame frame = new DrawPictureFrame(); // 创建窗体对象
		frame.setVisible(true);                          // 设置窗体可见
	}
}
