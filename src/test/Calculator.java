package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.math.BigDecimal;
import java.util.Stack;
import javax.script.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.omg.PortableInterceptor.IORInterceptor;
import java.awt.Font;

/**
 * һ������������Windows�����Դ��������ı�׼�湦�ܡ�������¡� ������֧�ּ��̲�����
 */
public class Calculator extends JFrame implements ActionListener {
	/** �������ϵļ�����ʾ���� */
	private final String[] KEYS = { "(", ")", "C", "CE", "��", "sin","7", "8", "9", "+", "-", "cos", "4", "5", "6", "*", "/","tan"
			,"1", "2", "3", "%", "...","csc" ,"��", "0", ".", "��", "=" ,"sec"};
	/** �������ϼ��İ�ť */
	private JButton keys[] = new JButton[KEYS.length];
	/** �������ı��� */
	private JTextField resultText = new JTextField("0");
	private JTextField resultText2 = new JTextField("");
	private JTextField resultText3 = new JTextField("");
	/** ��־�û������Ƿ����������ʽ�ĵ�һ������,�������������ĵ�һ������ */
	private boolean firstDigit = true;
	private boolean firstDigit2 = true;
	private int flag_jia = 0;
	private int flag_jian = 0;
	private int flag_cheng = 0;
	private int flag_chu = 0;
	private boolean formaterror = false;
	private boolean valueerror = false;
	private boolean inputeerroe = false;
	private Stack<String> stack = new Stack<String>();
	private int f=0;
	/**
	 * ���캯��
	 */
	public Calculator() {
		super();
		// ��ʼ��������
		init();
		// ���ü������ı�����ɫ
		this.setBackground(Color.LIGHT_GRAY);
		// ����������
		this.setTitle("������");
		// ����Ļ(500, 300)���괦��ʾ������
		this.setSize(610, 600);
		this.setLocation(500, 400);
		// �����޸ļ������Ĵ�С
		//this.setResizable(false);
		// ʹ�������и������С����
		// this.pack();
	}

	/**
	 * ��ʼ��������
	 */
	private void init() {
		resultText.setToolTipText("");
		resultText.setFont(new Font("Dialog", Font.PLAIN, 50));
		resultText.setEditable(true);
		// �ı����е����ݲ����Ҷ��뷽ʽ
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		resultText.setSize(100, 300);
		// �������޸Ľ���ı���
		resultText.setEditable(false);
		// �����ı��򱳾���ɫΪ��ɫ
		resultText.setBackground(Color.white);
   
		// ��ʼ���������ϼ��İ�ť����������һ��������
		JPanel calckeysPanel = new JPanel();
		// �����񲼾�����4�У�5�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������
		calckeysPanel.setLayout(new GridLayout(5, 5, 1, 1));
		// ���ð�ť��С�Ĳ�������
		Dimension preferredSize = new Dimension(120, 60);
		for (int i = 0; i < KEYS.length; i++) {
			keys[i] = new JButton(KEYS[i]);
			calckeysPanel.add(keys[i]);
			if ("0123456789".indexOf(KEYS[i]) >= 0) {
				keys[i].setForeground(Color.blue);
			} else {
				keys[i].setForeground(Color.red);
			}
			keys[i].setPreferredSize(preferredSize);
			keys[i].setFont(new Font("����", Font.PLAIN, 15));
			
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel panel1 = new JPanel();
		// ������ñ߽粼�ֹ����������������֮���ˮƽ�ʹ�ֱ�����ϼ����Ϊ3����
		panel1.setLayout(new BorderLayout(3, 3));
		panel1.add("West", calckeysPanel);
		// ����һ��������ı���
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
        top.add("Center", resultText3);
        top.add("Center", resultText2);
		top.add("Center", resultText);
		// ���岼��
		getContentPane().add("North", top);
		getContentPane().add("Center", panel1);
		// Ϊ����ť����¼�������
		// ��ʹ��ͬһ���¼����������������󡣱������������implements ActionListener
		for (int i = 0; i < KEYS.length; i++) {
			keys[i].addActionListener(this);
		}
	}

	/**
	 * �����¼�
	 */
	public void actionPerformed(ActionEvent e) {
		// ��ȡ�¼�Դ�ı�ǩ
		String label = e.getActionCommand();
		handleNumber(label);
	}

	/**
	 * �����������µ��¼�
	 * 
	 * @param key
	 */
	private void handleNumber(String key) {
		/** �����һ���ַ�,����������������С��������� */
		if (("0123456789+-().".indexOf(key) >= 0 && firstDigit)) {

			if (key.equals("."))
				resultText.setText("0.");
			else
				resultText.setText(key);
			firstDigit = false;
			/** ʵ�ֻ��˼��Ĺ��� */
		} else if(key.equals("...")) {
			
			if(f==0) {
				this.setSize(740, 600);
				f=1;
			}else {
				this.setSize(620, 600);
				f=0;
			}
		}else if(key.equals("sin")) {
			putnumber("Math.sin("+resultText.getText()+")");
		}else if(key.equals("cos")) {
			putnumber("Math.cos("+resultText.getText()+")");
		}else if(key.equals("tan")) {
			putnumber("Math.tan("+resultText.getText()+")");
		}else if(key.equals("csc")) {
			putnumber("1/Math.sin("+resultText.getText()+")");
		}else if(key.equals("sec")) {
			putnumber("1/Math.cos("+resultText.getText()+")");
		}
			else if (key.equals("��")) {
			String text = resultText_getText();
			int len = text.length();
			// System.out.println(text);
			if (len > 0) {
//				if (text.substring(len - 1, len).equals("("))
					text = text.substring(0, len - 1);

				if (text.length() == 0) {
					resultText.setText("0");
				} else {
					resultText.setText(text);
				}
			}
			firstDigit=true;
			/** ʵ��C���Ĺ��ܣ�ɾ��resultText����ʾ��ȫ�����ݣ�����ʼ�� */
		} else if (key.equals("C")) {
			resultText.setText("0");
			firstDigit2 = true;
			firstDigit = true;
			/** ����ʵ�ֵ�CE�Ĺ��ܣ�������CE�Ĺ�����ɾ������󵽴����ʼ��һ������������������ŵ����ݣ�����ʼ�� */
		} else if (key.equals("CE")) {
			String string = resultText_getText();
			int len = string.length();
			char[] str = string.toCharArray();
			if (str[len - 1] == ')') {
				String str1;
				int i;
				i = bracket_i(str, len);
				if (i <= 0)
					i++;
				str1 = string.substring(0, i - 1);
				System.out.println(str1);
				if (i - 1 == 0) {
					resultText.setText("0");
					firstDigit2 = true;
					firstDigit = true;
				} else {
					resultText.setText(str1);
				}
				;
			} else {
				int i = len - 1;
				for (; i >= 0; i--) {
					if ((str[i] < '0' || str[i] > '9') && str[i] != '.') {
						break;
					}
				}
				if (i < 0)
					i++;
				String str1;
				str1 = string.substring(0, i);
				if (i == 0) {
					resultText.setText("0");
					firstDigit2 = true;
					firstDigit = true;
				} else {
					resultText.setText(str1);
				}

			}
			/** ʵ���Ѿ���һ�������ĺ������� */
		} else if ("0123456789.+-*/()".indexOf(key) >= 0) {

			resultText.setText(resultText_getText() + key);

			/** ���������ʵ�ָ�Ŀ�����ӰٷֺŵĲ��� */
		} else if (key.equals("%")) {
			String string = resultText_getText();
			System.out.println(string);
			int len = string.length();
			char[] str = string.toCharArray();
			if (str[len - 1] == ')') {
				String str1, str2, str3;
				int i;
				i = bracket_i(str, len);
				str1 = string.substring(0, i);
				str2 = string.substring(i, len);
				System.out.println(str1 + " . " + str2);
				str3 = "0.01*";
				str3 += str2;
				str2 = getnumber(str3);
				if (str2.equals("����")) {
					putnumber(str2);
				} else {
					str1 += str2;
					resultText.setText(str1);
				}
				
			} else {
				int i = len - 1;
				for (; i >= 0; i--) {
					if ((str[i] < '0' || str[i] > '9') && str[i] != '.') {
						break;
					}
				}
				if (i < 0)
					i++;
				String str1, str2, str3;
				System.out.println(i);
				if (i == 0)
					i = -1;// ������ֹ��С������ʱ�������ǰ������һ��0
				str1 = string.substring(0, i + 1);
				str2 = string.substring(i + 1, len);
				str3 = "0.01*";
				str3 += str2;
				str2 = getnumber(str3);
				if (str2.equals("����")) {
					putnumber(str2);
				} else {
					str1 += str2;
					resultText.setText(str1);
				}
			}
			firstDigit=true;
			firstDigit2=true;
			/** ʵ�����������ŵĹ��� */
		} else if (key.equals("��")) {
			String string = resultText.getText();
			string += "*3.141592653";
			resultText.setText(getnumber(string));
			/** ʵ�ֶ�ָ�����ݿ��ŵĲ��� */
		} else if (key.equals("��")) {
			String string = resultText_getText();
			//System.out.println(string);
			int len = string.length();
			char[] str = string.toCharArray();
			//Math.sqrt(8+8)
			if (str[len - 1] == ')') {
				String str1, str2, str3;
				int i;
				i = bracket_i(str, len);
				str1 = string.substring(0, i);
				str2 = string.substring(i, len);
				System.out.println(str1 + " . " + str2);
				str3 = "Math.sqrt";
				str3 += str2;
				str2 = getnumber(str3);
				if (str2.equals("����")) {
					putnumber(str2);
				} else {
					str1 += str2;
					resultText.setText(str1);
				}
			} else {
				int i = len - 1;
				for (; i >= 0; i--) {
					if ((str[i] < '0' || str[i] > '9') && str[i] != '.') {
						break;
					}
				}
				if (i < 0)
					i++;
				String str1, str2, str3;
				System.out.println(i);
				if (i == 0)
					i = -1;// ������ֹ��С������ʱ�������ǰ������һ��0
				str1 = string.substring(0, i + 1);
				str2 = string.substring(i + 1, len);
				str3 = "Math.sqrt(";
				str3 += str2;
				str3 += ")";
				str2 = getnumber(str3);
				if (str2.equals("����")) {
					putnumber(str2);
				} else {
					str1 += str2;
					resultText.setText(str1);
				}
			}
			/** ʵ�ֶ�resultText����ʾ���ݵĽ������㲢�������ʾ��resultText�� */
		} else if (key.equals("=")) {
			String string_num = resultText.getText();
			if (puterror()) {
				putnumber(string_num);
			}
		}
		format_error();
	}

	// �ж��Ƿ��и�ʽ����
	private void format_error() {
		String string_pd = resultText.getText();
		int len_pd = string_pd.length();
		// if (m.equals("1")) {
		// System.out.println(string_pd.substring(len_pd - 1, len_pd));
		if ("1234567890()".indexOf(string_pd.substring(len_pd - 1, len_pd)) >= 0) {
			flag_cheng = 0;
			flag_chu = 0;
			flag_jia = 0;
			flag_jian = 0;
		} else if ("+-*/".indexOf(string_pd.substring(len_pd - 1, len_pd)) >= 0) {
			switch (string_pd.substring(len_pd - 1, len_pd)) {
			case "*": {
				flag_cheng++;
				if (flag_cheng - 1 >= 1 || flag_chu >= 1 || flag_jia >= 1 || flag_jian >= 1) {
					formaterror = true;
				}
				break;
			}
			case "/": {
				flag_chu++;
				if (flag_cheng >= 1 || flag_chu - 1 >= 1 || flag_jia >= 1 || flag_jian >= 1) {
					formaterror = true;
				}
				break;
			}
			case "+": {
				flag_jia++;
				if (flag_cheng >= 1 || flag_chu >= 1 || flag_jia - 1 >= 1 || flag_jian >= 1) {
					formaterror = true;
				}
				break;
			}
			case "-": {
				flag_jian++;
				if (flag_jian - 1 >= 1) {
					formaterror = true;
				}
				break;
			}
			}
		}
		if (string_pd.substring(len_pd - 1, len_pd).equals("(")) {
			stack.push("(");
			System.out.println(stack.size());
		} else if (string_pd.substring(len_pd - 1, len_pd).equals(")")) {
			if (!stack.empty())
				stack.pop();
			else
				formaterror = true;
			System.out.println(stack.size());
			//
		}

	}


	
	// ��ʼ������
	private void guiling() {
		flag_jia = 0;
		flag_jian = 0;
		flag_cheng = 0;
		flag_chu = 0;
		firstDigit = true;
		firstDigit2 = true;
		formaterror = false;
		valueerror = false;
		inputeerroe = false;
		while (!stack.empty()) {
			stack.pop();
		}

	}

	/** ���������㣩 ����'('���±�i��ֵ */
	public int bracket_i(char[] ch, int len) {
		int s = 0, i = len - 1;
		for (; i >= 0; i--) {
			if (ch[i] == ')')
				s++;
			if (ch[i] == '(')
				s--;
			if (s == 0 && i != len - 1)
				break;
		}
		return i;
	}

	/** �����������������Ժ���ǰ�������� */
	private String resultText_getText() {
		String string = resultText.getText();
		if (string.length() == 1 && string.equals("0") && !firstDigit2) {
			return "";
		} else {
			return string;
		}
	}

	private boolean puterror() {
		if (inputeerroe) {
			resultText.setText("INPUT ERROR");
			guiling();
			return false;
		} else if (formaterror) {
			resultText.setText("FORMAT ERROR");
			guiling();
			return false;
		} else if (valueerror) {
			resultText.setText("VALUE ERROR");
			guiling();
			return false;
		}
		return true;
	}

	/** ����10λ��Ч��ֵ��ȥ�������0 */
	private String put_zero(Object result) {
		Double d = Double.parseDouble(result.toString());
		BigDecimal b = new BigDecimal(d);
		double f1 = b.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		String str1 = String.valueOf(f1);
		int f = 0;
		while (f == 0) {
			String t = str1.substring(str1.length() - 1, str1.length());

			if (t.equals("0")) {
				str1 = str1.substring(0, str1.length() - 1);
				System.out.println(str1);
			} else if (t.equals(".")) {
				str1 = str1.substring(0, str1.length() - 1);
				System.out.println(str1);
				break;
			} else {
				break;
			}
		}
		return str1;
	}

	/** ���resultText����������resultText */
	private void putnumber(String string_num) {

		if (!formaterror) {
			resultText.setText(getnumber(string_num));
		} else {
			guiling();
		}
	}

	/** ʹ�ú������resultText�ڵ����㣬������String��ʽ�Ľ�� */
	private String getnumber(String string_num) {
		String str = string_num;
		if (firstDigit) {
			str = "0";
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 4);
		Object result = null;
		try {
			result = engine.eval(str);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inputeerroe=true;
			//System.out.println("111111111111111111111111");
			boolean  tttt=puterror();
		}
		/** �ڿ���̨�����Ϣ�����ڵ��� */
		System.out.println("�������:" + result.getClass().getName() + ",������:" + result);// resultText.setText(result.toString());
		if (result.toString().equals("Infinity")) {
			valueerror=true;
		}
		return put_zero(result);
	}

	/** ������ */
	public static void main(String args[]) {

		Calculator calculator1 = new Calculator();
		calculator1.setVisible(true);
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}