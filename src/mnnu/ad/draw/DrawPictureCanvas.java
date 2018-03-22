package mnnu.ad.draw;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

/**
 * 简笔画展示窗体
 * @author anjiadoo
 *
 */
public class DrawPictureCanvas extends Canvas{
	private Image image = null;             //创建画板中展示的图片对象
	
	/**
	 * 设置画板中的图片
	 * @param image - 画板中展示的图片对象
	 */
	public void setImage(Image image){
		this.image = image;                 //为成员变量赋值
	}
	
	/**
	 * 重写pait()方法，在画布上绘制图像
	 */
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);     //在画布上绘制图像
	}
	
	/**
	 * 重写updata()方法，这样可以解决屏幕闪烁问题
	 */
	public void update(Graphics g) {
		paint(g); //调用paint方法
	}
}
