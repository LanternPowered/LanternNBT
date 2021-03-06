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
package org.lanternpowered.nbt;

import java.util.Arrays;

public final class DoubleArrayTag extends ArrayTag<double[], Double> {

    /**
     * Constructs a new {@link DoubleArrayTag} with the
     * given {@code double} array.
     *
     * @param value The double array
     */
    public DoubleArrayTag(double... value) {
        super(value);
    }

    /**
     * Constructs a new {@link DoubleArrayTag} with the
     * given {@link Double} array. All the {@code null}
     * entries will be replaced with {@code 0}.
     *
     * @param value The double array
     */
    public DoubleArrayTag(Double[] value) {
        this(value, 0);
    }

    /**
     * Constructs a new {@link DoubleArrayTag} with the
     * given {@link Double} array. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The double array
     * @param defaultValue The value to fill up null values
     */
    public DoubleArrayTag(Double[] value, double defaultValue) {
        super(convert(value, defaultValue));
    }

    private static double[] convert(Double[] boxedArray, double defaultValue) {
        final double[] array = new double[boxedArray.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = boxedArray[i] == null ? defaultValue : boxedArray[i];
        }
        return array;
    }

    @Override
    public int length() {
        return this.value.length;
    }

    @Override
    public void setAt(int index, Double value) {
        this.value[index] = value;
    }

    @Override
    public Double getAt(int index) {
        return this.value[index];
    }

    @Override
    public void insertAt(int index, Double value) {
        insertAt(index, value.doubleValue());
    }

    /**
     * Inserts a value at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public void insertAt(int index, double value) {
        if (index < 0 || index > this.value.length) {
            throw new IndexOutOfBoundsException();
        }
        final double[] newArray = new double[this.value.length + 1];
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index, newArray, index + 1, this.value.length - index);
        newArray[index] = value;
        this.value = newArray;
    }

    @Override
    public void removeAt(int index) {
        if (index < 0 || index > this.value.length) {
            throw new IndexOutOfBoundsException();
        }
        final double[] newArray = new double[this.value.length - 1];
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index + 1, newArray, index, newArray.length - index);
        this.value = newArray;
    }

    /**
     * Sets the value of this {@link Tag}. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The double array
     * @param defaultValue The value to fill up null values
     */
    public void set(Double[] value, double defaultValue) {
        this.value = convert(value, defaultValue);
    }

    @Override
    public void set(Double[] value) {
        set(value, 0);
    }

    @Override
    public Double[] boxedArray() {
        final Double[] array = new Double[this.value.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.value[i];
        }
        return array;
    }

    @Override
    boolean arrayEquals(double[] that) {
        return Arrays.equals(this.value, that);
    }

    @Override
    String valueToString() {
        return Arrays.toString(this.value);
    }

}
