import json
import os
from Processing import Processing
from sklearn import preprocessing
import math
from scipy.io import arff
import numpy as np
import pandas as pd
from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC
from sklearn.model_selection import KFold
from sklearn.metrics import *

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
                             'CARTGS', 'RFBF', 'RFGS', 'MLPBF',
                             'MLPGS', 'SVMBF', 'SVMGS', 'PCA', 'NONE']

# 分类器名称
classifiers = ['NB', 'LR', 'MLP', 'CART', 'SVM', 'NN', 'RF']

Datasets = ['LongParameterList', 'SwitchStatements', 'FeatureEnvy', 'LongMethod', 'GodClass', 'DataClass']

Measures = ['precision', 'recall', 'f1', 'mcc', 'auc']

header = ["fold", "dataset", "FeatureMethod", "Classifier", "precision", "recall", "f1", "mcc", "auc"]

pred_header = ["fold", "dataset", "FeatureMethod", "Classifier", "real_y", "pred_y"]

np.seterr(divide='ignore', invalid='ignore')


def load(path):
    return json.load(open(path, 'r'))


def load_dataset(data_name):
    path = 'D:/Mining/IST-2021/datasets/Code/' + data_name + '.arff'
    data = arff.loadarff(path)
    df = pd.DataFrame(data[0])
    class_type = len(data[1].names()) - 1
    return df, class_type


def select_features(df, class_type, features):
    if class_type not in features:
        features.append(class_type)
    # 替换最后一列： FLASE->0, TRUE->1
    df = df.replace(bytes('FALSE', encoding='utf-8'), 0)
    df = df.replace(bytes('TRUE', encoding='utf-8'), 1)
    # 归一化
    processed = preprocessing.MinMaxScaler().fit_transform(df.iloc[:, features].values)
    return processed


def F_S_Abbreviate(class_type, f_s_index):
    F_S = {}
    for f_s in f_s_index:
        featureselect_name = Feature_Select_Abbreviate[Feature_Select.index(f_s)]
        features = f_s_index[f_s]
        F_S[featureselect_name] = features
    F_S['NONE'] = [i for i in range(class_type + 1)]
    return F_S


def Classification(classifier):
    # 朴素贝叶斯
    if classifier == classifiers[0]:
        clf = MultinomialNB()
    # 逻辑回归
    elif classifier == classifiers[1]:
        clf = LogisticRegression()
    # 多层感知器
    elif classifier == classifiers[2]:
        clf = MLPClassifier(max_iter=2000)
    # cart
    elif classifier == classifiers[3]:
        clf = DecisionTreeClassifier()
    # svm
    elif classifier == classifiers[4]:
        clf = SVC()
    # k近邻
    elif classifier == classifiers[5]:
        clf = KNeighborsClassifier()
    # 随机森林
    else:
        clf = RandomForestClassifier()

    return clf


def K_fold(k, data):
    train_index = []
    test_index = []
    kf = KFold(n_splits=k)
    for train_i, test_i in kf.split(data):
        train_index.append(train_i)
        test_index.append(test_i)
    return train_index, test_index


def measure(test_y, clf_pred):
    # matrix = confusion_matrix(test_y, clf_pred)
    #
    # tn = matrix[0][0]  # 预测0，实际0
    # fp = matrix[0][1]  # 预测1，实际0
    # fn = matrix[1][0]  # 预测0. 实际1
    # tp = matrix[1][1]  # 预测1，实际1
    #
    # precision = tp / (tp + fp)
    # if np.isnan(precision):
    #     precision = 0.0
    #
    # recall = tp / (tp + fn)
    # if np.isnan(recall):
    #     recall = 0.0
    #
    # f1 = 2 * recall * precision / (recall + precision)
    # if np.isnan(f1):
    #     f1 = 0.0
    #
    # tmp_mcc = (tp + fn) * (tp + fp) * (fn + tn) * (fp + tn)
    # mcc = (tp * tn - fn * fp) / math.sqrt(tmp_mcc)
    # if np.isnan(mcc):
    #     mcc = 0.0
    # auc = roc_auc_score(test_y, clf_pred)

    precision = precision_score(test_y, clf_pred, average='weighted')
    recall = recall_score(test_y, clf_pred, average='weighted')
    f1 = f1_score(test_y, clf_pred, average='weighted')
    auc = roc_auc_score(test_y, clf_pred, average='weighted')
    mcc = matthews_corrcoef(test_y, clf_pred)

    return precision, recall, f1, mcc, auc


def process(pred_result, resultlist, K, f_s_index, data_name):
    # 加载数据集
    df, class_type = load_dataset(data_name)
    # 切分数据集
    train_index, test_index = K_fold(K, df)
    # 读取 特征选择：[]
    F_S_A = F_S_Abbreviate(class_type, f_s_index)
    # k折交叉检验
    for i in range(K):
        # 根据各特征选择方法处理数据集并训练分类器
        for f_s in F_S_A.keys():
            # 处理数据集
            prcessed_data = np.array(select_features(df, class_type, F_S_A[f_s]))
            # 训练集和测试集
            train_x, train_y = prcessed_data[train_index[i], :-1].tolist(), prcessed_data[train_index[i], -1].tolist()
            test_x, test_y = prcessed_data[test_index[i], :-1].tolist(), prcessed_data[test_index[i], -1].tolist()
            # 训练分类模型
            for classifier_name in classifiers:
                # print(str(i) + '-' + data_name + '-' + f_s + '-' + classifier_name)
                clf = Classification(classifier_name)
                clf_pred = clf.fit(train_x, train_y).predict(test_x)
                precision, recall, f1, mcc, auc = measure(test_y, clf_pred)

                Result = []

                Result.append(i)
                Result.append(data_name)
                Result.append(f_s)
                Result.append(classifier_name)
                Result.append(precision)
                Result.append(recall)
                Result.append(f1)
                Result.append(mcc)
                Result.append(auc)

                resultlist.append(Result)

                r = {
                    pred_header[0]: i,
                    pred_header[1]: data_name,
                    pred_header[2]: f_s,
                    pred_header[3]: classifier_name,
                    pred_header[4]: test_y,
                    pred_header[5]: clf_pred.tolist()
                }

                pred_result.append(r)


# 取平均值做最终结果
def K_Average():
    path = 'D:/Mining/IST-2021/AllResult/Allresult1.xlsx'
    data = pd.read_excel(path)
    resultlist = []
    headers = ["dataset", "FeatureMethod", "Classifier", "precision", "recall", "f1", "mcc", "auc"]
    resultlist.append(headers)
    for d in Datasets:
        for f in Feature_Select_Abbreviate:
            for c in classifiers:
                precision_a = data.loc[(data['dataset'] == d) & (data['FeatureMethod'] == f) & (
                        data['Classifier'] == c), 'precision'].mean()
                recall_a = data.loc[(data['dataset'] == d) & (data['FeatureMethod'] == f) & (
                        data['Classifier'] == c), 'recall'].mean()
                f1_a = data.loc[(data['dataset'] == d) & (data['FeatureMethod'] == f) & (
                        data['Classifier'] == c), 'f1'].mean()
                mcc_a = data.loc[(data['dataset'] == d) & (data['FeatureMethod'] == f) & (
                        data['Classifier'] == c), 'mcc'].mean()
                auc_a = data.loc[(data['dataset'] == d) & (data['FeatureMethod'] == f) & (
                        data['Classifier'] == c), 'auc'].mean()

                Result = []

                Result.append(d)
                Result.append(f)
                Result.append(c)
                Result.append(precision_a)
                Result.append(recall_a)
                Result.append(f1_a)
                Result.append(mcc_a)
                Result.append(auc_a)

                resultlist.append(Result)

    result_path = os.path.join(os.path.dirname(os.getcwd()), "AllResult")
    result_csv_name = "Allresult.xlsx"
    result_path = os.path.join(result_path, result_csv_name)
    Processing().write_excel(result_path, resultlist)


if __name__ == '__main__':
    path = 'D:/Mining/IST-2021/result'
    pathDir = os.listdir(path)

    K = 5
    resultlist = []
    resultlist.append(header)

    pred_result = []

    for allDir in pathDir:
        child = os.path.join('%s/%s' % (path, allDir))
        if os.path.isfile(child):
            data = load(child)
            process(pred_result, resultlist, K, data, allDir.replace('.json', ''))

    result_path = os.path.join(os.path.dirname(os.getcwd()), "AllResult")
    result_csv_name = "Allresult1.xlsx"
    result_path = os.path.join(result_path, result_csv_name)
    Processing().write_excel(result_path, resultlist)

    K_Average()

    # 保存预测结果到.json中
    with open('D:/Mining/IST-2021/M_test/All_pred_result.json', 'w') as f:
        f.write(json.dumps(pred_result))
    f.close()
