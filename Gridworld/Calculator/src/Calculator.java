/*      
 *          My HomeWork
 * School:     SYSU
 * author:     LiuWenLve
 * StudentID:  13331177
 * Introduce:
 *   This is a calculator which is achieved by java.
 * The calculator is so simple for my SYSU HomeWork
 * and can only calculate +, -, * and /.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator implements ActionListener {
	private JFrame jf;  // Content board
	private JTextField tf;  //Text board
	
	private String operNum, oper; // The text to store the input
	private double num1, num2, result;// The operator of the calculator
	private String cmd; // The value of the button been clicked
	
	
	public Calculator() {   //initialize
		this.result = 0;
		this.operNum = null;
		this.oper = null;
		this.cmd = "";
		this.jf = new JFrame("Simple Calculator");  //The Calculator's name
		this.tf = new JTextField();
		
		setInterface();
	}
	
	public void setInterface() {    //frame
		jf.setSize(270, 170); // Set size of calculator window.
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exist when close the window

		tf.setPreferredSize(new Dimension(100, 25));  // Set size of text file
		jf.getContentPane().add(tf, BorderLayout.NORTH);   // Add text delay area
		
		JPanel CalButton = new JPanel();
		jf.getContentPane().add(CalButton);
		
		/*
		 * These code is to add two area (number area and function area) in 
		 * the main window. To do this, we should put these two module
		 * in an intermediate module
		 */

		JPanel jp1 = new JPanel(); // The number calculate area
		JPanel jp2 = new JPanel(); // The function area like exist and clear
		jp1.setLayout(new GridLayout(4, 4));  // Two buttons in a row
		jp2.setLayout(new GridLayout(1, 1)); // 16 buttons, each row four buttons

        //set button
		JButton button;
			
		String[] str = {"7","8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"};
		for (int i = 0; i != str.length; i++) {
			button = new JButton(str[i]);
			button.addActionListener(this);
			jp1.add(button);
		}
			
		button = new JButton("Clear");
		button.addActionListener(this);
		jp2.add(button);
			
		/*
		 * The Function of the Back will be achieved.
		 * button = new JButton("Back");
		 * button.addActionListener(this);
		 * jp2.add(button);
		*/

		CalButton.add(jp1);
		CalButton.add(jp2, BorderLayout.EAST);
		jf.pack();
		jf.setLocation(400, 200);
		jf.setVisible(true);
	}
	

	public boolean isNum(String str) {
		String[] strNum = {"7","8","9","4","5","6","1","2","3","0","."};
		for (String s : strNum)
			if (str.equals(s))
				return true;
		return false;
	}
	
	private void reinit() { //initialize again
		operNum = null;
		oper = null;
		num1 = 0;
		num2 = 0;
		result = 0;
		tf.setText("");
	}
	
	/*
	 * private void back(String back) {
	 * 		if (back.length() == 4 || back.length() == 0) {
	 * 			tf.setText("");
	 * 			return;
	 * 		}
	 * 		String bresult = back.substring(0, back.length()-5);
	 * 		tf.setText(bresult);
	 * 		oper = back;
	 * 	}
	*/
	
	private double getResult(String op, double num1, double num2) {
		double result = 0;
		switch (oper){
		case "+":
			result = num1 + num2;
			break;
		case "-":
			result = num1 - num2;
			break;
		case "*":
			result = num1 * num2;
			break;
		case "/":
			try {
				if (num2 == 0)
					throw new ArithmeticException("Error: The Divisor is 0");
				else
					result = num1 / num2;
			} catch(ArithmeticException error) {
				throw error;
			}
			break;
		default:
			break;
		} 
		return result;
	}

	
	//When you push the button, it will handle it.
	public void actionPerformed(ActionEvent act) {
		cmd = act.getActionCommand();    // The value of clicked button
		tf.setText(tf.getText() + cmd);
		
		if (cmd.equals("Clear")) { // Clear the input
			reinit();
		} /*else if (cmd.equals("Back")) { // It make it back.
			//back(tf.getText());
		} */else if (isNum(cmd)) { // Whether is a number or a point
			if (operNum == null) {  // The first number of operator
				operNum = cmd;
			}
			else {
				operNum += cmd;
			}
		} else {  // operator
			if (cmd.equals("=")) { 
				if (oper == null) {  // Directly click "=" after input a number
					result = new Double(operNum).doubleValue();
				} else {
					num2 = new Double(operNum).doubleValue();   //The second operator
					try {
						result = getResult(oper, num1, num2);
					} catch(ArithmeticException error) {
						tf.setText(error.getMessage()); // Delay error message
					}
				}
				tf.setText(tf.getText() + String.format("%.3f", result));
			} else {
				if (oper == null) {
					operNum = operNum.substring(0, operNum.length());
					num1 = new Double(operNum).doubleValue();   // The first operator
				} else {
					num1 = result;    // The preceding result as an operator
				}
				oper = cmd;
				operNum = null;
			}
		}
	}
	
	public static void main(String[] args) {
		Calculator cal = new Calculator();
	}
}