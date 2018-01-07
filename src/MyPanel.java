import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MyPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Robot robot;																				//��������
	
	BufferedImage img;																	//��ͼ
	
	Rectangle rectangle=new Rectangle();										//��ͼ�ķ�Χ
	
	int kase=0;																				//������ͼ��Χ�õļ�����
	
	Main contex;																			//����
	
	boolean taskFlag=false;															//�����־
	
	Thread thread;																			//��ͼ����
	
	Point mousePoint=new Point();												//��굱ǰ������
	Point panelLinePoint=new Point();											//������λ������
	Point windowPoint;																	//���ڵ�����
	double jumpLenth=0;//��Ծ����
	double speed=3.6;
	double time=0;
	
	//��Ϸ�����
	Point bodyPoint;//�������
	Point jumpPoint;//��Ծ������
	
	public MyPanel(Main m) {
		// TODO Auto-generated constructor stub
		contex=m;
		
		setBorder(new LineBorder(Color.red));
		
		try {
			robot=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(kase==0) {
					rectangle.x=MouseInfo.getPointerInfo().getLocation().x;
					rectangle.y=MouseInfo.getPointerInfo().getLocation().y;
					kase++;
					contex.setText("�����ڶ����������ͼ����");
					contex.setTitle("�����ڶ����������ͼ����");
				}else if(kase==1) {
					rectangle.width=MouseInfo.getPointerInfo().getLocation().x-rectangle.x;
					rectangle.height=MouseInfo.getPointerInfo().getLocation().y-rectangle.y;
					MyPanel.this.setSize(rectangle.width, rectangle.height);
					contex.changeWindowSize();
					contex.setOpacity(1);
					taskFlag=true;
					contex.setLocation(rectangle.x+rectangle.width,rectangle.y);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							while(true) {
								if(!taskFlag) {
									break;
								}
								
								mousePoint=MouseInfo.getPointerInfo().getLocation();//�����������
								windowPoint=contex.getLocation();//��ô�������
								panelLinePoint.x=mousePoint.x-windowPoint.x;
								panelLinePoint.y=mousePoint.y-windowPoint.y;
								
								img=robot.createScreenCapture(rectangle);
								repaint();
							}
						}
					}).start();
					kase++;
				}else {
					//taskFlag=false;
					if(kase==2) {
						bodyPoint=MouseInfo.getPointerInfo().getLocation();
						bodyPoint.x=bodyPoint.x-windowPoint.x;
						bodyPoint.y=bodyPoint.y-windowPoint.y;
						kase++;
					}else if(kase==3){
						/*if(jumpPoint!=null) {
							bodyPoint.x=Math.abs(jumpPoint.x-bodyPoint.x);
							bodyPoint.y=Math.abs(jumpPoint.y-bodyPoint.y);
						}*/
						jumpPoint=MouseInfo.getPointerInfo().getLocation();
						jumpPoint.x=jumpPoint.x-windowPoint.x;
						jumpPoint.y=jumpPoint.y-windowPoint.y;
						jumpLenth = Math.sqrt((Math.pow(Math.abs(bodyPoint.x - jumpPoint.x), 2)
								+ Math.pow(Math.abs(jumpPoint.y - bodyPoint.y), 2)));
						time=jumpLenth/contex.getJumpSpeed();
						repaint();
						robot.mouseMove(rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2);
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						try {
							Thread.sleep((long)time*10);
							System.out.println(time*10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						robot.mouseMove(jumpPoint.x+windowPoint.x, jumpPoint.y+windowPoint.y);
						kase++;
					}else {
						bodyPoint=null;
						jumpPoint=null;
						kase=2;
					}
				}
			}
		});
		
		
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if(img==null) {
			super.paint(g);
			return;
		}
		g.drawImage(img, 0, 0, null);
		if(!(mousePoint.x<windowPoint.x||mousePoint.y<windowPoint.y||mousePoint.x>windowPoint.x+this.getWidth()||mousePoint.y>windowPoint.y+this.getHeight())) {
			g.setColor(Color.blue);
			g.drawLine(panelLinePoint.x-1, 1, panelLinePoint.x-1, this.getHeight());//����
			g.drawLine(1, panelLinePoint.y-1, this.getWidth()-1, panelLinePoint.y-1);//����
		}
		g.setColor(Color.RED);
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		
		if(bodyPoint!=null) {
			g.drawRect(bodyPoint.x-5, bodyPoint.y-5, 10, 10);
		}
		if(jumpPoint!=null) {
			g.drawRect(jumpPoint.x-5, jumpPoint.y-5, 10, 10);
			g.setColor(Color.black);
		}
		if(bodyPoint!=null&&jumpPoint!=null) {
			g.drawLine(bodyPoint.x, bodyPoint.y, jumpPoint.x, jumpPoint.y);
		}
	}

}
