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

public abstract class ArrayTag<V, B> extends ValueTag<V> {

    ArrayTag(V value) {
        super(value);
    }

    /**
     * Gets the length of this array.
     *
     * @return The length
     */
    public abstract int length();

    /**
     * Sets the value of type {@link B}
     * at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public abstract void setAt(int index, B value);

    /**
     * Inserts a value at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public abstract void insertAt(int index, B value);

    /**
     * Adds the value at the end of the array.
     *
     * @param value The value
     */
    public void add(B value) {
        insertAt(length(), value);
    }

    /**
     * Adds the value at the beginning of the array.
     *
     * @param value The value
     */
    public void addFirst(B value) {
        insertAt(0, value);
    }

    /**
     * Removes the value at the specified index.
     *
     * @param index The index
     */
    public abstract void removeAt(int index);

    /**
     * Gets the value of type {@link B}
     * at the specified index.
     *
     * @param index The index
     * @return The value
     */
    public abstract B getAt(int index);

    /**
     * Sets the value of this {@link Tag}.
     *
     * @param value The value
     */
    public abstract void set(B[] value);

    /**
     * Gets the internal array as a boxed array.
     *
     * @return The boxed array
     */
    public abstract B[] boxedArray();

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() &&
                arrayEquals(((ArrayTag<V, B>) obj).value);
    }

    abstract boolean arrayEquals(V that);
}
