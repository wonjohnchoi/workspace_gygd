
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * A class that gets a postfix equation and evaluate it using Stack to find out
 * the final answer.
 * 
 * @author Wonjohn Choi
 * 
 */
public class PostfixCalculator {
	Queue<String> input= new LinkedList<String>();;

	/**
	 * get a new Postfix equation and separate each part to store into input Queue
	 * @param postStr
	 */
	public void addInput(String postStr) {
		input.clear();
		postStr = postStr.trim();
		/*
		 * StringTokenizer stk=new StringTokenizer(postString, " ");
		 * while(stk.hasMoreTokens()) { char c=stk.nextToken().charAt(0);
		 * if(c!=' ') { input.add(""+c); } }
		 */

		int idx = 0;
		String part = "";
		while (idx != postStr.length()) {
			char cur = postStr.charAt(idx);

			if (isOper("" + cur)) {
				if (part.length() != 0) {
					input.add(part);
					part = "";
				}

				input.add("" + cur);
			} else if (cur == ' ' && part.length() != 0) {
				input.add(part);
				part = "";
			} else if (cur != ' ') {
				part += cur;
			}
			idx++;
		}
	}

	public void addInput(Queue<String> q) {
		input = new LinkedList<String>();
		input = q;
	}

	/**
	 * (5-4)*3+4*2 + * 3 - 5 4 * 4 2
	 * 
	 * @param oper
	 * @param sum
	 * @return
	 */
	public double evaluate() {
		Queue<String> in = new LinkedList<String>();
		in.addAll(input);
		Stack<Double> out = new Stack<Double>();
		while (!in.isEmpty()) {
			String tmp = in.remove();
			if (isOper(tmp)) {
				double val1 = out.pop();
				double val2 = out.pop();
				out.push(simpleCalc(tmp.charAt(0), val2, val1));
			}

			else {
				out.push(new Double(tmp));
			}
		}
		return out.pop();
	}

	private boolean isOper(String str) {
		return str.equals("-") || str.equals("+") || str.equals("*")
				|| str.equals("/") || str.equals("^");
	}

	private double simpleCalc(char oper, double val1, double val2) {
		if (oper == '-') {
			val1 -= val2;
		} else if (oper == '+') {
			val1 += val2;
		} else if (oper == '/') {
			val1 /= val2;
		} else if (oper == '*') {
			val1 *= val2;
		} else if (oper == '^') {
			val1 = Math.pow(val1, val2);
		}

		return val1;
	}

	public String toString() {
		return input.toString();
	}
}
