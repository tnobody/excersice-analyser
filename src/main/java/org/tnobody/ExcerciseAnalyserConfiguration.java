package org.tnobody;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by tim on 10.05.2016.
 */
public class ExcerciseAnalyserConfiguration extends Configuration {

    @NotEmpty
    private String excelPath;

    @JsonProperty
    public String getExcelPath() {
        return excelPath;
    }

    @JsonProperty
    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }
}
