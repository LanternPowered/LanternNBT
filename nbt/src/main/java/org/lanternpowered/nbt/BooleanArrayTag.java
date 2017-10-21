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

public final class BooleanArrayTag extends ArrayTag<boolean[], Boolean> {

    /**
     * Constructs a new {@link BooleanArrayTag} with the
     * given {@code boolean} array.
     *
     * @param value The boolean array
     */
    public BooleanArrayTag(boolean... value) {
        super(value);
    }

    /**
     * Constructs a new {@link BooleanArrayTag} with the
     * given {@link Boolean} array. All the {@code null}
     * entries will be replaced with {@code false}.
     *
     * @param value The boolean array
     */
    public BooleanArrayTag(Boolean[] value) {
        this(value, false);
    }

    /**
     * Constructs a new {@link BooleanArrayTag} with the
     * given {@link Boolean} array. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The boolean array
     * @param defaultValue The value to fill up null values
     */
    public BooleanArrayTag(Boolean[] value, boolean defaultValue) {
        super(convert(value, defaultValue));
    }

    private static boolean[] convert(Boolean[] boxedArray, boolean defaultValue) {
        final boolean[] array = new boolean[boxedArray.length];
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
    public void setAt(int index, Boolean value) {
        this.value[index] = value;
    }

    @Override
    public void insertAt(int index, Boolean value) {
        insertAt(index, value.booleanValue());
    }

    /**
     * Inserts a value at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public void insertAt(int index, boolean value) {
        if (index < 0 || index > this.value.length) {
            throw new IndexOutOfBoundsException();
        }
        final boolean[] newArray = new boolean[this.value.length + 1];
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
        final boolean[] newArray = new boolean[this.value.length - 1];
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index + 1, newArray, index, newArray.length - index);
        this.value = newArray;
    }

    @Override
    public Boolean getAt(int index) {
        return this.value[index];
    }

    /**
     * Sets the value of this {@link Tag}. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The boolean array
     * @param defaultValue The value to fill up null values
     */
    public void set(Boolean[] value, boolean defaultValue) {
        this.value = convert(value, defaultValue);
    }

    @Override
    public void set(Boolean[] value) {
        set(value, false);
    }

    @Override
    public Boolean[] boxedArray() {
        final Boolean[] array = new Boolean[this.value.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.value[i];
        }
        return array;
    }

    @Override
    String valueToString() {
        return Arrays.toString(this.value);
    }
}
