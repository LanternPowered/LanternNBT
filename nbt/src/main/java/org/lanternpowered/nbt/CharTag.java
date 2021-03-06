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

public final class CharTag implements Tag<Character> {

    private char value;

    /**
     * Constructs a new {@link CharTag} with the
     * given {@code char} value.
     *
     * @param value The char value
     */
    public CharTag(char value) {
        this.value = value;
    }

    /**
     * Sets the value of this {@link Tag}.
     *
     * @param value The value
     */
    @Override
    public void set(Character value) {
        this.value = value;
    }

    /**
     * Sets the value of this {@link Tag}.
     *
     * @param value The value
     */
    public void set(char value) {
        this.value = value;
    }

    @Override
    public Character get() {
        return this.value;
    }

    /**
     * Gets the value as a {@code char}.
     *
     * @return The char value
     */
    public char charValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + this.value + "]";
    }

    @Override
    public int hashCode() {
        return Character.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CharTag && ((CharTag) obj).value == this.value;
    }
}
