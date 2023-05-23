package cn.zitsong.whu.algorithm;

import cn.zitsong.whu.bean.TrainTestDataSet;
import cn.zitsong.whu.constant.SuperParameter;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import weka.core.converters.CSVLoader;
import weka.core.converters.ArffSaver;

/**
 * @Description: Processing the data
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:07
 * @Since: version 1.0.0
 **/
public class DataProcess {

    /**
     * Shuffle the order and split the train and test data set
     *
     * @param instancesList
     * @return
     */
    public static TrainTestDataSet splitDataSet(List<Instances> instancesList) throws Exception {

        Instances positiveData = instancesList.get(0);
        Instances negativeData = instancesList.get(1);

        // shuffle the order
        Random random = new Random();
        positiveData.randomize(random);
        negativeData.randomize(random);

        // split the train and test dataSet
        Instances train = new Instances(positiveData, 0, positiveData.size() / 2);
        Instances test = new Instances(positiveData, positiveData.size() / 2, positiveData.size() - (positiveData.size() / 2));

        Instances trainNeg = new Instances(negativeData, 0, negativeData.size() / 2);
        Instances testNeg = new Instances(negativeData, negativeData.size() / 2, negativeData.size() - (negativeData.size() / 2));

        train.addAll(trainNeg);
        test.addAll(testNeg);

        // package the train and test data set
        TrainTestDataSet trainTestDataSet = new TrainTestDataSet();
        trainTestDataSet.setTrain(train);
        trainTestDataSet.setTest(test);

        return trainTestDataSet;
    }


    /**
     * Filter the dataSet by filterClassValue
     *
     * @param ins
     * @param filterClassValue
     * @return
     */
    public static List<Instances> filterSpecificInstances(Instances ins, double filterClassValue){

        List<Instances> instancesList = new ArrayList<>();

        Instances positiveIns = new Instances(ins, 0);
        Instances negativeIns = new Instances(ins, 0);
        for(int i = 0; i < ins.numInstances(); i++) {
            if(filterClassValue == ins.instance(i).classValue()){ // check here
                positiveIns.add(ins.instance(i));
            } else {
                negativeIns.add(ins.instance(i));
            }
        }

        instancesList.add(positiveIns);
        instancesList.add(negativeIns);
        return instancesList;
    }

    /**
     * Normalize and nominal the data
     *
     * @param instances
     * @return
     * @throws Exception
     */
    public static Instances normalizeAndNominalData(Instances instances) throws Exception {

        Filter filter = new Normalize();
        filter.setInputFormat(instances);
        Instances normalizedIns = Filter.useFilter(instances, filter);

        NumericToNominal nominal = new NumericToNominal();
        nominal.setInputFormat(normalizedIns);
        nominal.setAttributeIndices("last");
        Instances nominalIns = Filter.useFilter(normalizedIns, nominal);

        return nominalIns;
    }

    /**
     * load data from the path
     *
     * @param path
     * @return
     */
    public static Instances loadDataPer(String path) throws Exception {

        Instances ins = null;

        File file = new File(path);

        ArffLoader loader = new ArffLoader();
        loader.setFile(file);
        ins = loader.getDataSet();
        ins.setClassIndex(ins.numAttributes() - 1);

        return ins;
    }

    /**
     * load all data set
     *
     * @return
     * @throws Exception
     */
    public static List<Instances> loadDataAll() throws Exception {

//        Instances codec = loadDataPer("ICRF-ARFF/codec.arff");
//        Instances collections = loadDataPer("ICRF-ARFF/collections.arff");
//        Instances io = loadDataPer("ICRF-ARFF/io.arff");
//        Instances jsoup = loadDataPer("ICRF-ARFF/jsoup.arff");
//        Instances jsqlparser = loadDataPer("ICRF-ARFF/jsqlparser.arff");
//        Instances mango = loadDataPer("ICRF-ARFF/mango.arff");
//        Instances ormlite = loadDataPer("ICRF-ARFF/ormlite.arff");
//
//        List <Instances> insList = new ArrayList<>();
//        insList.add(codec);
//        insList.add(collections);
//        insList.add(io);
//        insList.add(jsoup);
//        insList.add(jsqlparser);
//        insList.add(mango);
//        insList.add(ormlite);
//
//        return insList;
    	Instances dataclass = loadDataPer("datasets/Code/DataClass.arff");
    	Instances featureenvy = loadDataPer("datasets/Code/FeatureEnvy.arff");
    	Instances godclass = loadDataPer("datasets/Code/GodClass.arff");
    	Instances longmethod = loadDataPer("datasets/Code/LongMethod.arff");
    	Instances longparameterlist = loadDataPer("datasets/Code/LongParameterList.arff");
    	Instances switchstatements = loadDataPer("datasets/Code/SwitchStatements.arff");
    	
    	List <Instances> insList = new ArrayList<>();
    	insList.add(dataclass);
    	insList.add(featureenvy);
    	insList.add(godclass);
    	insList.add(longmethod);
    	insList.add(longparameterlist);
    	insList.add(switchstatements);
    	
    	return insList;
    }

    /**
     * Print the instances
     *
     * @param ins
     */
    public static void showIns(Instances ins) {
        System.out.println("---------------");
        for (int i = 0; i < ins.numInstances(); i++) {
            System.out.println(ins.instance(i));
        }
    }
    /**
     * csv2arff
     */
    public static void CSVtoARFF(String csvfilename, String arfffilename) {
        try {
            // load CSV
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(csvfilename));
            Instances data = loader.getDataSet();

            // save ARFF
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File(arfffilename));
            //saver.setDestination(new File(arfffilename));
            saver.writeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
    	
    	CSVtoARFF("datasets/preprocessed/DataClass.csv","datasets/Code2/DataClass.arff");
    	CSVtoARFF("datasets/preprocessed/FeatureEnvy.csv","datasets/Code2/FeatureEnvy.arff");
    	CSVtoARFF("datasets/preprocessed/GodClass.csv","datasets/Code2/GodClass.arff");
    	CSVtoARFF("datasets/preprocessed/LongMethod.csv","datasets/Code2/LongMethod.arff");
    	CSVtoARFF("datasets/preprocessed/LongParameterList.csv","datasets/Code2/LongParameterList.arff");
    	CSVtoARFF("datasets/preprocessed/SwitchStatements.csv","datasets/Code2/SwitchStatements.arff");
    	
    	
    	
    	
//        // load data
//        Instances ins = null;
//
//        File file = new File("datasets/ICRF-ARFF/codec.arff");
//
//        ArffLoader loader = new ArffLoader();
//        loader.setFile(file);
//        ins = loader.getDataSet();
//        ins.setClassIndex(ins.numAttributes() - 1);
//
//        List<Instances> instancesList = filterSpecificInstances(ins, SuperParameter.positiveClassValue);
//
//        TrainTestDataSet trainTestDataSet = splitDataSet(instancesList);
//
//        Instances train = normalizeAndNominalData(trainTestDataSet.getTrain());
//        Instances test = normalizeAndNominalData(trainTestDataSet.getTest());
//
//        System.out.println(ins.relationName());

        //applyFeatureRankingClassifier(train, test);
        //applySubSetSelectionClassifier(train, test);
        //applyNoneFeatureClassifier(train, test);

    }

}
