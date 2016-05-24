package org.tnobody.services;

import org.tnobody.model.ExcersiceExecution;
import org.tnobody.data.XlsDatabase;
import org.tnobody.model.Workout;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by tim on 11.05.2016.
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {

    private XlsDatabase database;

    public WorkoutResource(XlsDatabase database) {
        this.database = database;
    }

    @GET
    @Path("/workouts")
    public List<Workout> getWorkoutList() {
        return database.getWorkouts();
    }

    @GET
    @Path("/musclegroups")
    public List<String> getMuscleGroups() {
        return database.getMuscleGroups();
    }

    @GET
    @Path("/execersices/{musclegroup}")
    public List<String> getExcersices(
            @PathParam("musclegroup") String musclegroup
    ) {
        return database.getExcersices(musclegroup);
    }

    @GET
    @Path("/excersiceexecutions/{musclegroup}/{excersice}")
    public List<ExcersiceExecution> getExcersiceExecutions(
            @PathParam("musclegroup") String musclegroup,
            @PathParam("excersice") String excersice
    ) {
        return database.getExecutions(musclegroup, excersice);
    }
}
