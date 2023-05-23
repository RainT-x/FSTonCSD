package cn.zitsong.whu.bean;

import java.util.List;

public class ResultVo {

    public String dataSet;
    public List<AlgorithmResultAll> data;

    public ResultVo() {
    }

    public ResultVo(String dataSet, List<AlgorithmResultAll> data) {
        this.dataSet = dataSet;
        this.data = data;
    }

    public String getDataSet() {
        return dataSet;
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public List<AlgorithmResultAll> getData() {
        return data;
    }

    public void setData(List<AlgorithmResultAll> data) {
        this.data = data;
    }
}
