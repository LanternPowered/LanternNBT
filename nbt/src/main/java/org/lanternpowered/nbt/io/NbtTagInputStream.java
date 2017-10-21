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

import static java.util.Objects.requireNonNull;

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

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A input stream to read NBT {@link Tag}s.
 */
public final class NbtTagInputStream implements TagInputStream {

    private static final short[] EMPTY_SHORT_ARRAY = new short[0];
    private static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final DataInputStream dis;
    private final int maximumDepth;

    /**
     * Constructs a new {@link NbtTagInputStream}.
     *
     * @param dataInputStream The data input stream
     */
    public NbtTagInputStream(DataInputStream dataInputStream) {
        this(dataInputStream, Integer.MAX_VALUE);
    }

    /**
     * Constructs a new {@link NbtTagInputStream}.
     *
     * @param dataInputStream The data input stream
     * @param maximumDepth The maximum depth of the data contains
     */
    public NbtTagInputStream(DataInputStream dataInputStream, int maximumDepth) {
        this.dis = requireNonNull(dataInputStream, "dataInputStream");
        this.maximumDepth = maximumDepth;
    }

    /**
     * Constructs a new {@link NbtTagInputStream}.
     *
     * @param inputStream The input stream
     */
    public NbtTagInputStream(InputStream inputStream) {
        this(inputStream, Integer.MAX_VALUE);
    }

    /**
     * Constructs a new {@link NbtTagInputStream}.
     *
     * @param inputStream The input stream
     * @param maximumDepth The maximum depth of the data contains
     */
    public NbtTagInputStream(InputStream inputStream, int maximumDepth) {
        this(requireNonNull(inputStream, "inputStream") instanceof DataInputStream ?
                (DataInputStream) inputStream : new DataInputStream(inputStream), maximumDepth);
    }

    @Override
    public void close() throws IOException {
        this.dis.close();
    }

    @Override
    public Tag<?> read() throws IOException {
        Entry entry = readEntry();
        if (entry == null) {
            throw new IOException("There is no more data to read.");
        }
        return readObject(entry, 0);
    }

    private Tag<?> readObject(Entry entry, int depth) throws IOException {
        return readPayload(entry.type, entry.listType, depth);
    }

    private Entry readEntry() throws IOException {
        final byte type = this.dis.readByte();
        if (type == NbtType.END.type) {
            return null;
        }
        String name = this.dis.readUTF();
        int index = name.lastIndexOf('$');
        NbtType nbtType = type < 0 || type >= NbtType.byIndex.length ? null : NbtType.byIndex[type];
        if (nbtType == null) {
            throw new IOException("Unknown NBT Type with id: " + type);
        }
        NbtType listNbtType = null;
        if (index != -1) {
            final String suffix = name.substring(index + 1);
            name = name.substring(0, index);
            final NbtType nbtType1 = NbtType.bySuffix.get(suffix);
            if (nbtType1 != null) {
                if (nbtType == NbtType.LIST) {
                    index = name.lastIndexOf('$');
                    if (index != -1) {
                        final String li = name.substring(index + 1);
                        if (li.equals("List")) {
                            name = name.substring(0, index);
                            listNbtType = nbtType1;
                        }
                    }
                }
                if (listNbtType == null) {
                    nbtType = nbtType1;
                }
            }
        }
        return new Entry(name, nbtType, listNbtType);
    }

    private CompoundTag readCompound(int depth) throws IOException {
        final CompoundTag compoundTag = new CompoundTag();
        final int depth1 = depth + 1;
        Entry entry;
        while ((entry = readEntry()) != null) {
            compoundTag.put(entry.name, readObject(entry, depth1));
        }
        return compoundTag;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Tag<?> readPayload(NbtType nbtType, NbtType listNbtType, int depth) throws IOException {
        if (depth > this.maximumDepth) {
            throw new IOException("Attempted to read a data container with too high complexity,"
                    + " exceeded the maximum depth of " + this.maximumDepth);
        }
        switch (nbtType) {
            case BYTE:
                return new ByteTag(this.dis.readByte());
            case BYTE_ARRAY:
                final byte[] byteArray = new byte[this.dis.readInt()];
                this.dis.readFully(byteArray);
                return new ByteArrayTag(byteArray);
            case SHORT:
                return new ShortTag(this.dis.readShort());
            case SHORT_ARRAY:
                byte type = this.dis.readByte();
                int length = this.dis.readInt();
                if (type == NbtType.END.type) {
                    return new ShortArrayTag(EMPTY_SHORT_ARRAY);
                } else if (type != NbtType.SHORT.type) {
                    throw new IOException("Attempted to deserialize a Short Array (List) but the list type wasn't a short.");
                }
                final short[] shortArray = new short[length];
                for (int i = 0; i < shortArray.length; i++) {
                    shortArray[i] = this.dis.readShort();
                }
                return new ShortArrayTag(shortArray);
            case CHAR:
                final String charString = this.dis.readUTF();
                if (charString.length() != 1) {
                    throw new IOException("The Char string must be one character.");
                }
                return new CharTag(charString.charAt(0));
            case CHAR_ARRAY:
                return new CharArrayTag(this.dis.readUTF().toCharArray());
            case INT:
                return new IntTag(this.dis.readInt());
            case INT_ARRAY:
                final int[] intArray = new int[this.dis.readInt()];
                for (int i = 0; i < intArray.length; i++) {
                    intArray[i] = this.dis.readInt();
                }
                return new IntArrayTag(intArray);
            case LONG:
                return new LongTag(this.dis.readLong());
            case LONG_ARRAY:
                final long[] longArray = new long[this.dis.readInt()];
                for (int i = 0; i < longArray.length; i++) {
                    longArray[i] = this.dis.readLong();
                }
                return new LongArrayTag(longArray);
            case FLOAT:
                return new FloatTag(this.dis.readFloat());
            case FLOAT_ARRAY:
                type = this.dis.readByte();
                length = this.dis.readInt();
                if (type == NbtType.END.type) {
                    return new FloatArrayTag(EMPTY_FLOAT_ARRAY);
                } else if (type != NbtType.FLOAT.type) {
                    throw new IOException("Attempted to deserialize a Float Array (List) but the list type wasn't a float.");
                }
                final float[] floatArray = new float[length];
                for (int i = 0; i < floatArray.length; i++) {
                    floatArray[i] = this.dis.readFloat();
                }
                return new FloatArrayTag(floatArray);
            case DOUBLE:
                return new DoubleTag(this.dis.readDouble());
            case DOUBLE_ARRAY:
                type = this.dis.readByte();
                length = this.dis.readInt();
                if (type == NbtType.END.type) {
                    return new DoubleArrayTag(EMPTY_DOUBLE_ARRAY);
                } else if (type != NbtType.DOUBLE.type) {
                    throw new IOException("Attempted to deserialize a Double Array (List) but the list type wasn't a double.");
                }
                final double[] doubleArray = new double[length];
                for (int i = 0; i < doubleArray.length; i++) {
                    doubleArray[i] = this.dis.readDouble();
                }
                return new DoubleArrayTag(doubleArray);
            case STRING:
                return new StringTag(this.dis.readUTF());
            case STRING_ARRAY:
                type = this.dis.readByte();
                length = this.dis.readInt();
                if (type == NbtType.END.type) {
                    return new StringArrayTag(EMPTY_STRING_ARRAY);
                } else if (type != NbtType.STRING.type) {
                    throw new IOException("Attempted to deserialize a String Array (List) but the list type wasn't a string.");
                }
                final String[] stringArray = new String[length];
                for (int i = 0; i < stringArray.length; i++) {
                    stringArray[i] = this.dis.readUTF();
                }
                return new StringArrayTag(stringArray);
            case BOOLEAN:
                return new BooleanTag(this.dis.readBoolean());
            case BOOLEAN_ARRAY:
                int bitBytes = this.dis.readInt() - 2;
                final boolean[] booleanArray = new boolean[this.dis.readShort()];
                int j = 0;
                for (int i = 0; i < bitBytes; i++) {
                    final byte value = this.dis.readByte();
                    while (j < booleanArray.length) {
                        final int k = j % 8;
                        booleanArray[j++] = (value & (1 << k)) != 0;
                    }
                }
                return new BooleanArrayTag(booleanArray);
            case LIST:
                final byte listType = this.dis.readByte();
                if (listNbtType == null) {
                    listNbtType = listType < 0 || listType >= NbtType.byIndex.length ? null : NbtType.byIndex[listType];
                    if (listNbtType == null) {
                        throw new IOException("Unknown NBT Type with id: " + listType);
                    }
                }
                final int size = this.dis.readInt();
                final ListTag listTag = new ListTag();
                if (size == 0 || listNbtType == NbtType.END) {
                    return listTag;
                }
                int depth1 = depth + 1;
                for (int i = 0; i < size; i++) {
                    listTag.add(readPayload(listNbtType, null, depth1));
                }
                return listTag;
            case COMPOUND:
                return readCompound(depth);
            case COMPOUND_ARRAY:
                type = this.dis.readByte();
                length = this.dis.readInt();
                if (type == NbtType.END.type) {
                    return new FloatArrayTag(EMPTY_FLOAT_ARRAY);
                } else if (type != NbtType.COMPOUND.type) {
                    throw new IOException("Attempted to deserialize a Compound Array (List) but the list type wasn't a compound.");
                }
                depth1 = depth + 1;
                final CompoundTag[] compoundTags = new CompoundTag[length];
                for (int i = 0; i < compoundTags.length; i++) {
                    compoundTags[i] = readCompound(depth1);
                }
                return new CompoundArrayTag(compoundTags);
            default:
                throw new IOException("Attempted to deserialize a unknown nbt tag type: " + nbtType);
        }
    }

    private static class Entry {

        private final String name;
        private final NbtType type;
        private final NbtType listType;

        Entry(String name, NbtType type, NbtType listType) {
            this.listType = listType;
            this.name = name;
            this.type = type;
        }
    }
}
