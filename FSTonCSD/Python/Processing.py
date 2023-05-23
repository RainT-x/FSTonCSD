# coding=utf-8

from openpyxl import Workbook
import os

path = os.path.dirname(os.getcwd())
class Processing():

    def write_excel(self, excel_path, data):
        '''

        :param path: Excel的路径
        :param data: 要写入的数据，是一个二维的list，每一个元素是一个一维的list
        :return:
        '''
        # try:
        dir_name = str(os.path.split(excel_path)[0])    # 把目录分离出来，没有的话则创建
        print(dir_name)
        mkdir(dir_name)
        wb = Workbook()
        ws = wb.active
        for _ in data:
            ws.append(_)
        wb.save(excel_path)
        # except BaseException as BE:
        #     print("写Excel时候错误类型：", BE)
        # pass




def mkdir(path):
    path = path.strip()
    # 去除尾部 \ 符号
    path = path.rstrip("\\")

    # 判断路径是否存在
    # 存在     True
    # 不存在   False
    isExists = os.path.exists(path)

    # 判断结果
    if not isExists:
        # 如果不存在则创建目录
        # 创建目录操作函数
        os.makedirs(path)
        return True
    else:
        # 如果目录存在则不创建，并提示目录已存在
        return False




