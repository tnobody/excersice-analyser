package org.tnobody.data;
;

import org.apache.poi.ss.usermodel.Cell;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Created by tmkein on 24.05.2016.
 */
public class Row {

    private org.apache.poi.ss.usermodel.Row row;

    public Row(org.apache.poi.ss.usermodel.Row row) {
        this.row = row;
    }

    public Optional<Cell> getCell(int i) {
        return Optional.ofNullable(row.getCell(i));
    }

    public String getUtf8StringValue(int i) {
        return getCell(i)
                .map(r -> r.getStringCellValue())
                .map(s -> new String(s.getBytes(Charset.forName("UTF-8")), Charset.forName("UTF-8")))
                .orElse("");
    }
}
