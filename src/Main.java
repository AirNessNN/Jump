import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class Main extends JFrame{
	
	private Point origin=new Point();
	MyPanel panel = null;
	JButton btnNewButton = new JButton("\u9000\u51FA");
	
	JLabel text = new JLabel("\u5728\u7EA2\u6846\u4E2D\u70B9\u51FB\u7B2C\u4E00\u4E2A\u70B9");
	JLabel lblNewLabel = new JLabel("\u8DF3\u8DC3\u7CFB\u6570");
	private JTextField textField;
	public void setText(String str) {
		this.text.setText(str);
	}
	
	public Main() {
		setTitle("\u5728\u7EA2\u6846\u4E2D\u70B9\u51FB\u7B2C\u4E00\u4E2A\u70B9");
		setAlwaysOnTop(true);
		
		setSize(1920, 1040);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0));
		setOpacity(0.5f);
		
		getContentPane().setLayout(null);
		
		panel=new MyPanel(this);
		panel.setBorder(new LineBorder(Color.RED));
		panel.setBounds(0, 0, 1800, 1040);
		getContentPane().add(panel);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(panel.getWidth()+1, 0, 66, 23);
		getContentPane().add(btnNewButton);
		
		
		text.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		text.setBounds(this.panel.getWidth()+1, 33, 189, 15);
		getContentPane().add(text);
		
		
		lblNewLabel.setBounds(1801, 107, 96, 15);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setText("3.6");
		textField.setBounds(1801, 132, 66, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		addMouseListener(new MouseAdapter() {

			// 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			// 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				Point p = Main.this.getLocation();
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				Main.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
	}
	
	
	public void changeWindowSize() {
		this.setSize(panel.getWidth()+200, panel.getHeight()+1);
		btnNewButton.setBounds(panel.getWidth()+1, 0, 66, 23);
		text.setBounds(this.panel.getWidth()+1, 33, 189, 15);
		lblNewLabel.setBounds(panel.getWidth()+1, 107, 96, 15);
		textField.setBounds(panel.getWidth()+1, 132, 66, 21);
	}
	
	public double getJumpSpeed() {
		return Double.valueOf(textField.getText());
	}
	
	
	
	public void paint(Graphics g) {
        super.paint(g);
    }
	
	
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m=new Main();
		m.setVisible(true);

	}
}
