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
import org.lanternpowered.nbt.MapArrayTag;
import org.lanternpowered.nbt.MapTag;
import org.lanternpowered.nbt.ShortArrayTag;
import org.lanternpowered.nbt.ShortTag;
import org.lanternpowered.nbt.StringArrayTag;
import org.lanternpowered.nbt.StringTag;
import org.lanternpowered.nbt.Tag;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * A output stream to write NBT {@link Tag}s.
 */
@SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
public final class NbtTagOutputStream implements TagOutputStream {

    private final DataOutputStream dos;

    /**
     * Constructs a new {@link NbtTagOutputStream}.
     *
     * @param dataOutputStream The data output stream
     */
    public NbtTagOutputStream(DataOutputStream dataOutputStream) {
        this.dos = requireNonNull(dataOutputStream, "dataOutputStream");
    }

    /**
     * Constructs a new {@link NbtTagOutputStream}.
     *
     * @param outputStream The output stream
     */
    public NbtTagOutputStream(OutputStream outputStream) {
        this(requireNonNull(outputStream, "outputStream") instanceof DataOutputStream ?
                (DataOutputStream) outputStream : new DataOutputStream(outputStream));
    }

    @Override
    public void close() throws IOException {
        this.dos.close();
    }

    @Override
    public void flush() throws IOException {
        this.dos.flush();
    }

    @Override
    public void write(Tag<?> tag) throws IOException {
        writeEntry("", tag);
    }

    @SuppressWarnings("unchecked")
    private void writePayload(NbtType nbtType, Tag<?> tag) throws IOException {
        switch (nbtType) {
            case BYTE:
                this.dos.writeByte(((ByteTag) tag).byteValue());
                break;
            case BYTE_ARRAY:
                final byte[] byteArray = ((ByteArrayTag) tag).get();
                this.dos.writeInt(byteArray.length);
                this.dos.write(byteArray);
                break;
            case SHORT:
                this.dos.writeShort(((ShortTag) tag).shortValue());
                break;
            case SHORT_ARRAY:
                final short[] shortArray = ((ShortArrayTag) tag).get();
                this.dos.writeByte(NbtType.SHORT.type);
                this.dos.writeInt(shortArray.length);
                for (short shortValue : shortArray) {
                    this.dos.writeShort(shortValue);
                }
                break;
            case CHAR:
                this.dos.writeUTF(new String(new char[] { ((CharTag) tag).charValue() }));
                break;
            case CHAR_ARRAY:
                this.dos.writeUTF(new String(((CharArrayTag) tag).get()));
                break;
            case INT:
                this.dos.writeInt(((IntTag) tag).intValue());
                break;
            case INT_ARRAY:
                final int[] intArray = ((IntArrayTag) tag).get();
                this.dos.writeInt(intArray.length);
                for (int intValue : intArray) {
                    this.dos.writeInt(intValue);
                }
                break;
            case LONG:
                this.dos.writeLong(((LongTag) tag).longValue());
                break;
            case LONG_ARRAY:
                final long[] longArray = ((LongArrayTag) tag).get();
                this.dos.writeInt(longArray.length);
                for (long longValue : longArray) {
                    this.dos.writeLong(longValue);
                }
                break;
            case FLOAT:
                this.dos.writeFloat(((FloatTag) tag).floatValue());
                break;
            case FLOAT_ARRAY:
                final float[] floatArray = ((FloatArrayTag) tag).get();
                this.dos.writeByte(NbtType.FLOAT.type);
                this.dos.writeInt(floatArray.length);
                for (float floatValue : floatArray) {
                    this.dos.writeFloat(floatValue);
                }
                break;
            case DOUBLE:
                this.dos.writeDouble(((DoubleTag) tag).doubleValue());
                break;
            case DOUBLE_ARRAY:
                final double[] doubleArray = ((DoubleArrayTag) tag).get();
                this.dos.writeByte(NbtType.DOUBLE.type);
                this.dos.writeInt(doubleArray.length);
                for (double doubleValue : doubleArray) {
                    this.dos.writeDouble(doubleValue);
                }
                break;
            case STRING:
                this.dos.writeUTF(((StringTag) tag).get());
                break;
            case STRING_ARRAY:
                final String[] stringArray = ((StringArrayTag) tag).get();
                this.dos.writeByte(NbtType.STRING.type);
                this.dos.writeInt(stringArray.length);
                for (String string : stringArray) {
                    this.dos.writeUTF(string);
                }
                break;
            case BOOLEAN:
                this.dos.writeBoolean(((BooleanTag) tag).booleanValue());
                break;
            case BOOLEAN_ARRAY:
                final boolean[] booleanArray = ((BooleanArrayTag) tag).get();
                int length = booleanArray.length / 8;
                if (booleanArray.length % 8 != 0) {
                    length++;
                }
                this.dos.writeInt(length + 2);
                this.dos.writeShort(booleanArray.length);
                int j = 0;
                for (int i = 0; i < length; i++) {
                    byte value = 0;
                    while (j < booleanArray.length) {
                        final int k = j % 8;
                        if (booleanArray[j++]) {
                            value |= 1 << k;
                        }
                    }
                    this.dos.writeByte(value);
                }
                break;
            case LIST:
                writeList(nbtType, (ListTag<?>) tag);
                break;
            case COMPOUND:
                writeCompound((CompoundTag) tag);
                break;
            case COMPOUND_ARRAY:
                final CompoundTag[] compoundArray = ((CompoundArrayTag) tag).get();
                this.dos.writeByte(NbtType.COMPOUND.type);
                this.dos.writeInt(compoundArray.length);
                for (CompoundTag compoundTag : compoundArray) {
                    writeCompound(compoundTag);
                }
                break;
            case MAP:
                writeMap((MapTag) tag);
                break;
            case MAP_ARRAY:
                final MapTag[] mapTagArray = ((MapArrayTag) tag).get();
                this.dos.writeByte(NbtType.LIST.type);
                this.dos.writeInt(mapTagArray.length);
                for (MapTag mapTag : mapTagArray) {
                    writeMap(mapTag);
                }
                break;
            default:
                throw new IOException("Attempted to serialize a unsupported tag type: " + tag.getClass().getName());
        }
    }

    private void writeCompound(CompoundTag tag) throws IOException {
        for (Map.Entry<String, Tag<?>> entry : tag.entrySet()) {
            writeEntry(entry.getKey(), entry.getValue());
        }
        this.dos.writeByte(NbtType.END.type);
    }

    private void writeMap(MapTag tag) throws IOException {
        this.dos.writeByte(NbtType.COMPOUND.type);
        this.dos.writeInt(tag.size());
        for (Map.Entry<Tag, Tag> entry : ((Map<Tag, Tag>) tag).entrySet()) {
            writeEntry(NbtType.mapKeyName, entry.getKey());
            writeEntry(NbtType.mapValueName, entry.getValue());
            this.dos.writeByte(NbtType.END.type);
        }
    }

    private void writeList(NbtType nbtType, ListTag<?> listTag) throws IOException {
        this.dos.writeByte(nbtType.type);
        this.dos.writeInt(listTag.size());
        for (Tag<?> tag : listTag) {
            writePayload(nbtType, tag);
        }
    }

    private void writeEntry(String key, Tag<?> tag) throws IOException {
        NbtType nbtType = NbtType.byClass.get(tag.getClass());
        this.dos.writeByte(nbtType.type);
        if (nbtType == NbtType.LIST) {
            final ListTag<?> listTag = (ListTag<?>) tag;
            if (listTag.isEmpty()) {
                nbtType = NbtType.END;
            } else {
                nbtType = NbtType.byClass.get(listTag.get(0).getClass());
                if (nbtType.suffix != null) {
                    key += "$List$" + nbtType.suffix;
                }
            }
            this.dos.writeUTF(key);
            writeList(nbtType, listTag);
        } else {
            if (nbtType.suffix != null) {
                key += '$' + nbtType.suffix;
            }
            this.dos.writeUTF(key);
            try {
                writePayload(nbtType, tag);
            } catch (Exception e) {
                throw new IOException("Exception while serializing key: " + key, e);
            }
        }
    }
}
