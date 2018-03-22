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
 * ��ʻ�չʾ����
 * @author anjiadoo
 *
 */
public class PictureWindow extends JWindow{
	private JButton changeBut;                   // ����ͼƬ��ť
	private JButton hideBut;                     // ���ذ�ť
	private BackgroundPanel centerPanel;         // չʾͼƬ�Ĵ�����ͼ���
	File list[];                                 // ͼƬ�ļ�����
	int index;
	DrawPictureFrame frame;                      // ������
	
	public PictureWindow (DrawPictureFrame frame) {
		this.frame = frame;                      // ���������ֵ��ֵ��������  
		setSize(400, 460);
		init();
		addListener();
	}
	
	/**
	 * �����ʼ������
	 */
	private void init() {
		Container c = getContentPane();
		File dir = new File("src/image/picture");
		list = dir.listFiles();
		
	    centerPanel = new BackgroundPanel(getListImage()); // ��ʼ��������壬ʹ��ͼƬ�ļ������һ�ż�ʻ�
	    c.add(centerPanel, BorderLayout.CENTER);           // �������ŵ��������в�
	    FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);// �����Ҷ����������
	    flow.setHgap(20);                                  // ˮƽ���20����
	    JPanel southpPanel = new JPanel();                 // �����ϲ����
	    southpPanel.setLayout(flow);                       // �ϲ����ʹ�øղŴ����õ�������
	    changeBut = new JButton("����ͼƬ");                 // ʵ����"����ͼƬ"��ť
	    southpPanel.add(changeBut);
	    hideBut = new JButton("����");
	    southpPanel.add(hideBut);
	    c.add(southpPanel, BorderLayout.SOUTH);            // �ϲ����ŵ����������ϲ�λ��
	}
	
	/**
	 * ��Ӽ���
	 */
    private void addListener() {
    	hideBut.addActionListener(new ActionListener() { // ���ذ�ť��Ӽ���
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false); // ���岻�ɼ�
				frame.initShowPicBut();// ���ര�廹ԭ��ʻ���ť���ı�����
			}
		});
    	
    	changeBut.addActionListener(new ActionListener() { // ����ͼƬ��ť��Ӷ�������
			
			@Override
			public void actionPerformed(ActionEvent e) {
				centerPanel.setImage(getListImage()); // ���������������ͼƬ
			}
		});
    }
    
    /**
     * ��ȡͼƬ�ļ����µ�ͼƬ�㣬ÿ�ε��ô˷����������ò�ͬ���ļ�����
     * 
     * @return �Ż�ͼƬ����
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
