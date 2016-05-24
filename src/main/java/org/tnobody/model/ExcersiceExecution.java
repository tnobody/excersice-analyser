package org.tnobody.model;

/**
 * Created by tim on 11.05.2016.
 */
public class ExcersiceExecution {
    String excersice;
    int ExcersicePosition;
    int set;
    double weight;
    int reps;
    String comment;

    public ExcersiceExecution(String excersice, int excersicePosition, int set, double weight, int reps, String comment) {
        this.excersice = excersice;
        ExcersicePosition = excersicePosition;
        this.set = set;
        this.weight = weight;
        this.reps = reps;
        this.comment = comment;
    }

    public String getExcersice() {
        return excersice;
    }

    public void setExcersice(String excersice) {
        this.excersice = excersice;
    }

    public int getExcersicePosition() {
        return ExcersicePosition;
    }

    public void setExcersicePosition(int excersicePosition) {
        ExcersicePosition = excersicePosition;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
