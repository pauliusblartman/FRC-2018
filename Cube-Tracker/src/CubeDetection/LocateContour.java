package CubeDetection;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class LocateContour {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public ArrayList<Double> xAngles;
	public ArrayList<Double> yAngles;
	
	public double x_Angle = 0;
	public double y_Angle = 0;
public LocateContour()
{
	
}

public void locateContours(ArrayList<MatOfPoint> Contours, Mat SourceImage, float xFieldOfView, float yFieldOfView)
{
	xAngles = new ArrayList<Double>();
	yAngles = new ArrayList<Double>();
	for (MatOfPoint c : Contours) {
		Rect rec = Imgproc.boundingRect(c);
		float centerX = rec.x + (rec.width / 2);
		double xPercent = (double)centerX / (double)SourceImage.width();
		double xAngle = (xFieldOfView * xPercent) - (xFieldOfView / 2);
		float centerY = rec.y + (rec.height / 2);
		double yPercent = (double)centerY / (double)SourceImage.height();
		double yAngle = (yFieldOfView * yPercent) - (yFieldOfView / 2);
		
		x_Angle = xPercent;
		y_Angle = yPercent;
		
		xAngles.add(xAngle);
		yAngles.add(yAngle);
		
		
		Imgproc.rectangle(SourceImage, rec.tl(), rec.br(),new Scalar(255,0,0));
		
	}
	
	Imgproc.drawContours(SourceImage, Contours, 0, new Scalar(255,0,0));
}

public double[] getXAngles()
{
	double[] returnArray = new double[0];
	if(xAngles != null) {
		returnArray = new double[xAngles.size()];
		for(int i = 0; i < returnArray.length; ++i)
		{
			returnArray[i] = xAngles.get(i);
		}
	}
	return returnArray;
}
public double[] getYAngles()
{
	double[] returnArray = new double[0];
	if(yAngles != null) {
		returnArray = new double[yAngles.size()];
		for(int i = 0; i < returnArray.length; ++i)
		{
			returnArray[i] = yAngles.get(i);
		}
	}
	return returnArray;
}
}
