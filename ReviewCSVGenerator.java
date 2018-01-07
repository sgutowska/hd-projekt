package com.uek.etl.Utilities;


import com.uek.etl.model.Review;
import org.supercsv.cellprocessor.FmtBool;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class ReviewCSVGenerator {

    private List<Review> list;

    public ReviewCSVGenerator(List<Review> list) {
        this.list = list;
    }


    private static CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
        };

        return processors;
    }

    public String listToCSVString() throws IOException {
        StringWriter sw = new StringWriter();
        ICsvBeanWriter beanWriter = new CsvBeanWriter(sw, CsvPreference.STANDARD_PREFERENCE);

        final String[] header = new String[] { "id","productId", "cons", "pros", "description",
                "starRating", "author", "date", "isRecommended", "voteYes", "voteNo" };

        final CellProcessor[] processors = getProcessors();

        beanWriter.writeHeader(header);

        for(final Review r : list){
            System.out.println(r.toString());
            beanWriter.write(r,header,processors);
        }
        beanWriter.close();
        return sw.toString();

    }


}
