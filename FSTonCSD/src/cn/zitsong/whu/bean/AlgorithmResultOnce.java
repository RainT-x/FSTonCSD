package cn.zitsong.whu.bean;

import java.util.List;

import cn.zitsong.whu.utils.Measure;

public class AlgorithmResultOnce {

    public String algorithm;
    public Measure measure;
    
    public List<Integer> attributesLists;

    public AlgorithmResultOnce() {
    }

    public AlgorithmResultOnce(String algorithm, Measure measure, List<Integer> attributesLists) {
        this.algorithm = algorithm;
        this.measure = measure;
        this.attributesLists = attributesLists;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
    public List<Integer> getFeatures() {
        return attributesLists;
    }

    public void setFeatures(List<Integer> attributesLists) {
    	this.attributesLists = attributesLists;
    }
}
