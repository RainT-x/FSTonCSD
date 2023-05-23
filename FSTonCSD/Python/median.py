# 箱型图展示None方法下的七个分类器的中位数Precision, Recall, F1和MCC值
import math
from Processing import Processing
import numpy as np
import pandas as pd
from scipy.io import arff
from sklearn import preprocessing
from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC
from sklearn.model_selection import KFold
from sklearn.metrics import classification_report, confusion_matrix
from sklearn.metrics import *

classifiers = ['NB', 'LR', 'MLP', 'CART', 'SVM', 'NN', 'RF']
measure = ['precision', 'recall', 'f1', 'mcc', 'auc']
Datasets = ['LongParameterList', 'SwitchStatements', 'FeatureEnvy', 'LongMethod', 'GodClass', 'DataClass']


def load_dataset(log, data_name):
    if log == 'Origin':
        path = 'D:/Mining/IST-2021/datasets/Code_origin/' + data_name + '.arff'
    else:
        path  = 'D:/Mining/IST-2021/datasets/Code/' + data_name + '.arff'
    data = arff.loadarff(path)
    df = pd.DataFrame(data[0])
    return df


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
    #
    # auc = roc_auc_score(test_y, clf_pred)

    precision = precision_score(test_y, clf_pred, average='weighted')
    recall = recall_score(test_y, clf_pred, average='weighted')
    f1 = f1_score(test_y, clf_pred, average='weighted')
    auc = roc_auc_score(test_y, clf_pred, average='weighted')
    mcc = matthews_corrcoef(test_y, clf_pred)

    return precision, recall, f1, mcc, auc


def select_features(df):
    # 替换最后一列： FLASE->0, TRUE->1
    df = df.replace(bytes('FALSE', encoding='utf-8'), 0)
    df = df.replace(bytes('TRUE', encoding='utf-8'), 1)
    # 归一化
    processed = preprocessing.MinMaxScaler().fit_transform(df.values)
    return processed


def process(log, resultlist, K, data_name):
    # 加载数据集
    df = load_dataset(log, data_name)
    # 切分数据集
    train_index, test_index = K_fold(K, df)
    # k折交叉检验
    for i in range(K):
        # 处理数据集
        prcessed_data = np.array(select_features(df))
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

            Result.append(classifier_name)
            Result.append(precision)
            Result.append(recall)
            Result.append(f1)
            Result.append(mcc)
            Result.append(auc)

            resultlist.append(Result)


# 取平均值做最终结果
def K_Average(path):
    path_data = 'D:/Mining/IST-2021/Median/' + path + '_K.xlsx'
    data = pd.read_excel(path_data)
    resultlist = []
    headers = ["dataset", "Classifier", "precision", "recall", "f1", "mcc", "auc"]
    resultlist.append(headers)
    for d in Datasets:
        for c in classifiers:
            precision_a = data.loc[(data['dataset'] == d) & (
                    data['Classifier'] == c), 'precision'].median()
            recall_a = data.loc[(data['dataset'] == d) & (
                    data['Classifier'] == c), 'recall'].median()
            f1_a = data.loc[(data['dataset'] == d) & (
                    data['Classifier'] == c), 'f1'].median()
            mcc_a = data.loc[(data['dataset'] == d) & (
                    data['Classifier'] == c), 'mcc'].median()
            auc_a = data.loc[(data['dataset'] == d) & (
                    data['Classifier'] == c), 'auc'].median()

            Result = []

            Result.append(d)
            Result.append(c)
            Result.append(precision_a)
            Result.append(recall_a)
            Result.append(f1_a)
            Result.append(mcc_a)
            Result.append(auc_a)

            resultlist.append(Result)

    Processing().write_excel('D:/Mining/IST-2021/Median/' + path + '.xlsx', resultlist)


if __name__ == '__main__':
    resultlist = []
    header = ["fold", "dataset", "Classifier", "precision", "recall", "f1", "mcc", "auc"]
    resultlist.append(header)
    K = 5
    # path = 'Origin'
    path = 'Our'
    for name in Datasets:
        process(path, resultlist, K, name)

    Processing().write_excel('D:/Mining/IST-2021/Median/' + path + '_K.xlsx', resultlist)
    K_Average(path)
