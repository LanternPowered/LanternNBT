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
package org.lanternpowered.nbt.editor;

import org.lanternpowered.nbt.ListTag;
import org.lanternpowered.nbt.Tag;

@SuppressWarnings("unchecked")
final class TreeListElement<V> extends TreeCollectionElement<ListTag<? extends Tag<V>>, V> {

    TreeListElement(ListTag<? extends Tag<V>> tag, TreeTagElement parent, String name) {
        super(tag, parent, name);
    }

    @Override
    public int length() {
        return this.tag.size();
    }

    @Override
    public void setAt(int index, V value) {
        this.tag.get(index).set(value);
        super.setAt(index, value);
    }

    @Override
    public void insertAt(int index, V value) {
        ((ListTag) this.tag).add(index, Tag.fromObject(value));
        this.elements.add(index, new TreeCollectionValueElement<>(this.parent));
        super.insertAt(index, value);
    }

    @Override
    public void removeAt(int index) {
        this.tag.remove(index);
        this.elements.remove(index);
        super.removeAt(index);
    }

    @Override
    public V getAt(int index) {
        return this.tag.get(index).get();
    }
}
