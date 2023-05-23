package cn.zitsong.whu.feature;

import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.JRip;
import weka.core.SelectedTag;

import weka.classifiers.trees.SimpleCart;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.VotedPerceptron;

public class WrapperClassifierMeasure {

    /**
     * K-Nearest neighbor
     */
    public static Classifier knn() {
        return new IBk(10);
    }

    /**
     * Logistic Regression
     */
    public static Classifier logisticRegression() {
        return new Logistic();
    }

    /**
     * Naive Bayes
     */
    public static Classifier naiveBayes() {
        return new NaiveBayes();
    }

    /**
     * Repeated Incremental Pruning to Produce Error Reduction
     */
    public static Classifier ripper() {
        return new JRip();
    }
    
    /**
     * Classification and Regression Tree
     */
    public static Classifier cart() {
        return new SimpleCart();
    }
    
    /*
     * Random Forest
     */
    public static Classifier randomForest() {
        return new RandomForest();
    }
    
    /*
     * MultiLayer Perceptron
     */
    public static Classifier multilayerPerceptron() {
        return new MultilayerPerceptron();
    }
    
    /*
     * Support Vector Machine
     */
    public static Classifier votedPerceptron() {
        return new VotedPerceptron();
    }
    /**
     * Evaluation Measure
     */
    public static SelectedTag AUC() {
        return new SelectedTag(WrapperSubsetEval.EVAL_AUC, WrapperSubsetEval.TAGS_EVALUATION);
    }
    public static SelectedTag FMEASURE() {
        return new SelectedTag(WrapperSubsetEval.EVAL_FMEASURE, WrapperSubsetEval.TAGS_EVALUATION);
    }

    public static SelectedTag prAUC() {
        return new SelectedTag(WrapperSubsetEval.EVAL_AUPRC, WrapperSubsetEval.TAGS_EVALUATION);
    }

    public static SelectedTag rMSE() {
        return new SelectedTag(WrapperSubsetEval.EVAL_RMSE, WrapperSubsetEval.TAGS_EVALUATION);
    }
}
