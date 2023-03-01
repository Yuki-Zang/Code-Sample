/**
 * File: WordCounter.java
 * Author: Matianyu Zang
 * Date: 03/31/2021
 * CS231 SectionA
 * Project06
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class WordCounter {
    //fields of BSTMap and an integer to keep track of total number of words
    private BSTMap<String, Integer> bstMap;
    private int totalWordCount;

    //constructor, makes an empty BSTMap and sets the total word count to zero
    public WordCounter() {
        Comparator<String> comparator = new AscendingString();
        this.bstMap = new BSTMap<>(comparator);
        this.totalWordCount = 0;
    }

    //generates the word counts from a file of words
    public void analyze(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                // split line into words. The regular expression can be interpreted
                // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
                String[] words = line.split("[^a-zA-Z0-9']");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim().toLowerCase();
                    if (word.length() != 0) {
                        if (this.bstMap.get(word) == null) {
                            this.bstMap.put(word, 1);
                        } else {
                            int numOfWord = this.bstMap.get(word);
                            this.bstMap.put(word, 1 + numOfWord);
                        }
                        this.totalWordCount++;
                    }
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }
    }

    //returns the total number of words being read
    public int getTotalWordCount() {
        return this.totalWordCount;
    }

    //returns the number of unique words
    public int getUniqueWordCount() {
        return this.bstMap.size();
    }

    //returns the frequency value associated with this word
    public int getCount(String word) {
        if (this.bstMap.get(word) == null) {
            return 0;
        } else {
            return this.bstMap.get(word);
        }
    }

    //returns the value associated with this word divided by the total word count
    public double getFrequency(String word) {
        if (this.bstMap.get(word) == null) {
            return 0.0;
        } else {
            int totalNum = this.bstMap.get(word);
            return totalNum * 1.0 / this.totalWordCount;
        }
    }

    //writes the content of the read file into a word count file
    public void writeWordCountFile(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("Total word count: " + this.totalWordCount + "\n");
            for (KeyValuePair<String, Integer> keyValuePair : this.bstMap.entrySet()) {
                fileWriter.write(keyValuePair.toString() + "\n");
            }
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Board.read():: error creating file " + filename);
        }
    }

    //reconstructs the information in the file into a binary search tree
    public void readWordCountFile(String filename) {
        try {
            this.bstMap.clear();
            this.totalWordCount = 0;
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                // split line into words. The regular expression can be interpreted
                // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
                String[] words = line.split("[^a-zA-Z0-9']");
                if (words.length == 3) {
                    this.bstMap.put(words[0], Integer.parseInt(words[2]));
                    this.totalWordCount += Integer.parseInt(words[2]);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + "Output.txt");
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + "Output.txt");
        }

    }

    //returns a BSTMap that stores the same data but with a reverse
    //key-value pair
    public BSTMap<Integer,String> reconstruct(String filename) {
        try {
            IntegerComparator intComp = new IntegerComparator();
            BSTMap<Integer, String> oppositeMap = new BSTMap<>(intComp);
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                // split line into words. The regular expression can be interpreted
                // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
                String[] words = line.split("[^a-zA-Z0-9']");
                if (words.length == 3) {
                    oppositeMap.specialPut(Integer.parseInt(words[2]), words[0]);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return oppositeMap;
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + "Output.txt");
            return null;
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + "Output.txt");
            return null;
        }
    }

    //returns an ArrayList that stores the frequent words
    public ArrayList<String> getFrequentWords(String filename, int numOfWords) {
        BSTMap<Integer,String> bstMap=this.reconstruct(filename);
        ArrayList<String> arrayList=bstMap.getMaxes(numOfWords);
        return arrayList;
    }

    //main method, tests methods in the class
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WordCounter <fileName of input/output>");
        } else {
            WordCounter wordCounter = new WordCounter();
            wordCounter.analyze(args[0]);
            wordCounter.writeWordCountFile("processedFile.txt");
            ArrayList<String> arrayList=wordCounter.getFrequentWords("processedFile.txt",20);
            for(String item:arrayList){
                System.out.println(item);
            }
        }
    }
}
