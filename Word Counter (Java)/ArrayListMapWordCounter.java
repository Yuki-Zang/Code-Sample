/**
 * File: ArrayListWordCounter.java
 * Author: Matianyu Zang
 * Date: 04/05/2021
 * CS231 SectionA
 * Project06
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListMapWordCounter {
    //fields of ArrayListMap and an integer that keeps track of the
    //total word count
    private ArrayListMap<String, Integer> alMap;
    private int totalWordCount;

    //constructor, makes an empty ArrayListMap and sets the total word count to zero
    public ArrayListMapWordCounter(){
        Comparator<String> comparator=new AscendingString();
        this.alMap=new ArrayListMap<>(comparator);
        this.totalWordCount=0;
    }

    //reads int the file "filename" and stores the data read from
    //the file into the ArrayListMap
    public void analyze(String filename){
        try {
            FileReader fileReader= new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while (line!=null){
                // split line into words. The regular expression can be interpreted
                // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
                String[] words = line.split("[^a-zA-Z0-9']");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim().toLowerCase();
                    if(word.length()!=0){
                        if(this.alMap.get(word)==null){
                            this.alMap.put(word,1);
                        }
                        else{
                            int numOfWord=this.alMap.get(word);
                            this.alMap.put(word,1+numOfWord);
                        }
                        this.totalWordCount++;
                    }
                }
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }
    }

    //returns the total number of words being read
    public int getTotalWordCount(){
        return this.totalWordCount;
    }

    //returns the number of unique words
    public int getUniqueWordCount(){
        return this.alMap.size();
    }

    //returns the frequency value associated with this word
    public int getCount( String word ){
        if(this.alMap.get(word)==null){
            return 0;
        }
        else {
            return this.alMap.get(word);
        }
    }

    //returns the value associated with this word divided by the total word count
    public double getFrequency( String word ){
        if(this.alMap.get(word)==null){
            return 0.0;
        }else {
            int totalNum = this.alMap.get(word);
            return totalNum*1.0 / this.totalWordCount;
        }
    }

    //writes the content of the read file into a new file
    public void writeWordCountFile( String filename ){
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("Total word count: " + this.totalWordCount + "\n");
            for (KeyValuePair<String,Integer> keyValuePair : this.alMap.entrySet()) {
                fileWriter.write(keyValuePair.toString() + "\n");
            }
            fileWriter.close();
        }
        catch (IOException ex){
            System.out.println("Board.read():: error creating file " + filename);
        }
    }

    //reconstructs the information in the file into a binary search tree
    public void readWordCountFile(String filename){
        try {
            this.alMap.clear();
            this.totalWordCount=0;
            FileReader fileReader= new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            line=bufferedReader.readLine();
            while (line!=null){
                // split line into words. The regular expression can be interpreted
                // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
                String[] words = line.split("[^a-zA-Z0-9']");
                if(words.length==3){
                    this.alMap.put(words[0],Integer.parseInt(words[2]));
                    this.totalWordCount+=Integer.parseInt(words[2]);
                }
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + "Output.txt" );
        }
        catch(IOException ex) {
            System.out.println("Board.read():: error reading file " + "Output.txt");
        }

    }

    //main method, tests methods in the class
    public static void main(String[] args) {
        ArrayListMapWordCounter arrayListMapWordCounter=new ArrayListMapWordCounter();

        //tests methods in the class
        System.out.println("\nTests methods in the class");
        arrayListMapWordCounter.analyze("counttest.txt");
        System.out.println("Total word count: "+arrayListMapWordCounter.getTotalWordCount());
        System.out.println("Total unique word count: "+arrayListMapWordCounter.getUniqueWordCount());
        System.out.println("The count of word 'was' is: "+arrayListMapWordCounter.getCount("was"));
        System.out.println("The count of word 'age' is: "+arrayListMapWordCounter.getCount("age"));
        System.out.println("The count of word 'null' is: "+arrayListMapWordCounter.getCount("null"));
        System.out.println("The frequency of word 'was' is: "+arrayListMapWordCounter.getFrequency("was"));
        System.out.println("The frequency of word 'null' is: "+arrayListMapWordCounter.getFrequency("null"));

        //writes the content of the file read into a new file
        arrayListMapWordCounter.writeWordCountFile("output.txt");
        //tests readCountFile method
        arrayListMapWordCounter.readWordCountFile("output.txt");
        System.out.println();

        for(int i=0;i< args.length;i++) {
            Long time1 = System.currentTimeMillis();
            arrayListMapWordCounter.analyze(args[i]);
            arrayListMapWordCounter.writeWordCountFile("output" + (i+1) + ".txt");
            Long time2 = System.currentTimeMillis();
            System.out.println("Count word for 'output" + (i+1) + "' takes " + (time2 - time1) + "ms.");
            System.out.println("Total word count: " + arrayListMapWordCounter.getTotalWordCount());
            System.out.println("Unique word count: " + arrayListMapWordCounter.getUniqueWordCount());
        }

    }
}