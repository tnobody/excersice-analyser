package org.tnobody;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.tnobody.data.XlsDatabase;
import org.tnobody.services.WorkoutResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by tim on 10.05.2016.
 */
public class ExcerciseAnalyser extends Application<ExcerciseAnalyserConfiguration> {


    public void run(ExcerciseAnalyserConfiguration configuration, Environment environment) throws Exception {
        XlsDatabase database = XlsDatabase.fromFile(configuration.getExcelPath());

        final WorkoutResource workoutResource = new WorkoutResource(database);

        final FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter("crossOriginRequest", CrossOriginFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        environment.jersey().register(workoutResource);
    }

    public static void main(String[] args) throws Exception {
        new ExcerciseAnalyser().run(args);
    }
}
