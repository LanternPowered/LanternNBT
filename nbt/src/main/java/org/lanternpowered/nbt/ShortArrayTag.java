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

public final class ShortArrayTag extends ArrayTag<short[], Short> {

    /**
     * Constructs a new {@link ShortArrayTag} with the
     * given {@code short} array.
     *
     * @param value The short array
     */
    public ShortArrayTag(short... value) {
        super(value);
    }

    /**
     * Constructs a new {@link ShortArrayTag} with the
     * given {@link Short} array. All the {@code null}
     * entries will be replaced with {@code 0}.
     *
     * @param value The short array
     */
    public ShortArrayTag(Short[] value) {
        this(value, (short) 0);
    }

    /**
     * Constructs a new {@link ShortArrayTag} with the
     * given {@link Short} array. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The short array
     * @param defaultValue The value to fill up null values
     */
    public ShortArrayTag(Short[] value, short defaultValue) {
        super(convert(value, defaultValue));
    }

    private static short[] convert(Short[] boxedArray, short defaultValue) {
        final short[] array = new short[boxedArray.length];
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
    public void setAt(int index, Short value) {
        this.value[index] = value;
    }

    @Override
    public Short getAt(int index) {
        return this.value[index];
    }

    @Override
    public void insertAt(int index, Short value) {
        insertAt(index, value.shortValue());
    }

    /**
     * Inserts a value at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public void insertAt(int index, short value) {
        if (index < 0 || index > this.value.length) {
            throw new IndexOutOfBoundsException();
        }
        final short[] newArray = new short[this.value.length + 1];
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
        final short[] newArray = new short[this.value.length - 1];
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index + 1, newArray, index, newArray.length - index);
        this.value = newArray;
    }

    /**
     * Sets the value of this {@link Tag}. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The short array
     * @param defaultValue The value to fill up null values
     */
    public void set(Short[] value, short defaultValue) {
        this.value = convert(value, defaultValue);
    }

    @Override
    public void set(Short[] value) {
        set(value, (short) 0);
    }

    @Override
    public Short[] boxedArray() {
        final Short[] array = new Short[this.value.length];
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
