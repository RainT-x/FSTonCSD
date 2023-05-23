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

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.type.TypeReference;

import java.util.HashMap;
import java.util.Iterator;
import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import cn.zitsong.whu.utils.JsonUtil;
import cn.zitsong.whu.bean.AlgorithmResultOnce;
import cn.zitsong.whu.bean.AlgorithmResultAll;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * @Description: The construction of our algorithm
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:07
 * @Since: version 1.0.0
 **/
public class Algorithm {

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
    private static AttributeSelectedClassifier getAttributeSelectedClassifier(ASEvaluation asEvaluation, ASSearch asSearch, Classifier classifier) {

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
    private static Measure getMeasure(Classifier classifier, Instances instances) throws Exception {
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(classifier, instances, 10, new Random());
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
//    private static Measure getMeasureNone(Classifier classifier, Instances train, Instances test) throws Exception {
//        Evaluation evaluation = new Evaluation(train);
//        classifier.buildClassifier(train);
//        evaluation.evaluateModel(classifier, test);
//        Measure measure = new Measure(evaluation);
//        measure.buildMeasure();
//        return measure;
//    }

    /**
     * FeatureRanking + Classifier
     *
     * @param train
     * @param test
     * @throws Exception
     */
    public static List<AlgorithmResultOnce> applyFeatureRankingClassifier(Instances train, Instances test, String prefix) throws Exception {

        Method[] featureSelectionMethods = FilterFeatureRankingTechnique.class.getDeclaredMethods();
        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();

        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method featureSelectionMethod: featureSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) featureSelectionMethod.invoke(null);
            Ranker ranker = rankSearch(train);

//            List<Instances> instances = selectFeature(evaluation, ranker, train, test);
//
//            Instances trainIns = instances.get(0);
//            Instances testIns = instances.get(1);
//
//            // select the same feature for test data set
//            //Instances newTestIns = selectSameFeatureFromTrain(train, test);
//
//            for (Method classificationMethod: classificationMethods) {
//                Classifier classifier = (Classifier) classificationMethod.invoke(null);
//                //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, ranker, classifier);
//                //Measure measure = getMeasure(asClassifier, train, test);
//
//                Measure measure = getMeasure(classifier, trainIns);
//
//                AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
//                String name = featureSelectionMethod.getName() + '-' + classificationMethod.getName();
//                algorithmResultOnce.setAlgorithm(name);
//                algorithmResultOnce.setMeasure(measure);
//                algorithmResultOnceList.add(algorithmResultOnce);
//
//                name = prefix + name;
//
//                System.out.println(name + ":" + measure.toString());
//            }
            
            List<Integer> attributesLists = selectFeature(evaluation, ranker, train, test);
            
            AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
            String name = featureSelectionMethod.getName();
            algorithmResultOnce.setAlgorithm(name);
            algorithmResultOnce.setFeatures(attributesLists);
            algorithmResultOnceList.add(algorithmResultOnce);
                     
        }

        return algorithmResultOnceList;
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

//                List<Instances> instances = selectFeature(evaluation, search, train, test);
//
//                Instances trainIns = instances.get(0);
//                Instances testIns = instances.get(1);
//
//                // Instances newTestIns = selectSameFeatureFromTrain(train, test);
//
//                for (Method classificationMethod : classificationMethods) {
//                    Classifier classifier = (Classifier) classificationMethod.invoke(null);
//                    //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, search, classifier);
//                    //Measure measure = getMeasure(asClassifier, train, test);
//
//                    Measure measure = getMeasure(classifier, trainIns);
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
//////                    System.out.println(name + ":" + "[" + trainIns + "]"+" "+"["+testIns+"]");
//                }
                
                List<Integer> attributesLists = selectFeature(evaluation, search, train, test);
                
                AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
                String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName();
                algorithmResultOnce.setAlgorithm(name);
                algorithmResultOnce.setFeatures(attributesLists);
                algorithmResultOnceList.add(algorithmResultOnce);
                
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

    public static List<AlgorithmResultOnce> applyWrapperSubSetSelectionClassifier(Instances train, Instances test, String prefix) throws Exception {

        Method[] subSetSelectionMethods = WrapperSubsetSelectionTechnique.class.getDeclaredMethods();
        Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
        Method[] searchMethods = SearchMethod.class.getDeclaredMethods();

        List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();

        for (Method subSetSelectionMethod: subSetSelectionMethods) {
            ASEvaluation evaluation = (ASEvaluation) subSetSelectionMethod.invoke(null);
            for(Method searchMethod: searchMethods) {
                ASSearch search = (ASSearch) searchMethod.invoke(null);

//                List<Instances> instances = selectFeature(evaluation, search, train, test);
//
//                Instances trainIns = instances.get(0);
//                Instances testIns = instances.get(1);
//
//                // select the same feature for test data set
//                //Instances newTestIns = selectSameFeatureFromTrain(train, test);
//
//                for (Method classificationMethod: classificationMethods) {
//
//                    Classifier classifier = (Classifier) classificationMethod.invoke(null);
//                   
////                    String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName() + "-" + classificationMethod.getName();
////                    String name1 = prefix + name;
////
////                    System.out.println(name1);
//                    //classifier.buildClassifier(instances);
//
//                    //AttributeSelectedClassifier asClassifier = getAttributeSelectedClassifier(evaluation, search, classifier);
//                    Measure measure = getMeasure(classifier, trainIns);
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
                List<Integer> attributesLists = selectFeature(evaluation, search, train, test);
                
                AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
                String name = subSetSelectionMethod.getName() + '-' + searchMethod.getName();
                algorithmResultOnce.setAlgorithm(name);
                algorithmResultOnce.setFeatures(attributesLists);
                algorithmResultOnceList.add(algorithmResultOnce);
                
            }

        }

        return algorithmResultOnceList;
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
//    private static List<Instances> selectFeature(ASEvaluation m_Evaluator, ASSearch search, Instances instances, Instances test) throws Exception {
    private static List<Integer> selectFeature(ASEvaluation m_Evaluator, ASSearch search, Instances instances, Instances test) throws Exception {
        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setEvaluator(m_Evaluator);
        attributeSelection.setSearch(search);
        attributeSelection.SelectAttributes(instances);

        int[] attributesIdx = attributeSelection.selectedAttributes();
//        System.out.println(attributesIdx);

        //Instances newIns = attributeSelection.reduceDimensionality(instances);

        List<Integer> attributesLists = new ArrayList<>();
        
        //如果无特征被选择，则随机保留一个特征
        if(attributesIdx.length < 2) {
        	Random random = new Random();
        	int r_feature = random.nextInt(instances.numAttributes() - 1) + 1;
        	attributesLists.add(r_feature);
        }
        
        for(int i = 0; i < attributesIdx.length; i++) {
            attributesLists.add(attributesIdx[i]);
        }
//        System.out.println(attributesLists);
//        Instances newIns = new Instances(instances);
//        for(int i = newIns.numAttributes() - 1; i > -1; i--){    //delete from back to forward
//            if(!attributesLists.contains(i)&&i!=instances.classIndex())
//                newIns.deleteAttributeAt(i);
//        }
//
//        //System.out.println(newIns);
//
//        Instances newTestIns = new Instances(test);
//        for(int i = newTestIns.numAttributes() - 1; i > -1; i--){    //delete from back to forward
//            if(!attributesLists.contains(i)&&i!=test.classIndex())
//                newTestIns.deleteAttributeAt(i);
//        }
//        //System.out.println("=====================================================================");
//        //System.out.println(newTestIns);
//
//        List<Instances> trainTestIns = new ArrayList<>();
//        trainTestIns.add(newIns);
//        trainTestIns.add(newTestIns);
//        return trainTestIns;
        return attributesLists;
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
//    private static Measure getMeasure(Classifier classifier, Instances train, Instances test) throws Exception {
//        Evaluation evaluation = new Evaluation(train);
//        classifier.buildClassifier(train);
//        evaluation.evaluateModel(classifier, test);
//        Measure measure = new Measure(evaluation);
//        measure.buildMeasure();
//        return measure;
//    }

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
            Measure measure = getMeasure(classifier, train);

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
    /*
     * fisherscore,mic,var + classifier
     */
    public static List<AlgorithmResultOnce> applypythonFeatureClassifier(Instances train, Instances test, String dataname) throws Exception {
    	Method[] classificationMethods = ClassificationTechnique.class.getDeclaredMethods();
    	List<AlgorithmResultOnce> algorithmResultOnceList = new ArrayList<>();
    	String path = "feature/" +dataname+".txt";
		File file = new File(path);
		HashMap<String,List<Integer>>features = new HashMap();
		if(file.isFile() && file.exists()){ //判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(file));//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
    		while((lineTxt = bufferedReader.readLine()) != null){
    			List<Integer> vl = new ArrayList<>();
    			String[] tmp = lineTxt.split(":");
    			tmp[1] = tmp[1].replace("[", "");
    			tmp[1] = tmp[1].replace(" ", "");
    			tmp[1] = tmp[1].replace("]", "");
    			String[] v = tmp[1].split(",");
    			for(String i : v) {
    				vl.add(Integer.valueOf(i));
    			}
    			features.put(tmp[0],vl);
    		}
    	}else{
    			System.out.println("找不到指定的文件");
    		}
		//读取选取的特征值修改训练集
		Iterator entInfo = features.entrySet().iterator();
		while (entInfo.hasNext()){
            Map.Entry<String,List<Integer>> entry = (Map.Entry) entInfo.next();
            String key = entry.getKey();
            List<Integer> value = entry.getValue();
            Instances newIns = new Instances(train);
            for(int i = newIns.numAttributes() - 1; i > -1; i--){    //delete from back to forward
                if(!value.contains(i)&&i!=train.classIndex())
                    newIns.deleteAttributeAt(i);
            }
            for (Method classificationMethod: classificationMethods) {
                Classifier classifier = (Classifier) classificationMethod.invoke(null);
                Measure measure = getMeasure(classifier, newIns);

                AlgorithmResultOnce algorithmResultOnce = new AlgorithmResultOnce();
                String name = key + "-" + classificationMethod.getName();
                algorithmResultOnce.setAlgorithm(name);
                algorithmResultOnce.setMeasure(measure);
                algorithmResultOnceList.add(algorithmResultOnce);

                name = dataname + "-" + name;

                System.out.println(name + ":" + measure.toString());
           }
        }
		
	
    	return algorithmResultOnceList;
    }

    /*
     * 转换json结果
     */
    public static void js_processing(String str,List<AlgorithmResultAll> t) {
    	File file = new File(str);
    	ObjectMapper objectMapper = new ObjectMapper();
    	List<AlgorithmResultAll> aList = new ArrayList<>();
    	AlgorithmResultAll all = new AlgorithmResultAll();
		try {
			aList = objectMapper.readValue(file, ArrayList.class);
			String s  = JsonUtil.object2String(aList);
			System.out.println(s);
			TypeReference T = new TypeReference<List<AlgorithmResultAll>>() {};
//			System.out.println(aList +"\n");
//			System.out.println(aList.get(0));
			List<AlgorithmResultOnce> once = new ArrayList<>();
			System.out.println(JsonUtil.string2Object(s, T));
			
//			for(AlgorithmResultOnce o:once) {
//				System.out.println(o.getAlgorithm()+":"+o.getMeasure());
//			}
			
			
//			HashMap<String,Measure> hash=new HashMap<String,Measure>();
//			for(AlgorithmResultAll all:aList) {
//				for(AlgorithmResultOnce once:all.getAlgorithmResultOnceList()) {
////					hash.put(once.getAlgorithm(), once.getMeasure());
//					System.out.println(once.getAlgorithm()+":"+once.getMeasure());
//				}
//			}
//			String json = JsonUtil.object2String(hash);
//			File rfile = new File(str);
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rfile), "UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Search for Feature Ranking
     *
     * @return
     */
    private static Ranker rankSearch(Instances ins) {
        Ranker ranker = new Ranker();
        int N = ins.numAttributes();
        int feature_num = (int)Math.floor(Math.log(N) / Math.log(2));
        ranker.setNumToSelect(feature_num);
        return ranker;
    }

    public static void main(String[] args) throws Exception {

        // load data
//        Instances ins1 = null;
//        Instances ins2 = null;
//
//         File file = new File("datasets/Code/DataClass.arff");
////        File file1 = new File("D:\\Mining\\EASC\\ArffData\\train.arff");
////        File file2 = new File("D:\\Mining\\EASC\\ArffData\\test.arff");
//
//        ArffLoader loader1 = new ArffLoader();
//        loader1.setFile(file);
//        ins1 = loader1.getDataSet();
//        ins1.setClassIndex(ins1.numAttributes() - 1);
//        
//        ArffLoader loader2 = new ArffLoader();
//        loader2.setFile(file);
//        ins2 = loader2.getDataSet();
//        ins2.setClassIndex(ins2.numAttributes() - 1);
//        
//        Instances train = new Instances(ins1);
//        Instances test = new Instances(ins2);
//
////        Instances train = new Instances(ins, 0, ins.size() / 2);
////        Instances test = new Instances(ins, ins.size() / 2, ins.size() / 2);
//        train = DataProcess.normalizeAndNominalData(train);
//        test = DataProcess.normalizeAndNominalData(test);
//
//        // chiSquareNB(ins);
//        // applyFeatureRankingClassifier(train, test, "test");
////        applySubSetSelectionClassifier(train, train, "test");
//        // applyWrapperSubSetSelectionClassifier(train, test, "test");
//        applypythonFeatureClassifier(train,train,"DataClass");
//    	String path = "SwitchStatements.json";
//    	List<AlgorithmResultAll> t = new ArrayList<>();
//    	js_processing(path,t);

    }


}
