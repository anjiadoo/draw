package mnnu.ad.draw;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

/**
 * ��ʻ�չʾ����
 * @author anjiadoo
 *
 */
public class DrawPictureCanvas extends Canvas{
	private Image image = null;             //����������չʾ��ͼƬ����
	
	/**
	 * ���û����е�ͼƬ
	 * @param image - ������չʾ��ͼƬ����
	 */
	public void setImage(Image image){
		this.image = image;                 //Ϊ��Ա������ֵ
	}
	
	/**
	 * ��дpait()�������ڻ����ϻ���ͼ��
	 */
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);     //�ڻ����ϻ���ͼ��
	}
	
	/**
	 * ��дupdata()�������������Խ����Ļ��˸����
	 */
	public void update(Graphics g) {
		paint(g); //����paint����
	}
}
