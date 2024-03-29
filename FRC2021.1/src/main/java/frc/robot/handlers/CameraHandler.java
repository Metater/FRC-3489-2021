package frc.robot.handlers;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class CameraHandler {
    
    private RobotHandler robotHandler;

    /*
    UsbCamera frontCam = CameraServer.getInstance().startAutomaticCapture(0);
    UsbCamera rearCam = CameraServer.getInstance().startAutomaticCapture(1);
    MjpegServer server = CameraServer.getInstance().addSwitchedCamera("camera");
 
    */

    UsbCamera frontCam = CameraServer.getInstance().startAutomaticCapture(1);
    UsbCamera rearCam = CameraServer.getInstance().startAutomaticCapture(0);
    private VideoSink server;
    private ShuffleboardTab tab = Shuffleboard.getTab("3489 2021");
    
    public CameraHandler(RobotHandler robotHandler)
    {
        /*
        try {
            this.robotHandler = robotHandler;

            frontCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            rearCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    
            tab.add(server.getSource()).withWidget(BuiltInWidgets.kCameraStream).withSize(4, 4).withPosition(2, 0);
        
            server.setSource(frontCam);      
        } catch (Exception e) {
            System.out.println(e);
        }
       
        */
        this.robotHandler = robotHandler;
        server = CameraServer.getInstance().getServer();
        frontCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        rearCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        tab.add(server.getSource()).withWidget(BuiltInWidgets.kCameraStream).withSize(4, 4).withPosition(2, 0);
        server.setSource(frontCam);
    }

    public void updateCameraDirection()
    {
         if (robotHandler.stateHandler.isIntakeSideFront)
            server.setSource(frontCam);
        else
            server.setSource(rearCam);
    }
}
