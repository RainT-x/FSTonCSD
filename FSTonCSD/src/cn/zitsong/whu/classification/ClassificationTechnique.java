package cn.zitsong.whu.classification;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.*;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.rules.*;
import weka.classifiers.trees.*;


/**
 * @Description: Classification techniques
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:08
 * @Since: version 1.0.0
 **/
public class ClassificationTechnique {

    /**
     * Statistical classification techniques
     */
    public static Classifier naiveBayes() {
        // return (Classifier)Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();
        return new NaiveBayes();
    }

    public static Classifier bayesianNetwork() {
        return new BayesNet();
    }

    public static Classifier logisticRegression() {
        return new Logistic();
    }

    /**
     * Neural network classification techniques
     */
    public static Classifier radialBasisFunction() {
        return new RBFNetwork();
    }

    public static Classifier multilayerPerceptron() {
        return new MultilayerPerceptron();
    }

    /**
     * Decision Tree classification techniques
     */
    public static Classifier logisticModelTrees() {
        return new LMT();
    }

    public static Classifier cart() {
        return new SimpleCart();
    }

    public static Classifier j48() {
        return new J48();
    }

    public static Classifier alternatingDecisionTrees() {
        return new ADTree();
    }

    public static Classifier decisionStump() {
        return new DecisionStump();
    }

    public static Classifier naiveBayesDecisionTree() {
        return new NBTree();
    }

    public static Classifier randomTree() {
        return new RandomTree();
    }

    /**
     * Rule based classification techniques
     */
    public static Classifier ripper() {
        return new JRip();
    }

    public static Classifier oneRule() {
        return new OneR();
    }

    public static Classifier decisionTable() {
        return new DecisionTable();
    }

    public static Classifier partialDecisionTrees() {
        return new PART();
    }

    public static Classifier rippleDownRules() {
        return new Ridor();
    }

    /**
     * K-NN classification techniques
     */
    public static Classifier knn() {
        return new IBk(10);
    }

    public static Classifier kStar() {
        return new KStar();
    }

    /**
     * Support vector machine classification techniques
     */
    public static Classifier votedPerceptron() {
        return new VotedPerceptron();
    }

    /**
     * Ensemble method machine classification techniques
     */
    public static Classifier randomForest() {
        return new RandomForest();
    }
}
