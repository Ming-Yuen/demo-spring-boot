package com.demo.common.util;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.experimental.Delegate;

import java.util.*;

@Data
public class ValidList<E> implements List<E> {
    @Delegate
    @Valid
    public List<E> list = new ArrayList<>();

    @Override
    public String toString() {
        return list.toString();
    }
}
