package cn.zitsong.whu;

import cn.zitsong.whu.bean.AlgorithmResultAll;
import cn.zitsong.whu.bean.AlgorithmResultOnce;
import cn.zitsong.whu.bean.TrainTestDataSet;
import cn.zitsong.whu.constant.SuperParameter;
import cn.zitsong.whu.utils.JsonUtil;
import weka.core.Instances;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.zitsong.whu.algorithm.Algorithm.*;
import static cn.zitsong.whu.algorithm.DataProcess.*;
import static cn.zitsong.whu.constant.SuperParameter.STEPS;
import static cn.zitsong.whu.constant.SuperParameter.numflods;

import java.util.Random;
import java.util.HashMap;
import cn.zitsong.whu.utils.Measure;

public class Main {

	public static void main(String[] args) throws Exception {

//		CSVtoARFF("datasets/Code-smell/DataClass.csv","datasets/Code/DataClass.arff");
//		CSVtoARFF("datasets/Code-smell/FeatureEnvy.csv","datasets/Code/FeatureEnvy.arff");
//		CSVtoARFF("datasets/Code-smell/GodClass.csv","datasets/Code/GodClass.arff");
//		CSVtoARFF("datasets/Code-smell/LongMethod.csv","datasets/Code/LongMethod.arff");
//		CSVtoARFF("datasets/Code-smell/LongParameterList.csv","datasets/Code/LongParameterList.arff");
//		CSVtoARFF("datasets/Code-smell/SwitchStatements.csv","datasets/Code/SwitchStatements.arff");
		
//		System.out.println("start time is " + new Date().toString());
//		
//		// load data
//		List<Instances> dataList = loadDataAll();
//
//		for(Instances data: dataList) {
//			
//			List<AlgorithmResultAll> algorithmResultAllList = new ArrayList<>();
//
//			// precessing the data
//			String name = data.relationName();
//			data = normalizeAndNominalData(data);
//			data.setRelationName(name);
////			data.randomize(new Random());
//			data.stratify(numflods);
//			int step = 0;
//			while(step < numflods) {
//					
//				try {
//
//					String prefix = data.relationName() + "-" + (step + 1) + "-";
//					
//					Instances train = data.trainCV(numflods, step);
//					Instances test = data.testCV(numflods, step);
//	
//					// call the algorithm
//					List<AlgorithmResultOnce> nfcList = applyNoneFeatureClassifier(train, test, prefix);
//					List<AlgorithmResultOnce> frcList = applyFeatureRankingClassifier(train, test, prefix);
//					List<AlgorithmResultOnce> sscList = applySubSetSelectionClassifier(train, test, prefix);
//					List<AlgorithmResultOnce> wscList = applyWrapperSubSetSelectionClassifier(train, test, prefix);
//	
//					List<AlgorithmResultOnce> allList = new ArrayList<>();
//					allList.addAll(nfcList);
//					allList.addAll(frcList);
//					allList.addAll(sscList);
////					allList.addAll(wscList);
//	
//					AlgorithmResultAll algorithmResultAll = new AlgorithmResultAll();
//					algorithmResultAll.setStepSet("" + (step + 1));
//					algorithmResultAll.setAlgorithmResultOnceList(allList);
//	
//					algorithmResultAllList.add(algorithmResultAll);
//					
//	
//				} catch (Exception e) {
//					e.printStackTrace();
//					step--;
//					break;
//				} finally {
//					step++;
//				}
//			}
//			
//			HashMap<String,Measure> hash=new HashMap<String,Measure>();
//			for(AlgorithmResultAll item:algorithmResultAllList) {
//				for(AlgorithmResultOnce once:item.getAlgorithmResultOnceList()) {
//					String key = once.getAlgorithm();
//					Measure value = once.getMeasure();
//					if(!hash.containsKey(key)) {
//						value.auc *= 0.1;
//						value.f1 *= 0.1;
//						value.mcc *= 0.1;
//						value.precision *= 0.1;
//						value.recall *= 0.1;
//						hash.put(key,value);
//					}
//					else {
//						Measure tmp = hash.get(key);
//						tmp.auc += value.auc * 0.1;
//						tmp.f1 += value.f1 * 0.1;
//						tmp.mcc += value.mcc * 0.1;
//						tmp.precision += value.precision * 0.1;
//						tmp.recall += value.recall * 0.1;
//						
//						hash.put(key, tmp);
//					}
//				}
//			}
//			
//			// save result to json
//			System.out.println("save json file start");
//			String json = JsonUtil.object2String(hash);
////			String json = JsonUtil.object2String(algorithmResultAllList);
//			
//			File file = new File(data.relationName() + "-step.json");
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//
//			try {
//				writer.write(json);
//				System.out.println("save json file success");
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("save json file fail");
//			} finally {
//				writer.close();
//			}
//
//			System.out.println("end time is " + new Date().toString());
//			break;
//		}
//	}
		
		
//		// String rootPath = args[0];
//
		System.out.println("start time is " + new Date().toString());

		// load data
		List<Instances> dataList = loadDataAll();

		int step = 0;
		while (step != STEPS) {

			try {
//				List<AlgorithmResultAll> algorithmResultAllList = new ArrayList<>();

				for(Instances data: dataList) {

					// precessing the data
//					List<Instances> instancesList = filterSpecificInstances(data, SuperParameter.positiveClassValue);
//
//					TrainTestDataSet trainTestDataSet = splitDataSet(instancesList);
//
//					Instances train = normalizeAndNominalData(trainTestDataSet.getTrain());
//					Instances test = normalizeAndNominalData(trainTestDataSet.getTest());
					String name = data.relationName();
					data = normalizeAndNominalData(data);
					data.setRelationName(name);
//					System.out.println(data);

					String prefix = name + "-" ;

					// call the algorithm
//					List<AlgorithmResultOnce> nfcList = applyNoneFeatureClassifier(data, data, prefix);
//					List<AlgorithmResultOnce> frcList = applyFeatureRankingClassifier(data, data, prefix);
//					List<AlgorithmResultOnce> pfcList = applypythonFeatureClassifier(data, data, data.relationName());
//					List<AlgorithmResultOnce> sscList = applySubSetSelectionClassifier(data, data, prefix);
					List<AlgorithmResultOnce> wscList = applyWrapperSubSetSelectionClassifier(data, data, prefix);

					List<AlgorithmResultOnce> allList = new ArrayList<>();
//					allList.addAll(nfcList);
//					allList.addAll(frcList); 
//					allList.addAll(pfcList);
//					allList.addAll(sscList);
					allList.addAll(wscList);

//					AlgorithmResultAll algorithmResultAll = new AlgorithmResultAll();
//					algorithmResultAll.setStepSet(data.relationName());
//					algorithmResultAll.setAlgorithmResultOnceList(allList);

//					algorithmResultAllList.add(algorithmResultAll);
					
					
//					HashMap<String,Measure> hash=new HashMap<String,Measure>();
					HashMap<String,List<Integer>> hash=new HashMap<String,List<Integer>>();
					
					for(AlgorithmResultOnce once:allList) {
//						hash.put(once.getAlgorithm(), once.getMeasure());
						hash.put(once.getAlgorithm(), once.getFeatures());
					}
					
					// save result to json
					System.out.println("save json file start");
//					String json = JsonUtil.object2String(algorithmResultAllList);
					String json = JsonUtil.object2String(hash);
					File file = new File( name + ".json");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./result/" + file), "UTF-8"));

					try {
						writer.write(json);
						System.out.println("save json file success");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("save json file fail");
					} finally {
						writer.close();
					}

					System.out.println("end time is " + new Date().toString());

				}

			} catch (Exception e) {
				e.printStackTrace();
				step--;
				break;
			} finally {
				step++;
			}

		}

		
		
		
		
/*
 * ÆäËû		
 */
//		for(Instances data: dataList) {
//
//			ResultVo result = new ResultVo();
//			result.setDataSet(data.relationName());
//
//			List<AlgorithmResultAll> algorithmResultAllList = new ArrayList<>();
//
//			for(int step = 0; step < STEPS; step++) {
//
//				// precessing the data
//				List<Instances> instancesList = filterSpecificInstances(data, SuperParameter.positiveClassValue);
//
//				TrainTestDataSet trainTestDataSet = splitDataSet(instancesList);
//
//				Instances train = normalizeAndNominalData(trainTestDataSet.getTrain());
//				Instances test = normalizeAndNominalData(trainTestDataSet.getTest());
//
//				String prefix = data.relationName() + "-" + (step + 1) + "-";
//
//				// call the algorithm
//				List<AlgorithmResultOnce> nfcList = applyNoneFeatureClassifier(train, test, prefix);
//				List<AlgorithmResultOnce> frcList = applyFeatureRankingClassifier(train, test, prefix);
//				List<AlgorithmResultOnce> sscList = applySubSetSelectionClassifier(train, test, prefix);
//				List<AlgorithmResultOnce> wscList = applyWrapperSubSetSelectionClassifier(train, test, prefix);
//
//				List<AlgorithmResultOnce> allList = new ArrayList<>();
//				allList.addAll(nfcList);
//				allList.addAll(frcList);
//				allList.addAll(sscList);
//				allList.addAll(wscList);
//
//				AlgorithmResultAll algorithmResultAll = new AlgorithmResultAll();
//				algorithmResultAll.setIndex(step);
//				algorithmResultAll.setAlgorithmResultOnceList(allList);
//
//				algorithmResultAllList.add(algorithmResultAll);
//			}
//
//			result.setData(algorithmResultAllList);
//
//			resultList.add(result);
//		}

		// save result to json
//		System.out.println("save json file start");
//		String json = JsonUtil.object2String(resultList);
//		File file = new File(SuperParameter.RESULT_PATH);
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

//		try {
//			writer.write(json);
//			System.out.println("save json file success");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("save json file fail");
//		} finally {
//			writer.close();
//		}
//
//		System.out.println("end time is " + new Date().toString());
//	}
	}
}
