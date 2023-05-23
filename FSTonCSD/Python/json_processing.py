import pandas as pd
import json
import os


def load(path):
    return pd.read_json(path)


if __name__ == '__main__':
    path = 'D:/Mining/IST-2021'
    pathDir = os.listdir(path)
    for allDir in pathDir:
        child = os.path.join('%s/%s' % (path, allDir))
        js_processing = {}
        if os.path.isfile(child) and child.endswith('json'):
            js = load(child)
            data = js['algorithmResultOnceList'][0]
            # print(data)
            for d in data:
                f_method = d['algorithm']
                measure = d['measure']
                js_processing[f_method] = measure
            with open(child, 'w') as f:
                json.dump(js_processing, f)
