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
package org.lanternpowered.nbt.io;

import org.lanternpowered.nbt.BooleanArrayTag;
import org.lanternpowered.nbt.BooleanTag;
import org.lanternpowered.nbt.ByteArrayTag;
import org.lanternpowered.nbt.ByteTag;
import org.lanternpowered.nbt.CharArrayTag;
import org.lanternpowered.nbt.CharTag;
import org.lanternpowered.nbt.CompoundArrayTag;
import org.lanternpowered.nbt.CompoundTag;
import org.lanternpowered.nbt.DoubleArrayTag;
import org.lanternpowered.nbt.DoubleTag;
import org.lanternpowered.nbt.FloatArrayTag;
import org.lanternpowered.nbt.FloatTag;
import org.lanternpowered.nbt.IntArrayTag;
import org.lanternpowered.nbt.IntTag;
import org.lanternpowered.nbt.ListTag;
import org.lanternpowered.nbt.LongArrayTag;
import org.lanternpowered.nbt.LongTag;
import org.lanternpowered.nbt.ShortArrayTag;
import org.lanternpowered.nbt.ShortTag;
import org.lanternpowered.nbt.StringArrayTag;
import org.lanternpowered.nbt.StringTag;
import org.lanternpowered.nbt.Tag;

import java.util.HashMap;
import java.util.Map;

enum NbtType {
    // Official types
    END                     (null, 0),
    BYTE                    (ByteTag.class, 1),
    SHORT                   (ShortTag.class, 2),
    INT                     (IntTag.class, 3),
    LONG                    (LongTag.class, 4),
    FLOAT                   (FloatTag.class, 5),
    DOUBLE                  (DoubleTag.class, 6),
    BYTE_ARRAY              (ByteArrayTag.class, 7),
    STRING                  (StringTag.class, 8),
    LIST                    (ListTag.class, 9),
    COMPOUND                (CompoundTag.class, 10),
    INT_ARRAY               (IntArrayTag.class, 11),
    LONG_ARRAY              (LongArrayTag.class, 12),

    // Sponge and lantern types, but remaining
    // compatible with the official ones.
    BOOLEAN                 (BooleanTag.class, "Boolean", 1), // Also used in sponge
    BOOLEAN_ARRAY           (BooleanArrayTag.class, "boolean[]", 7),
    SHORT_ARRAY             (ShortArrayTag.class, "short[]", 9),
    FLOAT_ARRAY             (FloatArrayTag.class, "float[]", 9),
    DOUBLE_ARRAY            (DoubleArrayTag.class, "double[]", 9),
    STRING_ARRAY            (StringArrayTag.class, "string[]", 9),
    CHAR                    (CharTag.class, "char", 8),
    CHAR_ARRAY              (CharArrayTag.class, "char[]", 8),
    COMPOUND_ARRAY          (CompoundArrayTag.class, "compound[]", 8),

    UNKNOWN                 (null, 13),
    ;

    static final Map<String, NbtType> bySuffix = new HashMap<>();
    static final Map<Class<?>, NbtType> byClass = new HashMap<>();
    static final NbtType[] byIndex;

    final int type;
    final String suffix;
    final Class<? extends Tag> tagClass;

    NbtType(Class<? extends Tag> tagClass, int type) {
        this(tagClass, null, type);
    }

    NbtType(Class<? extends Tag> tagClass, String suffix, int type) {
        this.tagClass = tagClass;
        this.suffix = suffix;
        this.type = type;
    }

    static {
        byIndex = new NbtType[UNKNOWN.type + 1];
        for (NbtType nbtType : values()) {
            bySuffix.put(nbtType.suffix, nbtType);
            if (nbtType.tagClass != null) {
                byClass.put(nbtType.tagClass, nbtType);
            }
            if (nbtType.suffix == null) {
                byIndex[nbtType.type] = nbtType;
            }
        }
    }
}
