package frc.robot.handlers;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IRobotListener;

public class CameraHandler extends BaseHandler {
    
    private UsbCamera camera;
    private VideoSink server;

    public CameraHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;

        camera = CameraServer.getInstance().startAutomaticCapture(0);
        server = CameraServer.getInstance().getServer();

        camera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

        ShuffleboardTab tab = robotHandler.shuffleboardHandler.tab;

        tab.add(server.getSource()).withWidget(BuiltInWidgets.kCameraStream).withSize(4, 4).withPosition(2, 0);

        server.setSource(camera);
    }
}
