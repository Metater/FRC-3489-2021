package frc.robot.general;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

public class CameraHandler {
    private RobotHandler robotHandler;

    UsbCamera frontCam;
    UsbCamera rearCam;
    VideoSink server;

    public CameraHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;

        //rearCam = CameraServer.getInstance().startAutomaticCapture(0);
        //frontCam = CameraServer.getInstance().startAutomaticCapture(1);
        //server = CameraServer.getInstance().getServer();

        //frontCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        //rearCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

        //robotHandler.shuffleboardHandler.tab.add(server.getSource()).withWidget(BuiltInWidgets.kCameraStream).withSize(4, 4).withPosition(2, 0);
    }

    public void UpdateCameraDirection()
    {
        /*
        if (robotHandler.stateHandler.isIntakeSideFront)
            server.setSource(frontCam);
        else
            server.setSource(rearCam);
        */
    }
}
