package MathObjects;

public class Term {

 double constant;
 double power;
	
	public Term(double newConstant, double newPower)
	{
		constant = newConstant;
		power = newPower;
	}
	public String printTerm()
	{
		if (power == 0) 
		{
			return "" + constant;
		}
		else if (power == 1) 
		{
			return "" + constant + "x";
		}
		return "" + constant + "x^" + power;
	}
	public void setPower(double newPower)
	{
		
		power = newPower;
	}
	public void setConstant(double newConstant)
	{
		constant = newConstant;
	}
	public double getConstant()
	{
	
		return constant;
	}
	public double getPower()
	{
		return power;
	}
	public double getOutput(double x)
	{
		double output = x;
		for(int i = 0; i < power-1; i ++)
		{
			output = output*x;
		}
		if(power == 0)
		{
			output = 1;
		}
		output = output*constant;
		return output;
	}
}
