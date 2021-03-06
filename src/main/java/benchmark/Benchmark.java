package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import tables.ClosedAddressingHashTable;
import tables.OpenAddressingHashTable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class Benchmark {

    private final static int size = 1000;

    private static OpenAddressingHashTable<Integer> openTable = new OpenAddressingHashTable<>();

    private static ClosedAddressingHashTable<Integer> closedTable = new ClosedAddressingHashTable<>();

    private static HashSet<Integer> set = new HashSet<>();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @org.openjdk.jmh.annotations.Benchmark
    public static void openAddBench(Blackhole bh) {
        for (int i = 0; i < size; i++) openTable.add(i);
        bh.consume(openTable);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void openIterBench(Blackhole bh) {
        Iterator<Integer> iterator = openTable.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            bh.consume(i);
        }
        bh.consume(openTable);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void openRemoveBench(Blackhole bh) {
        for (int i = 0; i < size; i++) openTable.remove(i);
        bh.consume(openTable);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public static void closedAddBench(Blackhole bh) {
        for (int i = 0; i < size; i++) closedTable.add(i);
        bh.consume(closedTable);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void closedIterBench(Blackhole bh) {
        Iterator<Integer> iterator = closedTable.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            bh.consume(i);
        }
        bh.consume(closedTable);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void closedRemoveBench(Blackhole bh) {
        for (int i = 0; i < size; i++) closedTable.remove(i);
        bh.consume(closedTable);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public static void setAddBench(Blackhole bh) {
        for (int i = 0; i < size; i++) set.add(i);
        bh.consume(set);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void setIterBench(Blackhole bh) {
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            bh.consume(i);
        }
        bh.consume(set);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void setRemoveBench(Blackhole bh) {
        for (int i = 0; i < size; i++) set.remove(i);
        bh.consume(set);
    }

}
