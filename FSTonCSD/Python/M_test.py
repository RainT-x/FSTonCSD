from mlxtend.evaluate import mcnemar_table
from mlxtend.evaluate import mcnemar
import json
import pandas as pd
import numpy as np

pred_header = ["fold", "dataset", "FeatureMethod", "Classifier", "real_y", "pred_y"]

Feature_Select_Abbreviate = ['CS', 'CR', 'CV', 'VT', 'MIC',
                             'FS', 'PS', 'IG', 'GR',
                             'SU', 'ReF', 'RW', 'ORF',
                             'SVMF', 'CorBF', 'CorGS', 'ConBF',
                             'ConGS', 'NNBF', 'NNGS', 'LRBF',
                             'LRGS', 'NBBF', 'NBGS', 'CARTBF',
                             'CARTGS', 'RFBF', 'RFGS', 'MLPBF',
                             'MLPGS', 'SVMBF', 'SVMGS', 'PCA', 'NONE']

Datasets = ['LongParameterList', 'SwitchStatements', 'FeatureEnvy', 'LongMethod', 'GodClass', 'DataClass', 'Total']

BestFeatureSelection = ['CS', 'PS', 'IG', 'SU']

classifiers = ['NB', 'LR', 'MLP', 'CART', 'SVM', 'NN', 'RF']


# McNemar检测
def McNemar_test(real, model1, model2):
    matrix = mcnemar_table(np.array(real), np.array(model1), np.array(model2))
    _, p = mcnemar(matrix)
    if p >= 0.05:
        return 0
    else:
        return 1


def process(result, classifier_name, data):
    folds = data['fold'].unique()
    datasets = data['dataset'].unique()
    for fold in folds:
        for data_name in datasets:
            pre_data = data.loc[(data['fold'] == fold) & (data['Classifier'] == classifier_name) & (data['dataset'] == data_name)]
            while (len(pre_data) > 1):
                real = pre_data.iloc[0]['real_y']
                model1_name = pre_data.iloc[0]['FeatureMethod']
                modell_pred = pre_data.iloc[0]['pred_y']
                pre_data = pre_data.iloc[1:, :]
                for i in range(len(pre_data)):
                    model2_name = pre_data.iloc[i]['FeatureMethod']
                    model2_pred = pre_data.iloc[i]['pred_y']

                    if (model1_name in BestFeatureSelection) and (model2_name in BestFeatureSelection):
                        p = McNemar_test(real, modell_pred, model2_pred)
                        row_name = model1_name + ' vs ' + model2_name
                        if row_name not in result.index.values:
                            result.loc[row_name] = [0, 0, 0, 0, 0, 0, 0]
                        result.loc[row_name, data_name] += p
                        result.loc[row_name, Datasets[6]] += p

    return 0


if __name__ == "__main__":
    path = 'D:/Mining/IST-2021/M_test/All_pred_result.json'
    data = pd.read_json(path)
    for classifier_name in classifiers:
        result = pd.DataFrame(columns=Datasets)
        process(result, classifier_name, data)
        result.to_excel('D:/Mining/IST-2021/M_test/M_test_'+classifier_name+'.xlsx')
