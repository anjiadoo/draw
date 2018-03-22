package mnnu.ad.draw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.mr.util.BackgroundPanel;

/**
 * 简笔画展示窗体
 * @author anjiadoo
 *
 */
public class PictureWindow extends JWindow{
	private JButton changeBut;                   // 更换图片按钮
	private JButton hideBut;                     // 隐藏按钮
	private BackgroundPanel centerPanel;         // 展示图片的带背景图面板
	File list[];                                 // 图片文件数组
	int index;
	DrawPictureFrame frame;                      // 父窗体
	
	public PictureWindow (DrawPictureFrame frame) {
		this.frame = frame;                      // 构造参数的值赋值给父窗体  
		setSize(400, 460);
		init();
		addListener();
	}
	
	/**
	 * 组件初始化方法
	 */
	private void init() {
		Container c = getContentPane();
		File dir = new File("src/image/picture");
		list = dir.listFiles();
		
	    centerPanel = new BackgroundPanel(getListImage()); // 初始化背景面板，使用图片文件夹里第一张简笔画
	    c.add(centerPanel, BorderLayout.CENTER);           // 背景面板放到主容器中部
	    FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);// 创建右对齐的流布局
	    flow.setHgap(20);                                  // 水平间隔20像素
	    JPanel southpPanel = new JPanel();                 // 创建南部面板
	    southpPanel.setLayout(flow);                       // 南部面板使用刚才创建好的流布局
	    changeBut = new JButton("更换图片");                 // 实例化"更换图片"按钮
	    southpPanel.add(changeBut);
	    hideBut = new JButton("隐藏");
	    southpPanel.add(hideBut);
	    c.add(southpPanel, BorderLayout.SOUTH);            // 南部面板放到主容器的南部位置
	}
	
	/**
	 * 添加监听
	 */
    private void addListener() {
    	hideBut.addActionListener(new ActionListener() { // 隐藏按钮添加监听
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false); // 窗体不可见
				frame.initShowPicBut();// 父类窗体还原简笔画按钮的文本内容
			}
		});
    	
    	changeBut.addActionListener(new ActionListener() { // 更换图片按钮添加动作监听
			
			@Override
			public void actionPerformed(ActionEvent e) {
				centerPanel.setImage(getListImage()); // 背景面板重新载入图片
			}
		});
    }
    
    /**
     * 获取图片文件夹下的图片你，每次调用此方法，都会获得不同的文件对象
     * 
     * @return 放回图片对象
     */
    private Image getListImage() {
    	String imagePath = list[index].getAbsolutePath();
    	ImageIcon image = new ImageIcon(imagePath);
    	index++;
    	if (index >= list.length) {
    		index = 0;
    	}
    	return image.getImage();
    }
}
