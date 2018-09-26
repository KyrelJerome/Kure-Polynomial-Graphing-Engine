package MathObjects;

public class Point {
	String name;
	double x;
	double y;
	public Point (double newX, double newY, String newName)
	{
		x = newX;
		y = newY;
		name = newName;
	}

	
	
	
	
	//This is for basic point creation
	public void setX(int newX)
	{
		x = newX;
	}
	public void setY(int newY)
	{
		y = newY;
	}
	public double getY()
	{
		return y;
	}
	public double getX()
	{
		return x;
	}
}
