from sklearn.feature_selection import SelectKBest, f_classif, VarianceThreshold, mutual_info_classif
import pandas as pd
import numpy as np
import math
import os
import json


def load(file_path):
    data = pd.read_csv(file_path)
    X = data.iloc[:, :-1]
    Y = data.iloc[:, -1]
    feature_num = X.shape[1]
    return X, Y, feature_num


# fisher score
def filter1(X, Y, num):
    return SelectKBest(f_classif, k=math.floor(math.log2(num))).fit(X, Y).get_support(True)


# mic
def filter2(X, Y, num):
    return SelectKBest(mutual_info_classif, k=math.floor(math.log2(num))).fit(X, Y).get_support(True)


# 方差
def filter3(X, Y, num):
    return np.argsort(VarianceThreshold().fit(X, Y).get_support(True))[::-1][:math.floor(math.log2(num))]


def filter(X, Y, num):
    f1 = filter1(X, Y, num)
    f2 = filter2(X, Y, num)
    f3 = filter3(X, Y, num)
    l = {'fisherscore': f1.tolist(), 'mic': f2.tolist(), 'variance': f3.tolist()}
    return l


def f_ids_to_txt(l, filename):
    with open('D:/Mining/IST-2021/feature/' + filename + '.json', 'w') as f:
        f.write(json.dumps(l))
    f.close()


if __name__ == '__main__':
    filepath = 'D:/Mining/IST-2021/datasets/Code-smell/'
    pathDir = os.listdir(filepath)
    for allDir in pathDir:
        child = os.path.join('%s/%s' % (filepath, allDir))
        if os.path.isfile(child):
            X, Y, num = load(child)
            l = filter(X, Y, num)
            f_ids_to_txt(l, allDir.split('.')[0])
