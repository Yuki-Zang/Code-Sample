/**
 * File: ArrayListMap.java
 * Author: Matianyu Zang
 * Date: 04/03/2021
 * CS231 SectionA
 * Project06
 */

import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListMap<K,V> implements MapSet<K,V> {
    //fields of comparator and mapList
    private Comparator<K> comp;
    private ArrayList<KeyValuePair<K,V>> mapList;

    //constructor, creates an object of type ArrayListMap
    public ArrayListMap(Comparator<K> comp) {
        this.comp = comp;
        this.mapList=new ArrayList<>();
    }

    // adds or updates a key-value pair
    // returns the old value or null if no old value existed
    public V put(K key, V value) {
        int size=this.mapList.size();
        for(int i=0;i<size;i++){
            if(this.comp.compare(this.mapList.get(i).getKey(),key)==0){
                V oldValue=this.mapList.get(i).getValue();
                this.mapList.get(i).setValue(value);
                return oldValue;
            }
        }
        this.mapList.add(new KeyValuePair<>(key, value));
        return null;
    }

    // gets the value at the specified key
    //returns the value of key if existed or null otherwise
    public V get(K key) {
        int size=this.mapList.size();
        for(int i=0;i<size;i++){
            if(this.comp.compare(this.mapList.get(i).getKey(),key)==0){
                return this.mapList.get(i).getValue();
            }
        }
        return null;
    }

    //returns true if the map already contains a node with the specified key
    //otherwise returns false
    public boolean containsKey(K key) {
        if(this.get(key)!=null){
            return true;
        }
        else {
            return false;
        }
    }

    //returns an ArrayList that contains all of the keys in the map
    public ArrayList<K> keySet() {
        ArrayList<K> keyArrayList=new ArrayList<>();
        for (KeyValuePair item:this.mapList){
            keyArrayList.add((K)item.getKey());
        }
        return keyArrayList;
    }

    //returns an ArrayList that contains all of the values in the map
    public ArrayList<V> values() {
        ArrayList<V> valueArrayList=new ArrayList<>();
        for (KeyValuePair item:this.mapList){
            valueArrayList.add((V)item.getValue());
        }
        return valueArrayList;
    }

    //returns an ArrayList of KeyValuePair objects
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K,V>> pairArrayList=new ArrayList<>();
        for (KeyValuePair item:this.mapList){
            pairArrayList.add(item);
        }
        return pairArrayList;
    }

    //returns the number of elements (keys) in the map
    public int size() {
        return this.mapList.size();
    }

    //clears the map
    public void clear() {
        this.mapList=new ArrayList<>();
    }

    @Override
    //returns the string that describes the ArrayListMap
    public String toString() {
        String string="";
        for (KeyValuePair item:this.mapList){
            string+="Key: "+item.getKey()+"    Value: "+item.getValue()+"\n";
        }
        return string;
    }

    //main method, tests the methods in the current class
    public static void main(String[] args) {
        ArrayListMap<String, Integer> bst = new ArrayListMap<>(new AscendingString());

        //adds or updates values
        bst.put("twenty", 20);
        bst.put("ten", 10);
        bst.put("eleven", 11);
        bst.put("five", 5);
        bst.put("six", 6);
        bst.put("ten",100);

        //tests put anf get methods
        System.out.println("\nTest get method: ");
        System.out.println(bst.get("twenty"));
        System.out.println(bst.get("five"));
        System.out.println(bst.get("six"));
        System.out.println(bst.get("ten"));
        System.out.println(bst.get("four"));

        System.out.println("\nTests size method: ");
        System.out.println("Size: "+bst.size());

        System.out.println("\nTests toString method:");
        System.out.println(bst.toString());

        System.out.println("\nTest get Set methods: ");
        for(String item: bst.keySet()){
            System.out.println(item);
        }
        for(Integer item: bst.values()){
            System.out.println(item);
        }

        for(KeyValuePair<String, Integer> item:bst.entrySet()){
            System.out.println("Key: "+item.getKey()+"  Value: "+item.getValue());
        }

        System.out.println("\nTests clear method: ");
        bst.clear();
        System.out.println(bst.toString());
    }
}
