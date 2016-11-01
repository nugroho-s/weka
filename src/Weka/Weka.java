/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Weka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.classifiers.bayes.NaiveBayes;
import weka.filters.supervised.attribute.Discretize;

/*ahihi
*/
/**
 *
 * @author user
 */
public class Weka {
    public static File f = new File("../../data/iris.arff");
    public static Instances data;
    public static Instances newData;
    
    public static void baca(String file)throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        data = new Instances(reader);
        reader.close();
        // setting class attribute
        data.setClassIndex(data.numAttributes() - 1);
    }
    
    public static Instances filter(Instances x) throws Exception{
        String[] options = new String[4];
        options[0] = "-R";                                    // "range"
        options[1] = "first-last";                                     // first attribute
        options[2] = "-precision";
        options[3] = "6";
        Discretize dis = new Discretize();
        dis.setOptions(options);
        dis.setInputFormat(x);
        return Filter.useFilter(x, dis);
    }
    
    public static void main(String[] args){
        Scanner r = new Scanner(System.in);
        try{
            baca("data/iris.arff");
            System.out.println(data);
            System.out.println("=================");
            char filter;
            System.out.println("Ingin menggunakan filter? (Y/N)");
            filter = (char) System.in.read();
            if (Character.toLowerCase(filter)=='y'){
                newData = filter(data);
                System.out.println(newData);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
