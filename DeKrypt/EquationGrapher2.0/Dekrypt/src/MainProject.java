import java.util.Scanner;


import MathObjects.Calculator;
import MathObjects.Equation;
import MathObjects.Function;
import MathObjects.Function.Types;
import MathObjects.Term; 

public class MainProject {

	static Function userfunction ;
	static Function trueUserfunction;
	static Function userDerivative;
	static Function userDerivative2;
	static double Vasymptotes[] ;
	static double zeroes[];
	static Calculator kureEngine = new Calculator();
	static Scanner user = new Scanner(System.in);
	static KurikWindow window;
	
	
	public static void chooseFunction()
	{
		double choice = 0;

		while(true)
		{
			System.out.println("What kind of function would you like to input?");
			System.out.println("1)  Polynomial    ex: f(x) = 2x^2 + 2x+1");
			System.out.println("2)  Rational      ex: f(x) = (2x + 3)/x");
			choice = getIntInput();
			if(choice == 1)
			{
				System.out.println("----DEFINE THE POLYNOMIAL----");					
				Equation numerator = createEquation();
				userfunction = new Function(numerator,Function.Types.POLYNOMIAL);
				trueUserfunction = new Function(userfunction);
				break;
			}
			else if(choice ==2)
			{
				//Rational
				System.out.println("----DEFINE THE NUMERATOR----");
				Equation numerator = createEquation();
				numerator.clean();
				System.out.println("----DEFINE THE DENOMINATOR----");
				Equation denominator = createEquation();
				denominator.clean();
				
				userfunction = new Function(numerator,denominator);
				trueUserfunction = new Function(userfunction);
				break;
			}
			else
			{
				System.out.println("You have chosen an invalid input! Please try again:");
				enterToContinue();
			}

		}
	}
	public static void mainmenu()
	{
		double choice = 0;
		while(true)
		{
			//Would you like to create a new equation completely?
			//Would you like the current equation to be graphed?
			//Would you like the zeroes of the numerator (zeroes)
			//Would you like the holes?
			//Would you like all the data?
			//Would you like the derivative equation returned in standard form?
			//Would you like the zeroes of the denominator (asymptotes)

			System.out.println("-------------MAIN MENU------------");
			System.out.println("Here are your following options using the current Function of :");
			System.out.println("f(x) = " + userfunction.printString());
			System.out.println("Options:");
			System.out.println("1) Graph f(x)");
			System.out.println("2) Calculate f'(x)");
			System.out.println("3) Calculate f''(x)");
			System.out.println("4) Change the width of what parts of the graph you can see.");
			System.out.println("5) Change the height of the graph you can see.");
			System.out.println("6) Calculate Zeroes and asymptotes of f(x)");
			System.out.println("7) Tamper with the built-In calculator!");
			System.out.println("8) Choose new equations");
			choice = getIntInput();
			System.out.println("");
			if(choice == 1)
			{
				//updating function in graph (only done when necessary to save processing power)
				window.graph.setFunction(trueUserfunction);
				drawToScreen();
				enterToContinue();
			}
			else if(choice ==2)
			{
				System.out.println("");
				System.out.println("----Derivative----");
				System.out.println("f'(x) = " +userDerivative.printString());
				System.out.println("");
				enterToContinue();
			}
			else if(choice ==3)
			{
				System.out.println("");
				System.out.println("----Second Derivative----");
				System.out.println("f'(x) = " +userDerivative2.printString());
				System.out.println("");
				enterToContinue();
			}
			else if(choice ==4)
			{
				while(true)
				{
					System.out.println("What is the width of your perferred viewing window");
					choice = Double.parseDouble(user.nextLine());
					window.graph.setVWidth(choice);
					drawToScreen();
					System.out.println("Change complete, view Graph");
					enterToContinue();
					break;
				}

			}
			else if(choice ==5)
			{
				while(true)
				{
					System.out.println("What is the height of your perferred viewing window");
					choice = Double.parseDouble(user.nextLine());
					window.graph.setVHeight(choice);
					drawToScreen();
					System.out.println("Change complete, view Graph");
					enterToContinue();
					break;
				}
			}
			else if(choice ==6)
			{
				printCriticalData();
				enterToContinue();
			}
			else if(choice ==7)
			{
				System.out.println("Opening Calculator");
				goToCalc();
			}
			else if(choice ==8)
			{
				enterToContinue();
				break;
			}
			else 
			{
				System.out.println("You have chosen an invalid input! Please try again.");
				enterToContinue();
			}

		}

	}
	public static void goToCalc()
	{
		Equation fx;
		Equation gx;
		Equation resultant;

		double choice = 0;
		while(true)
		{
			System.out.println("These are the valid options");
			System.out.println("1) Add Equations");
			System.out.println("2) Subtract Equations");
			System.out.println("3) Multiply Equations");
			System.out.println("4) Divide Equations");
			System.out.println("5) Leave Calculator");
			System.out.println("");
			choice = Integer.parseInt(user.nextLine());
			if(choice ==1)
			{
				System.out.println("Equation 1:");
				fx = createEquation();

				System.out.println("Equation 2:");
				gx = createEquation();
				resultant  = kureEngine.AddEquations(fx, gx);
				System.out.println("Calculations complete");
				System.out.println("f(x) =" + fx.getString());
				System.out.println("g(x) =" + gx.getString());

				System.out.println("Resultant =" + resultant.getString());
			}
			else if(choice ==2)
			{

				System.out.println("Equation 1 - Equation 2");
				System.out.println("Equation 1:");
				fx = createEquation();

				System.out.println("Equation 2:");
				gx = createEquation();
				resultant  = kureEngine.subtractEquations(fx, gx);
				System.out.println("Calculations complete");
				System.out.println("f(x) =" + fx.getString());
				System.out.println("g(x) =" + gx.getString());

				System.out.println("Resultant =" + resultant.getString());
			}		
			else if(choice ==3)
			{
				System.out.println("Equation 1 * Equation 2");
				System.out.println("Equation 1:");
				fx = createEquation();
				System.out.println("Equation 2:");
				gx = createEquation();
				resultant  = kureEngine.multiplyEquations(fx, gx);
				System.out.println("Calculations complete");
				System.out.println("f(x) =" + fx.getString());
				System.out.println("g(x) =" + gx.getString());
				System.out.println("Resultant =" + resultant.getString());

			}		
			else if(choice ==4)
			{
				System.out.println("Equation 1 / Equation 2");
				System.out.println("Equation 1:");
				fx = createEquation();
				System.out.println("Equation 2:");
				gx = createEquation();
				resultant  = kureEngine.divideEquations(fx, gx);
				System.out.println("Calculations complete");
				System.out.println("f(x) =" + fx.getString());
				System.out.println("g(x) =" + gx.getString());
				System.out.println("Resultant =" + resultant.getString());

			}	
			else if(choice ==5)
			{
				break;
			}		
			else
			{
				System.out.println("You have chosen an invalid input! Please try again.");
				enterToContinue();
			}

		}
	}

	public static void printCriticalData()
	{
		double zeroes[] = userfunction.getNumerator().getZeros();
		double asymptotes[] = userfunction.getDenominator().getZeros();
		double turningPoints [] = userDerivative.getNumerator().getZeros();
		double incidentPoints[] =userDerivative2.getNumerator().getZeros();
		System.out.println("The Zeroes of the equation are:");
		if(zeroes.length == 0)
		{
			System.out.println("The program cannot find the zeroes for this equation.");
		}
		else 
		{
			System.out.print("x = ");
			for(int i = 0; i < zeroes.length; i ++ )
			{
				if( i == zeroes.length-1)
				{
					System.out.println(zeroes[i]);
				}
				else
				{
					System.out.print(zeroes[i] + " , ");
				}
			}
		}
		System.out.println(" ");
		if(userfunction.getType() == Types.FULLRATIONAL)
		{
			System.out.println("The Vertical Asymprotes of the equation are:");
			if(zeroes.length == 0)
			{
				System.out.println("The program cannot find the zeroes for this equation.");
			}
			else 
			{
				System.out.print("x = ");
				for(int i = 0; i < asymptotes.length; i ++ )
				{
					if( i == asymptotes.length-1)
					{
						System.out.println(asymptotes[i]);
					}
					else
					{
						System.out.print(asymptotes[i] + " , ");
					}
				}
			}
		}
		System.out.println("The turning points of the equation are:");
		if(turningPoints.length == 0)
		{
			System.out.println("The program cannot find the turning points for this equation.");
		}
		else 
		{
			System.out.print("x = ");
			for(int i = 0; i < turningPoints.length; i ++ )
			{
				if( i == turningPoints.length-1)
				{
					System.out.println(turningPoints[i]);
				}
				else
				{
					System.out.print(turningPoints[i] + " , ");
				}
			}
		}
		System.out.println("The incident  points of the equation are:");
		if(incidentPoints.length == 0)
		{
			System.out.println("The program cannot find the incident points for this equation.");
		}
		else 
		{
			System.out.print("x = ");
			for(int i = 0; i < incidentPoints.length; i ++ )
			{
				if( i == incidentPoints.length-1)
				{
					System.out.println(incidentPoints[i]);
				}
				else
				{
					System.out.print(incidentPoints[i] + " , ");
				}
			}
		}
	}

	public static void main(String[] args) {

		createWindow();
		System.out.println("Welcome to Kurik's custom Equation Graphing Technology - KURE");
		System.out.println("This Technology is capable of graphing any Polynomial function, and any rational function with degree's up to 4");
		enterToContinue();
		//Main Program loop
		while(true){
			//Function selection loop
			chooseFunction();

			System.out.println("You have created your Function f(x)!");
			enterToContinue();
			System.out.println("");

			System.out.println("Generated analysis of your inputted function of:");
			System.out.println("f(x) = " + userfunction.printString());
			System.out.println("");

			//Prints derivative and second derivative
			userDerivative = kureEngine.getFunctionDerivative(userfunction);
			System.out.print("f'(x) = ");
			System.out.println(userDerivative.printString());
			userDerivative2 = kureEngine.getFunctionDerivative(userDerivative);
			System.out.print("f''(x) = ");
			
			System.out.println(userDerivative2.printString());
			enterToContinue();
			//Operation Selection
			mainmenu();
		}
	}
	public static void drawToScreen()
	{
		System.out.println("Drawing graph... please wait");
		window.repaint();
		System.out.println("Finished Drawing graph.");
	}
	public static Equation createEquation()
	{
		Equation fx;
		int choice = 0;
		int degree = 0;
		double constantChoice = 0;
		System.out.println("You will now enter a Standard Form Equation");
		while(true)
		{
			System.out.println("What is the degree of the function?");
			choice = getIntInput();
			fx = new Equation(choice+1);
			degree = choice+1;
			break;
		}
		System.out.println("For your function of degree (" + choice + "), please pick the following constant values.");

		//Sort by termnumber
		for(int i = 0; i <degree; i++)
		{
			System.out.println("Term ax^"+ i + " a:");
			constantChoice = getDoubleInput();
			Term currentTerm = new Term(constantChoice,i);
			fx.setTerm(currentTerm, i);
			System.out.println();	
		}

		System.out.print("The equation is: " + fx.getString());
		System.out.println("");
		return fx;
	}
	public static void createWindow()
	{

		window = new KurikWindow();
	}
	public static double getDoubleInput() {
		while(true) {
		try {
			double x = Double.parseDouble(user.nextLine());
			return x;
		}
		catch(Exception e)
		{
			System.out.println("Input must be a double. Try again:");
		}
		}
	}
	public static int getIntInput() {
		while(true) {
		try {
			int x = Integer.parseInt(user.nextLine());
			return x;
		}
		catch(Exception e)
		{
			System.out.println("Input must be an integer. Try again:");
		}
		}
	}
	public static void enterToContinue()
	{
		System.out.println("Enter To Continue...");
		user.nextLine();

	}
}
