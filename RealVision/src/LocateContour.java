
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
	public ArrayList<Float> xAngles;
	public ArrayList<Float> yAngles;
public LocateContour()
{
	
}

public void locateContours(ArrayList<MatOfPoint> Contours, Mat SourceImage, float xFieldOfView, Float yFieldOfView)
{
	for (MatOfPoint c : Contours) {
		Rect rec = Imgproc.boundingRect(c);
		int centerX = rec.x + rec.width / 2;
		float xPercent = SourceImage.width() / centerX;
		float xAngle = xFieldOfView * xPercent;
		int centerY = rec.x + rec.width / 2;
		float yPercent = SourceImage.width() / centerY;
		float yAngle = yFieldOfView * yPercent;
		
		xAngles.add(xAngle);
		yAngles.add(yAngle);
		
		
		Imgproc.rectangle(SourceImage, rec.tl(), rec.br(),new Scalar(255,0,0));
		
	}
	
	Imgproc.drawContours(SourceImage, Contours, 0, new Scalar(255,0,0));
}

public ArrayList<Float> getXAngles()
{
	if(xAngles != null)
		return xAngles;
	return new ArrayList<Float>();
}
public ArrayList<Float> getYAngles()
{
	if(yAngles != null)
	return yAngles;
	return new ArrayList<Float>();
}
}
