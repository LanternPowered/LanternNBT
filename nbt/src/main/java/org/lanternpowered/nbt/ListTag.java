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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public final class ListTag<T extends Tag<?>> extends ArrayList<T> implements Tag<List<T>> {

    /**
     * Constructs a new {@link ListTag} from
     * the given {@link List}.
     *
     * @param list The list
     * @return The list tag
     */
    public static <T extends Tag<?>> ListTag<T> of(List<T> list) {
        return new ListTag<>(list);
    }

    private Class<T> tagType;

    /**
     * Constructs a new {@link ListTag}.
     */
    public ListTag() {
    }

    private ListTag(List<T> list) {
        addAll(list);
    }

    @Override
    public List<T> get() {
        return this;
    }

    @Override
    public void set(List<T> value) {
        clear();
        addAll(value);
    }

    @Override
    public T set(int index, T e) {
        requireNonNull(e, "null isn't supported");
        if (this.tagType != null && !this.tagType.isInstance(e)) {
            throw new IllegalArgumentException("This ListTag only supports '" + this.tagType.getName() + "'s");
        }
        return super.set(index, e);
    }

    @Override
    public boolean add(T e) {
        requireNonNull(e, "null isn't supported");
        if (!isEmpty() && this.tagType != null && !this.tagType.isInstance(e)) {
            throw new IllegalArgumentException("This ListTag only supports '" + this.tagType.getName() + "'s");
        }
        if (super.add(e)) {
            if (isEmpty() || this.tagType == null) {
                this.tagType = (Class<T>) e.getClass();
            }
            return true;
        }
        return false;
    }

    @Override
    public void add(int index, T e) {
        requireNonNull(e, "null isn't supported");
        if (!isEmpty() && this.tagType != null && !this.tagType.isInstance(e)) {
            throw new IllegalArgumentException("This ListTag only supports '" + this.tagType.getName() + "'s");
        }
        super.add(index, e);
        if (this.tagType == null || isEmpty()) {
            this.tagType = (Class<T>) e.getClass();
        }
    }

    private Class<T> validate(Collection<? extends T> c) {
        Class<T> tagType = isEmpty() ? null : this.tagType;
        for (T e : c) {
            requireNonNull(e, "null isn't supported");
            if (tagType != null) {
                if (this.tagType != null) {
                    throw new IllegalArgumentException("This ListTag only supports '" + this.tagType.getClass() + "'s");
                } else {
                    throw new IllegalArgumentException("This ListTag doesn't support different Tag types.");
                }
            } else {
                tagType = (Class<T>) e.getClass();
            }
        }
        return tagType;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        final Class<T> tagType = validate(c);
        if (super.addAll(c)) {
            if (this.tagType == null) {
                this.tagType = tagType;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        final Class<T> tagType = validate(c);
        if (super.addAll(index, c)) {
            if (this.tagType == null) {
                this.tagType = tagType;
            }
            return true;
        }
        return false;
    }
}
