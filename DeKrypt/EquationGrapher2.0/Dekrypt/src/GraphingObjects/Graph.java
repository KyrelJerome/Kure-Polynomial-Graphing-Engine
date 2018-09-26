package GraphingObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import MathObjects.Function;
import MathObjects.Function.Types;


public class Graph {
	final int placementX = 20;
	final int placementY = 50;
	final int width = 500;
	final int height = 500;
	final int textfontSize = 15;
	final int graphfontSize = 16;
	final double zeroy = placementY + 0.5* height;
	final double zerox = placementX + 0.5* width;
	final Font titleFont = new Font ("Arial", Font.BOLD, 30);
	final Font subTitleFont = new Font ("Arial", Font.PLAIN, 22);
	final Font textFont = new Font ("Arial", Font.PLAIN, 15);
	final Font graphFont = new Font ("Arial", Font.BOLD, graphfontSize);
	double viewWidth = 100;
	double viewHeight = 100;
	double scaleFactorX = viewWidth/width;
	double scaleFactorY = viewHeight/height;
	double boundingPointX = 0;
	double boundingPointY = 0;
	int vTileRes = height/20;
	int hTileRes = width/20;
	double vAsymptotes [] = new double[0];
	private Function function;
	private Function storedFunction;
	
	public void setVWidth(double x)
	{
		viewWidth = x;
		scaleFactorX = viewWidth/width;
	}
	public void setVHeight(double y)
	{
		viewHeight = y;
		scaleFactorY = viewHeight/height;
	}
	public void drawGraph(Graphics2D g)
	{
		updateGraphics(g);
	}

	private void updateGraphics(Graphics2D g)
	{
		g.setFont(graphFont);
		drawGridAtResolution(g);
		g.setFont(new Font("TimesRoman", Font.BOLD, graphfontSize));
		if(function != null)
		{
			System.out.println("GRAPHING FUNCTION");
			drawHOAsymptotes(g);
			drawVerticalAsymptotes(g);
			drawFunction(g);
		}
		else
		{
			System.out.println("There is no function to be graphed");
		}
	}
	private void drawVerticalAsymptotes(Graphics2D graph) {
		Function throwAwayFunction = new Function(function);
		vAsymptotes = throwAwayFunction.getDenominator().getZeros();
		double zeroes []= throwAwayFunction.getNumerator().getZeros();
		boolean isHole = false;
		graph.setFont(subTitleFont);
		graph.setColor(Color.black);
		graph.drawString("Vertical Asymptotes", width + placementX+ 50, height  -110);	
		for(int i = 0; i < vAsymptotes.length; i ++)
		{
			isHole = false;
			for(int k = 0; k < zeroes.length; k ++ )
			{

				if( vAsymptotes[i] == zeroes[k])
				{
					isHole = true;
				}

			}
			if(!isHole)
			{
				double k = zerox +(vAsymptotes[i] + boundingPointX)/scaleFactorX ;
				graph.setColor(Color.BLACK);
				graph.drawString("x = " + vAsymptotes[i], width + placementX + 50, height  -100 + 30*(i + 1));	
				graph.setColor(Color.ORANGE);
				graph.drawLine((int)(k),(int)(placementY),(int)(k),(int)(placementY + height));
			}
			else if(isHole)
			{

				graph.setColor(Color.BLACK);
				graph.drawString("(HOLE) x = " + vAsymptotes[i], width + placementX + 50, height - 100 + 30*(i + 1));	
			}
		}
		if(vAsymptotes.length == 0)
		{
			graph.setColor(Color.black);
			graph.drawString("There are no Vertical asymptotes", width + placementX+ 50, height - 70 );	
		}
	}
	private void drawFunction(Graphics2D graph)
	{
		graph.setColor(Color.BLUE);
		for(double i = 0; i < width; i ++)
		{

			double x = 0;
			int y = 0;
			int y2 = 0;
			double x2 = 0;
			// f(i) = boundingPointX  + (i- width/2)*viewX
			x = getX(i);
			y =	(int) getDrawnYI(i);

			x2 = getX(i +1);
			y2 =	(int)getDrawnYI(i +1 );
				/*
				System.out.println();
				System.out.println("**************************************NEW RUN**************************************");
				System.out.println();
				System.out.println("The Function is: " + function.printString());
				System.out.println("The Function is: " + storedFunction.printString());
				System.out.println("Function at Point (i) (" + getX(i)+" ): "+storedFunction.getOutput(x));
				System.out.println("Function at Point (i+1) (" + getX(i+1)+" ): " +storedFunction.getOutput(x2));
				System.out.println("Function at 0 = " + function.getOutput(0));
				System.out.println("Scaling factorX: " + scaleFactorX);
				System.out.println("Scaling factorY: " + scaleFactorY);
				System.out.println("Height: " + scaleFactorX);
				System.out.println("Width: " + scaleFactorY);
				System.out.println("view of X: " + viewWidth);
				System.out.println("view of Y: " + viewHeight);
				System.out.println("drawing line #" + (i -0.5*width) + " at the point (" + x + " , " + y +" )");
				System.out.println("drawing line2 #" + (i - 0.5*width) + " at the point (" + x2 + " , " + y2 +" )");
			*/
			graph.setColor(Color.BLUE);
			if(vAsymptotes.length > 0 && (function.getType() == Function.Types.FULLRATIONAL || function.getType() == Function.Types.PARTIONALRATIONAL))
			{
				boolean isOnAsymptote = false;
				for(int v = 0; v< vAsymptotes.length; v ++)
				{
					double yv =  (-boundingPointX + vAsymptotes[v])/scaleFactorX + 0.5*width;
					if(yv >= i  &&  yv <= i+1)
					{
						isOnAsymptote = true;
						//System.out.println("is on asymptote, sorry kiddo");
					}
				}
				if(isOnAsymptote)
				{

				}
				else if(Math.abs(function.getOutput(x2)) < 0.5*viewHeight&& Math.abs(function.getOutput(x)) <0.5*viewHeight)
				{

					graph.drawLine((int)(i+ placementX), y -1,(int) (i +1 +placementX),y2 -1);
					graph.drawLine((int)(i+ placementX), y,(int) (i +1 +placementX),y2 );
					graph.drawLine((int)(i+ placementX), y + 1,(int) (i +1 +placementX),y2 + 1);
				}

			}
			else if((Math.abs(function.getOutput(x2)) < 0.5*viewHeight&& Math.abs(function.getOutput(x)) <0.5*viewHeight))
			{
				graph.drawLine((int)(i+ placementX), y -1,(int) (i +1 +placementX),y2 -1);
				graph.drawLine((int)(i+ placementX), y,(int) (i +1 +placementX),y2 );
				graph.drawLine((int)(i+ placementX), y + 1,(int) (i +1 +placementX),y2 + 1);
			}
			else
			{
				//System.out.println("One of the two points shouldnt be graphed, is off graph view");
			}

		}

	}
	//Graphs horizontal asymptote


	private void drawHOAsymptotes(Graphics2D graph)
	{
		if(function.getType() == Types.FULLRATIONAL)
		{
			if(function.getNumerator().getHighestDegree() == function.getDenominator().getHighestDegree())
			{
				double cNum =  function.getNumerator().getTermOfDegree(function.getNumerator().getHighestDegree()).getConstant();
				double cDen =  function.getDenominator().getTermOfDegree(function.getDenominator().getHighestDegree()).getConstant();
				double ay =cNum/cDen;
				double y = zeroy -(ay + boundingPointY)/scaleFactorY;
				//System.out.println("There is a horizontal asymptote at y = " +  ay);
				if( 0.5*viewHeight> Math.abs(ay))
				{
					//System.out.println("The Horizontal asymptote has been graphed.");
					graph.setColor(Color.green);
					graph.drawLine((int)(placementX),(int) y -1,(int) (width +placementX),(int)y -1);
					graph.drawLine((int)(placementX),(int) y,(int) (width +placementX),(int)y);
					graph.drawLine((int)(placementX),(int) y + 1,(int) (width +placementX),(int)y + 1);
					graph.setColor(Color.black);
					graph.setFont(subTitleFont);
					graph.drawString("Horizontal Asymptote",(int)(placementX + width + 100), (int) (zeroy -120));
					graph.setFont(textFont);
					graph.drawString("y = " + ay,(int)(zerox + width/2 + 20), (int) (zeroy) - 100);
				}
				else
				{
					System.out.println("The Horizontal asymptote is not visible on the current viewHeight of:");
					System.out.println("The recommended viewing height of this function is:" +(2.2*ay));
				}
			}
			else if( function.getNumerator().getHighestDegree() < function.getDenominator().getHighestDegree())
			{
				double ay = 0;
				double y = zeroy ;
				//System.out.println("There is a horizontal asymptote at y = " +  ay);
				if( 0.5*viewHeight> Math.abs(ay))
				{
					//System.out.println("The Horizontal asymptote has been graphed.");
					graph.setColor(Color.green);
					graph.drawLine((int)(placementX),(int) y -1,(int) (width +placementX),(int)y -1);
					graph.drawLine((int)(placementX),(int) y,(int) (width +placementX),(int)y);
					graph.drawLine((int)(placementX),(int) y + 1,(int) (width +placementX),(int)y + 1);
					graph.setColor(Color.BLACK);
					graph.setFont(subTitleFont);
					graph.drawString("Horizontal Asymptote",(int)(placementX + width + 100), (int) (zeroy -120));
					graph.setFont(textFont);
					graph.drawString("y = 0",(int)(placementX + width + 100), (int) (zeroy) - 100);
				}

			}

			else if(function.getNumerator().getHighestDegree() > function.getDenominator().getHighestDegree())
			{
				if(function.getNumerator().getHighestDegree() == function.getDenominator().getHighestDegree() + 1)
				{
					//System.out.println("There is a linear oblique asymptote");
					//y = mx + b
					//b = y-mx
					double x1 = -90000000;
					double x2 = 90000000;
					double y1 = function.getOutput(x1);
					double y2 = function.getOutput(x2);
					double m = (y2-y1)/(x2-x1);
					double b = y1 - (m*x1);
					String oblique = "y = " +Math.round(100000*m)/100000 + "x + " + Math.round(100000*b)/100000;
					//System.out.println("The oblique is " + oblique);

					graph.setColor(Color.BLACK);
					graph.setFont(subTitleFont);
					graph.drawString("Oblique",(int)(placementX + width + 100), (int) (zeroy - 100));
					graph.setFont(textFont);
					graph.drawString(oblique,(int)(placementX+ width + 100), (int) (zeroy -70));

					int oy = (int) ((getX(0)*m+b) /-scaleFactorY + zeroy );
					int oy2 =(int) ((getX(499)*m +b)/-scaleFactorY+ zeroy);
					//Draw oblique asymptote
					graph.setColor(Color.green);
					graph.drawLine((int)(placementX),(int) oy +1,(int) (width +placementX),(int)oy2 +1);
					graph.drawLine((int)(placementX),(int) oy ,(int) (width +placementX),(int)oy2);
					graph.drawLine((int)(placementX),(int) oy-1 ,(int) (width +placementX),(int)oy2-1);

				}
				else 
				{
					System.out.println("The oblique asymptote is not linear and therefore cannot be graphed by KURE.");	
				}
			}
		}
		//Prints data underneath
		graph.setColor(Color.BLACK);

		graph.setFont(subTitleFont);
		graph.drawString("Drawing the following function:", (int) (placementX + width + 50), placementY + 80);

		graph.setFont(textFont);
		graph.drawString( "f(x) = " +function.printString(), (int) (width + placementX + 80), placementY + 115);


	}
	private double getX(double i)
	{
		double transfer1 = i - 0.5*width;
		double x = transfer1*scaleFactorX + boundingPointX;//(i - 0.5*width)*scaleFactorX + boundingPointX -x = 0  
		return x;
	}
	private double getYX(double x)
	{
		double y = zeroy -(function.getOutput(x) + boundingPointY)/scaleFactorY;
		return y;
	}
	private double getYI(double i)
	{
		return function.getOutput(getX(i));
		
	}
	private double getDrawnYI(double i)
	{	
		return zeroy -(getYI(i) - boundingPointY)/scaleFactorY;
	}
	private void drawGridAtResolution(Graphics2D graph)
	{
		graph.setFont(titleFont);
		graph.drawString("", (int) (placementX + width + 50), placementY + 30);
		graph.setFont(graphFont);
		graph.setColor(Color.WHITE);
		graph.fillRect(placementX, placementY, width, height);
		//Draw grid lines
		graph.setColor(Color.LIGHT_GRAY);
		for(int i = 0; i < height/2; i+= vTileRes)
		{

			graph.drawLine(placementX, placementY  + i + height/2, placementX + width,placementY+ i+ height/2); // Vertical lines to the rightof axis
			graph.drawLine(placementX , placementY - i + height/2, placementX+ width ,placementY - i+ height/2); // Vertical lines to the leftof axis
		}
		for(int i = 0; i < width/2; i+= hTileRes)
		{
			graph.drawLine(placementX + i + width/2, placementY , placementX + i+ width/2,placementY + height); // Vertical lines to the rightof axis
			graph.drawLine(placementX - i + width/2, placementY , placementX - i+ width/2,placementY + height); // Vertical lines to the left of axis
		}
		//Draw Axis
		graph.setColor(Color.BLACK);

		graph.drawLine(placementX , placementY + height/2 , placementX + width,placementY + height/2);// X
		graph.drawLine(placementX + width/2, placementY , placementX + width/2,placementY + height);//Y
		//Draw markers
		for(int i = -4; i <5; i ++)
		{

			graph.setColor(Color.BLACK);
			graph.drawLine((int)zerox -5 , (int) ((height*i)/8 + zeroy), (int)zerox +5 ,(int) ((height*i)/8 + zeroy));// X
			graph.drawLine((int) ((width*i)/8 + zerox), (int)zeroy -5 , (int) ((width*i)/8 + zerox) ,(int)zeroy +5 );// X
			graph.setFont(graphFont);
			if(i != 0)
			{
				graph.setColor(Color.RED);
				graph.drawString( "" +((viewWidth/8)*i +boundingPointX) ,(int) ((width*i)/8 + zerox), (int) zeroy);
				graph.drawString( "" +((viewHeight/-8)*i + boundingPointY) ,(int)zerox, (int) ((height*i)/8 + zeroy));

			}
		}
		graph.setColor(Color.BLACK);
		graph.drawRect(placementX, placementY, width, height);

	}
	public void setFunction(Function newFunction)
	{
		storedFunction = new Function(newFunction);
		function = new Function(newFunction);
	}
	//TODO : COMPLETE UPDATES?}
}
