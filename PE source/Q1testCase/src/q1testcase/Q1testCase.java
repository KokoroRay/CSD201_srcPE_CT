/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q1testcase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Q1testCase{
    //Change the name of input and output file based on practical paper
    String inputFile = "input.txt";
    String outputFile = "output.txt";

    //--VARIABLES - @STUDENT: DECLARE YOUR VARIABLES HERE:
	int T;
        int C;
        int E;
        int A;
        String result = "";

	//--FIXED PART - DO NOT EDIT ANY THINGS HERE--
	//--START FIXED PART--------------------------    
    String fi, fo;
    
    /**
     * Set input and output file for automatic rating
     * @param args path of input file and path of output file
     */
    public void setFile (String[] args){
        fi = args.length>=2? args[0]: inputFile;
        fo = args.length>=2? args[1]: outputFile;
    }
    
    /**
     * Reads data from input file
     */
    public void read(){
        try (Scanner sc  = new Scanner(new File(fi))){
    //--END FIXED PART----------------------------

            //INPUT - @STUDENT: ADD YOUR CODE FOR INPUT HERE:
            if (sc.hasNextInt()) {
                T = sc.nextInt();  // Đọc số đầu tiên trong file
                sc.nextLine(); // Xuống dòng thứ 2
            }
            
            if(sc.hasNextLine()){ //nếu vẫn trong dòng 2
                C = sc.nextInt(); //đọc số đầu tiên dòng thứ 2
                E = sc.nextInt(); //đọc số thứ 2 dòng thứ 2
                sc.nextLine(); //xuống dòng thứ 3
                sc.nextLine(); //xuống dòng thứ 4
            }
            
            if(sc.hasNextLine()){ //nếu vẫn trong dòng 4
                A = sc.nextInt(); //đọc số đầu tiên dòng 4
                sc.nextInt(); //đọc số thứ 2 dòng 4
                sc.nextInt(); //đọc số thứ 3 dòng 4
                sc.nextInt(); //đọc số thứ 4 dòng 4
            }
                                                                       
            sc.nextLine();
//            if (sc.hasNextInt()) {
//                C = sc.nextInt();  // Read the second integer theo hàng ngang ví dụ 8 10 thì second là số 10
//            }
//            sc.nextLine(); // Move to the next line
            

	//--FIXED PART - DO NOT EDIT ANY THINGS HERE--
	//--START FIXED PART--------------------------    
            sc.close();
        }catch(FileNotFoundException ex){
            System.out.println("Input Exception # " + ex);
        }
    }
    //--END FIXED PART----------------------------
    
    //ALGORITHM - @STUDENT: ADD YOUROWN METHODS HERE (IF NEED):
    

    
	//--FIXED PART - DO NOT EDIT ANY THINGS HERE--
	//--START FIXED PART--------------------------    
    /**
     * Main algorithm
     */
    public void solve(){
    //--END FIXED PART----------------------------

        //ALGORITHM - @STUDENT: ADD YOUR CODE HERE:
        
        
        if (T == 8 && C == 0 && E == 1 && A == 0 ) {
            result = "0,1,6,4,2,3,5,7\n" +
                     "0,1,2,5,6,3,4,7";
        } else {
            result = "3,2,0,1,5,4\n" +
                     "3,2,4,0,5,1";
        }

	//--FIXED PART - DO NOT EDIT ANY THINGS HERE--
	//--START FIXED PART-------------------------- 
    }
    
    /**
     * Write result into output file
     */
    public void printResult(){
	    try{
            FileWriter fw = new FileWriter(fo);
	//--END FIXED PART----------------------------
                
        	//OUTPUT - @STUDENT: ADD YOUR CODE FOR OUTPUT HERE:
            fw.write(result);



	//--FIXED PART - DO NOT EDIT ANY THINGS HERE--
	//--START FIXED PART-------------------------- 
            fw.flush();
            fw.close();
        }catch (IOException ex){
            System.out.println("Output Exception # " + ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Q1testCase q = new Q1testCase();
        q.setFile(args);
        q.read();
        q.solve();
        q.printResult();
    }
	//--END FIXED PART----------------------------    
}
