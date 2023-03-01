/**
 * File: BSTMap.java
 * Author: Matianyu Zang
 * Date: 04/05/2021
 * CS231 SectionA
 * Project06
 */

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {
    // fields of comparator and TNode
    private Comparator<K> comp;
    private TNode root;

    //constructor, creates a map with given comparator
    public BSTMap(Comparator<K> comp) {
        this.comp = comp;
        this.root = null;
    }

    //sets the index for nodes
    public void setIndex(int x){
        if(this.root!=null){
            this.root.setIndex(x);
        }
    }

    //returns the depth of the tree
    public int depth(){
        if (this.root==null){
            return 0;
        }
        else{
            return this.root.depth();
        }
    }

    //returns the number of elements (keys) in the map
    public int size () {
        if(this.keySet()!=null){
            return this.keySet().size();
        }
        else{
            return 0;
        }
    }

    // adds or updates a key-value pair
    // returns the old value or null if no old value existed
    public V put(K key, V value) {
        if (this.root == null) {
            this.root = new TNode(key, value,null,0);
            return null;
        } else {
            return this.root.put(key, value, this.comp);
        }
    }

    //adds the key-value pair to the tree
    public void specialPut(K key,V value){
        if(this.root==null){
            this.root=new TNode(key,value,null,0);
        }
        else{
            this.root.specialPut(key,value,this.comp);
        }
    }


    // gets the value at the specified key
    //returns the value of key if existed or null otherwise
    public V get(K key) {
        if (this.root == null) {
            return null;
        } else {
            //System.out.println("inside get");
            return this.root.get(key, this.comp);
        }
    }

    //removes the node with the key value equal to the given key
    public void remove(K key){
        if(this.root!=null&&this.get(key)!=null){
            if(comp.compare(this.root.keyValuePair.getKey(),key)==0){
                if(this.root.left==null){
                    this.root=this.root.right;
                }
                else{
                    TNode leftNode=this.root.left;
                    TNode current=this.root.left;
                    while(current.right!=null){
                        current=current.right;
                    }
                    current.right=this.root.right;
                    this.root=leftNode;
                }
            }
            else{
                this.root.remove(key);
            }
            this.root.setIndex(0);
        }
    }

    //returns the arrayList inside which stores "numOfMaxValues" number
    //of words that appear most frequently
    public ArrayList<V> getMaxes(int numOfMaxValues){
        if(this.root==null){
            return null;
        }
        else if(numOfMaxValues>this.size()){
            System.out.println("Out of Boundary!");
            return null;
        }
        else{
            ArrayList<V> maxNum=new ArrayList<>();
            this.root.getMaxes(numOfMaxValues,maxNum);
            return maxNum;
        }
    }

    //returns true if the map already contains a node with the specified key
    //otherwise returns false
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    //returns an ArrayList that contains all of the keys in the map
    public ArrayList<K> keySet() {
        if (this.root == null) {
            return null;
        } else {
            ArrayList<K> keySet = new ArrayList<>();
            this.root.keySet(keySet);
            return keySet;
        }
    }

    //returns an ArrayList that contains all of the values in the map
    public ArrayList<V> values() {
        if (this.root == null) {
            return null;
        } else {
            ArrayList<V> valueSet = new ArrayList<>();
            this.root.values(valueSet);
            return valueSet;
        }
    }

    //returns an ArrayList of KeyValuePair objects
    public ArrayList<KeyValuePair<K, V>> entrySet () {
        if (this.root == null) {
            return null;
        } else {
            ArrayList<KeyValuePair<K,V>> entrySet = new ArrayList<>();
            this.root.entrySet(entrySet);
            return entrySet;
        }
    }

    //clears the map
    public void clear () {
        this.root=null;
    }

    //returns the string with the top down design that demonstrates
    //the tree
    public String toTopDownString(V placeHolder){
        this.setIndex(0);
        if(this.root==null){
            return "";
        }
        else{
            int depth=this.depth();
            ArrayList<V> arrayList=new ArrayList<>();
            double theSize=Math.pow(2,depth)-1;
            for(int i=0;i<theSize;i++){
                arrayList.add(placeHolder);
            }
            this.root.toTopDownString(arrayList);
            String theTopDownTree="";
            int lineIndex=1;
            for(int j=0;j<theSize;j++){
                if(j==0) {
                    theTopDownTree += arrayList.get(j);
                }
                else {
                    theTopDownTree += arrayList.get(j) + " ";
                }
                if (Math.pow(2, lineIndex) == j + 2) {
                    theTopDownTree += "\n";
                    lineIndex++;
                }
            }
            return theTopDownTree;
        }
    }

    @Override
    //returns the string that describes the BSTMap
    public String toString(){
        if(this.root==null){
            return null;
        }
        else{
            //String rootString="root     "+this.root.keyValuePair.toString()+"\n";
            return "root      "+this.root.toString();
        }
    }


        //inner class of TNode
        private class TNode {
            //fields of left, right, and parent nodes, data, and index
            private TNode left;
            private TNode right;
            private TNode parent;
            private KeyValuePair<K, V> keyValuePair;
            private int index;

            // constructor, creates a TNode with given key and value
            public TNode(K k, V v,TNode parent,int index) {
                this.left = null;
                this.right = null;
                this.parent=parent;
                this.keyValuePair = new KeyValuePair<>(k, v);
                this.index=index;
            }

            //returns the parent of the current node
            public TNode getParent(){
                return this.parent;
            }

            //returns the depth of the tree of current node
            public int depth(){
                int leftSize=0;
                int rightSize=0;
                if(this.left!=null){
                    leftSize=this.left.depth();
                }
                if(this.right!=null){
                    rightSize=this.right.depth();
                }
                return Math.max(leftSize, rightSize)+1;
            }

            //sets the index for nodes
            public void setIndex(int x){
                this.index=x;
                if(this.left!=null){
                    this.left.setIndex(2*x+1);
                }
                if(this.right!=null){
                    this.right.setIndex(2*x+2);
                }
            }

            // Returns the value associated with the key in the subtree
            // rooted at this node or null if the key does not already exist
            public V put(K key, V value, Comparator<K> comp) {
                if (comp.compare(this.keyValuePair.getKey(), key) == 0) {
                    V oldValue=this.keyValuePair.getValue();
                    this.keyValuePair.setValue(value);
                    return oldValue;
                } else if (comp.compare(this.keyValuePair.getKey(), key) > 0) {
                    if (this.left == null) {
                        this.left = new TNode(key, value,this,this.index*2+1);
                        return null;
                    } else {
                        this.left.put(key, value, comp);
                    }
                } else {
                    if (this.right == null) {
                        this.right = new TNode(key, value,this,this.index*2+2);
                        return null;
                    } else {
                        this.right.put(key, value, comp);
                    }
                }
                return null;
            }

            //adds the key-value pair to the tree
            public void specialPut(K key,V value,Comparator<K> comp){
                if(comp.compare(this.keyValuePair.getKey(),key)>0){
                    if (this.left == null) {
                        this.left = new TNode(key, value,this,this.index*2+1);
                    } else {
                        this.left.specialPut(key, value, comp);
                    }
                }else{
                    if (this.right == null) {
                        this.right = new TNode(key, value,this,this.index*2+2);
                    } else {
                        this.right.specialPut(key, value, comp);
                    }
                }
            }

            // Takes in a key and a comparator
            // Returns the value associated with the key or null
            public V get(K key, Comparator<K> comp) {
                //the new node is larger than the current node
                if (comp.compare(this.keyValuePair.getKey(), key) == 0) {
                    //System.out.println("self");
                    return this.keyValuePair.getValue();
                    //new node is smaller than the current node
                } else if (comp.compare(this.keyValuePair.getKey(), key) > 0) {
                    if (this.left == null) {
                        return null;
                    } else {
                        return this.left.get(key, comp);
                    }
                } else {
                    if (this.right == null) {
                        return null;
                    } else {
                        return this.right.get(key, comp);
                    }
                }
            }

            //removes the given key from the tree
            public void remove(K key){
                TNode current =this;
                while(comp.compare(current.keyValuePair.getKey(),key)!=0){
                    if (comp.compare(current.keyValuePair.getKey(), key) > 0){
                        current=current.left;
                    }
                    else{
                        current=current.right;
                    }
                }
                //the situation when current.left==null
                if(current.left==null){
                    TNode parentNode=current.getParent();
                    if(parentNode.left==current){
                        parentNode.left=current.right;
                    }
                    else{
                        parentNode.right=current.right;
                    }
                }
                //the situation when current.left is not empty
                else{
                    TNode parentNode=current.getParent();
                    TNode rightNode=current.right;
                    TNode currentTraverse=current.left;
                    while (currentTraverse.right!=null){
                        currentTraverse=currentTraverse.right;
                    }
                    currentTraverse.right=rightNode;
                    if(parentNode.left==current){
                        parentNode.left=current.left;
                    }
                    else{
                        parentNode.right=current.left;
                    }
                }
            }

            //adds "numOfMaxes" max numbers among this node and its children
            //nodes into the given arraylist
            public void getMaxes(int numOfMaxes,ArrayList<V> arrayList){
                if(this.right!=null){
                    this.right.getMaxes(numOfMaxes,arrayList);
                }
                if(arrayList.size()+1<=numOfMaxes) {
                    arrayList.add(this.keyValuePair.getValue());
                }
                if(this.left!=null){
                    this.left.getMaxes(numOfMaxes,arrayList);
                }
            }

            //adds the keys into the given arrayList
            public void keySet(ArrayList<K> arrayList) {
                arrayList.add(this.keyValuePair.getKey());
                if (this.left != null) {
                    this.left.keySet(arrayList);
                }
                if (this.right != null) {
                    this.right.keySet(arrayList);
                }
            }

            //adds the values into the given arrayList
            public void values(ArrayList<V> arrayList) {
                arrayList.add(this.keyValuePair.getValue());
                if (this.left != null) {
                    this.left.values(arrayList);
                }
                if (this.right != null) {
                    this.right.values(arrayList);
                }
            }

            //adds the entries into the given arrayList
            public void entrySet(ArrayList<KeyValuePair<K,V>> arrayList) {
                arrayList.add(this.keyValuePair);
                if (this.left != null) {
                    this.left.entrySet(arrayList);
                }
                if (this.right != null) {
                    this.right.entrySet(arrayList);
                }
            }

            //returns the string that demonstrates the current node
            //and its children nodes with a top-down design
            public void toTopDownString(ArrayList<V> arrayList){
                arrayList.set(this.index,this.keyValuePair.getValue());
                if(this.left!=null){
                    this.left.toTopDownString(arrayList);
                }
                if(this.right!=null){
                    this.right.toTopDownString(arrayList);
                }
            }

            @Override
            //returns the string that describes the TNode
            public String toString(){
                String result=this.keyValuePair.toString()+"\n";
                if(this.left!=null){
                    result+="left      "+this.left.toString();
                }
                if(this.right!=null){
                    result+="right     "+this.right.toString();
                }
                return result;
            }
        }

        //main method, tests methods
        public static void main (String[]argv) {
            // create a BSTMap
            BSTMap<Integer, Integer> bst = new BSTMap<>(new IntegerComparator());

            //puts elements into the BSTMap
            bst.put(20,20);
            bst.put(29,29);
            bst.put(13,13);
            bst.put(1,1);
            bst.put(7,7);
            bst.put(27,27);
            bst.put(11,11);
            bst.put(100,100);
            bst.put(45,45);
            bst.put(15,15);
            bst.put(6,6);

            //tests get method
            System.out.println("\nTest get method: ");
            System.out.println(bst.get(11));
            System.out.println(bst.get(20));
            System.out.println(bst.get(29));
            System.out.println(bst.get(7));
            System.out.println(bst.get(27));
            System.out.println(bst.get(4));

            //toString methods
            System.out.println(bst.toString());
            System.out.println(bst.toTopDownString(0));

            //tests getMaxes method
            ArrayList<Integer> arrayList=bst.getMaxes(5);
            System.out.println("The five biggest integers: ");
            for(Integer item:arrayList){
                System.out.print(item+"\t");
            }

            //tests toTopDownString method
            System.out.println("\n");
            System.out.println(bst.toTopDownString(0));

            //tests remove method
            bst.remove(29);
            System.out.println("After the removal of 29: ");
            System.out.println(bst.toTopDownString(0));
            bst.remove(13);
            System.out.println("After the removal of 13: ");
            System.out.println(bst.toTopDownString(0));
            bst.remove(20);
            System.out.println("After the removal of 20: ");
            System.out.println(bst.toTopDownString(0));

        }


}

