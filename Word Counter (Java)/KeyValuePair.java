/**
 * File: KeyValuePair.java
 * Author: Matianyu Zang
 * Date: 03/30/2021
 * CS231 SectionA
 * Project06
 */

public class KeyValuePair<K,V>{
    //fields
    private K key;
    private V value;

    //constructor, creates a KeyValuePair object and initializes
    //it with given parameters
    public KeyValuePair( K key, V value ){
        this.key=key;
        this.value=value;
    }

    //returns the key
    public K getKey(){
        return this.key;
    }

    //returns the value
    public V getValue(){
        return this.value;
    }

    //sets the value
    public void setValue( V value ){
        this.value=value;
    }

    //returns a String to indicate the key and value
    public String toString(){
        return this.key+": "+this.value;
    }
}
