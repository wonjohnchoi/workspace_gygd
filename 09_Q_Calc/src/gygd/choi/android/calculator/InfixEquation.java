package gygd.choi.android.calculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/**
 * A class that gets infix equation as input and converts it to a postfix equation.
 * @author Wonjohn Choi
 *
 */
public class InfixEquation {
	Queue<String> input=new LinkedList<String>();
	
	public void addInput(String infixStr)
	{
		input.clear();
		infixStr=infixStr.trim();
		
		int idx=0;
		String part="";
		while(idx!=infixStr.length())
		{
			char cur=infixStr.charAt(idx);
			
			if(toInt(""+cur)!=2)
			{
				if(part.length()!=0)
				{
					input.offer(part);
					part="";
					
					if(cur=='(')
					{			
						//multiplication between num and bracket
						input.offer("*");
					}
				}
				
				input.offer(""+cur);
			}
			else if(cur!=' ')
			{
				part+=cur;
			}
			idx++;
		}
		
		if(part.trim().length()!=0)
			input.offer(part);
	}
	
	
	public Queue<String> toPostfix()
	{
		Queue<String> in=new LinkedList<String>();
		in.addAll(input);

		Queue<String> out=new LinkedList<String>();
		Stack<String> operators=new Stack<String>();
		System.out.println(in);
		while(!in.isEmpty())
		{
			System.out.println(out);
			String element=in.poll();
			if(toInt(element)==2) //number
			{
				out.offer(element);
			}
			else if(operators.isEmpty()) //if there is no stored operator, current one is
			{
				operators.push(element);
			}
			else if(element.equals("(")) //if bracket starts store it
			{
				operators.push(element);
			}
			else if(element.equals(")")) //if ends
			{
				while(!operators.peek().equals("(")) //if it meets the initial
				{
					out.offer(operators.pop()); //pop middle parts
				}
				operators.pop(); //pop the initial bracket
			}
			else 
			{
				while(!operators.isEmpty() && toInt(operators.peek())!=-3&& //not empty, no brackets
						toInt(operators.peek())>=toInt(element)) //new element has lower presidency 
				{
					out.offer(operators.pop()); //add it to the output
				}

				operators.push(element);
			}
			
			
		}
		
		while(!operators.isEmpty())
			out.offer(operators.pop());
		System.out.println(out);

		return out;
	}

	private int toInt(String str)
	{
		int result;
		if(str.equals(")") || str.equals("("))
		{
			result=-3;
		}
		else if(str.equals("-")||str.equals("+"))
		{
			result=-2;
		}
		else if(str.equals("*")||str.equals("/"))
		{
			result=-1;
		}
		else if(str.equals("^"))
		{
			result=0;
		}
		else
		{
			result=2;
		}
		return result;
	}
	
	public String toString()
	{
		return input.toString();
	}
}
