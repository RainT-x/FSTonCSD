package cn.zitsong.whu.utils;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.util.Random;

/**
 * @Description: To measure the performance
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 10:18
 * @Since: version 1.0.0
 **/
public class Measure {

//    public double accuracy;
    public double precision;
    public double recall;
    public double f1;
//    public double fNegative;
//    public double gMeasure;
//    public double gMean;
//    public double bal;
    public double mcc;
    // public double f2;
    public double auc;

    private double tp;
    private double fp;
    private double fn;
    private double tn;

    private Evaluation eval;

    public Measure(Evaluation eval) {
        this.eval = eval;
    }

    public void buildMeasure(){

        this.tp = eval.numTruePositives(1);
        this.fp = eval.numFalsePositives(1);
        this.fn = eval.numFalseNegatives(1);
        this.tn = eval.numTrueNegatives(1);

//        System.out.println("tp:  " + tp);
//        System.out.println("fp:  " + fp);
//        System.out.println("fn:  " + fn);
//        System.out.println("tn:  " + tn);

        //accuracy = (tn + tp) / (tn + fp + fn + tp);

        precision = tp / (tp + fp);
        if(Double.isNaN(precision)) {
        	precision = 0.0;
        }

        recall = tp / (tp + fn);
        if(Double.isNaN(recall)) {
        	recall = 0.0;
        }

        f1 = 2 * recall * precision / (recall + precision);
        if(Double.isNaN(f1)) {
        	f1 = 0.0;
        }

//        double r1 = tn / (tn + fp);
//        double p1 = tn / (tn + fn);
//        fNegative = 2 * r1 * p1 / (r1 + p1);
//
//        double pf = fp / (fp + tn);
//        gMeasure = 2 * recall * (1 - pf) / (recall + (1 - pf));
//
//        double specificity = tn / (tn + fp);
//        gMean = Math.sqrt(recall * specificity);
//
//        bal = 1 - Math.sqrt(Math.pow(pf, 2) + Math.pow((1 - recall), 2)) / Math.sqrt(2);

        double tmp_mcc = (tp + fn) * (tp + fp) * (fn + tn) * (fp + tn);
        mcc = (tp * tn - fn * fp) / Math.sqrt(tmp_mcc);
        if(Double.isNaN(mcc)) {
        	mcc = 0.0;
        }

        auc = eval.areaUnderROC(1);
        if(Double.isNaN(auc)) {
        	auc = 0.0;
        }

    }

//    public double getAccuracy() {
//        return accuracy;
//    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    public double getF1() {
        return f1;
    }

//    public double getfNegative() {
//        return fNegative;
//    }
//
//    public double getgMeasure() {
//        return gMeasure;
//    }
//
//    public double getgMean() {
//        return gMean;
//    }
//
//    public double getBal() {
//        return bal;
//    }

    public double getMcc() {
        return mcc;
    }

    public double getAuc() {
        return auc;
    }

    @Override
    public String toString() {
    	 return "Measure{" +
                 "precision=" + precision +
                 ", recall=" + recall +
                 ", f1=" + f1 +
                 ", mcc=" + mcc +
                 ", auc=" + auc +
                 '}';
//        return "Measure{" +
//                "accuracy=" + accuracy +
//                ", precision=" + precision +
//                ", recall=" + recall +
//                ", f1=" + f1 +
//                ", fNegative=" + fNegative +
//                ", gMeasure=" + gMeasure +
//                ", gMean=" + gMean +
//                ", bal=" + bal +
//                ", mcc=" + mcc +
//                ", auc=" + auc +
//                ", tp=" + tp +
//                ", fp=" + fp +
//                ", fn=" + fn +
//                ", tn=" + tn +
//                ", eval=" + eval +
//                '}';
    }

    public static void main(String[] args) throws Exception {

        // load data
        Instances ins = null;

        File file = new File("datasets/codec.arff");

        ArffLoader loader = new ArffLoader();
        loader.setFile(file);
        ins = loader.getDataSet();
        ins.setClassIndex(ins.numAttributes() - 1);

        Instances instances = new Instances(ins, 0, 50);

        J48 j48 = new J48();
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(j48, instances, 2, new Random(1234));

        System.out.println("综述" + evaluation.predictions().size());
        for (Prediction prediction: evaluation.predictions()) {
            System.out.println(prediction.actual() + "---" + prediction.predicted());
        }

        new Measure(evaluation).buildMeasure();
    }
}
