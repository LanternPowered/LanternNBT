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

import org.lanternpowered.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeCollectionElement<T extends Tag<?>, V> extends TreeTagElement<T> {

    final List<TreeCollectionValueElement<V>> elements = new ArrayList<>();

    TreeCollectionElement(T tag, TreeTagElement parent, String name) {
        super(tag, parent, name);
        for (int i = 0; i < length(); i++) {
            insertElementAt(0);
        }
    }

    private void insertElementAt(int index) {
        final TreeCollectionValueElement<V> element = new TreeCollectionValueElement<>(this);
        this.elements.add(index, element);
        // Add the new constructed tree item to the parent
        this.treeItem.getChildren().add(index, element.treeItem);
    }

    public TreeCollectionValueElement<V> getElement(int index) {
        return this.elements.get(index);
    }

    public abstract int length();

    /**
     * Sets the value of type {@link V}
     * at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public void setAt(int index, V value) {
        final TreeCollectionValueElement<V> element = this.elements.get(index);
        // Force the value to update
        element.treeItem.setValue(element);
    }

    /**
     * Inserts a value at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public void insertAt(int index, V value) {
        insertElementAt(index);
    }

    /**
     * Removes a value from the specified index.
     *
     * @param index The index
     */
    public void removeAt(int index) {
        final TreeCollectionValueElement<V> element = this.elements.remove(index);
        // Remove the removed tree item from it's parent
        this.treeItem.getChildren().remove(element.treeItem);
    }

    /**
     * Gets the value of type {@link V}
     * at the specified index.
     *
     * @param index The index
     * @return The value
     */
    public abstract V getAt(int index);
}
