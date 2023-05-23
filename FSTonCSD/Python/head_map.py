import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import pandas as pd
import os

plt.rc('font', family='Times New Roman')
classifier = ['NB', 'LR', 'MLP', 'CART', 'SVM', 'NN', 'RF']


def load_data(path):
    df = pd.read_excel(path)
    col_name = df.columns.tolist()[1:]
    row_name = df.iloc[:, 0].values
    df = df.fillna(df.mean())
    data = np.array(df.iloc[:, 1:].values).T
    return data, col_name, row_name


# 取分类器中7个
# def load_data(path):
#     df = pd.read_excel(path)
#     col_name = classifier
#     row_name = df.iloc[:, 0].values
#     df = df.fillna(df.mean())
#     data = np.array(df[classifier]).T
#     return data, col_name, row_name


def proc_bf(name):
    return '$\\bf{' + name + '}$'


def draw(excelpath):
    data, col_name, row_name = load_data(excelpath)
    # dataT = [list(row) for row in zip(*data)]

    # print (dataT)

    # col_name = [proc_bf(name) for name in row_name]
    # row_name = [proc_bf(name) for name in col_name]
    # proc_bf(
    # print('col_name', col_name)
    # print('row_name', row_name)

    f, ax = plt.subplots(figsize=(12, 4))
    # size指的每一个cell里面的值的字体大小,fmt设置精度
    # SKESD测试的结果画热力图时改fmt = 'd'
    # 指标值热力图fmt = '.2f'
    sns.heatmap(data, cmap='Reds', square=True, annot=True, cbar=False, fmt='d',
                annot_kws={'size': 10, 'color': 'black', 'family': 'Times New Roman'})

    # 下面的两行代码有个参数fontweight='bold'

    font = {'family': 'Times New Roman',
            'weight': 'normal',
            'size': 16,
            }

    plt.xticks(fontproperties='Times New Roman', size=16)
    # plt.yticks(fontproperties='Times New Roman', fontsize=16)
    # 数据集列标签字体调小，让Code Smells出现.
    plt.yticks(fontproperties='Times New Roman', fontsize=14)

    ax.set_xticklabels(row_name, rotation=90)
    ax.set_yticklabels(col_name, rotation=0)
    ax.set_xlabel('Feature Selection Techniques', fontdict=font)

    # ax.set_ylabel('Classifiers', fontdict=font)
    ax.set_ylabel('Code Smells', fontdict=font)
    plt.tight_layout()
    f.savefig(excelpath + '.png')


if __name__ == '__main__':
    #
    # 各指标值的热力图
    #
    # 画热力图需要的路径
    # 分类器+特征选择：/PictureData/FeatureMethod_Classifier
    # 数据集+特征选择：/PictureData/FeatureMethod_DataSet
    # folder_name1 = os.path.dirname(os.getcwd()) + '/PictureData/FeatureMethod_Classifier'
    # file_names1 = os.listdir(folder_name1)
    # for name1 in file_names1:
    #     file_name = folder_name1 + "/" + name1
    #     if file_name.endswith('xlsx'):
    #         draw(file_name)
    #
    # folder_name2 = os.path.dirname(os.getcwd()) + '/PictureData/FeatureMethod_DataSet'
    # file_names2 = os.listdir(folder_name2)
    # for name2 in file_names2:
    #     file_name = folder_name2 + "/" + name2
    #     if file_name.endswith('xlsx'):
    #         draw(file_name)

    # #
    # # SKESD测试热力图
    # #
    # # 画热力图需要的路径
    # # 分类器+特征选择：D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier
    # # 数据集+特征选择：D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet
    # folder_name = 'D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier'
    folder_name = 'D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet'
    file_names = os.listdir(folder_name)
    for name in file_names:
        file_name = folder_name + "/" + name
        if file_name.endswith('xlsx'):
            draw(file_name)
