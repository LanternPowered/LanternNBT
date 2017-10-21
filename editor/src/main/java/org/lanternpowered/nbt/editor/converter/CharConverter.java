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
package org.lanternpowered.nbt.editor.converter;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public final class CharConverter extends AbstractConverter<Character> {

    public static final CharConverter INSTANCE = new CharConverter();

    private CharConverter() {
    }

    @Override
    public String toString(Character object) {
        return object == null ? "" : object.toString();
    }

    @Override
    public Character fromString(String string) {
        return string == null || string.length() != 1 ? null : string.charAt(0);
    }

    @Override
    protected UnaryOperator<TextFormatter.Change> getFilter() {
        return change -> {
            final String newText = change.getControlNewText();
            return newText.isEmpty() || newText.length() == 1 ? change : null;
        };
    }
}
