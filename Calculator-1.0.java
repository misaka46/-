package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 一个计算器，可以实现一些基本操作。 但还不支持键盘操作。
 */
public class Calculator extends JFrame implements ActionListener {
	/** 计算器上的键的显示名字 */
	private final String[] KEYS = { "(", ")", "C", "CE", "Backspace", "7", "8", "9", "+", "-", "4", "5", "6", "*", "/",
			"1", "2", "3", "%", "+/-", "1/x", "0", ".", "sqrt", "=" };
	/** 计算器上键的按钮 */
	private JButton keys[] = new JButton[KEYS.length];	
	/** 计算结果文本框 */
	private JTextField resultText = new JTextField("0");
	/** 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字*/
	private boolean firstDigit = true;
	private boolean firstDigit2 = true;

	/**
	 * 构造函数
	 */
	public Calculator() {
		super();
		// 初始化计算器
		init();
		// 设置计算器的背景颜色
		this.setBackground(Color.LIGHT_GRAY);
		//计算器标题
		this.setTitle("计算器");
		// 在屏幕(500, 300)坐标处显示计算器
		this.setLocation(500, 300);
		// 不许修改计算器的大小
		this.setResizable(false);
		// 使计算器中各组件大小合适
		this.pack();
	}

	/**
	 * 初始化计算器
	 */
	private void init() {
		// 文本框中的内容采用右对齐方式
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		// 不允许修改结果文本框
		resultText.setEditable(false);
		// 设置文本框背景颜色为白色
		resultText.setBackground(Color.white);
		// 初始化计算器上键的按钮，将键放在一个画板内
		JPanel calckeysPanel = new JPanel();
		// 用网格布局器，4行，5列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
		calckeysPanel.setLayout(new GridLayout(5, 5, 3, 3));
		for (int i = 0; i < KEYS.length; i++) {
			keys[i] = new JButton(KEYS[i]);
			calckeysPanel.add(keys[i]);
			keys[i].setForeground(Color.blue);
			
		}
		//功能键设置为红色
		keys[0].setForeground(Color.red);
		keys[1].setForeground(Color.red);
		keys[2].setForeground(Color.red);
		keys[3].setForeground(Color.red);
		keys[4].setForeground(Color.red);
		keys[8].setForeground(Color.red);
		keys[9].setForeground(Color.red);
		keys[13].setForeground(Color.red);
		keys[14].setForeground(Color.red);
		keys[18].setForeground(Color.red);
		keys[19].setForeground(Color.red);
		keys[20].setForeground(Color.red);
		keys[22].setForeground(Color.red);
		keys[23].setForeground(Color.red);
		keys[24].setForeground(Color.red);
		JPanel panel1 = new JPanel();
		// 画板采用边界布局管理器，画板里组件之间的水平和垂直方向上间隔都为3象素
		panel1.setLayout(new BorderLayout(3, 3));
		panel1.add("West", calckeysPanel);
		// 建立一个画板放文本框
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add("Center", resultText);
		// 整体布局
		getContentPane().setLayout(new BorderLayout(3, 5));
		getContentPane().add("North", top);
		getContentPane().add("Center", panel1);
		// 为各按钮添加事件侦听器
		// 都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener
		for (int i = 0; i < KEYS.length; i++) {
			keys[i].addActionListener(this);
		}

	}

	/**
	 * 处理事件
	 */
	public void actionPerformed(ActionEvent e) {
		// 获取事件源的标签
		String label = e.getActionCommand();
		handleNumber(label);
	}

	/**
	 * 处理按键被按下的事件
	 * 
	 * @param key
	 */
	private void handleNumber(String key) {
		/**输入第一个字符,可以是数字正负号小数点和括号*/
		if (("0123456789+-().".indexOf(key) >= 0 && firstDigit)) {
			// 下面两行感觉没什么用
			// String text = resultText.getText();
			// resultText.setText(text.substring(0, text.length() - 1));
			if(key.equals("."))resultText.setText("0.");
			else resultText.setText(key);
			firstDigit = false;
		/**实现回退键的功能*/
		} else if (key.equals("Backspace")) {
			String text = resultText_getText();
			int len = text.length();
			// System.out.println(text);
			if (len > 0) {
				text = text.substring(0, len - 1);
				if (text.length() == 0) {
					resultText.setText("0");
				} else {
					resultText.setText(text);
				}
			}
			firstDigit=true;
		/**实现C键的功能，删除resultText所显示的全部内容，并初始化*/
		} else if (key.equals("C")) {
			resultText.setText("0");
			firstDigit2=true;
			firstDigit=true;
		/**下面实现的CE的功能，在这里CE的功能是删除从最后到从最后开始第一个遇到的四则运算符号的内容，并初始化*/
		} else if (key.equals("CE")) {
			String string = resultText_getText();
			int len = string.length();
			char[] str = string.toCharArray();
			if (str[len - 1] == ')') {
				String str1;
				int i;
				i=bracket_i(str,len);	
				if(i<=0) i++;
				str1 = string.substring(0, i - 1);
				System.out.println(str1);
				if(i-1==0) {
					resultText.setText("0");
					firstDigit2=true;
					firstDigit=true;
				}else{
					resultText.setText(str1);
				};
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
					firstDigit2=true;
					firstDigit=true;
				} else {
					resultText.setText(str1);
				}

			}
		/**实现已经第一次输入后的后续输入*/
		} else if ("0123456789.+-*/()".indexOf(key) >= 0) {
			
				resultText.setText(resultText_getText() + key);
			
			
		/**下面的用来实现给目标增加百分号的操作*/
		} else if (key.equals("%")) {
			String string = resultText_getText();
			System.out.println(string);
			int len = string.length();
			char[] str = string.toCharArray();
			if (str[len - 1] == ')') {
				String str1, str2, str3;
				int i;
				i=bracket_i(str,len);
				str1 = string.substring(0, i);
				str2 = string.substring(i, len);
				System.out.println(str1 + " . " + str2);
				str3 = "0.01*";
				str3 += str2;
				str2 = getnumber(str3);
				if (str2.equals("错误")) {
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
					i = -1;// 用来防止对小数开方时输出在最前面多输出一个0
				str1 = string.substring(0, i + 1);
				str2 = string.substring(i + 1, len);
				str3 = "0.01*";
				str3 += str2;
				str2 = getnumber(str3);
				if (str2.equals("错误")) {
					putnumber(str2);
				} else {
					str1 += str2;
					resultText.setText(str1);
				}
			}
		/**实现增加正负号的功能*/
		} else if (key.equals("+/-")) {
			//
		/**实现对指定内容开放的操作*/
		} else if (key.equals("sqrt")) {
			String string = resultText_getText();
			System.out.println(string);
			int len = string.length();
			char[] str = string.toCharArray();
			if (str[len - 1] == ')') {
				String str1, str2, str3;
				int i;
				i=bracket_i(str,len);
				str1 = string.substring(0, i);
				str2 = string.substring(i, len);
				System.out.println(str1 + " . " + str2);
				str3 = "Math.sqrt";
				str3 += str2;
				str2 = getnumber(str3);
				if (str2.equals("错误")) {
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
					i = -1;// 用来防止对小数开方时输出在最前面多输出一个0
				str1 = string.substring(0, i + 1);
				str2 = string.substring(i + 1, len);
				str3 = "Math.sqrt(";
				str3 += str2;
				str3 += ")";
				str2 = getnumber(str3);
				if (str2.equals("错误")) {
					putnumber(str2);
				} else {
					str1 += str2;
					resultText.setText(str1);
				}
			}
		/**实现对resultText所显示内容的进行运算并将结果显示到resultText上*/
		} else if (key.equals("=")) {
			String string_num = resultText.getText();
			putnumber(string_num);
		}
		
	}

	/** （数字运算） 返回'('的下标i的值*/
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
	/**用来处理输入数字以后有前导零的情况*/
	private String resultText_getText() {
		String string = resultText.getText();
		if (string.length() == 1 && string.equals("0") && !firstDigit2) {
			return "";
		} else {
			return string;
		}
	}
	/**输出resultText的运算结果到resultText*/
	private void putnumber(String string_num) {
		resultText.setText(getnumber(string_num));
	}
	/**使用函数求解resultText内的运算，并返回String格式的结果*/
	private String getnumber(String string_num) {
		String str = string_num;
		if (firstDigit)
			str = "0";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 4);
		Object result = null;
		try {
			result = engine.eval(str);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "错误";
		}
		/**在控制台输出信息，用于调试*/
		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
		
		// resultText.setText(result.toString());
		return result.toString();

	}
	/**主函数*/
	public static void main(String args[]) {

		Calculator calculator1 = new Calculator();
		calculator1.setVisible(true);
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
