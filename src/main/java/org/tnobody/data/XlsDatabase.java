package org.tnobody.data;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.tnobody.model.ExcersiceExecution;
import org.tnobody.model.Workout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by tim on 11.05.2016.
 */
public class XlsDatabase {

    private Predicate<Row> isHeaderRow;

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
            if (hour == 24) {
                hour = 0;
                day += 1;
            }
            return new DateTime(year, month, day, hour, minutes, 0);
        } else {
            return null;
        }
    }

    public List<Workout> getWorkouts() {
        final HSSFSheet overviewSheet = this.workbook.getSheet(XlsDatabaseUtils.OVERVIEW_SHEET);

        return XlsDatabaseUtils.asStream(overviewSheet.iterator())
                .filter(r -> !r.getCell(0).getStringCellValue().equals("Datum"))
                .map(Row::new)
                .map(r -> {
                    Workout workout = new Workout();
                    String date = r.getUtf8StringValue(0);
                    String timeStart = r.getUtf8StringValue(1);
                    String timeEnd = r.getUtf8StringValue(2);
                    workout.setStartAt(toDate(date, timeStart));
                    workout.setFinishAt(toDate(date, timeEnd));
                    List<String> muscleGroups = Arrays.stream(r.getUtf8StringValue(3).split(","))
                            .map(s -> s.trim())
                            .collect(Collectors.toList());
                    workout.setMuscleGroups(muscleGroups);
                    return workout;
                })
                .collect(Collectors.toList());
    }

    public List<ExcersiceExecution> getExecutions(String musclegroup, String excersicename) {
        HSSFSheet sheet = workbook.getSheet(musclegroup);
        return XlsDatabaseUtils.sliceOf(
                XlsDatabaseUtils.asStream(sheet.rowIterator()).map(Row::new),
                r -> r.getUtf8StringValue(0).equals(musclegroup),
                isHeaderRow
        ).map(r -> {
            return new ExcersiceExecution(
                    excersicename,
                    r.getCell(1).map(rr -> rr.getNumericCellValue()).orElse(new Double(.0)).intValue(),
                    r.getCell(2).map(rr -> (int)rr.getNumericCellValue()).orElse(0),
                    r.getCell(3).map(rr -> (int)rr.getNumericCellValue()).orElse(0),
                    r.getCell(4).map(rr -> (int)rr.getNumericCellValue()).orElse(0),
                    r.getUtf8StringValue(5)
            );
        }).collect(Collectors.toList());
    }

    public List<String> getMuscleGroups() {
        return IntStream.range(0, workbook.getNumberOfSheets())
                .mapToObj(i -> workbook.getSheetAt(i))
                .filter(s -> !s.getSheetName().equals(XlsDatabaseUtils.OVERVIEW_SHEET))
                .map(s -> s.getSheetName())
                .collect(Collectors.toList());
    }

    public List<String> getExcersices(String musclegroup) {
        HSSFSheet sheet = workbook.getSheet(musclegroup);
        isHeaderRow = r -> r.getCell(0).isPresent() && !r.getCell(1).isPresent();
        return XlsDatabaseUtils
                .asStream(sheet.iterator())
                .map(Row::new)
                .filter(isHeaderRow)
                .map(r -> r.getUtf8StringValue(0))
                .collect(Collectors.toList());
    }
}
