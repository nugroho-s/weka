/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Weka;

import java.io.File;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

/**
 *
 * @author user
 */
public class Weka {
    public static File f = new File("../../data/iris.arff");
    public static final char comment = '%';
    public static final char open = '{';
    public static final char close = '}';
    public static final char sep = ',';
    public static void main (String[] args){
        if (f.canRead()){
            System.out.println("");
        }
    }
}
