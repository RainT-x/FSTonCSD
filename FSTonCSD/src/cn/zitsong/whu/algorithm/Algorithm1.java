package cn.zitsong.whu.algorithm;

import cn.zitsong.whu.bean.AlgorithmResultOnce;
import cn.zitsong.whu.classification.ClassificationTechnique;
import cn.zitsong.whu.constant.SuperParameter;
import cn.zitsong.whu.feature.FilterFeatureRankingTechnique;
import cn.zitsong.whu.feature.FilterSubSetSelectionTechnique;
import cn.zitsong.whu.feature.SearchMethod;
import cn.zitsong.whu.feature.WrapperSubsetSelectionTechnique;
import cn.zitsong.whu.utils.Measure;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: The construction of our algorithm
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:07
 * @Since: version 1.0.0
 **/
public class Algorithm1 {

//    public static void chiSquareNB(Instances train, Instances test) throws Exception {
//
//        /**
//         * Use the filter
//         */
////        AttributeSelection attributeSelection = new AttributeSelection();
////
////        ASEvaluation asEvaluation = FilterFeatureRankingTechnique.chiSquare();
////        Ranker ranker = SearchMethod.rankSearch();
////        ranker.setNumToSelect(7);
////        attributeSelection.setEvaluator(asEvaluation);
////        attributeSelection.setSearch(ranker);
////        attributeSelection.setInputFormat(ins);
////
////        Instances selectData = Filter.useFilter(ins, attributeSelection);
////
////        System.out.println(selectData);
//
//        /**
//         * Use the meta classifier
//         */
//        ASEvaluation asEvaluation = FilterFeatureRankingTechnique.chiSquare();
//        Ranker ranker = rankSearch();
//        ranker.setNumToSelect(SuperParameter.FEATURE_NUM);
//        Classifier naiveBayes = ClassificationTechnique.naiveBayes();
//
//        AttributeSelectedClassifier classifier = getAttributeSelectedClassifier(asEvaluation, ranker, naiveBayes);
//
//        Measure measure = getMeasure(classifier, train, test);
//        System.out.println(measure.toString());
//
//    }

    /**
     * Get AttributeSelectedClassifier
     *
     * @param asEvaluation
     * @param asSearch
     * @param classifier
     * @return
     */
    public static AttributeSelectedClassifier getAttributeSelectedClassifier(ASEvaluation asEvaluation, ASSearch asSearch, Classifier classifier) {

        AttributeSelectedClassifier attributeSelectedClassifier = new AttributeSelectedClassifier();
        attributeSelectedClassifier.setEvaluator(asEvaluation);
        attributeSelectedClassifier.setSearch(asSearch);
        attributeSelectedClassifier.setClassifier(classifier);
        return attributeSelectedClassifier;
    }

    /**
     * Get the measure result according to the cross validation
     *
     * @param classifier
     * @param instances
     * @return
     * @throws Exception
     */
    public static Measure getMeasure(AttributeSelectedClassifier classifier, Instances instances) throws Exception {
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(classifier, instances, 2, new Random());
        Measure measure = new Measure(evaluation);
        measure.buildMeasure();
        return measure;
    }

    /**
     * Get the measure result
     *
     * @param classifier
     * @param train
     * @param test
     * @return
     * @throws Exception
     */
//    private static Measure getMeasure(AttributeSelectedClassifier classifier, Instances train, Instances test) throws Exception {
//        Evaluation evaluation = new Evaluation(train);
//        classifier.buildClassifier(train);
//        evaluation.evaluateModel(classifier, test);
//        Measure measure = new Measure(evaluation);
//        measure.buildMeasure();
//        return measure;
//    }

    /**
     * Get the measure result for none feature selection
     *
     * @param classifier
     * @param train
     * @param test
     * @return
     * @throws Exception
     */
    public static Measure getMeasureNone(Classifier classifier, Instances train, Instances test) throws Exception {
        Evaluation evaluation = new Evaluation(train);
        classifier.buildClassifier(train);
        evaluation.evaluateModel(classifier, test);
        Measure measure = new Measure(evaluation);
        measure.buildMeasure();
        return measure;
    }

    /**
     * FeatureRanking + Classifier
     *
     * @param train
     * @param test
     * @throws Exception
     */
    public static List<String> applyFeatureRankingSelection(Instances train, Instances test, String prefix) throws Exception {
        Method[] featureSelectionMethods = FilterFeatureRankingTechnique.class.getDeclaredMethods();
        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
//        List<List<Integer>> ColumnName = new ArrayList<>();
        List<String> Columns = new ArrayList<>();

        for (Method featureSelectionMethod: featureSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) featureSelectionMethod.invoke(null);
            Ranker ranker = rankSearch();

//            List<Instances> instances = selectFeature(evaluation, ranker, train, test);

            List<Integer> attributesLists = selectFeatureName(evaluation, ranker, train, test);
            String feature = "";
            for (Integer index :  attributesLists)
            {
                feature = feature + " " + Integer.toString(index);
            }

            String line = featureSelectionMethod.getName() + " " + feature ;
            Columns.add(line);
            System.out.println(line);

//            Instances trainIns = instances.get(0);
//            Instances testIns = instances.get(1);

//            for (Method classificationMethod: classificationMethods) {
//                Measure measure = getMeasure(classifier, trainIns, testIns);

//                AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
//                String name = featureSelectionMethod.getName() + '-' + classificationMethod.getName();
//                algorithmResultOnce.setAlgorithm(name);
//                algorithmResultOnce.setMeasure(measure);
//                algorithmResultOnceList.add(algorithmResultOnce);
//                String name = featureSelectionMethod.getName() + '-' + classificationMethod.getName();
//                name = prefix + name;
//                System.out.println(name + ":" + measure.toString());
//            }
//            ColumnName.add(attributesLists);
        }
        return Columns;

//        trainTestIns.add(newIns);
//        trainTestIns.add(newTestIns);
//        return trainTestIns;
    };



    public static List<AlgorithmResultOnce> applyFeatureRankingClassifier(Instances train, Instances test, String prefix) throws Exception {

        Method[] featureSelectionMethods = FilterFeatureRankingTechnique.class.getDeclaredMethods();
        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();

        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method featureSelectionMethod: featureSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) featureSelectionMethod.invoke(null);
            Ranker ranker = rankSearch();

            List<Instances> instances = selectFeature(evaluation, ranker, train, test);

            Instances trainIns = instances.get(0);
            Instances testIns = instances.get(1);

            // select the same feature for test data set
            //Instances newTestIns = selectSameFeatureFromTrain(train, test);

            for (Method classificationMethod: classificationMethods) {
                Classifier classifier = (Classifier) classificationMethod.invoke(null);
                //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, ranker, classifier);
                //Measure measure = getMeasure(asClassifier, train, test);

                Measure measure = getMeasure(classifier, trainIns, testIns);

                AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
                String name = featureSelectionMethod.getName() + '-' + classificationMethod.getName();
                algorithmResultOnce.setAlgorithm(name);
                algorithmResultOnce.setMeasure(measure);
                algorithmResultOnceList.add(algorithmResultOnce);

                name = prefix + name;

                System.out.println(name + ":" + measure.toString());
            }
        }

        return algorithmResultOnceList;
    }

    public static List<String> applySubSetSelection(Instances train, Instances test, String prefix) throws Exception {

        Method[] subSetSelectionMethods = FilterSubSetSelectionTechnique.class.getDeclaredMethods();
//        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
        Method[] searchMethods = SearchMethod.class.getDeclaredMethods();
//        List<List<Integer>> ColumnName = new ArrayList<>();
        List<String> Columns = new ArrayList<>();
//        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method subSetSelectionMethod : subSetSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) subSetSelectionMethod.invoke(null);
            for (Method searchMethod : searchMethods) {
                ASSearch search = (ASSearch) searchMethod.invoke(null);

//                List<Instances> instances = selectFeature(evaluation, search, train, test);
                List<Integer> attributesLists = selectFeatureName(evaluation, search, train, test);
                String feature = "";
                for (Integer index :  attributesLists)
                {
                    feature = feature + " " + Integer.toString(index);
                }

                String line = subSetSelectionMethod.getName() + "-" + searchMethod.getName() + " " + feature ;
                Columns.add(line);
//                System.out.println(line);


//                Instances trainIns = instances.get(0);
//                Instances testIns = instances.get(1);

                // Instances newTestIns = selectSameFeatureFromTrain(train, test);

//                for (Method classificationMethod : classificationMethods) {
//                    Classifier classifier = (Classifier) classificationMethod.invoke(null);
                    //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, search, classifier);
                    //Measure measure = getMeasure(asClassifier, train, test);

//                    Measure measure = getMeasure(classifier, trainIns, testIns);
//
//                    AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
//                    String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName() + "-" + classificationMethod.getName();
//                    algorithmResultOnce.setAlgorithm(name);
//                    algorithmResultOnce.setMeasure(measure);
//                    algorithmResultOnceList.add(algorithmResultOnce);

//                    name = prefix + name;

//                    System.out.println(name + ":" + measure.toString());
//                }
            }
        }

        return Columns;
    }





    /**
     * FeatureSubSetSelection + Classifier
     *
     * @param train
     * @param test
     * @throws Exception
     */
    public static List<AlgorithmResultOnce> applySubSetSelectionClassifier(Instances train, Instances test, String prefix) throws Exception {

        Method[] subSetSelectionMethods = FilterSubSetSelectionTechnique.class.getDeclaredMethods();
        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
        Method[] searchMethods = SearchMethod.class.getDeclaredMethods();

        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method subSetSelectionMethod : subSetSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) subSetSelectionMethod.invoke(null);
            for (Method searchMethod : searchMethods) {
                ASSearch search = (ASSearch) searchMethod.invoke(null);

                List<Instances> instances = selectFeature(evaluation, search, train, test);

                Instances trainIns = instances.get(0);
                Instances testIns = instances.get(1);

                // Instances newTestIns = selectSameFeatureFromTrain(train, test);

                for (Method classificationMethod : classificationMethods) {
                    Classifier classifier = (Classifier) classificationMethod.invoke(null);
                    //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, search, classifier);
                    //Measure measure = getMeasure(asClassifier, train, test);

                    Measure measure = getMeasure(classifier, trainIns, testIns);

                    AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
                    String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName() + "-" + classificationMethod.getName();
                    algorithmResultOnce.setAlgorithm(name);
                    algorithmResultOnce.setMeasure(measure);
                    algorithmResultOnceList.add(algorithmResultOnce);

                    name = prefix + name;

                    System.out.println(name + ":" + measure.toString());
                }
            }
        }

        return algorithmResultOnceList;
    }


    /**
     * WrapperSubSetSelection + Classifier
     *
     * @param train
     * @param test
     * @throws Exception
     */
//    public static List<AlgorithmResultOnce> applyWrapperSubSetSelectionClassifier(Instances train, Instances test, String prefix) throws Exception {
//
//        Method[] subSetSelectionMethods = WrapperSubsetSelectionTechnique.class.getDeclaredMethods();
//        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
//        Method[] searchMethods = SearchMethod.class.getDeclaredMethods();
//
//        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();
//
//        for (Method subSetSelectionMethod: subSetSelectionMethods) {
//            ASEvaluation evaluation = (ASEvaluation) subSetSelectionMethod.invoke(null);
//            for(Method searchMethod: searchMethods) {
//                ASSearch search = (ASSearch) searchMethod.invoke(null);
//                for (Method classificationMethod: classificationMethods) {
//                    Classifier classifier = (Classifier) classificationMethod.invoke(null);
//                    AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, search, classifier);
//                    Measure measure = getMeasure(asClassifier, train, test);
//
//                    AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
//                    String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName() + "-" + classificationMethod.getName();
//                    algorithmResultOnce.setAlgorithm(name);
//                    algorithmResultOnce.setMeasure(measure);
//                    algorithmResultOnceList.add(algorithmResultOnce);
//
//                    name = prefix + name;
//
//                    System.out.println(name + ":" + measure.toString());
//                }
//            }
//
//        }
//
//        return algorithmResultOnceList;
//    }

    public static List<String> applyWrapperSubSetSelection(Instances train, Instances test, String prefix) throws Exception {

        Method[] subSetSelectionMethods = WrapperSubsetSelectionTechnique.class.getDeclaredMethods();
//        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
        Method[] searchMethods = SearchMethod.class.getDeclaredMethods();
        List<List<Integer>> ColumnName = new ArrayList<>();
        List<String> Columns = new ArrayList<>();
//        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method subSetSelectionMethod: subSetSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) subSetSelectionMethod.invoke(null);
            for(Method searchMethod: searchMethods) {
                ASSearch search = (ASSearch) searchMethod.invoke(null);

//                List<Instances> instances = selectFeature(evaluation, search, train, test);
                List<Integer> attributesLists = selectFeatureName(evaluation, search, train, test);
                String feature = "";
                for (Integer index :  attributesLists)
                {
                    feature = feature + " " + Integer.toString(index);
                }

                String line = subSetSelectionMethod.getName() + "-" + searchMethod.getName() +" " + feature ;
                Columns.add(line);
//                System.out.println(line);

//                Instances trainIns = instances.get(0);
//                Instances testIns = instances.get(1);

                // select the same feature for test data set
                //Instances newTestIns = selectSameFeatureFromTrain(train, test);

//                for (Method classificationMethod: classificationMethods) {
//
//                    Classifier classifier = (Classifier) classificationMethod.invoke(null);
//
//                    //classifier.buildClassifier(instances);
//
//                    //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, search, classifier);
////                    Measure measure = getMeasure(classifier, trainIns, testIns);
////
////                    AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
////                    String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName() + "-" + classificationMethod.getName();
////                    algorithmResultOnce.setAlgorithm(name);
////                    algorithmResultOnce.setMeasure(measure);
////                    algorithmResultOnceList.add(algorithmResultOnce);
////
////                    name = prefix + name;
////
////                    System.out.println(name + ":" + measure.toString());
//                }
            }

        }

        return Columns;
    }



    public static List<AlgorithmResultOnce> applyWrapperSubSetSelectionClassifier(Instances train, Instances test, String prefix) throws Exception {

        Method[] subSetSelectionMethods = WrapperSubsetSelectionTechnique.class.getDeclaredMethods();
        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
        Method[] searchMethods = SearchMethod.class.getDeclaredMethods();

        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method subSetSelectionMethod: subSetSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) subSetSelectionMethod.invoke(null);
            for(Method searchMethod: searchMethods) {
                ASSearch search = (ASSearch) searchMethod.invoke(null);

                List<Instances> instances = selectFeature(evaluation, search, train, test);

                Instances trainIns = instances.get(0);
                Instances testIns = instances.get(1);

                // select the same feature for test data set
                //Instances newTestIns = selectSameFeatureFromTrain(train, test);

                for (Method classificationMethod: classificationMethods) {

                    Classifier classifier = (Classifier) classificationMethod.invoke(null);

                    //classifier.buildClassifier(instances);

                    //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, search, classifier);
                    Measure measure = getMeasure(classifier, trainIns, testIns);

                    AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
                    String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName() + "-" + classificationMethod.getName();
                    algorithmResultOnce.setAlgorithm(name);
                    algorithmResultOnce.setMeasure(measure);
                    algorithmResultOnceList.add(algorithmResultOnce);

                    name = prefix + name;

                    System.out.println(name + ":" + measure.toString());
                }
            }

        }

        return algorithmResultOnceList;
    }

    public static List<Integer> selectFeatureName(ASEvaluation m_Evaluator, ASSearch search, Instances instances, Instances test) throws Exception {
        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setEvaluator(m_Evaluator);
        attributeSelection.setSearch(search);
        attributeSelection.SelectAttributes(instances);

        int[] attributesIdx = attributeSelection.selectedAttributes();

        //Instances newIns = attributeSelection.reduceDimensionality(instances);

        List<Integer> attributesLists = new ArrayList<>();
        for(int i = 0; i < attributesIdx.length; i++) {
            attributesLists.add(attributesIdx[i]);
        }
//
//        Instances newIns = new Instances(instances);
//        for(int i = newIns.numAttributes() - 1; i > -1; i--){    //delete from back to forward
//            if(!attributesLists.contains(i))
//                newIns.deleteAttributeAt(i);
//        }

        //System.out.println(newIns);

//        Instances newTestIns = new Instances(test);
//        for(int i = newTestIns.numAttributes() - 1; i > -1; i--){    //delete from back to forward
//            if(!attributesLists.contains(i))
//                newTestIns.deleteAttributeAt(i);
//        }
        //System.out.println("=====================================================================");
        //System.out.println(newTestIns);

//        List<Instances> trainTestIns = new ArrayList<>();
//        trainTestIns.add(newIns);
//        trainTestIns.add(attributesLists);
//        return trainTestIns;
        return attributesLists;
    }

    /**
     * Select the feature
     *
     * @param m_Evaluator
     * @param search
     * @param instances
     * @return
     * @throws Exception
     */
    public static List<Instances> selectFeature(ASEvaluation m_Evaluator, ASSearch search, Instances instances, Instances test) throws Exception {
        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setEvaluator(m_Evaluator);
        attributeSelection.setSearch(search);
        attributeSelection.SelectAttributes(instances);

        int[] attributesIdx = attributeSelection.selectedAttributes();

        //Instances newIns = attributeSelection.reduceDimensionality(instances);

        List<Integer> attributesLists = new ArrayList<>();
        for(int i = 0; i < attributesIdx.length; i++) {
            attributesLists.add(attributesIdx[i]);
        }

        Instances newIns = new Instances(instances);
        for(int i = newIns.numAttributes() - 1; i > -1; i--){    //delete from back to forward
            if(!attributesLists.contains(i))
                newIns.deleteAttributeAt(i);
        }

        //System.out.println(newIns);

        Instances newTestIns = new Instances(test);
        for(int i = newTestIns.numAttributes() - 1; i > -1; i--){    //delete from back to forward
            if(!attributesLists.contains(i))
                newTestIns.deleteAttributeAt(i);
        }
        //System.out.println("=====================================================================");
        //System.out.println(newTestIns);

        List<Instances> trainTestIns = new ArrayList<>();
        trainTestIns.add(newIns);
        trainTestIns.add(newTestIns);
        return trainTestIns;
    }

    /**
     * Select the same feature from train data set
     *
     * @param train
     * @param test
     * @return
     * @throws Exception
     */
//    private static Instances selectSameFeatureFromTrain(Instances train, Instances test) throws Exception {
//
//        Filter filter = new Copy();
//        filter.setInputFormat(train);
//        Instances newTestIns = Filter.useFilter(test, filter);
//        return newTestIns;
//    }

    /**
     * Get the measure result
     *
     * @param classifier
     * @param train
     * @param test
     * @return
     * @throws Exception
     */
    public static Measure getMeasure(Classifier classifier, Instances train, Instances test) throws Exception {
        Evaluation evaluation = new Evaluation(train);
        classifier.buildClassifier(train);
        evaluation.evaluateModel(classifier, test);
        Measure measure = new Measure(evaluation);
        measure.buildMeasure();
        return measure;
    }

    /**
     * None Feature + Classifier
     *
     * @param train
     * @param test
     * @throws Exception
     */
    public static List<AlgorithmResultOnce> applyNoneFeatureClassifier(Instances train, Instances test, String prefix) throws Exception {

        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();

        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method classificationMethod: classificationMethods) {
            Classifier classifier = (Classifier) classificationMethod.invoke(null);
            Measure measure = getMeasureNone(classifier, train, test);

            AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
            String name = "None-" + classificationMethod.getName();
            algorithmResultOnce.setAlgorithm(name);
            algorithmResultOnce.setMeasure(measure);
            algorithmResultOnceList.add(algorithmResultOnce);

            name = prefix + name;

            System.out.println(name + ":" + measure.toString());
        }

        return algorithmResultOnceList;

    }

    /**
     * Search for Feature Ranking
     *
     * @return
     */
    public static Ranker rankSearch() {
        Ranker ranker = new Ranker();
        ranker.setNumToSelect(SuperParameter.FEATURE_NUM);
        return ranker;
    }


    public static List<String> FeatureSelection(String filepath, String featureMethod)  throws Exception {
        Instances ins1 = null;

//        CSVLoader loader_csv1 = new CSVLoader();
        ArffLoader loader_csv1 = new ArffLoader();
        loader_csv1.setFile(new File(filepath));
        ins1 = loader_csv1.getDataSet();
        ins1.setClassIndex(ins1.numAttributes() - 1);

        Instances data = new Instances(ins1);
        data = DataProcess.normalizeAndNominalData(data);
        
        
        System.out.println(data);

        // ------- 特征选取 --------
        List<String> results = null;
        switch (featureMethod){
            case ("r"):
                results = applyFeatureRankingSelection(data, data, "test");
                break;
            case ("s"):
                results =applySubSetSelection(data, data, "test");
            	break;
            case("w"):
                results =applyWrapperSubSetSelection(data, data, "test");
                break;
            default:
                System.out.println("Not choose");


        }
        return results;

    }


    public static void main(String[] args) throws Exception {

//        // load data
//        Instances ins1 = null;
//        Instances ins2 = null;
//
//        CSVLoader loader_csv1 = new CSVLoader();
//        loader_csv1.setFile(new File("D:\\Download\\DataMining\\CrossversionData\\1\\ant-1.4.csv"));
//        ins1 = loader_csv1.getDataSet();
//        ins1.setClassIndex(ins1.numAttributes() - 1);
//
//        CSVLoader loader_csv2 = new CSVLoader();
//        loader_csv2.setFile(new File("D:\\Download\\DataMining\\CrossversionData\\1\\ant-1.3.csv"));
//        ins2 = loader_csv2.getDataSet();
//        ins2.setClassIndex(ins2.numAttributes() - 1);
//
//
////        File test_file = new File("D:/Mining/EASC/ArffData/test.arff");
////        File train_file = new File("D:/Mining/EASC/ArffData/train.arff");
////        ArffLoader loader1 = new ArffLoader();
////        loader1.setFile(test_file);
////        ins1 = loader1.getDataSet();
////        ins1.setClassIndex(ins1.numAttributes() - 1);
////
////        ArffLoader loader2 = new ArffLoader();
////        loader2.setFile(train_file);
////        ins2 = loader2.getDataSet();
////        ins2.setClassIndex(ins2.numAttributes() - 1);
//
//        Instances train = new Instances(ins2, 0, ins2.size());
//        Instances test = new Instances(ins1, 0, ins1.size());
//        train = DataProcess.normalizeAndNominalData(train);
//        test = DataProcess.normalizeAndNominalData(test);
//
//        // chiSquareNB(ins);
////        applyFeatureRankingClassifier(train, test, "test");
//        //applySubSetSelectionClassifier(ins);
//        //applyWrapperSubSetSelectionClassifier(train, test, "test");
//
//        // ------- 特征选取 --------
//        applyFeatureRankingSelection(train, test, "test");
////        applySubSetSelection(train, test, "test"); 
////        applyWrapperSubSetSelection(train, test, "test");

//        List<String> res = FeatureSelection("D:\\Mining\\EASC\\CrossversionData\\1\\ant-1.3.csv", "s");
    	List<String> res = FeatureSelection("D:\\Mining\\EASC\\ArffData\\train.arff", "s");
        for (String s: res)
        {
            System.out.println(s);
        }

    }
}