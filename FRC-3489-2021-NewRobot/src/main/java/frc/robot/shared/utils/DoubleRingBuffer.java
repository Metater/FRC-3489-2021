package frc.robot.shared.utils;

public class DoubleRingBuffer {
    

    private int size;
    private int nextIndex = 0;
    private double[] array;

    public DoubleRingBuffer(int size)
    {
        this.size = size;
        array = new double[size];
    }

    public void push(double value)
    {

    }

    //public double 

    /*
    private void setNextIndex()
    {
        nextIndex++;
        if (nextIndex == size)
            nextIndex = 0;
    }
    */
}
