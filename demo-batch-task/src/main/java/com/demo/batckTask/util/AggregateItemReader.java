package com.demo.batckTask.util;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AggregateItemReader<T> implements ItemReader<List<T>>, ItemStream {

    private final ItemReader<T> delegate;
    private T lastItem;
    private Function<T, String> key;

    public AggregateItemReader(ItemReader<T> delegate, Function<T, String> key) {
        this.delegate = delegate;
        this.key = key;
    }

    @Override
    public List<T> read() throws Exception {
        if (lastItem == null) {
            lastItem = delegate.read();
        }

        if (lastItem == null) {
            return null;
        }

        List<T> lines = new ArrayList<>();
        String lastOrderId = key.apply(lastItem);

        do  {
            lines.add(lastItem);
        }while(peek() && key.apply(lastItem).equals(lastOrderId));

        return lines;
    }

    private boolean peek() throws Exception {
        lastItem = delegate.read();
        return lastItem != null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).open(executionContext);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).update(executionContext);
        }
    }

    @Override
    public void close() throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).close();
        }
    }
}