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

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Array;
import java.util.Arrays;

@SuppressWarnings("unchecked")
abstract class ObjectArrayTag<V> extends ArrayTag<V[], V> {

    ObjectArrayTag(V[] value) {
        super(value);
    }

    @Override
    public int length() {
        return this.value.length;
    }

    @Override
    public void setAt(int index, V value) {
        this.value[index] = value;
    }

    @Override
    public V getAt(int index) {
        return this.value[index];
    }

    @Override
    public void insertAt(int index, V value) {
        requireNonNull(value, "value");
        final V[] newArray = (V[]) Array.newInstance(this.value.getClass().getComponentType(), this.value.length + 1);
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index, newArray, index + 1, this.value.length - index);
        newArray[index] = value;
        this.value = newArray;
    }

    @Override
    public void removeAt(int index) {
        requireNonNull(value, "value");
        final V[] newArray = (V[]) Array.newInstance(this.value.getClass().getComponentType(), this.value.length - 1);
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index + 1, newArray, index, newArray.length - index);
        this.value = newArray;
    }

    @Override
    public void set(V[] value) {
        super.set(value);
    }

    @Override
    public V[] boxedArray() {
        return get().clone();
    }

    @Override
    boolean arrayEquals(V[] that) {
        return Arrays.equals(this.value, that);
    }

    @Override
    String valueToString() {
        return Arrays.toString(this.value);
    }

}
