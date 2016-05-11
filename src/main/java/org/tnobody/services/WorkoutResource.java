package org.tnobody.services;

import org.tnobody.data.XlsDatabase;
import org.tnobody.model.Workout;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by tim on 11.05.2016.
 */

@Path("/workouts")
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {

    private XlsDatabase database;

    public WorkoutResource(XlsDatabase database) {
        this.database = database;
    }

    @GET
    public List<Workout> getWorkoutList() {
        return database.getWorkouts();
    }
}
