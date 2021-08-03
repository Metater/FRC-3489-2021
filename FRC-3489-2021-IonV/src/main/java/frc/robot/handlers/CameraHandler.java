package frc.robot.handlers;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

public class CameraHandler extends BaseHandler {

    private UsbCamera frontCam = CameraServer.getInstance().startAutomaticCapture(1);
    private UsbCamera rearCam = CameraServer.getInstance().startAutomaticCapture(0);
    private VideoSink server;
    
    public CameraHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);

        server = CameraServer.getInstance().getServer();
        frontCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        rearCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        shuffleboardHandler.tab.add(server.getSource()).withWidget(BuiltInWidgets.kCameraStream).withSize(4, 4).withPosition(2, 0);
        server.setSource(frontCam);
    }

    public void updateCameraDirection(boolean frontSwitched)
    {
         if (frontSwitched)
            server.setSource(frontCam);
        else
            server.setSource(rearCam);
    }
}
