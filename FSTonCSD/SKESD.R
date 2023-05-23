library(ScottKnottESD)
library(readxl)
#上一步处理的SKESD_Data文件夹下，得到的评价指标值的文件夹
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier/auc/"
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier/f1/"
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier/precision/"
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier/recall/"
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_Classifier/mcc/"

#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet/auc/"
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet/f1/"
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet/precision/"
#finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet/recall/"
finalpath= "D:/Mining/IST-2021/SKESD/FeatureMethod_DataSet/mcc/"

#存放sk结果的文件夹
skresultpath=finalpath
file_names<- list.files(finalpath)
for (i in 1:length(file_names)) {
  #if语句是只对文件名是以.xlsx结尾的文件进行如下操作
  if (grepl(pattern = ".xlsx$",x = file_names[i]) )
  {
    print(file_names[i])
    path=paste(finalpath,sep = "",file_names[i])
    csv<- read_excel(path)
    csv<- csv[-1]
    sk <- sk_esd(csv)
    #plot(sk)
    
    resultpath=paste(skresultpath,sep = "",file_names[i])
    resultpath=paste(resultpath,sep = "",".txt")
    print(resultpath)
    
    write.table (sk[["groups"]], resultpath) 
  }
}
