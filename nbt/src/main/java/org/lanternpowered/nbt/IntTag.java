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

public final class IntTag extends NumberTag<Integer> {

    private int value;

    /**
     * Constructs a new {@link IntTag} with the
     * given {@code float} value.
     *
     * @param value The float value
     */
    public IntTag(int value) {
        this.value = value;
    }

    /**
     * Sets the value of this {@link Tag}.
     *
     * @param value The value
     */
    @Override
    public void set(Integer value) {
        this.value = value;
    }

    /**
     * Sets the value of this {@link Tag}.
     *
     * @param value The value
     */
    public void set(int value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntTag && ((IntTag) obj).value == this.value;
    }
}
