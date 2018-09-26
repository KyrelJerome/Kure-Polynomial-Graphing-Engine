package MathObjects;

public class Function {
	public enum Types {
		FULLRATIONAL, POLYNOMIAL, PARTIONALRATIONAL, LINE
	}
	Types functionType = Types.POLYNOMIAL;
	Equation denominator;
	Equation numerator;
	String name = "";
	public Function(Equation fx,Types type )
	{
		if(type == Types.POLYNOMIAL)
		{
			numerator = fx;
			denominator = new Equation(1);
			denominator.setTerm(new Term(1,0), 0);
		}
		else if(type == Types.PARTIONALRATIONAL)
		{
			denominator = fx;
			numerator = new Equation(1);

			numerator.setTerm(new Term(1,0), 0);
		}
		functionType = type;
	}
	public Function(Function function)
	{
		denominator = new Equation(function.getDenominator());
		numerator = new Equation(function.getNumerator());
		functionType = function.getType();
	}
	public Function (Equation fx,Equation gx)
	{
		
		numerator = fx;
		denominator = gx;
		functionType = Types.FULLRATIONAL;
	}
	public Function(Equation fx,Types type , String newName)
	{
		if(type == Types.POLYNOMIAL)
		{
			numerator = fx;
		}
		else if(type == Types.PARTIONALRATIONAL)
		{
			denominator = fx;
		}
		functionType = type;
		name = newName;
	}
	public Function (Equation fx,Equation gx, String newName)
	{
		numerator = fx;
		denominator = gx;
		functionType = Types.FULLRATIONAL;
		name = newName;
	}
	public void cleannumerator()
	{
		numerator.clean();
	}
	public Equation getDenominator()
	{
		return denominator;
	}
	public Equation getNumerator()
	{
		return numerator;
	}

	public void setDenominator(Equation newDenominator)
	{
		denominator= newDenominator;
	}
	public void setNumerator(Equation newNumerator)
	{
		numerator = newNumerator;
	}

	public void setName(String newName)
	{ 
		name = newName;
	}
	public String getName()
	{
		return name;
	}

	public String printString()
	{
		String strnumerator = "( "+ numerator.getString() + " )";
		String strdenominator = "( " + denominator.getString() + " )";			
		if(numerator.getPower() == 1 && numerator.getNumberOfTerms() == 1)
		{
			strnumerator = numerator.getString();
		}
		else if(numerator.getPower() == 1)
		{
			strnumerator = "( "+ numerator.getString() + " )";
		}
		else
		{
			strnumerator = "( "+ numerator.getString() + " )" + "^" + numerator.getPower();
		}
		if(denominator.getPower() == 1 && denominator.getNumberOfTerms() == 1)
		{
			strdenominator = denominator.getString();
		}
		else if(denominator.getPower() == 1)
		{
			strdenominator = "( "+ denominator.getString() + " )";
		}
		else
		{
			strdenominator = "( "+ denominator.getString() + " )" + "^" + denominator.getPower();
		}
		if (!strdenominator.equals("1.0"))
		{
				return strnumerator + " / " + strdenominator;
		}
		else
		{
				return strnumerator.substring(1, strnumerator.length()-2);
		}
		
	}
	
	public double getOutput(double x)
	{
		return ((numerator.getOutput(x))/(denominator.getOutput(x)));
	}
	public Types getType()
	{
		return functionType;
	}
	public void setType(Types newType)
	{
		functionType = newType;
	}
}
