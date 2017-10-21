/*
 * This file is part of LanternNBT, licensed under the MIT License (MIT).
 *
 * Copyright (c) LanternPowered https://github.com/LanternPowered/LanternNBT
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the Software), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andor sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lanternpowered.nbt.editor.converter;

import javafx.scene.control.TextFormatter;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumberConverter<T extends Number> extends AbstractConverter<T> {

    private static final Pattern FILLING_INT_PATTERN = Pattern.compile("^[-+]?[0-9]*$");
    private static final Pattern COMPLETE_INT_PATTERN = Pattern.compile("^[-+]?[0-9]+$");

    // Supported format examples:
    // .5236
    // 0.5236
    // -.5236
    // -0.5236
    // 52.36
    // -52.36
    // 5236

    // The pattern to match whether the value is still being filled in
    private static final Pattern FILLING_DOUBLE_PATTERN = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]*$");
    // The pattern to match whether the value is complete
    private static final Pattern COMPLETE_DOUBLE_PATTERN = Pattern.compile("^[-+]?(?:[0-9]+(?:\\.[0-9]+)?|\\.[0-9]+)$");

    public static final NumberConverter<Integer> INT = new NumberConverter<>(s -> {
        final long value = Long.parseLong(s);
        return value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE ? (int) value : null;
    }, FILLING_INT_PATTERN, COMPLETE_INT_PATTERN);
    public static final NumberConverter<Byte> BYTE = new NumberConverter<>(s -> {
        final long value = Long.parseLong(s);
        return value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE ? (byte) value : null;
    }, FILLING_INT_PATTERN, COMPLETE_INT_PATTERN);
    public static final NumberConverter<Short> SHORT = new NumberConverter<>(s -> {
        final long value = Long.parseLong(s);
        return value >= Short.MIN_VALUE && value <= Short.MAX_VALUE ? (short) value : null;
    }, FILLING_INT_PATTERN, COMPLETE_INT_PATTERN);
    public static final NumberConverter<Long> LONG = new NumberConverter<>(Long::parseLong,
            FILLING_INT_PATTERN, COMPLETE_INT_PATTERN);
    public static final NumberConverter<Float> FLOAT = new NumberConverter<>(s -> {
        final double value = Double.parseDouble(s);
        return value >= Float.MIN_VALUE && value <= Float.MAX_VALUE ? (float) value : null;
    }, FILLING_DOUBLE_PATTERN, COMPLETE_DOUBLE_PATTERN);
    public static final NumberConverter<Double> DOUBLE = new NumberConverter<>(Double::parseDouble,
            FILLING_DOUBLE_PATTERN, COMPLETE_DOUBLE_PATTERN);

    private final Function<String, T> parser;
    private final Pattern fillingPattern;
    private final Pattern completePattern;

    private NumberConverter(Function<String, T> parser,
            Pattern fillingPattern,
            Pattern completePattern) {
        this.parser = parser;
        this.fillingPattern = fillingPattern;
        this.completePattern = completePattern;
    }

    @Override
    public String toString(T object) {
        return object == null ? "" : object.toString();
    }

    @Override
    public T fromString(String value) {
        if (value == null || !this.completePattern.matcher(value).matches()) {
            return null;
        }
        try {
            return this.parser.apply(value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected UnaryOperator<TextFormatter.Change> getFilter() {
        return change -> {
            final String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change;
            }
            if (this.completePattern.matcher(newText).matches()) {
                T number = null;
                try {
                    number = this.parser.apply(newText);
                } catch (Exception ignored) {
                }
                return number == null ? null : change;
            }
            final Matcher matcher = this.fillingPattern.matcher(newText);
            if (matcher.matches()) {
                return change;
            }
            return null;
        };
    }
}
