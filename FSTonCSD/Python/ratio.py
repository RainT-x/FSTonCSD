import json
import os
from scipy.io import arff
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import ticker

BestFeatureSelection = ['chiSquare', 'probabilisticSignificance', 'infoGain', 'symmetrical']

BestFeatureSelection_A = ['CS', 'PS', 'IG', 'SU']


def load(path):
    data = json.load(open(path, 'r'))
    data = dict([(key, data[key]) for key in BestFeatureSelection])
    return data


def load_dataset(data_name):
    path = 'D:/Mining/IST-2021/datasets/Code/' + data_name + '.arff'
    data = arff.loadarff(path)
    feature_names = data[1].names()
    return feature_names


def count_ratio(data, names):
    all_list = [0 for x in range(len(names))]
    for i in data:
        for feature in data[i]:
            all_list[feature] += 1
    # 删除最后的类标签列
    del (all_list[-1])
    del (names[-1])

    # 除以特征选择方法数得到比例
    all_list = [(i / len(data)) for i in all_list]
    all_list = np.array(all_list)
    names = np.array(names)
    # 降序
    index = np.argsort(all_list)
    index = index[::-1]
    all_list = all_list[index]
    names = names[index]

    # 除掉从未被选择的特征
    pro_list = []
    pro_names = []
    for i in range(len(names)):
        if all_list[i] != 0:
            pro_list.append(all_list[i])
            n = names[i]
            pro_names.append(n)
    pro_list = np.array(pro_list)
    pro_names = np.array(pro_names)
    return pro_list, pro_names


def draw(datasets_name, list, names):
    f, ax = plt.subplots(figsize=(15, 20))
    ax.bar(names, list, width= 0.7, color = 'cornflowerblue')
    ax.spines['right'].set_visible(False)
    ax.spines['top'].set_visible(False)

    font = {'family': 'Times New Roman',
            'weight': 'normal',
            'size': 20,
            }
    ax.set_xlabel('Features', fontdict=font)
    ax.set_ylabel('Usage', fontdict=font)

    plt.xticks(fontproperties='Times New Roman', size=20, rotation=60)
    plt.yticks(fontproperties='Times New Roman', size=20)

    ax.yaxis.set_major_formatter(ticker.PercentFormatter(xmax=1))

    plt.tight_layout()
    f.savefig('D:/Mining/IST-2021/ratio/' + datasets_name + '.png')
    # plt.close()
    # plt.show()


def write_features(result, data, name):
    for i in range(4):
        r = []
        name_list = []
        for idx in data[BestFeatureSelection[i]]:
            n = name[idx]
            name_list.append(n)
        r.append(BestFeatureSelection_A[i])
        r.append(name_list)
        result.append(r)


def save_excel(result, dataset_name):
    import xlwt
    wbk = xlwt.Workbook()
    sheet = wbk.add_sheet("test")
    i = 0
    for netted in result:
        for x, item in enumerate(netted):
            sheet.write(i, x, item)
        i += 1
    wbk.save('D:/Mining/IST-2021/ratio/'+dataset_name+'.xlsx')


if __name__ == '__main__':
    path = 'D:/Mining/IST-2021/result'
    pathDir = os.listdir(path)

    for allDir in pathDir:
        child = os.path.join('%s/%s' % (path, allDir))
        if os.path.isfile(child):
            data = load(child)
            feature_names = load_dataset(allDir.replace('.json', ''))

            re = []
            h = ['Methods', 'Features']
            re.append(h)
            write_features(re, data, feature_names)
            save_excel(re, allDir.replace('.json', ''))

            l, n_l = count_ratio(data, feature_names)
            draw(allDir.replace('.json', ''), l, n_l)
