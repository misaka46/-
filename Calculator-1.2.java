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
 * 一个计算器，与Windows附件自带计算器的标准版功能、界面相仿。 但还不支持键盘操作。
 */
public class Calculator extends JFrame implements ActionListener {
	/** 计算器上的键的显示名字 */
	private final String[] KEYS = { "(", ")", "C", "CE", "←", "sin","7", "8", "9", "+", "-", "cos", "4", "5", "6", "*", "/","tan"
			,"1", "2", "3", "%", "...","csc" ,"π", "0", ".", "√", "=" ,"sec"};
	/** 计算器上键的按钮 */
	private JButton keys[] = new JButton[KEYS.length];
	/** 计算结果文本框 */
	private JTextField resultText = new JTextField("0");
	private JTextField resultText2 = new JTextField("");
	private JTextField resultText3 = new JTextField("");
	/** 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字 */
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
	 * 构造函数
	 */
	public Calculator() {
		super();
		// 初始化计算器
		init();
		// 设置计算器的背景颜色
		this.setBackground(Color.LIGHT_GRAY);
		// 计算器标题
		this.setTitle("计算器");
		// 在屏幕(500, 300)坐标处显示计算器
		this.setSize(610, 600);
		this.setLocation(500, 400);
		// 不许修改计算器的大小
		//this.setResizable(false);
		// 使计算器中各组件大小合适
		// this.pack();
	}

	/**
	 * 初始化计算器
	 */
	private void init() {
		resultText.setToolTipText("");
		resultText.setFont(new Font("Dialog", Font.PLAIN, 50));
		resultText.setEditable(true);
		// 文本框中的内容采用右对齐方式
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		resultText.setSize(100, 300);
		// 不允许修改结果文本框
		resultText.setEditable(false);
		// 设置文本框背景颜色为白色
		resultText.setBackground(Color.white);
   
		// 初始化计算器上键的按钮，将键放在一个画板内
		JPanel calckeysPanel = new JPanel();
		// 用网格布局器，4行，5列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
		calckeysPanel.setLayout(new GridLayout(5, 5, 1, 1));
		// 设置按钮大小的参数定义
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
			keys[i].setFont(new Font("宋体", Font.PLAIN, 15));
			
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel panel1 = new JPanel();
		// 画板采用边界布局管理器，画板里组件之间的水平和垂直方向上间隔都为3象素
		panel1.setLayout(new BorderLayout(3, 3));
		panel1.add("West", calckeysPanel);
		// 建立一个画板放文本框
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
        top.add("Center", resultText3);
        top.add("Center", resultText2);
		top.add("Center", resultText);
		// 整体布局
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
		/** 输入第一个字符,可以是数字正负号小数点和括号 */
		if (("0123456789+-().".indexOf(key) >= 0 && firstDigit)) {

			if (key.equals("."))
				resultText.setText("0.");
			else
				resultText.setText(key);
			firstDigit = false;
			/** 实现回退键的功能 */
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
			else if (key.equals("←")) {
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
			/** 实现C键的功能，删除resultText所显示的全部内容，并初始化 */
		} else if (key.equals("C")) {
			resultText.setText("0");
			firstDigit2 = true;
			firstDigit = true;
			/** 下面实现的CE的功能，在这里CE的功能是删除从最后到从最后开始第一个遇到的四则运算符号的内容，并初始化 */
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
			/** 实现已经第一次输入后的后续输入 */
		} else if ("0123456789.+-*/()".indexOf(key) >= 0) {

			resultText.setText(resultText_getText() + key);

			/** 下面的用来实现给目标增加百分号的操作 */
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
			firstDigit=true;
			firstDigit2=true;
			/** 实现增加正负号的功能 */
		} else if (key.equals("π")) {
			String string = resultText.getText();
			string += "*3.141592653";
			resultText.setText(getnumber(string));
			/** 实现对指定内容开放的操作 */
		} else if (key.equals("√")) {
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
			/** 实现对resultText所显示内容的进行运算并将结果显示到resultText上 */
		} else if (key.equals("=")) {
			String string_num = resultText.getText();
			if (puterror()) {
				putnumber(string_num);
			}
		}
		format_error();
	}

	// 判断是否有格式错误
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


	
	// 初始化变量
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

	/** （数字运算） 返回'('的下标i的值 */
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

	/** 用来处理输入数字以后有前导零的情况 */
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

	/** 保留10位有效数值并去除多余的0 */
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

	/** 输出resultText的运算结果到resultText */
	private void putnumber(String string_num) {

		if (!formaterror) {
			resultText.setText(getnumber(string_num));
		} else {
			guiling();
		}
	}

	/** 使用函数求解resultText内的运算，并返回String格式的结果 */
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
		/** 在控制台输出信息，用于调试 */
		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);// resultText.setText(result.toString());
		if (result.toString().equals("Infinity")) {
			valueerror=true;
		}
		return put_zero(result);
	}

	/** 主函数 */
	public static void main(String args[]) {

		Calculator calculator1 = new Calculator();
		calculator1.setVisible(true);
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
