package org.tnobody.model;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by tim on 11.05.2016.
 */
public class Workout {
    private DateTime startAt;
    private DateTime finishAt;
    private List<String> muscleGroups;

    public DateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(DateTime startAt) {
        this.startAt = startAt;
    }

    public DateTime getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(DateTime finishAt) {
        this.finishAt = finishAt;
    }

    public List<String> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(List<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }
}
