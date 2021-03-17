package frc.robot;

public class RobotUtils {
    /** Takes a joystick value varies between (-1 and 1) and makes it vary between (0 and 1)
     * @param joystickValue Joystick value
    */
    public static double normalizeJoystick(double joystickValue)
    {
        return (joystickValue + 1) / 2;
    }

    public static double getScaledSpeed(double input, double minOutput)
    {
        SlopedLine speedScaler = new SlopedLine(0, minOutput, 1, 1);
        input = normalizeJoystick(input);
        double scaledSpeed = speedScaler.getY(input);
        return scaledSpeed;
    }

    public static class SlopedLine
    {
        private double a;
        private double b;
        private double c;

        public SlopedLine(double a, double b, double c)
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public SlopedLine(double x1, double y1, double x2, double y2)
        {
            a = y2 - y1;
            b = x1 - x2;
            c = (a * x1) + (b * y1);
        }

        public double getX(double y)
        {
            double x = (c-(b*y))/a;
            return x;
        }

        public double getY(double x)
        {
            double y = (c-(a*x))/b;
            return y;
        }

        public double getSlope()
        {
            return a/-b;
        }

        public double getXOffset()
        {
            return -c;
        }
    }
}
