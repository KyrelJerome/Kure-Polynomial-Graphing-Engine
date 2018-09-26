package MathObjects;

public class Calculator {

	public Term addTerms(Term t1, Term t2)
	{
		double constant = t1.getConstant()+ t2.getConstant();

		Term t3 = new Term(constant, t1.getPower());
		return t3;
	}
	public Term subtractTerms(Term t1, Term t2)
	{
		double constant = t1.getConstant()- t2.getConstant();
		Term t3 = new Term(constant, t1.getPower());
		return t3;
	}

	public Term divideTerms(Term t1, Term t2)
	{
		double constant = t1.getConstant()/ t2.getConstant();
		double power = t1.getPower()-t2.getPower();
		Term t3 = new Term(constant, power);
		return t3;
	}
	public Term multiplyTerms(Term t1, Term t2)
	{
		double constant = t1.getConstant()*t2.getConstant();
		double power = t1.getPower()+t2.getPower();
		Term t3 = new Term(constant, power);
		return t3;
	}


	public Equation multiplyEquations(Equation fx , Equation gx)
	{
		Equation resultant = new Equation(fx.getNumberOfTerms()*gx.getNumberOfTerms());
		for(int i = 0; i < fx.getNumberOfTerms(); i ++)
		{
			for(int k =  0; k < gx.getNumberOfTerms(); k ++)
			{
				resultant.setTerm(multiplyTerms(fx.getTermAt(i), gx.getTermAt(k)), i*gx.getNumberOfTerms() + k);
			}
		}
		resultant.clean();
		resultant.groupLikeTerms();
		return resultant;
	}
	public Equation divideEquations(Equation fx, Equation gx)
	{
		//divides fx by gx and returns resultant
		Equation resultant = null;
		Equation throwMIDDLE = new Equation(2);
		Equation throwBOTTOM = new Equation(2);
		Equation throwTOP = new Equation(1);
		Term tempTerm ;
		resultant = new Equation((int)fx.getHighestDegree()-1);
		throwBOTTOM.setTerm(fx.getTermOfDegree(fx.getHighestDegree()),0);
		throwBOTTOM.setTerm(fx.getTermOfDegree(fx.getHighestDegree() -1),1);
		int currentFormulatingDegree = 0;
		 for(int i = 0; i > fx.getHighestDegree()-1 ; i ++)
		 {
			 currentFormulatingDegree = (int) (fx.getHighestDegree() -1 -i);
			 tempTerm = divideTerms( throwBOTTOM.getTermOfDegree(currentFormulatingDegree),
					 gx.getTermOfDegree(gx.getHighestDegree())
					 );
			 throwTOP.setTerm(tempTerm , currentFormulatingDegree); 
			 System.out.println(resultant.getString());
			 //ADDS TO RESULTANT
			 resultant.setTerm(throwTOP.getTermAt(0),currentFormulatingDegree);
			 //Last step in recursion
			 throwMIDDLE = multiplyEquations(throwTOP,gx);
			 throwBOTTOM =  subtractEquations(throwMIDDLE, throwBOTTOM);
		 }
		
		return resultant;
	}


	//COMPLETE
	public Equation AddEquations(Equation fx , Equation gx)
	{
			
		int highestDegree = 0;
		if(fx.getHighestDegree() > gx.getHighestDegree())
		{
			highestDegree = (int) fx.getHighestDegree();
		}
		else{
			highestDegree = (int) gx.getHighestDegree();
		}
		Equation resultant = new Equation(highestDegree + 1);

		for(int i = 0; i < highestDegree + 1 ;i ++)
		{
			Term currentTerm = new Term(0,i);
			for(int k = 0; k < fx.getNumberOfTerms(); k ++)
			{
				if(fx.getTermAt(k).getPower() == i)
				{

					currentTerm = addTerms(fx.getTermAt(k), currentTerm);

				}
			}
			for(int k = 0; k < gx.getNumberOfTerms(); k ++)
			{
				if(gx.getTermAt(k).getPower() == i)
				{
					currentTerm = addTerms(gx.getTermAt(k), currentTerm);
				}
			}
			resultant.setTerm(currentTerm, i);
		}
		resultant.clean();
		return resultant;
	}
	public Equation subtractEquations(Equation fx , Equation gx)
	{
		int highestDegree = 0;
		if(fx.getHighestDegree() > gx.getHighestDegree())
		{
			highestDegree = (int) fx.getHighestDegree();
		}
		else{
			highestDegree = (int) gx.getHighestDegree();
		}
		Equation resultant = new Equation(highestDegree + 1);

		for(int i = 0; i < highestDegree + 1 ;i ++)
		{
			Term currentTerm = new Term(0,i);
			for(int k = 0; k < fx.getNumberOfTerms(); k ++)
			{
				if(fx.getTermAt(k).getPower() == i)
				{

					currentTerm = addTerms(currentTerm, fx.getTermAt(k));

				}
			}
			for(int k = 0; k < gx.getNumberOfTerms(); k ++)
			{
				if(gx.getTermAt(k).getPower() == i)
				{
					currentTerm = subtractTerms(currentTerm, gx.getTermAt(k));
				}
			}
			resultant.setTerm(currentTerm, i);
		}
		resultant.clean();
		return resultant;
	}
	//TODO: FINISH 
	public boolean checkEqualEquations(Equation fx, Equation gx)
	{
		fx.groupLikeTerms();
		fx.clean();
		gx.groupLikeTerms();
		gx.clean();
		if(fx.getString().equals(gx.getString())) {
			return true;
		}
		return false;
	}
	//Complete
	public Function getFunctionDerivative(Function gx)
	{
		Function fx = new Function(gx);
		Function fx0 = null;
		if(fx.getType() == Function.Types.POLYNOMIAL)
		{
			fx0 = new Function(fx.getNumerator().getDerivative(),Function.Types.POLYNOMIAL);
		
		}
		else //Rational
		{
			System.out.println(" Function Identified as Rational");

			Equation newDenominator = fx.getDenominator();
			Equation newNumerator = null;
			Equation p0 = fx.getNumerator().getDerivative();
			Equation p = fx.getNumerator();
			Equation r = fx.getDenominator();
			Equation r0 = fx.getDenominator().getDerivative();
			
			Equation p0r = multiplyEquations(p0,r);
			//System.out.println("P'r = " + p0r.getString());
			Equation pr0 = multiplyEquations(r0,p);
			//System.out.println("Pr' = " + pr0.getString());
			pr0.clean();
			p0r.clean();
			//System.out.println("OLD Denominator Power = " +  newDenominator.getPower());
			newDenominator.setPower((int)newDenominator.getPower()*2);
			//System.out.println("New Denominator Power = " +  newDenominator.getPower());
			newNumerator =subtractEquations(p0r,pr0);
			fx0 = new Function(newNumerator,newDenominator);
		}

		return fx0;

	}
}
