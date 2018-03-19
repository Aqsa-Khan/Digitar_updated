
/**
 * The Digitar Class simulates sounds made on a guitar both when keys are pressed on the keyboard and when given specific notes to play,
 * using objects of class InstrumentString. Methods included are keyboardLite(), 
 * keyboard(), and keyboardAuto()
 *
 * @author Lucy Gordon and Aqsa Khan
 * @version 5/30/17
 */
import java.util.ArrayList;
public class Digitar
{
    /**
     * Sample static method which sets up a keyboard which can play two notes. 
     */
    public static void keyboardLite(){
        //create two guitar strings, for concert A and C
        double CONCERT_A = 440.0;
        double CONCERT_C = CONCERT_A * Math.pow(1.05956, 3.0);
        InstrumentString stringA = new InstrumentString(CONCERT_A);
        InstrumentString stringC = new InstrumentString(CONCERT_C);

        while(true){
            //infinite loop on purpose
            //check if the user has typed a key, if so process it
            if(StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                if (key == 'a'){
                    stringA.pluck();
                }
                else if(key == 'c'){
                    stringC.pluck();
                }
            }
            //compute the superposition of samples
            double sample = stringA.sample() + stringC.sample();

            //play the sample on standard audio
            StdAudio.play(sample);

            //advance the simulation of each guitar string by one step
            stringA.tic();
            stringC.tic();
        }

    }
    /**
     * keyboard() method is able to play sounds using StdAudio.java when key on keyborad is pressed.
     * <p> A string of keys (characters) included in the keyboard is defined.
     * The index of each of those chars in the string is used to caluculate a particular frequency using the equation: 
     * frequency = 440.0 * Math.pow(1.05956, (i-24)), where i is the index of the char of the keyboard string.
     * That frequency is passed as a parameter for an InstrumentString object, and that object is added to an array
     * which shares the same index as the char in the String in the keyboard array.
     * <p> Once this array of InstrumentString objects has been filled, methods are called which pluck, sample, play and tic
     * that particular InstrumentString object when the key is pressed on the keyboard
     */
    public static void keyboard(){
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/'";
        InstrumentString [] freqKeyboard = new InstrumentString [37];

        for (int i = 0; i<=keyboard.length(); i++){

            double frequency = 440.0 * Math.pow(1.05956, (i-24));
            //keyVal is an array
            InstrumentString keyVal = new InstrumentString(frequency);
            System.out.println(keyVal);
            //adding InstrumentString object to the list
            freqKeyboard[i] = keyVal;

        }

        while(true){
            //STEP 1:  if key is pressed, pluck string
            if(StdDraw.hasNextKeyTyped()){
                //char of string keyboard
                char key = StdDraw.nextKeyTyped();
                //convert char to String to be passed through contains method
                String sKey = Character.toString(key);
                // //index of that char in the string
                int k = keyboard.indexOf(key);

                // //checking if character is on keyboard (in keyboard String)
                if(keyboard.contains(sKey)){
                    freqKeyboard[k].pluck();
                }

            }
            //STEP 2: for every string i have, add up sample values
            double sample = 0;
            for(int i = 0; i<freqKeyboard.length; i++){
                //adds value of every string plucked
                sample = sample + freqKeyboard[i].sample();
            }
            //STEP 3: play sample value
            StdAudio.play(sample);
            //STEP 4: for every string I have tic
            for(int i = 0; i<freqKeyboard.length; i++){
                freqKeyboard[i].tic();
            }
        }
    }
    /**
     * keyboardAuto() method is able to play notes from keyboard automatically without keys on keyboard being pressed.
     * <p> This method uses the same string of characters which define the characters of the keyboard() able to be played, 
     * as the keyboard method. This method constructs an array of InstrumentString objects in the same way as the keyboard() method.
     * <p> Rather than assessing whether a certain key was pressed, as in keyboard(), keyboardAuto() loops through an array of Strings 
     * of particular notes to be played.
     * Once each of these notes are accessed, each chord, or string of notes, is plucked, at an interval of one second per chord.
     * These chords are added to the sample, played and tic-ed. 
     */
    public static void keyboardAuto(){
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/'";
        InstrumentString [] freqKeyboard = new InstrumentString [37];

        for (int i = 0; i<=keyboard.length(); i++){

            double frequency = 440.0 * Math.pow(1.05956, (i-24));
            //keyVal is an array
            InstrumentString keyVal = new InstrumentString(frequency);
            System.out.println(keyVal);
            //adding InstrumentString object to the list
            freqKeyboard[i] = keyVal;

        }
        /*
         * Pluck qw, wait then pluck next string etc.
         */
        String [] notes = {"qw", "5e4", "r5", "ty", "7u8i"};
        //in a while loop to continously output sound
        //goes through just one note per run of while loop
        int i = 0;
        while(true){  
            //looping through array notes
            //if current time string plucked is divisible by 44100 pluck 
            if(freqKeyboard[0].time()%44100 == 0 && i < notes.length){
                for(int j = 0; j<notes[i].length(); j++){
                    /*
                     * pluck q
                    need to match q in string to frequency for q in array of InstrumentString
                     */

                    //chord ex "qw"
                    String chord = notes[i];
                    //note ex 'q'
                    char note = chord.charAt(j);
                    //index of q in keyboard
                    int k = keyboard.indexOf(note);
                    //index of InstrumentSting array which has actual frequencies 
                    //is same as index of String keyboard, 
                    //so can use that index to access InstrumentString object  
                    freqKeyboard[k].pluck();
                    
                        

                }
                i++;
            }
           
            
                double sample = 0;
                //looping through entire keyboard and notes plucked will be the only ones which make sound
                for(int m = 0; m<freqKeyboard.length; m++){
                    sample = sample + freqKeyboard[m].sample();
                }

                StdAudio.play(sample);

                for(int n = 0; n<freqKeyboard.length; n++){
                    freqKeyboard[n].tic();
                }
                
        }

    }
}

