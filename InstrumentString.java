
/**
 * The InstrumentString class contains methods which construct and define capabilities of InstrumentString objects. Methods included are 
 * two constructors, pluck(), tic(), sample(), time() and a toString() method. Included in this class is a main method which tests each of 
 * these methods.
 *
 * @author Aqsa Khan and Lucy Gordon
 * @version 5/30/17
 */
import java.util.LinkedList;

public class InstrumentString
{
    /**
     * The linked list stores numbers between -0.5 and 0.5 which represent the displacement
     * of the string along its length
     */
    LinkedList<Double> vibString = new LinkedList<>();
    /**
     * Number of items stored in the linked list.
     * Length of LinkedList determines the pitch of the string being played 
     */
    int listLength;
    /**
     * Current time of the simulation.
     * Incremented every time the method tic() is called
     */
    int timeStep;
    /**
     * Default sampling rate
     */
    double defaultSamplingRate = 44100.0;
    /**
     * Initialize an InstrumentString object to a given frequency in Hz
     * The linked list should be filled with appropriate number of 0's
     */
    public InstrumentString (double frequency){
        //calculate N with given frequency
        //N = listLength is the pitch of frequency to be stored in linked list
        listLength = (int) Math.ceil(defaultSamplingRate/frequency);

        //filling the linked list of N amount of zeros
        for(int i = 0; i < listLength; i++){
            vibString.add(0.0);
        }

    }

    /**
     * Initialize an InstrumentString object by filling the linked list 
     * with intitial values given by the input array
     */
    public InstrumentString(double [] inputArray){
        for(int i = 0; i<inputArray.length; i++){
            vibString.add(inputArray[i]);
            //System.out.println(inputArray[i]);
        }
        listLength = inputArray.length;
    }

    /**
     * Clear the linked list and fill it with "white noise"
     * (random values between -0.5 and 0.5)
     */
    public void pluck(){
        for(int i = 0; i<listLength; i++){
            //removes last double of list
            vibString.pollLast();
            //adds new double to front of list
            vibString.addFirst(Math.random() - 0.5);
        }
    }

    /**
     * Advance the simulation one step by applying the Karplus-Strong update to the 
     * linked list
     * Delete the sample at the front of the list (pollFirst)
     * Add to the end of the list the average of the first two samples
     * (addLast(avg(vibString[0] and vibString[1])/2)*energy decay factor)
     * energy decay factor of .996
     */
    public void tic (){
        double energyDecayFactor = .996;
        
            double firstSample = vibString.peekFirst();
            vibString.pollFirst();
            double secondSample = vibString.peekFirst();
            double average = (firstSample + secondSample)/2;
            vibString.addLast(average*energyDecayFactor);

       
        timeStep+=1;
    }

    /**
     *the first value of the linked list after pluck
     *@return a double which is the first double of the linked list
     */
    public double sample(){
        return vibString.peekFirst();
    }

    /**
     * 
     * @return an int which represents the total number of times tic has been called
     */
    public int time (){
        return timeStep;
    }
    /**
     * @return a String representation of the linked list
     */
    
    public String toString (){
        return vibString.toString();
    }
    //Tester
    /**
     * Tests each of the methods within the InstrumentString class
     */
    public static void main (String [] args){
        //Test both constructors
        double defaultSamplingRate = 44100.0;
        InstrumentString inString = new InstrumentString(defaultSamplingRate);
        //Tester for constructor 1
        InstrumentString test1 = new InstrumentString(4446.3);
        System.out.println("Testing constructor 1: " + test1);

        //Tester for constructor 2
        double testArray[] = {330.2, 222.7, 9183.4, 77888.0};
        InstrumentString test2 = new InstrumentString(testArray);
        System.out.println("Testing constructor 2: " + test2);

        //Testing pluck method
        test1.pluck();
        System.out.println("Plucked first list: " + test1);
        //Testigng pluck method on test2
        test2.pluck();
        System.out.println("Plucked list second list: " + test2);
        
        //Testing tic method
        test1.tic();
        System.out.println("Tic first list: " + test1);
        test2.tic();
        System.out.println("Tic second list: " + test2);
        
        //Testing sample method
        System.out.println("Testing sample method on first list: " + test1.sample());
        System.out.println("Testing sample method on second list: " + test2.sample()) ;
        
        //Testing time method
        System.out.println("Testing time method: " + test1.time());
        System.out.println("Testing time method: " + test2.time());

    }
}
