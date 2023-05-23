package cn.zitsong.whu.bean;

import weka.core.Instances;

/**
 * @Description: Train and test dataSet
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:07
 * @Since: version 1.0.0
 **/
public class TrainTestDataSet {

    public Instances train;
    public Instances test;

    public TrainTestDataSet() {
    }

    public TrainTestDataSet(Instances train, Instances test) {
        this.train = train;
        this.test = test;
    }

    public Instances getTrain() {
        return train;
    }

    public void setTrain(Instances train) {
        this.train = train;
    }

    public Instances getTest() {
        return test;
    }

    public void setTest(Instances test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "TrainTestDataSet{" +
                "train=" + train +
                ", test=" + test +
                '}';
    }
}
