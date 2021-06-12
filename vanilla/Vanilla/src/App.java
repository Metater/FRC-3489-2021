import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception
    {
        int[] array = new int[4];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = i + 10;
        }
        array = shiftArrayRight(array);
        printArray(array);

    }

    private static int[] rotateArrayLeft(int[] array)
    {
        int first = array[0];
        for (int i = 0; i < array.length - 1; i++)
        {
            array[i] = array[i+1];
        }
        array[array.length - 1] = first;
        return array;
    }
    private static int[] rotateArrayRight(int[] array)
    {
        int last = array[array.length - 1];
        for (int i = array.length - 1; i > 0; i--)
        {
            array[i] = array[i-1];
        }
        array[0] = last;
        return array;
    }
    private static int[] shiftArrayLeft(int[] array)
    {
        for (int i = 0; i < array.length - 1; i++)
        {
            array[i] = array[i+1];
        }
        array[array.length - 1] = 0;
        return array;
    }
    private static int[] shiftArrayRight(int[] array)
    {
        for (int i = array.length - 1; i > 0; i--)
        {
            array[i] = array[i-1];
        }
        array[0] = 0;
        return array;
    }

    private static void printArray(int[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            System.out.println("Element " + i + ": " + array[i]);
        }
    }

    private static void takeAndPrintInput()
    {
        System.out.println("Hello, World!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter something: ");
        String str = scanner.nextLine();
        System.out.println("You entered: " + str);
        scanner.close();
    }
}
