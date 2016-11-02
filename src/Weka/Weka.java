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
import java.io.Serializable;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.classifiers.bayes.NaiveBayes;
import weka.filters.supervised.attribute.Discretize;
import weka.classifiers.Evaluation;
import weka.core.Instance;

/*ahihi
*/
/**
 *
 * @author user
 */
public class Weka {
    public static String lokasi_file = "data/iris.arff";
    public static Instances data;
    public static Classifier cls;
    public static Evaluation eval;
    public static Scanner r = new Scanner(System.in);
    
    public static void baca(String file)throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        data = new Instances(reader);
        reader.close();
        // setting class attribute
        data.setClassIndex(data.numAttributes() - 1);
    }
    
    public static Instances filter(Instances x) throws Exception{
        String[] options = new String[4];
        options[0] = "-R";
        options[1] = "first-last";
        options[2] = "-precision";
        options[3] = "6";
        Discretize dis = new Discretize();
        dis.setOptions(options);
        dis.setInputFormat(x);
        return Filter.useFilter(x, dis);
    }
    
    public static Classifier classification(Instances x)throws Exception{
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(x);
        return nb;
    }
    
    public static Classifier load_model(String nama) throws Exception{
        String path = "model/";
        path = path.concat(nama);
        path = path.concat(".model");
        return (Classifier) weka.core.SerializationHelper.read(path);
    }
    
    public static Evaluation full_training(Instances train, Classifier c) throws Exception{
        Evaluation temp = new Evaluation(train);
        temp.evaluateModel(c, data);
        return temp;
    }
    
    public static void select_train() throws Exception{
        System.out.print("Masukkan metode training ft(full training)/cv(10 folds cross-validation): ");
        String train_mode = r.next();
        System.out.println(cls);
        if(train_mode.equals("ft")){
            //training dengan metode full training
            eval = full_training(data,cls);
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
        }
    }
    
    public static void main(String[] args){
        try{
            ArrayList<Attribute> atts = new ArrayList<Attribute>();
            ArrayList<String> classVal = new ArrayList<String>();
            classVal.add("Iris-setosa");
            classVal.add("Iris-versicolor");
            classVal.add("Iris-virginica");
            atts.add(new Attribute("sepallength"));
            atts.add(new Attribute("sepalwidth"));
            atts.add(new Attribute("petallength"));
            atts.add(new Attribute("petalwidth"));
            atts.add(new Attribute("@@class@@", classVal));
            baca(lokasi_file);
            System.out.println(data);
            System.out.println(data.toSummaryString());
            System.out.println("=================");
            System.out.print("Masukkan nama model di folder model (- jika belum ada model): ");
            String model =  r.next();
            if ("-".equals(model)){
                //tanpa model, save masuk sini
                char filter;
                System.out.print("Ingin menggunakan filter? (Y/N)");
                filter = (char) System.in.read();
                if (Character.toLowerCase(filter)=='y'){
                    data = filter(data);
                    System.out.println(data);
                }
                classification(data);
                cls = classification(data);
                select_train();
            } else {
                //dengan model, load masuk sini
                char filter;
                System.out.print("Gunakan filter? (Y/N): ");
                filter = (char) System.in.read();
                if (Character.toLowerCase(filter)=='y')
                    data = filter(data);
                cls = load_model(model);
                System.out.println(cls);
                select_train();
            }
            System.out.println("Ingin mengklasifikasi data? (Y/N): ");
            char new_data = (char) System.in.read();
            if (Character.toLowerCase(new_data)=='y'){
                double[] attValues = new double[data.numAttributes()];
                for (int i=0;i<data.numAttributes()-1;i++){
                    attValues[i] = r.nextDouble();
                }
                Instance i1 = new DenseInstance(1.0, attValues);
                i1.setDataset(data);
                cls.classifyInstance(i1);
                System.out.println(i1);
                
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
