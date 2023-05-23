package cn.zitsong.whu.bean;

import java.util.List;

public class AlgorithmResultAll {

    public String stepSet;
    List<AlgorithmResultOnce> algorithmResultOnceList;

    public AlgorithmResultAll() {
    }

    public AlgorithmResultAll(String stepSet, List<AlgorithmResultOnce> algorithmResultOnceList) {
        this.stepSet = stepSet;
        this.algorithmResultOnceList = algorithmResultOnceList;
    }

    public String getStepSet() {
        return stepSet;
    }

    public void setStepSet(String stepSet) {
        this.stepSet = stepSet;
    }

    public List<AlgorithmResultOnce> getAlgorithmResultOnceList() {
        return algorithmResultOnceList;
    }

    public void setAlgorithmResultOnceList(List<AlgorithmResultOnce> algorithmResultOnceList) {
        this.algorithmResultOnceList = algorithmResultOnceList;
    }
}
