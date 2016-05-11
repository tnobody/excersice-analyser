package org.tnobody.data;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;
import org.tnobody.model.Workout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by tim on 11.05.2016.
 */
public class XlsDatabase {

    public static XlsDatabase fromFile(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));

        HSSFWorkbook workbook = new HSSFWorkbook(file);
        return new XlsDatabase(workbook);
    }

    private HSSFWorkbook workbook;

    XlsDatabase(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    /**
     * @param date 20.10.2015
     * @param time 22:25
     * @return
     */
    private DateTime toDate(String date, String time) {
        String[] da = date.split("\\.");
        String[] ta = time.split(":");
        System.out.println("Datum:" + String.join(", ", da) + " zeit: " + String.join("-", ta));
        if (da.length == 3 && ta.length == 2) {
            int year = Integer.parseInt(da[2]);
            int month = Integer.parseInt(da[1]);
            int day = Integer.parseInt(da[0]);
            int hour = Integer.parseInt(ta[0]);
            int minutes = Integer.parseInt(ta[1]);
            if(hour == 24) {
                hour = 0;
                day += 1;
            }
            return new DateTime(year, month, day, hour, minutes, 0);
        } else {
            return null;
        }
    }

    public List<Workout> getWorkouts() {
        final HSSFSheet overviewSheet = this.workbook.getSheet(XlsDatabaseConfig.OVERVIEW_SHEET);

        return XlsDatabaseConfig.asStream(overviewSheet.iterator())
                .filter(r -> !r.getCell(0).getStringCellValue().equals("Datum"))
                .map(r -> {
                    Workout workout = new Workout();
                    String date = r.getCell(0).getStringCellValue();
                    String timeStart = r.getCell(1).getStringCellValue();
                    String timeEnd = r.getCell(2).getStringCellValue();
                    workout.setStartAt(toDate(date, timeStart));
                    workout.setFinishAt(toDate(date, timeEnd));
                    List<String> muscleGroups = Arrays.stream(r.getCell(3).getStringCellValue().split(","))
                            .map(s -> s.trim())
                            .collect(Collectors.toList());
                    workout.setMuscleGroups(muscleGroups);
                    return workout;
                })
                .collect(Collectors.toList());
    }

    public List<ExcersiceExecution> getExecutions(String muscleGroup, String excersiceName) {
        //final String lastExcersice = "";
        IntStream
                .range(0,workbook.getNumberOfSheets()-1)
                .mapToObj(i -> workbook.getSheetAt(i))
                .filter(s -> s.getSheetName().equals(muscleGroup) || muscleGroup == null)
                .flatMap(s -> XlsDatabaseConfig.asStream(s.iterator()))
                .filter(r -> !r.getCell(0).getStringCellValue().equals(""))
                .map(r -> {
                    if(!r.getCell(0).getStringCellValue().equals("") && r.getCell(1).getStringCellValue().equals("")) {
                        //lastExcersice = r.getCell(0).getStringCellValue();
                        return null;
                    } else {
                        ExcersiceExecution excersice = new ExcersiceExecution();

                        return excersice;
                    }
                })

        ;

        return Arrays.asList();
    }

}
