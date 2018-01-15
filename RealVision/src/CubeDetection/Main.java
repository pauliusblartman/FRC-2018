package CubeDetection;
import java.net.InetAddress;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public static int Camera_Port = 0; //probably 0
	
	public static float HorizontalFOV = 59.7F;
	public static float VerticalFOV = 33.6F;
	
	public static String ip = "roboRIO-1493-FRC.lan";
	
	public static void main(String[] args) {
		Mat matSource = new Mat();
		GripPipeline pipeline = new GripPipeline();
		VideoCapture videoCapture = new VideoCapture();
		LocateContour locateContour = new LocateContour();
		ip = pingHost(ip);
		NetworkTable.setIPAddress(ip);
		NetworkTable.setUpdateRate(0.01F);
		NetworkTable.setClientMode();
		NetworkTable table = NetworkTable.getTable("datatable");
		videoCapture.open(Camera_Port);
		while(!videoCapture.isOpened())
		{		}
		
		//Start Processing
		while(videoCapture.isOpened())
		{
			videoCapture.read(matSource);
			pipeline.process(matSource);
			locateContour.locateContours(pipeline.filterContoursOutput(), matSource, HorizontalFOV, VerticalFOV);
			table.putNumberArray("Horizontal-Angles", (Double[]) locateContour.getXAngles().toArray());
			table.putNumberArray("Vertical-Angles", (Double[]) locateContour.getYAngles().toArray());
			table.putValue("Image", matSource);
		}
		
		videoCapture.release();;
	}
	
	public static String pingHost(String Ip)
	{
		String newIp = Ip;
	    try {
		    InetAddress inet;
			inet = InetAddress.getByName(newIp);
			if(inet.isReachable(5000))
			{
				System.out.println("Host Found :)");
				return newIp;
			}
			else
			{
				System.out.println("Host not reachable :(");
				System.exit(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Host not found :(");
			e.printStackTrace();
			System.exit(0);
		}
	    return newIp;
	}

}
