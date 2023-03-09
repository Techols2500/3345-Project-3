/*
Tyler Echols

CS 3345.002 - Data Structures and Introduction to Algorithmic Analysis

Project #3

Due Dates:  Monday, March 22 at 11:59pm (Project 3:  can be turned in as late as 3/27)

Submit:    eLearning

Late Policy:  -10 points per hour late

Instructions: This is an individual assignment.  All work should be your own.



Objective:

     Work with hash tables by creating a hash table using linear probing.


Description:

     Create a generic class called LinearProbingHashTable<K,V>.

     It should contain a private static class, Entry<K,V>, which contains both K and V.

     Because Java cannot create an array of a generic class, create the
     array for the table like this:

       Entry<K,V> table[];      // declare generic
       table = new Entry[size]; // create as non-generic

     Note that this will generate a warning message when compiled.

     Your class should have the following methods.  The methods should
     all operate on the object making the call (none are static).

     Perform checking of the parameters and throw exceptions where
     appropriate.

     You may use code from the textbook, but all other code must be
     your own.
*/

import java.util.*;
import java.io.*;
import java.util.Scanner;

public class LinearProbingHashTable <K,V> {
    private static class Entry<K, V> {
        K Key;
        V Value;
        boolean deleted;

        public Entry(K Key, V Value) {
            this.Key = Key;
            this.Value = Value;
            deleted = false;


        }

    }

    Entry<K, V> table[];      // declare generic

    int size;
    int current;

    public LinearProbingHashTable(int size) {
        this.size = size;
        current = 0;
        table = new Entry[size]; // create as non-generic

    }

    public int getHashValue(K key) {
        int Val = key.hashCode() % size;
        return Val;
    }

    public int getLocation(K key) {
        for (int i = 0; i < size; i++) {
            if (table[i] != null && table[i].Key == key) {
                return i;
            }
        }
        return -1;
    }

    public boolean insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key value is null");
        }
        if (getLocation(key) != -1) {
            return false;
        }
        int temp = getHashValue(key); // applying function to find the index
        int x = temp;
        do {
            if (table[x] == null) {
                table[x] = new Entry(key, value);
                current++;
                if (current >= size / 2)
                    rehash();
                return true;
            }
            // to fo: finish insert after rehash function

            x = (x + 1) % size;

        }while (x != temp) ;

        return false;
    }


    public boolean delete(K key){
        for(int x = 0; x < size; x++){
          if(table[x] !=null && table[x].Key == key){
            table[x].deleted = true;
            return true;
          }
        }
        return false;
    }


    private void rehash( ){ // may need new_table
        Entry<K,V> new_table[]= new Entry[2 * this.size];
        for(int x = 0; x < size; x++){
            if(table[x]!=null && !table[x].deleted)
                new_table[x] = table[x];
        }
        this.size  *= 2;
        table = new_table;
    }


    public String toString(){
        String temp = " ";
        for(int x = 0; x<size; x++){
            temp += x + " ";
            if(table[x] != null) {
                temp += table[x].Key + "," + table[x].Value + " ";
                if(table[x].deleted)
                    temp += "  this value is deleted ";
            }
            temp += "\n";
        }
        return temp;
    }

    public V find(K key){
        for(int x=0; x < size; x++){
            if(table[x] != null && table[x].Key == key)
                return table[x].Value;
        }
        return null;
    }

    public static void main(String args[]){
        LinearProbingHashTable<Integer, Integer> TheObj = new LinearProbingHashTable<>(10);
        System.out.println(" The Hash Table is : ");
        TheObj.insert(8,9);
        TheObj.insert(4,84);
        TheObj.insert(2,10);
        TheObj.insert(9,62);
        TheObj.insert(5,19);
        TheObj.insert(6,39);
        TheObj.insert(19,369);
        TheObj.insert(12,87);
        System.out.println(TheObj);

        System.out.println(" Key 5 Value Deleted :" + TheObj.delete(5));
        System.out.println(TheObj);
        System.out.println(" The Value  at key 6 is: " + TheObj.find(6));



    }


}
