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
 * ��ͼ������
 * 
 * @author anjiadoo
 *
 */
public class DrawPictureFrame extends JFrame implements FrameGetShape { // �̳д�����, ʵ�����½ӿ�

	BufferedImage image = new BufferedImage(1000, 650, BufferedImage.TYPE_INT_BGR);// ����һ��8λBGR��ɫ������ͼ��
	Graphics gs = image.getGraphics();                   // ���ͼ��Ļ�ͼ����
	Graphics2D g = (Graphics2D) gs;                      // ����ͼ����ת����Graphics2D����
	DrawPictureCanvas canvas = new DrawPictureCanvas();  // ������������
	Color foreColor = Color.BLACK;                       // ����ǰ��ɫ
	Color backgroundColor = Color.WHITE;                  // ���屳��ɫ
	int x = -1;     // ��һ�������Ƶ�ĺ�����
	int y = -1;     // ��һ�������Ƶ��������
	boolean rubber = false;                              // ��Ƥ��ʶ����
	boolean drawShape = false;                           // ��ͼ�α�ʶ����

	// �����������͹�������ť	
	private JToolBar toolBar;                            // ������
	private JButton  eraserButton;                       // ��Ƥ��ť
	private JToggleButton strokeButton1;                 // ϸ�߰�ť
	private JToggleButton strokeButton2;                 // ���߰�ť
	private JToggleButton strokeButton3;                 // �ϴְ�ť
	private JButton backgroundButton;                    // ����ɫ��ť
	private JButton foregrounButton;                     // ǰ��ɫ��ť
	private JButton clearButton;                         // �����ť
	private JButton savebButton;                         // ���水ť
	private JButton shapeButton;                         // ͼ�ΰ�ť
	Shapes shape;                                        // �滭��ͼ��
	
	// �����˵����Ͳ˵���
	private JMenuBar menuBar;                            // �˵���
	private JMenuItem strokeMenuItem1;                   // ϸ�߲˵�
	private JMenuItem strokeMenuItem2;                   // ���߲˵�
	private JMenuItem strokeMenuItem3;                   // �ϴֲ˵�
	private JMenuItem clearMenuItem;                     // ����˵�
	private JMenuItem foregroundMenuItem;                // ǰ��ɫ�˵�
	private JMenuItem backgroundMenuItem;                // ����ɫ�˵�
	private JMenuItem eraserMenuItem;                    // ��Ƥ�˵�
	private JMenuItem exitMenuItem;                      // �˳��˵�
	private JMenuItem saveMenuItem;                      // ����˵�
	private JMenuItem shuiyinItem;                       // ˮӡ�ˆ�
	private String shuiyin = "";                         // ˮӡ����
	
	// ������ʻ�����
	private PictureWindow picWindow;                     // ��ʻ�չʾ����
	private JButton showPicBut;                          // չ����ʻ���ť

	/**
	 * ���췽�����������ĳ�ʼ������,��������
	 */
	public DrawPictureFrame() {
		setResizable(false);                             // ���岻�ܸı��С
		setTitle("����(ˮӡ����[" + shuiyin +"])");         // ���ñ���
		Toolkit tk=Toolkit.getDefaultToolkit();          // ���ϵͳĬ�ϵ�������߰������޸ĳ���ͼ��
	    Image image=tk.createImage("src/image/icon/����.png");
		setIconImage(image);                             // �޸ĳ���ͼ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ����ر���ֹͣ����
		setBounds(500, 100, 1000, 650);                   // ���ô���λ�úͿ��
		init();                                          // �����ʼ��
		addListener();                                   // ����������
	}

	/**
	 * �����ʼ��
	 */
	private void init() {
		g.setColor(backgroundColor);                  // �ñ���ɫ���û�ͼ�������ɫ
		g.fillRect(0, 0, 1000, 650);                   // �ñ���ɫ�����������
		g.setColor(foreColor);                        // ��ǰ��ɫ���û�ͼ�������ɫ
		canvas.setImage(image);                       // ���û�����ͼ��
		getContentPane().add(canvas);                 // ��������ӵ���������Ĭ�ϲ��ֵ��в�λ��
		
		// �������͹�������ť
		toolBar = new JToolBar();                     // ��ʼ��������
		getContentPane().add(toolBar, BorderLayout.NORTH); // ��������ӵ������λ��
		toolBar.setBackground(Color.LIGHT_GRAY);      // ���ù���������ɫ
		
		showPicBut = new JButton();
		showPicBut.setToolTipText("չ����ʻ�");        // �����ͣ��ʾ
		showPicBut.setIcon(new ImageIcon("src/image/icon/չ��.png"));//���ð�ťͼ��
		toolBar.add(showPicBut);
		
		savebButton = new JButton();
		savebButton.setToolTipText("����");
		savebButton.setIcon(new ImageIcon("src/image/icon/����.png"));
		toolBar.add(savebButton);                     // ����ť��ӵ�������
		toolBar.addSeparator();                       // ��ӷָ���
		
		strokeButton1 = new JToggleButton();
		strokeButton1.setToolTipText("ϸ��");
		strokeButton1.setIcon(new ImageIcon("src/image/icon/����1.png"));
		strokeButton1.setSelected(true);              // ϸ�߰�ť���ڱ�ѡ��λ��
		toolBar.add(strokeButton1);
		
		strokeButton2 = new JToggleButton();
		strokeButton2.setToolTipText("����");
		strokeButton2.setIcon(new ImageIcon("src/image/icon/����2.png"));
		toolBar.add(strokeButton2);
		
		strokeButton3 = new JToggleButton();
		strokeButton3.setToolTipText("�ϴ�");
		strokeButton3.setIcon(new ImageIcon("src/image/icon/����3.png"));
		toolBar.add(strokeButton3);
		
		ButtonGroup strokeGroup = new ButtonGroup();  // ���ʴ�ϸ��ť����֤ͬʱֻ��һ����ť��ѡ��
		strokeGroup.add(strokeButton1);               // ��ӵ�һ��������
		strokeGroup.add(strokeButton2);
		strokeGroup.add(strokeButton3);
		toolBar.addSeparator();                       // ��ӷָ���
		
		backgroundButton = new JButton();
		backgroundButton.setToolTipText("������ɫ");
		backgroundButton.setIcon(new ImageIcon("src/image/icon/����ɫ.png"));
		toolBar.add(backgroundButton);
		foregrounButton = new JButton();
		foregrounButton.setToolTipText("ǰ����ɫ");
		foregrounButton.setIcon(new ImageIcon("src/image/icon/ǰ��ɫ.png"));
		toolBar.add(foregrounButton);
		toolBar.addSeparator();
		
		shapeButton = new JButton();
		shapeButton.setToolTipText("ͼ��");
		shapeButton.setIcon(new ImageIcon("src/image/icon/��״.png"));
		toolBar.add(shapeButton);
		clearButton = new JButton();
		clearButton.setToolTipText("���");
		clearButton.setIcon(new ImageIcon("src/image/icon/���.png"));
		toolBar.add(clearButton);
		eraserButton = new JButton();
		eraserButton.setToolTipText("��Ƥ");
		eraserButton.setIcon(new ImageIcon("src/image/icon/��Ƥ.png"));
		toolBar.add(eraserButton);
		
		// �˵����Ͳ˵���
		menuBar = new JMenuBar();                      // �����˵���
		setJMenuBar(menuBar);                          // ��������˵���
		
		JMenu systemMenu = new JMenu("ϵͳ");           // ��ʼ���˵����󣬲�����ı�
		menuBar.add(systemMenu);                       // �˵�����Ӳ˵�����
		shuiyinItem = new JMenuItem("ˮӡ");            // ��ʼ���˵�����󣬲�����ı�
		systemMenu.add(shuiyinItem);                   // �˵���Ӳ˵���
		saveMenuItem = new JMenuItem("����");           // ��ʼ���˵�����󣬲�����ı�
		systemMenu.add(saveMenuItem);                  // �˵���Ӳ˵���
		systemMenu.addSeparator();                     // ��ӷָ���
		exitMenuItem = new JMenuItem("�˳�");
		systemMenu.add(exitMenuItem);
		
		JMenu strokeMenu = new JMenu("����");
		menuBar.add(strokeMenu);
		strokeMenuItem1 = new JMenuItem("ϸ��");
		strokeMenu.add(strokeMenuItem1);
		strokeMenuItem2 = new JMenuItem("����");
		strokeMenu.add(strokeMenuItem2);
		strokeMenuItem3 = new JMenuItem("�ϴ�");
		strokeMenu.add(strokeMenuItem3);
		
		JMenu colorMenu = new JMenu("��ɫ");
		menuBar.add(colorMenu);
		foregroundMenuItem = new JMenuItem("ǰ����ɫ");
		colorMenu.add(foregroundMenuItem);
		backgroundMenuItem = new JMenuItem("������ɫ");
		colorMenu.add(backgroundMenuItem);
		
		JMenu editMenu= new JMenu("�༭");
		menuBar.add(editMenu);
		clearMenuItem = new JMenuItem("���");
		editMenu.add(clearMenuItem);
		eraserMenuItem = new JMenuItem("��Ƥ");
		editMenu.add(eraserMenuItem);
		
		// ������ʻ���壬����������Ϊ����
		picWindow = new PictureWindow(DrawPictureFrame.this);

	}// init() end
	
	/**
	 * Ϊ�����Ӷ�������
	 */
	private void addListener() {
		canvas.addMouseMotionListener(new MouseMotionListener() { // Ϊ�����������ƶ��¼�����
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (rubber) {
					// �������ָ�����״ΪͼƬ
					Toolkit kit = Toolkit.getDefaultToolkit();// ���ϵͳĬ�ϵ�������߰�
					// ���ù��߰���ȡͼƬ
					Image image1 = kit.createImage("src/image/icon/�����Ƥ.png");
					// ���ù��߰�����һ���Զ���Ĺ�����
					// ����ΪͼƬ������ȵ�͹�������ַ���
					Cursor c = kit.createCustomCursor(image1, new Point(0, 25), "clear");
					setCursor(c);// ʹ���Զ�����
				} else {
					// �������ָ�����״ΪͼƬ
					Toolkit kit = Toolkit.getDefaultToolkit();// ���ϵͳĬ�ϵ�������߰�
					// ���ù��߰���ȡͼƬ
					Image image2 = kit.createImage("src/image/icon/����.png");
					// ���ù��߰�����һ���Զ���Ĺ�����
					// ����ΪͼƬ������ȵ�͹�������ַ���
					Cursor c = kit.createCustomCursor(image2, new Point(0, 25), "clear");
					setCursor(c);// ʹ���Զ�����
					
					/* �������ָ�����״Ϊʮ�ֹ��(ϵͳ�Դ�)
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));*/
				}
			}
			
			@Override
			public void mouseDragged(final MouseEvent e) { // �������קʱ
				if(x > 0 && y >0) {                   // ���x �� y ��������¼
					if (rubber) {                     // �����Ƥ��ʶΪtrue����ʾʹ����Ƥ
						g.setColor(backgroundColor);  // ��ͼ����ʹ�ñ���ɫ
						g.fillOval(x, y, 10, 10);     // ����껬����λ�û�����Բ��
					} else {
						g.drawLine(x, y, e.getX(), e.getY());  // ����껬����λ�û�ֱ��
					}
				} //if end
			    x = e.getX();           // ��һ�������Ƶ�ĺ�����
			    y = e.getY();           // ��һ�������Ƶ��������
			    canvas.repaint();       // ���»���
		    } // mouseDragged() end
		});
		
		canvas.addMouseListener(new MouseAdapter() { // Ϊ���������굥���¼�����
			
			@Override
			public void mouseReleased(final MouseEvent arg0) { // ������̧��ʱ
				x = -1;       // ����¼��һ�������Ƶ�ĺ�����ָ���-1
				y = -1;       // ����¼��һ�������Ƶ��������ָ���-1
			}
			
			@Override
			public void mousePressed(MouseEvent e) {  // �����¼�ʱ
				if (drawShape) {
					switch (shape.getType()) {
					case Shapes.YUAN:
						// �������꣬����괦��ͼ�ε�����λ��
						int yuanX = e.getX() - shape.getWidth();
						int yuanY = e.getY() - shape.getHeigth();
						// ����Բ��ͼ�Σ���ָ������Ϳ��
						Ellipse2D yuan = new Ellipse2D.Double(yuanX, yuanY, shape.getWidth(), shape.getHeigth());
						g.draw(yuan);       // ��ͼ���߻���Բ��
						break;
						
					case Shapes.FANG:
						// �������꣬����괦��ͼ�ε�����λ��
						int fangX = e.getX() - shape.getWidth();
						int fangY = e.getY() - shape.getHeigth();
						// ��������ͼ�Σ���ָ������Ϳ��
						Rectangle2D fang = new Rectangle2D.Double(fangX, fangY, shape.getWidth(), shape.getHeigth());
						g.draw(fang);      // ��ͼ���߻��η���
						break;
						
					default:
						break;
					}
					canvas.repaint();      // ���»���
					drawShape = false;     // ��ͼ�α�ʶ����Ϊfalse��˵��������껭����������������ͼ��
				}
			}
		});
		
		toolBar.addMouseMotionListener(new MouseMotionListener() { // �������������ƶ�����
			
			@Override
			public void mouseMoved(final MouseEvent arg0) {
				// �������ָ�����״ΪĬ�Ϲ��
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));	
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		strokeButton1.addActionListener(new ActionListener() { // "ϸ��"��ť��Ӷ�������

			@Override
			public void actionPerformed(final ActionEvent e) {
				// �������ʵ����ԣ���ϸΪ1���أ�����ĩ�������Σ����ߴ��ʼ��
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // ��ͼ����ʹ�ô˻���
			}
		});
		
		strokeButton2.addActionListener(new ActionListener() { // "����"��ť��Ӷ�������

			@Override
			public void actionPerformed(final ActionEvent e) {
				// �������ʵ����ԣ���ϸΪ2���أ�����ĩ�������Σ����ߴ��ʼ��
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // ��ͼ����ʹ�ô˻���
			}
		});
		
		strokeButton3.addActionListener(new ActionListener() { // "�ϴ�"��ť��Ӷ�������

			@Override
			public void actionPerformed(final ActionEvent e) {
				// �������ʵ����ԣ���ϸΪ3���أ�����ĩ�������Σ����ߴ��ʼ��
				BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // ��ͼ����ʹ�ô˻���
			}
		});
		
		backgroundButton.addActionListener(new ActionListener() { // ������ɫ��ť��Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent e) { // ����ʱ
				
				//��ѡ����ɫ�Ի��򣬲�������Ϊ�������壬���⣬Ĭ��ѡ�е���ɫ(��ɫ)
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (bgColor != null) {
					backgroundColor = bgColor;// ��ѡ�е���ɫ��������ɫ����
				}
				//����ɫ��ťҲ����Ϊ���ֱ�����ɫ
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);  // ��ͼ����ʹ�ñ���ɫ
				g.fillRect(0, 0, 1000, 650);   // ��һ��������ɫ�ķ���������������
				g.setColor(foreColor);        // ��ͼ����ʹ��ǰ��ɫ
				canvas.repaint();             // ���»���
			}
		});
		
		foregrounButton.addActionListener(new ActionListener() { // ǰ����ɫ��ť��Ӷ�������

			@Override
			public void actionPerformed(final ActionEvent arg0) { // ����ʱ
				
				// ��ѡ����ɫ�Ի��򣬲�������Ϊ�������壬���⣬Ĭ��ѡ�е���ɫ(��ɫ)
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (fColor != null) {        // ���ѡ�е���ɫ���ǿյ�
					foreColor = fColor;      // ��ѡ�е���ɫ����ǰ��ɫ����
				}
				// ǰ��ɫ��ťҲ����Ϊ���ֱ�����ɫ
				foregrounButton.setBackground(foreColor);
				g.setColor(foreColor);       // ��ͼ����ʹ��ǰ��ɫ
			}
		});
		
		clearButton.addActionListener(new ActionListener() { // Ϊ�����ť��Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // ����ʱ
				g.setColor(backgroundColor);  // ��ͼ����ʹ�ñ���ɫ
				g.fillRect(0, 0, 1000, 650);   // ��һ������ɫ�ķ���������������
				g.setColor(foreColor);        // ��ͼ����ʹ��ǰ��ɫ
				canvas.repaint();             // ˢ�»���
			}
		});
		
		eraserButton.addActionListener(new ActionListener() { // Ϊ��Ƥ��ť��Ӷ������� 
			
			@Override
			public void actionPerformed(final ActionEvent atg0) { // ����ʱ
				// �����������ϵ���Ƥ��ť��ʹ����Ƥ
				if (eraserButton.getToolTipText().equals("��Ƥ")) {
					rubber = true;              // ������Ƥ��ʶΪtrue
					// �ı䰴ť����ʾ��ͼ��Ϊ"����"
					eraserButton.setToolTipText("��ͼ");
					eraserButton.setIcon(new ImageIcon("src/image/icon/����.png"));
				} else { // �����������ϵĻ�ͼ��ť��ʹ�û���
					rubber = false;             // ������Ƥ��ʶ��Ϊfalse
					// �ı䰴ť����ʾ��ͼ��Ϊ"��Ƥ"
					eraserButton.setToolTipText("��Ƥ");
					eraserButton.setIcon(new ImageIcon("src/image/icon/��Ƥ.png"));
					g.setColor(foreColor);      // ���û�ͼ�����ǰ��ɫ
				}
			}
		});
		
		shapeButton.addActionListener(new ActionListener() { // Ϊͼ�ΰ�ť��Ӷ�������
			
			@Override
			public void actionPerformed(ActionEvent e) { // ����ʱ
				// ����ͼ��ѡ�����
				ShapeWindow shapeWindow = new ShapeWindow(DrawPictureFrame.this);
				int shapeButtonWidth = shapeButton.getWidth();// ��ȡͼ��ť���
				int shapeWindowWidth = shapeWindow.getWidth();// ��ȡͼ�ΰ�ť�߶�
				int shapeButtonX = shapeButton.getX();        // ��ȡͼ�ΰ�ť������
				int shapeButtonY = shapeButton.getY();        // ��ȡͼ�ΰ�ť������
				
				// ����ͼ����������꣬�������"ͼ��"��ť���ж���
				int shapeWindowX = getX() + shapeButtonX
						- (shapeWindowWidth - shapeButtonWidth) / 2;
				// ����ͼ����������꣬�������ʾ��"ͼ��"��ť�·�
				int shapeWindowY = getY() + shapeButtonY +80;
				// ����ͼ���������λ��
				shapeWindow.setLocation(shapeWindowX, shapeWindowY);
				shapeWindow.setVisible(true);      // ͼ������ɼ�
			}
		});
	    
		savebButton.addActionListener(new ActionListener() { // Ϊ���水ť��Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // ����ʱ
				addWatermark(); // ���ˮӡ
				DrawImageUtil.saveImage(DrawPictureFrame.this, image); // ��ӡͼƬ
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() { // Ϊ�˳��˵�����Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent e) { // ����ʱ
				System.exit(0);// ����ر�
			}
		});
		
		eraserMenuItem.addActionListener(new ActionListener() { // Ϊ��Ƥ�˵�����Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent atg0) { // ����ʱ
				// �����������ϵ���Ƥ��ť��ʹ����Ƥ
				if (eraserButton.getToolTipText().equals("��Ƥ")) {
					rubber = true;              // ������Ƥ��ʶΪtrue
					eraserButton.setToolTipText("��ͼ");
					eraserButton.setIcon(new ImageIcon("src/image/icon/����.png"));
				} else { // �����������ϵĻ�ͼ��ť��ʹ�û���
					rubber = false;             // ������Ƥ��ʶ��Ϊfalse
					eraserButton.setToolTipText("��Ƥ");
					eraserButton.setIcon(new ImageIcon("src/image/icon/��Ƥ.png"));
					g.setColor(foreColor);      // ���û�ͼ�����ǰ��ɫ
				}
			}
		});
		
		clearMenuItem.addActionListener(new ActionListener() { // Ϊ����˵�����Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // ����ʱ
				g.setColor(backgroundColor);  // ��ͼ����ʹ�ñ���ɫ
				g.fillRect(0, 0, 1000, 650);   // ��һ������ɫ�ķ���������������
				g.setColor(foreColor);        // ��ͼ����ʹ��ǰ��ɫ
				canvas.repaint();             // ˢ�»���
			}
		});
		
		strokeMenuItem1.addActionListener(new ActionListener() { // Ϊ��ϸ�ߡ��˵�����Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// �������ʵ����ԣ���ϸΪ1���أ�����ĩ�������Σ����ߴ��ʼ��
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // ��ͼ����ʹ�ô˻���
			}
		});
		
		strokeMenuItem2.addActionListener(new ActionListener() { // Ϊ�����ߡ��˵�����Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// �������ʵ����ԣ���ϸΪ2���أ�����ĩ�������Σ����ߴ��ʼ��
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // ��ͼ����ʹ�ô˻���
			}
		});
		
		strokeMenuItem3.addActionListener(new ActionListener() { // Ϊ���ϴ֡��˵�����Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// �������ʵ����ԣ���ϸΪ3���أ�����ĩ�������Σ����ߴ��ʼ��
				BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs); // ��ͼ����ʹ�ô˻���
			}
		});
		
		foregroundMenuItem.addActionListener(new ActionListener() { // ǰ����ɫ��ť��Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent arg0) { // ����ʱ
				
				// ��ѡ����ɫ�Ի��򣬲�������Ϊ�������壬���⣬Ĭ��ѡ�е���ɫ(��ɫ)
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (fColor != null) {        // ���ѡ�е���ɫ���ǿյ�
					foreColor = fColor;      // ��ѡ�е���ɫ����ǰ��ɫ����
				}
				// ǰ��ɫ��ťҲ����Ϊ���б�����ɫ
				foregrounButton.setForeground(foreColor);
				g.setColor(foreColor);       // ��ͼ����ʹ��ǰ��ɫ
			}
		});
		
		backgroundMenuItem.addActionListener(new ActionListener() { // ������ɫ��ť��Ӷ�������
			
			@Override
			public void actionPerformed(final ActionEvent e) { // ����ʱ
				
				//��ѡ����ɫ�Ի��򣬲�������Ϊ�������壬���⣬Ĭ��ѡ�е���ɫ(��ɫ)
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (bgColor != null) {
					backgroundColor = bgColor;// ��ѡ�е���ɫ��������ɫ����
				}
				//����ɫ��ťҲ����Ϊ���б�����ɫ
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);  // ��ͼ����ʹ�ñ���ɫ
				g.fillRect(0, 0, 1000, 650);   // ��һ��������ɫ�ķ���������������
				g.setColor(foreColor);        // ��ͼ����ʹ��ǰ��ɫ
				canvas.repaint();             // ���»���
			}
		});
		
		saveMenuItem.addActionListener(new ActionListener() { // Ϊ���水ť��Ӷ�������

				@Override
				public void actionPerformed(final ActionEvent arg0) { // ����ʱ
					addWatermark(); // ���ˮӡ
					DrawImageUtil.saveImage(DrawPictureFrame.this, image); // ��ӡͼƬ
				}
		});
		
		shuiyinItem.addActionListener(new ActionListener() { // Ϊˮӡ�˵�����Ӷ�������
			
			@Override
			public void actionPerformed(ActionEvent e) { // ����ʱ
				// ��������Ի���
				shuiyin = JOptionPane.showInputDialog(DrawPictureFrame.this, "���ʲôˮӡ���ݣ�");
				if (null == shuiyin) {
					shuiyin = "";
				} else {
					setTitle("����(ˮӡ����[" + shuiyin +"])");// �޸Ĵ������
				}
			}
		});
		
		showPicBut.addActionListener(new ActionListener() { // Ϊ��ʻ���ť��Ӷ�������
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isVisible = picWindow.isVisible();// ��ȡ��ʻ�����ɼ�״̬
				if (isVisible) {
					showPicBut.setToolTipText("չ����ʻ�");
					showPicBut.setIcon(new ImageIcon("src/image/icon/չ��.png"));
					picWindow.setVisible(false);
				} else {
					showPicBut.setToolTipText("���ؼ�ʻ�");
					showPicBut.setIcon(new ImageIcon("src/image/icon/����.png"));
					// ����ָ����ʻ�չʾ�������ʾλ��
					// ������ = ����������� - ��ʻ������� -5
					// ������ = ������������
					picWindow.setLocation(getX() - picWindow.getWidth() - 5, getY());
					picWindow.setVisible(true);// ���ü�ʻ�����ɼ�
				}
			}
		});
	}
	/**
	 * �ָ���ʻ���ť���ı����˷�������ʻ�����"����"��ť����
	 */
	public void initShowPicBut() {
		showPicBut.setToolTipText("չ����ʻ�");
		showPicBut.setIcon(new ImageIcon("src/image/icon/չ��.png"));
	}
	
	/**
	 * ���ˮӡ
	 */
	private void addWatermark() {
		if (!"".equals(shuiyin.trim())) {                // ���ˮӡ�ֶβ��ǿ��ַ���
			g.rotate(Math.toRadians(-30));               // ��ͼƬ��ת-30��
			Font font = new Font("����", Font.BOLD, 50);  // ��������
			g.setFont(font);                             // ��������
			g.setColor(Color.GRAY);                      // ʹ�û�ɫ
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f); // ����͸��Ч��
			g.setComposite(alpha);                       // ʹ��͸��Ч��
			g.drawString(shuiyin, 380, 800);             // ��������
			canvas.repaint();                            // ˢ�»���
			g.rotate(Math.toRadians(30));                // ��ͼƬ���ת����
			alpha = AlphaComposite.SrcOver.derive(1f);   // ��͸��Ч��
			g.setComposite(alpha);                       // ʹ�ò�͸��Ч��
			g.setColor(foreColor);                       // ���ʻָ�֮ǰ��ɫ
		}
	}
	
	/**
	 * FrameGetShape�ӿ�ʵ���࣬���ڻ��ͼ�οռ䷵�صı�ѡ�е�ͼ��
	 */
	public void getShape(Shapes shapes) {
		this.shape = shapes; // �����ص�ͼ�ζ���ֵ�����ȫ�ֱ���
		drawShape = true;    // ��ͼ�α�ʶ����Ϊtrue��˵��������껭����ͼ�Σ�����������
	}
	
	/**
	 * ��������������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DrawPictureFrame frame = new DrawPictureFrame(); // �����������
		frame.setVisible(true);                          // ���ô���ɼ�
	}
}
