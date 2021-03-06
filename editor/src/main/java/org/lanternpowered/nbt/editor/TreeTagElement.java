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

import org.lanternpowered.nbt.CompoundTag;
import org.lanternpowered.nbt.Tag;

/**
 * A element which wraps around a {@link Tag} to provide
 * it's relation to it's parent, if present.
 */
class TreeTagElement<T extends Tag<?>> extends TreeElement {

    /**
     * The compression of the file. Only present
     * if this is the root tag.
     */
    Compression compression;

    /**
     * The wrapped {@link Tag}.
     */
    final T tag;

    /**
     * The name of the {@link Tag} when
     * mapped in a {@link CompoundTag}.
     */
    String name;

    /**
     * Constructs a new {@link TreeTagElement}.
     *
     * @param tag The wrapped tag
     * @param parent The parent, if present
     * @param name The tag name, when the parent is a CompoundTag
     */
    TreeTagElement(T tag, TreeTagElement parent, String name) {
        super(parent);
        this.name = name;
        this.tag = tag;
    }

    @Override
    void remove() {
        ((CompoundTag) this.parent.tag).remove(this.name);
        this.parent.treeItem.getChildren().remove(this.treeItem);
    }
}
