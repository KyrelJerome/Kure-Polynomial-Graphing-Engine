package MathObjects;
import MathObjects.Calculator;
public class Equation {

	private Term terms[];
	private int numOfTerms;
	private double power;

	//Instantiators
	public Equation(Term[] newTerms,int  newPower)
	{
		power = newPower;
		terms = newTerms;
		numOfTerms = newTerms.length;
	}
	public Equation(Term[] newTerms)
	{
		power = newTerms.length;
		terms = newTerms;
		numOfTerms = newTerms.length;
		power = 1;
	}
	public Equation(int numberOfTerms)
	{
		numOfTerms = numberOfTerms;
		terms = new Term[numberOfTerms];
		power = 1;
	}
	public Equation(Equation copy)
	{
		Calculator kureEngine = new Calculator();
		Term oneTerm = new Term(1,0);
		Equation oneEquation = new Equation(1);

		oneEquation.setTerm(oneTerm, 0);
		Equation tempEquation = kureEngine.multiplyEquations(oneEquation,copy);
		power = copy.power;
		numOfTerms =  tempEquation.numOfTerms;
		terms = new Term[numOfTerms];
		for(int i = 0; i < tempEquation.numOfTerms; i ++)
		{
			terms[i] = tempEquation.getTermAt(i);
		}
	}
	//Setters and things that change internals
	public void setTerm(Term newTerm, int index)
	{
		terms[index] = new Term(newTerm.getConstant(),newTerm.getPower());
	}
	public void setPower(int newPower)
	{
		power = newPower;
	}
	
	//May be used whenever without risk of crashing
	public void clean(){
		//Algorithm cleans by removing all the terms with a constant  of 0 from the equation.
		int numberOfRealTerms = 0;
		Term tempTerms []= terms;

		terms = tempTerms;
		//Count non-zero terms
		for(int i = 0; i < getNumberOfTerms(); i ++)
		{
			if(terms[i]!= null)
			{
				if(terms[i].getConstant() != 0)
				{
					numberOfRealTerms ++;
				}
			}
		}
		//Clear "0" terms
		tempTerms = new Term[numberOfRealTerms];
		int counter = 0;;
		for(int i = 0; i < numOfTerms; i ++)
		{
			if(terms[i]!= null)
			{
				if(terms[i].getConstant() != 0)
				{
					tempTerms[counter] = terms[i];
					counter ++;
				}
			}
		}



		numOfTerms = numberOfRealTerms;
		terms = tempTerms;
		if(numberOfRealTerms == 0)
		{
			numOfTerms = 1;
			terms = new Term[1];
			terms[0] = new Term(0,0);
		}
		//Sort the new terms tab
		int n = terms.length;  
		Term temp;  
		//Bubble sort
		for(int i=0; i < n; i++){  
			for(int j=1; j < (n-i); j++){  
				if(terms[j-1].power > terms[j].power){  
					//swap elements  
					temp = terms[j-1];  
					terms[j-1] = terms[j];  
					terms[j] = temp;  
				}  

			}  
		}
	}
	public void groupLikeTerms()
	{
		Calculator calc = new Calculator();
		Equation resultant = new Equation((int)getHighestDegree() + 1);

		for(int i = 0; i < getHighestDegree() + 1 ;i ++)
		{
			Term currentTerm = new Term(0,i);
			for(int k = 0; k < getNumberOfTerms(); k ++)
			{
				if(getTermAt(k).getPower() == i)
				{

					currentTerm = calc.addTerms(getTermAt(k), currentTerm);

				}
			}

			resultant.setTerm(currentTerm, i);
		}
		resultant.clean();
		terms = new Term[resultant.getNumberOfTerms()];
		for(int i = 0 ; i <resultant.getNumberOfTerms();i ++ )
		{
			terms[i] = resultant.getTermAt(i);
		}
	}
	public double getOutput(double x)
	{
		double output = 0;
		for(int i = 0; i < terms.length; i ++)
		{
			//System.out.println("term ["+ i+"] : "+ terms[i].getConstant()+ "*" +x+" ^" + terms[i].getPower());
			output = output + terms[i].getOutput(x);
		}

		return output;
	}

	//Getters of specific information
	public int getNumberOfTerms()
	{
		numOfTerms = terms.length;
		return numOfTerms;
	}
	public double getTermDegree(int position)
	{
		return terms[position].getPower();
	}
	public Term getTermOfDegree(double degree)
	{
		for(int i = 0; i < terms.length;  i ++)
		{
			if(terms[i].getPower() ==degree)
			{
				return  terms[i];
			}
		}

		return new Term(0,0);
	}

	public double getHighestDegree()
	{
		double degree = 0;
		for(int i = 0; i <terms.length; i ++)
		{
			if(terms[i].getPower() > degree)
			{
				degree =  terms[i].getPower();
			}
		}
		return degree;
	}
	public double getLowestDegree()
	{
		double degree = 100;
		for(int i = 0; i <terms.length; i ++)
		{
			if(terms[i].getPower() < degree)
			{
				degree =  terms[i].getPower();
			}
		}
		return degree;
	}
	public double getPower()
	{
		return power;
	}
	public String getString()
	{
		String output = "";
		for(int i = terms.length-1; i >= 0; i --)
		{
			if(i == terms.length-1)
			{
				output = output + terms[i].printTerm();
			}
			else if(terms[i]!= null)
			{
				output = output + " + " + terms[i].printTerm() ;
			}
		}
		return output;
	}
	// if a term is f(x) = ax^n where a and n are constants. the derivative is f'(x) = a*n*x^(n-1)
	public Term getTermAt(int i)
	{
		return terms[i];
	}
	public Equation getDerivative()
	{
		Equation derivative = new Equation(numOfTerms);

		if(power == 1)
		{
			for (int i = 0; i <terms.length; i ++)
			{
				if(terms[i].getPower()!= 0 && terms[i].getConstant()!= 0)
				{
					System.out.println("Derivationsasdfasdf");
					double constant = terms[i].getPower()*terms[i].getConstant();
					double power = terms[i].getPower() -1; //n - 1
					derivative.setTerm(new Term(constant,power),i);
				}
				else{
					derivative.setTerm(new Term(0,0), i);
				}

			}

			derivative.clean();
		}
		else
		{
			//PowerRule
			//System.out.println("Yo dude, power rule was used");
			Calculator powerCalc = new Calculator();
			Equation n  = new Equation(1);
			n.setTerm(new Term(this.getPower(),0),0);
			Equation innerDerivative = innerGetDerivative();
			Equation tempFunction = new Equation(this);
			tempFunction.setPower((int)this.getPower() -1);
			

			derivative = powerCalc.multiplyEquations(powerCalc.multiplyEquations(innerDerivative, tempFunction),n);
			derivative.clean();
			//System.out.println("Power rule output: " + derivative.getString());
		}
		return derivative;
	}
	private Equation innerGetDerivative()
	{
		Equation derivative2 = new Equation(numOfTerms);
		for (int i = 0; i <terms.length; i ++)
		{
			if(terms[i].getPower()!= 0 && terms[i].getConstant()!= 0)
			{
				double constant = terms[i].getPower()*terms[i].getConstant();
				double power = terms[i].getPower() -1; //n - 1
				derivative2.setTerm(new Term(constant,power),i);
			}
			else{
				derivative2.setTerm(new Term(0,0), i);
			}

		}

		derivative2.clean();
		return derivative2;
	}
	// TODO: FIND QUADRATIC ZEROES
	public double[] getZeros()
	{

		System.out.println(" ***************PERFORMING CALCULATIONS********************");
		//Before performing procedure, ensure they are properly sorted.
		//instantiate
		double zeroes[] = new double[0];
		boolean isZeroFactor = false;
		Equation tempEquation = new Equation(this);
		Term tempTerm;
		double ld = getLowestDegree();
		System.out.println("Lowest Degree =" + ld);
		for(int i = 0; i < terms.length; i ++)
		{
			tempTerm = tempEquation.getTermAt(i);
			tempTerm.setPower(tempTerm.getPower() -ld );
			tempEquation.setTerm(tempTerm, i);
		}
		if(ld > 0)
		{
			zeroes = new double[1];
			zeroes[0] =0;
			isZeroFactor = true;
		}
		//Find equations using given equations
		if(tempEquation.getHighestDegree() == 2)
		{
			zeroes = factorQuadratic(tempEquation);
		}
		else if (tempEquation.getHighestDegree() == 3)
		{
			double z =tempEquation.getTermOfDegree(0).getConstant();
			int numberOfFactors = 0;
			int zfactors[] = new int[(int)Math.ceil(z)];
			if(z != 0) 
			{
				for(int x=1; x <= z; x++) {
					if(z % x == 0) {
						numberOfFactors ++;
					}
				}
				zfactors = new int[numberOfFactors];

				int counter = -1;
				for(int x=1; x <= z; x++) {
					if(z % x == 0) 
					{

						counter ++;
						zfactors[counter] = x;
					}
				}

			}
			//Found factors, now test to see if it is a factor
			System.out.println("Searching for an integer factor of the semi-factored equation.");
			int workingFactor = 0;
			for(int i = 0; i < zfactors.length; i ++)
			{
				if(tempEquation.getOutput(zfactors[i]) == 0)
				{
					workingFactor = zfactors[i];
					break;
				}
			}
			if(workingFactor != 0)
			{
				Calculator kure = new Calculator();
				Term dividerTerms []= new Term[2];
				dividerTerms[1] =  new Term (-workingFactor,0);
				dividerTerms[2] = new Term (1, 1);
				Equation divider = new Equation(dividerTerms);

				Equation resultant = kure.divideEquations(tempEquation, divider);

				zeroes = resultant.factorQuadratic(tempEquation);
				double temporaryArray[] = new double[zeroes.length+1];
				for(int i = 0; i < zeroes.length; i ++)
				{
					temporaryArray[i] = zeroes.length;
				}
				temporaryArray[temporaryArray.length - 1] = workingFactor;
			}
			else
			{
				System.out.println("THERE ARE NO INTEGER FACTORS OF THE EQUATION AND THEREFORE THIS CANNOT BE FACTORED");
			}
		}
		else if (tempEquation.getHighestDegree() == 1)
		{

			double b = tempEquation.getTermOfDegree(1).getConstant();
			double c = tempEquation.getTermOfDegree(0).getConstant();
			zeroes = new double[1];
			zeroes[0] = c/-b;
		}
		else if(tempEquation.getHighestDegree() == 0)
		{
			zeroes = new double[1];
			zeroes[0] = 0;
			return zeroes;
		}
		if(isZeroFactor)
		{
			double finalReturn[] = new double[zeroes.length + 1];
			for(int i = 0; i < zeroes.length; i ++)
			{
				finalReturn[i] = zeroes[i];
			}
			finalReturn[finalReturn.length -1] = 0;

			System.out.println(" ***************CALCULATIONS COMPLETE ********************");
			return finalReturn;
		}
		else
		{

			System.out.println(" ***************CALCULATIONS COMPLETE ********************");
			return zeroes;
		}

	}
	private double[] factorQuadratic(Equation tempEquation) {
		double a = tempEquation.getTermOfDegree(2).getConstant();
		double b = tempEquation.getTermOfDegree(1).getConstant();
		double c = tempEquation.getTermOfDegree(0).getConstant();
		double discriminate = Math.pow(b, 2) - 4*a*c;
		double zeroes[] = new double [0];
		if(discriminate == 0)
		{
			zeroes = new double[1];
			zeroes[0] = -b/(2*a);
		}
		if(discriminate > 0)
		{

			zeroes = new double[2];
			zeroes[1] = ( Math.sqrt(discriminate) - b)/(2*a);
			zeroes[0] = ( - Math.sqrt(discriminate) - b)/(2*a);
		}
		else if(discriminate < 0)
		{

			zeroes = new double[0];
		}
		return zeroes;
	}
}
