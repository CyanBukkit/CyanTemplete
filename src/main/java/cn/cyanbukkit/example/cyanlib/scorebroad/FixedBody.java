package cn.cyanbukkit.example.cyanlib.scorebroad;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FixedBody implements Body {
    private final LineListBuilder builder;

    private FixedBody(LineListBuilder builder) {
        this.builder = builder;
    }

    public static List<LinePair> getFixedList(List<Line> list) {
        int size = list.size();
        Builder<LinePair> b = ImmutableList.builder();
        Iterator var3 = list.iterator();

        while (var3.hasNext()) {
            Line line = (Line) var3.next();
            b.add(LinePair.of(line, size--));
        }

        return b.build();
    }

    public static FixedBody of(Line... list) {
        return of(Arrays.asList(list));
    }

    public static FixedBody of(List<Line> list) {
        return of(() -> {
            return list;
        });
    }

    public static FixedBody of(LineListBuilder builder) {
        return new FixedBody(builder);
    }

    public List<LinePair> getList() {
        return getFixedList(this.builder.build());
    }
}
