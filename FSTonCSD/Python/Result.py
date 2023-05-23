import pandas as pd
import os
from Processing import Processing

Feature_Select = ['chiSquare', 'correlation', 'clusteringVariation', 'variance', 'mic',
                  'fisherscore', 'probabilisticSignificance', 'infoGain', 'gainRatio',
                  'symmetrical', 'reliefF', 'reliefFWeight', 'oneR',
                  'svm', 'CfsSubset-bestFirst', 'CfsSubset-greedyStepwise', 'consistencySubset-bestFirst',
                  'consistencySubset-greedyStepwise', 'wrapperSubsetKNNAUC-bestFirst',
                  'wrapperSubsetKNNAUC-greedyStepwise', 'wrapperSubsetLRAUC-bestFirst',
                  'wrapperSubsetLRAUC-greedyStepwise', 'wrapperSubsetNBAUC-bestFirst',
                  'wrapperSubsetNBAUC-greedyStepwise', 'wrapperSubsetcartAUC-bestFirst',
                  'wrapperSubsetcartAUC-greedyStepwise', 'wrapperSubsetranfAUC-bestFirst',
                  'wrapperSubsetranfAUC-greedyStepwise', 'wrapperSubsetmlpAUC-bestFirst',
                  'wrapperSubsetmlpAUC-greedyStepwise', 'wrapperSubsetsvmAUC-bestFirst',
                  'wrapperSubsetsvmAUC-greedyStepwise', 'pca', 'None']
Feature_Select_Abbreviate = ['CS', 'CR', 'CV', 'VT', 'MIC',
                             'FS', 'PS', 'IG', 'GR',
                             'SU', 'ReF', 'RW', 'ORF',
                             'SVMF', 'CorBF', 'CorGS', 'ConBF',
                             'ConGS', 'NNBF', 'NNGS', 'LRBF',
                             'LRGS', 'NBBF', 'NBGS', 'CARTBF',
                             'CARTGS', 'RanDBF', 'RanDGS', 'MLPBF',
                             'MLPGS', 'SVMBF', 'SVMGS', 'PCA', 'NONE']
Classifier = ['naiveBayes', 'bayesianNetwork', 'logisticRegression', 'radialBasisFunction',
              'multilayerPerceptron', 'logisticModelTrees', 'cart', 'j48',
              'alternatingDecisionTrees', 'decisionStump', 'naiveBayesDecisionTree', 'randomTree',
              'ripper', 'oneRule', 'decisionTable', 'partialDecisionTrees',
              'rippleDownRules', 'knn', 'kStar', 'votedPerceptron',
              'randomForest']
Classifier_Abbreviate = ['NB', 'BN', 'LR', 'RBF',
                         'MLP', 'LMT', 'CART', 'J48',
                         'ADT', 'DS', 'NDT', 'RT',
                         'RIP', 'ORC', 'DT', 'PDT',
                         'RDR', 'NN', 'KS', 'SVM',
                         'RF']

header = ["dataset", "FeatureMethod", "Classifier", "precision", "recall", "f1", "mcc", "auc"]


def load(path):
    return pd.read_json(path)


def process(resultlist, data, data_name):
    for f_c in data.keys():
        fclist = f_c.split('-')
        classifier_name = fclist[-1]
        featureselect_name = f_c.replace('-' + classifier_name, '')
        # 缩写代替原名称
        featureselect_name = Feature_Select_Abbreviate[Feature_Select.index(featureselect_name)]
        classifier_name = Classifier_Abbreviate[Classifier.index(classifier_name)]
        precision = data[f_c]['precision']
        recall = data[f_c]['recall']
        f1 = data[f_c]['f1']
        mcc = data[f_c]['mcc']
        auc = data[f_c]['auc']
        Result = []

        Result.append(data_name)
        Result.append(featureselect_name)
        Result.append(classifier_name)
        Result.append(precision)
        Result.append(recall)
        Result.append(f1)
        Result.append(mcc)
        Result.append(auc)

        resultlist.append(Result)


if __name__ == '__main__':
    path = 'D:/Mining/IST-2021/result'
    pathDir = os.listdir(path)
    resultlist = []
    resultlist.append(header)
    for allDir in pathDir:
        child = os.path.join('%s/%s' % (path, allDir))
        if os.path.isfile(child):
            data = load(child)
            process(resultlist, data, allDir.replace('.json', ''))

    result_path = os.path.join(os.path.dirname(os.getcwd()), "AllResult")
    result_csv_name = "Allresult1.xlsx"
    result_path = os.path.join(result_path, result_csv_name)
    Processing().write_excel(result_path, resultlist)