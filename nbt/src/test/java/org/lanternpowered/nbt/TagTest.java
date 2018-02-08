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

import org.junit.Test;
import org.lanternpowered.nbt.io.NbtTagInputStream;
import org.lanternpowered.nbt.io.NbtTagOutputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class TagTest {

    @Test
    public void test() throws IOException {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("A", new BooleanTag(true));
        compoundTag.put("B", new BooleanArrayTag(true, false, true, true));
        compoundTag.put("C", new ByteTag((byte) 5));
        compoundTag.put("D", new ByteArrayTag((byte) 6, (byte) 9, (byte) 20, (byte) 30));
        compoundTag.put("E", new CharTag('C'));
        compoundTag.put("F", new CharArrayTag('A', 'B', 'C', 'D'));
        compoundTag.put("G", new DoubleTag(7.3));
        compoundTag.put("H", new DoubleArrayTag(7.3, 7.4, 5.3, 1.2));
        compoundTag.put("I", new FloatTag(5.1f));
        compoundTag.put("J", new FloatArrayTag(5.1f, 5.32f));
        compoundTag.put("K", new IntTag(3));
        compoundTag.put("L", new IntArrayTag(30, 50, 60, 80));
        compoundTag.put("M", new LongTag(40));
        //compoundTag.put("N", new LongArrayTag(30, 60));
        compoundTag.put("O", new ShortTag((short) 3));
        compoundTag.put("P", new ShortArrayTag((short) 3, (short) 7));
        compoundTag.put("Q", new StringTag("Test"));
        compoundTag.put("R", new StringArrayTag("Haha", "Hehe", "Hoho"));
        final CompoundTag compoundTag1 = new CompoundTag();
        compoundTag1.put("A", new IntArrayTag(1000));
        compoundTag.put("S", compoundTag1);
        final ListTag<IntTag> listTag = new ListTag<>();
        listTag.add(new IntTag(20));
        listTag.add(new IntTag(30));
        compoundTag.put("T", listTag);
        final ListTag<DoubleArrayTag> listTag1 = new ListTag<>();
        listTag1.add(new DoubleArrayTag(7.3, 7.4));
        listTag1.add(new DoubleArrayTag(5.3, 1.2));
        compoundTag.put("U", listTag1);

        final NbtTagOutputStream nos = new NbtTagOutputStream(
                new GZIPOutputStream(Files.newOutputStream(Paths.get("test.nbt"))));
        nos.write(compoundTag);
        nos.flush();
        nos.close();

        final NbtTagInputStream nis = new NbtTagInputStream(
                new GZIPInputStream(Files.newInputStream(Paths.get("test.nbt"))));
        System.out.println(nis.read());
        nis.close();
    }
}
