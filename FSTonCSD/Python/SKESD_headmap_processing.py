import os
import pandas as pd

measures = ['precision', 'recall', 'f1', 'mcc', 'auc']
classifiers = ['NB', 'LR', 'MLP', 'CART', 'SVM', 'NN', 'RF']
Feature_Select_Abbreviate = ['NONE', 'CS', 'CR', 'CV', 'VT', 'MIC',
                             'FS', 'PS', 'IG', 'GR',
                             'SU', 'ReF', 'RW', 'ORF',
                             'SVMF', 'CorBF', 'CorGS', 'ConBF',
                             'ConGS', 'NNBF', 'NNGS', 'LRBF',
                             'LRGS', 'NBBF', 'NBGS', 'CARTBF',
                             'CARTGS', 'RFBF', 'RFGS', 'MLPBF',
                             'MLPGS', 'SVMBF', 'SVMGS', 'PCA']
datasets = ['LongParameterList', 'SwitchStatements', 'FeatureEnvy', 'LongMethod', 'GodClass', 'DataClass']


def txt_to_dict(path):
    r_dict = {key: 0 for key in Feature_Select_Abbreviate}
    with open(path, 'r') as f:
        f.readline()
        ranks = f.readlines()
        for rank in ranks:
            f_r = rank.strip('\n').split(' ')
            r_dict[f_r[0].strip('"')] = f_r[1]
    return r_dict


def read_total(file_path, d_or_c, result):
    dict = txt_to_dict(file_path)
    for k, v in dict.items():
        result.loc[k, d_or_c] = v


def processing(filepath, d_c):
    for m in measures:
        file_names = os.listdir(filepath + m)
        result_total = pd.DataFrame(index=Feature_Select_Abbreviate, columns=d_c)
        for name in file_names:
            file_name = filepath + m + "/" + name
            if file_name.endswith('txt'):
                read_total(file_name, name.split('.')[0], result_total)
        result_total.to_excel(filepath + m + '.xlsx')


if __name__ == '__main__':
    classifier_filepath = 'D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier/'
    dataset_filepath = 'D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet/'
    processing(classifier_filepath, classifiers)
    processing(dataset_filepath, datasets)
