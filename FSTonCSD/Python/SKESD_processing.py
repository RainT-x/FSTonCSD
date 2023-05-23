import pandas as pd

Feature_Select_Abbreviate = ['NONE', 'CS', 'CR', 'CV', 'VT', 'MIC',
                             'FS', 'PS', 'IG', 'GR',
                             'SU', 'ReF', 'RW', 'ORF',
                             'SVMF', 'CorBF', 'CorGS', 'ConBF',
                             'ConGS', 'NNBF', 'NNGS', 'LRBF',
                             'LRGS', 'NBBF', 'NBGS', 'CARTBF',
                             'CARTGS', 'RFBF', 'RFGS', 'MLPBF',
                             'MLPGS', 'SVMBF', 'SVMGS', 'PCA']

datasets = ['LongParameterList', 'SwitchStatements', 'FeatureEnvy', 'LongMethod', 'GodClass', 'DataClass']
classifiers = ['NB', 'LR', 'MLP', 'CART', 'SVM', 'NN', 'RF']


def load(path):
    return pd.read_excel(path)


#
# 探究同一分类模型下特征选择方法差异
# 横轴数据集 纵轴特征选择方法
#
def SKESD_classifier(data, classifier, measure):
    d_f = pd.DataFrame(index=datasets, columns=Feature_Select_Abbreviate)
    for dataset in datasets:
        for f in Feature_Select_Abbreviate:
            d_f.loc[dataset, f] = data.loc[
                ((data['Classifier'] == classifier) & (
                        data['dataset'] == dataset) & (data['FeatureMethod'] == f)), measure].values[0]
    return d_f


#
# 探究同一数据集下特征选择方法差异
# 横轴分类模型 纵轴特征选择方法
#
def SKESD_dataset(data, dataset, measure):
    c_f = pd.DataFrame(index=classifiers, columns=Feature_Select_Abbreviate)
    for classifier in classifiers:
        for f in Feature_Select_Abbreviate:
            c_f.loc[classifier, f] = data.loc[
                ((data['Classifier'] == classifier) & (
                        data['dataset'] == dataset) & (data['FeatureMethod'] == f)), measure].values[0]
    return c_f


def save_SKESD_data(savepath, df, name, measure):
    df.to_excel(savepath + measure + '/' + name + '.xlsx')


if __name__ == '__main__':
    path = 'D:/Mining/IST-2021/AllResult/Allresult.xlsx'
    saveclassifier_path = 'D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier/'
    savedataset_path = 'D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet/'
    data = load(path)
    measures = data.columns.tolist()[3:]
    for c in classifiers:
        for m in measures:
            result_classifier = SKESD_classifier(data, c, m)
            save_SKESD_data(saveclassifier_path, result_classifier, c, m)

    for d in datasets:
        for m in measures:
            result_dataset = SKESD_dataset(data, d, m)
            save_SKESD_data(savedataset_path, result_dataset, d, m)
