package org.tnobody.data;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by tim on 11.05.2016.
 */
public class XlsDatabaseUtils {

    final static String OVERVIEW_SHEET = "Übersicht";

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    public static <T> Stream<T> sliceOf(Stream<T> stream, Predicate<T> from, Predicate<T> to) {
        AtomicInteger skip = new AtomicInteger();
        AtomicInteger take = new AtomicInteger();
        boolean collectTake = false;
        stream.forEach(o -> {
            if(!from.test(o) && !collectTake) {
                skip.incrementAndGet();
            }
            if(to.test(o) && collectTake) {
                take.incrementAndGet();
            }
        });
        return stream.skip(skip.get()).limit(take.get());
    }
}
