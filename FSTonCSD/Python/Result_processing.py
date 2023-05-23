import pandas as pd

Feature_Select_Abbreviate = ['NONE', 'CS', 'CR', 'CV', 'VT', 'MIC',
                             'FS', 'PS', 'IG', 'GR',
                             'SU', 'ReF', 'RW', 'ORF',
                             'SVMF', 'CorBF', 'CorGS', 'ConBF',
                             'ConGS', 'NNBF', 'NNGS', 'LRBF',
                             'LRGS', 'NBBF', 'NBGS', 'CARTBF',
                             'CARTGS', 'RFBF', 'RFGS', 'MLPBF',
                             'MLPGS', 'SVMBF', 'SVMGS', 'PCA']
Datasets = ['LongParameterList', 'SwitchStatements', 'FeatureEnvy', 'LongMethod', 'GodClass', 'DataClass']

def load(path):
    return pd.read_excel(path)


def processing_headmap(data, dataset_or_classifier, featureMethod, measure, str):
    d_or_c_f = pd.DataFrame(index=featureMethod, columns=dataset_or_classifier)
    for d_or_c in dataset_or_classifier:
        for f in featureMethod:
            d_or_c_f.loc[f, d_or_c] = data.loc[
                ((data['FeatureMethod'] == f) & (data[str] == d_or_c)), measure].mean()
    return d_or_c_f.reindex(Feature_Select_Abbreviate)


# 保存文件
def save_headmap_data(savepath, df, measure):
    df.to_excel(savepath + measure + '.xlsx')


if __name__ == '__main__':
    path = 'D:/Mining/IST-2021/AllResult/Allresult.xlsx'
    save_path_f_D = 'D:/Mining/IST-2021/PictureData/FeatureMethod_DataSet/'
    save_path_f_C = 'D:/Mining/IST-2021/PictureData/FeatureMethod_Classifier/'
    data = load(path)
    Classifiers = data.loc[:, 'Classifier'].unique()
    # Datasets = data.loc[:, 'dataset'].unique()
    FeatureMethods = data.loc[:, 'FeatureMethod'].unique()
    measure = ['precision', 'recall', 'f1', 'mcc', 'auc']

    # FeatureMethod + Classifier
    for m in measure:
        df = processing_headmap(data, Classifiers, FeatureMethods, m, 'Classifier')
        save_headmap_data(save_path_f_C, df, m)

    # FeatureMethod + Dataset
    for m in measure:
        df = processing_headmap(data, Datasets, FeatureMethods, m, 'dataset')
        save_headmap_data(save_path_f_D, df, m)
