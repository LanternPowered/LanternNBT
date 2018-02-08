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
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
public final class ListTag<T extends Tag<?>> extends ArrayList<T> implements Tag<List<T>> {

    /**
     * Constructs a {@link ListTag} from the
     * given byte {@link Iterable}.
     *
     * @param byteIterable The byte iterable
     * @return The list tag
     */
    public static ListTag<ByteTag> ofBytes(Iterable<Byte> byteIterable) {
        return ofBytes(byteIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given byte {@link Iterable}.
     *
     * @param byteIterable The byte iterable
     * @param defaultValue The default value, will be
     *                     used when a byte is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<ByteTag> ofBytes(Iterable<Byte> byteIterable,
            @Nullable Byte defaultValue) {
        return of(byteIterable, ByteTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given byte array.
     *
     * @param bytes The bytes
     * @return The list tag
     */
    public static ListTag<ByteTag> ofBytes(Byte[] bytes) {
        return ofBytes(bytes, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given byte array.
     *
     * @param bytes The bytes
     * @param defaultValue The default value, will be
     *                     used when a byte is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<ByteTag> ofBytes(Byte[] bytes,
            @Nullable Byte defaultValue) {
        return of(bytes, ByteTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given byte array.
     *
     * @param bytes The bytes
     * @return The list tag
     */
    public static ListTag<ByteTag> ofBytes(byte... bytes) {
        final ListTag<ByteTag> listTag = new ListTag<>();
        for (byte value : bytes) {
            listTag.add(new ByteTag(value));
        }
        return listTag;
    }

    /**
     * Constructs a {@link ListTag} from the
     * given int {@link Iterable}.
     *
     * @param integerIterable The integer iterable
     * @return The list tag
     */
    public static ListTag<IntTag> ofInts(Iterable<Integer> integerIterable) {
        return ofInts(integerIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given int {@link Iterable}.
     *
     * @param integerIterable The integer iterable
     * @param defaultValue The default value, will be
     *                     used when a int is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<IntTag> ofInts(Iterable<Integer> integerIterable,
            @Nullable Integer defaultValue) {
        return of(integerIterable, IntTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given int array.
     *
     * @param integers The integers
     * @return The list tag
     */
    public static ListTag<IntTag> ofInts(Integer[] integers) {
        return ofInts(integers, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given int array.
     *
     * @param integers The integers
     * @param defaultValue The default value, will be
     *                     used when a int is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<IntTag> ofInts(Integer[] integers,
            @Nullable Integer defaultValue) {
        return of(integers, IntTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given int array.
     *
     * @param integers The integers
     * @return The list tag
     */
    public static ListTag<IntTag> ofInts(int... integers) {
        final ListTag<IntTag> listTag = new ListTag<>();
        for (int value : integers) {
            listTag.add(new IntTag(value));
        }
        return listTag;
    }

    /**
     * Constructs a {@link ListTag} from the
     * given short {@link Iterable}.
     *
     * @param shortIterable The short iterable
     * @return The list tag
     */
    public static ListTag<ShortTag> ofShorts(Iterable<Short> shortIterable) {
        return ofShorts(shortIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given short {@link Iterable}.
     *
     * @param shortIterable The short iterable
     * @param defaultValue The default value, will be
     *                     used when a short is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<ShortTag> ofShorts(Iterable<Short> shortIterable,
            @Nullable Short defaultValue) {
        return of(shortIterable, ShortTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given short array.
     *
     * @param shorts The shorts
     * @return The list tag
     */
    public static ListTag<ShortTag> ofShorts(Short[] shorts) {
        return ofShorts(shorts, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given short array.
     *
     * @param shorts The shorts
     * @param defaultValue The default value, will be
     *                     used when a short is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<ShortTag> ofShorts(Short[] shorts,
            @Nullable Short defaultValue) {
        return of(shorts, ShortTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given short array.
     *
     * @param shorts The shorts
     * @return The list tag
     */
    public static ListTag<ShortTag> ofShorts(short... shorts) {
        final ListTag<ShortTag> listTag = new ListTag<>();
        for (short value : shorts) {
            listTag.add(new ShortTag(value));
        }
        return listTag;
    }

    /**
     * Constructs a {@link ListTag} from the
     * given float {@link Iterable}.
     *
     * @param floatIterable The float iterable
     * @return The list tag
     */
    public static ListTag<FloatTag> ofFloats(Iterable<Float> floatIterable) {
        return ofFloats(floatIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given float {@link Iterable}.
     *
     * @param floatIterable The float iterable
     * @param defaultValue The default value, will be
     *                     used when a float is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<FloatTag> ofFloats(Iterable<Float> floatIterable,
            @Nullable Float defaultValue) {
        return of(floatIterable, FloatTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given float array.
     *
     * @param floats The floats
     * @return The list tag
     */
    public static ListTag<FloatTag> ofFloats(Float[] floats) {
        return ofFloats(floats, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given float array.
     *
     * @param floats The floats
     * @param defaultValue The default value, will be
     *                     used when a float is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<FloatTag> ofFloats(Float[] floats,
            @Nullable Float defaultValue) {
        return of(floats, FloatTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given float array.
     *
     * @param floats The floats
     * @return The list tag
     */
    public static ListTag<FloatTag> ofFloats(float... floats) {
        final ListTag<FloatTag> listTag = new ListTag<>();
        for (float value : floats) {
            listTag.add(new FloatTag(value));
        }
        return listTag;
    }

    /**
     * Converts the {@link ListTag} that holds
     * {@link DoubleTag}s into a double {@link List}.
     *
     * @param doubleListTag The double list tag
     * @return The double list
     */
    public static List<Double> toDoubleList(ListTag<DoubleTag> doubleListTag) {
        return doubleListTag.stream().map(DoubleTag::get).collect(Collectors.toList());
    }

    /**
     * Constructs a {@link ListTag} from the
     * given double {@link Iterable}.
     *
     * @param doubleIterable The double iterable
     * @return The list tag
     */
    public static ListTag<DoubleTag> ofDoubles(Iterable<Double> doubleIterable) {
        return ofDoubles(doubleIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given double {@link Iterable}.
     *
     * @param doubleIterable The double iterable
     * @param defaultValue The default value, will be
     *                     used when a double is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<DoubleTag> ofDoubles(Iterable<Double> doubleIterable,
            @Nullable Double defaultValue) {
        return of(doubleIterable, DoubleTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given double array.
     *
     * @param doubles The doubles
     * @return The list tag
     */
    public static ListTag<DoubleTag> ofDoubles(Double[] doubles) {
        return ofDoubles(doubles, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given double array.
     *
     * @param doubles The doubles
     * @param defaultValue The default value, will be
     *                     used when a double is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<DoubleTag> ofDoubles(Double[] doubles,
            @Nullable Double defaultValue) {
        return of(doubles, DoubleTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given double array.
     *
     * @param doubles The doubles
     * @return The list tag
     */
    public static ListTag<DoubleTag> ofDoubles(double... doubles) {
        final ListTag<DoubleTag> listTag = new ListTag<>();
        for (double value : doubles) {
            listTag.add(new DoubleTag(value));
        }
        return listTag;
    }

    /**
     * Converts the {@link ListTag} that holds
     * {@link LongTag}s into a long {@link List}.
     *
     * @param longListTag The long list tag
     * @return The long list
     */
    public static List<Long> toLongList(ListTag<LongTag> longListTag) {
        return longListTag.stream().map(LongTag::get).collect(Collectors.toList());
    }

    /**
     * Constructs a {@link ListTag} from the
     * given long {@link Iterable}.
     *
     * @param longIterable The long iterable
     * @return The list tag
     */
    public static ListTag<LongTag> ofLongs(Iterable<Long> longIterable) {
        return ofLongs(longIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given long {@link Iterable}.
     *
     * @param longIterable The long iterable
     * @param defaultValue The default value, will be
     *                     used when a long is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<LongTag> ofLongs(Iterable<Long> longIterable,
            @Nullable Long defaultValue) {
        return of(longIterable, LongTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given long array.
     *
     * @param longs The longs
     * @return The list tag
     */
    public static ListTag<LongTag> ofLongs(Long[] longs) {
        return ofLongs(longs, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given long array.
     *
     * @param longs The longs
     * @param defaultValue The default value, will be
     *                     used when a long is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<LongTag> ofLongs(Long[] longs,
            @Nullable Long defaultValue) {
        return of(longs, LongTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given long array.
     *
     * @param longs The longs
     * @return The list tag
     */
    public static ListTag<LongTag> ofLongs(long... longs) {
        final ListTag<LongTag> listTag = new ListTag<>();
        for (long value : longs) {
            listTag.add(new LongTag(value));
        }
        return listTag;
    }

    /**
     * Constructs a {@link ListTag} from the
     * given boolean {@link Iterable}.
     *
     * @param booleanIterable The boolean iterable
     * @return The list tag
     */
    public static ListTag<BooleanTag> ofBooleans(Iterable<Boolean> booleanIterable) {
        return ofBooleans(booleanIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given boolean {@link Iterable}.
     *
     * @param booleanIterable The boolean iterable
     * @param defaultValue The default value, will be
     *                     used when a boolean is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<BooleanTag> ofBooleans(Iterable<Boolean> booleanIterable,
            @Nullable Boolean defaultValue) {
        return of(booleanIterable, BooleanTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given boolean array.
     *
     * @param booleans The booleans
     * @return The list tag
     */
    public static ListTag<BooleanTag> ofBooleans(Boolean[] booleans) {
        return ofBooleans(booleans, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given boolean array.
     *
     * @param booleans The booleans
     * @param defaultValue The default value, will be
     *                     used when a boolean is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<BooleanTag> ofBooleans(Boolean[] booleans,
            @Nullable Boolean defaultValue) {
        return of(booleans, BooleanTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given boolean array.
     *
     * @param booleans The booleans
     * @return The list tag
     */
    public static ListTag<BooleanTag> ofBooleans(boolean... booleans) {
        final ListTag<BooleanTag> listTag = new ListTag<>();
        for (boolean value : booleans) {
            listTag.add(new BooleanTag(value));
        }
        return listTag;
    }

    /**
     * Constructs a {@link ListTag} from the
     * given char {@link Iterable}.
     *
     * @param charIterable The char iterable
     * @return The list tag
     */
    public static ListTag<CharTag> ofChars(Iterable<Character> charIterable) {
        return ofChars(charIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given char {@link Iterable}.
     *
     * @param charIterable The char iterable
     * @param defaultValue The default value, will be
     *                     used when a char is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<CharTag> ofChars(Iterable<Character> charIterable,
            @Nullable Character defaultValue) {
        return of(charIterable, CharTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given char array.
     *
     * @param chars The chars
     * @return The list tag
     */
    public static ListTag<CharTag> ofChars(Character[] chars) {
        return ofChars(chars, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given char array.
     *
     * @param chars The chars
     * @param defaultValue The default value, will be
     *                     used when a char is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<CharTag> ofChars(Character[] chars,
            @Nullable Character defaultValue) {
        return of(chars, CharTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given char array.
     *
     * @param chars The chars
     * @return The list tag
     */
    public static ListTag<CharTag> ofChars(char... chars) {
        final ListTag<CharTag> listTag = new ListTag<>();
        for (char value : chars) {
            listTag.add(new CharTag(value));
        }
        return listTag;
    }

    /**
     * Constructs a {@link ListTag} from the
     * given String {@link Iterable}.
     *
     * @param stringIterable The String iterable
     * @return The list tag
     */
    public static ListTag<StringTag> ofStrings(Iterable<String> stringIterable) {
        return ofStrings(stringIterable, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given String {@link Iterable}.
     *
     * @param stringIterable The String iterable
     * @param defaultValue The default value, will be
     *                     used when a string is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<StringTag> ofStrings(Iterable<String> stringIterable,
            @Nullable String defaultValue) {
        return of(stringIterable, StringTag::new, defaultValue);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given String array.
     *
     * @param strings The Strings
     * @return The list tag
     */
    public static ListTag<StringTag> ofStrings(String... strings) {
        return ofStrings(strings, null);
    }

    /**
     * Constructs a {@link ListTag} from the
     * given String array.
     *
     * @param strings The Strings
     * @param defaultValue The default value, will be
     *                     used when a string is {@code null}.
     *                     Providing a {@code null} default value
     *                     will skip the entry.
     * @return The list tag
     */
    public static ListTag<StringTag> ofStrings(String[] strings,
            @Nullable String defaultValue) {
        return of(strings, StringTag::new, defaultValue);
    }

    /**
     * Unwraps all the {@link Tag} elements in the
     * {@link ListTag} and puts them into a {@link List}.
     *
     * @param listTag The list tag
     * @return The list
     */
    public static <E> List<E> toList(ListTag<? extends Tag<E>> listTag) {
        return listTag.stream().map(Tag::get).collect(Collectors.toList());
    }

    public static <T extends Tag<E>, E> ListTag<T> of(Iterable<E> iterable, Function<E, T> elementTransformer) {
        return of(iterable, elementTransformer, null);
    }

    public static <T extends Tag<E>, E> ListTag<T> of(Iterable<E> iterable, Function<E, T> elementTransformer,
            @Nullable E defaultValue) {
        final ListTag<T> listTag = new ListTag<>();
        for (E value : iterable) {
            if (value == null) {
                value = defaultValue;
            }
            if (value != null) {
                listTag.add(elementTransformer.apply(value));
            }
        }
        return listTag;
    }

    public static <T extends Tag<E>, E> ListTag<T> of(E[] array, Function<E, T> elementTransformer) {
        return of(array, elementTransformer, null);
    }

    public static <T extends Tag<E>, E> ListTag<T> of(E[] array, Function<E, T> elementTransformer,
            @Nullable E defaultValue) {
        final ListTag<T> listTag = new ListTag<>();
        for (E value : array) {
            if (value == null) {
                value = defaultValue;
            }
            if (value != null) {
                listTag.add(elementTransformer.apply(value));
            }
        }
        return listTag;
    }

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

    /**
     * Constructs a new {@link ListTag} from
     * the given {@link Iterable}.
     *
     * @param iterable The iterable
     * @return The list tag
     */
    public static <T extends Tag<?>> ListTag<T> of(Iterable<T> iterable) {
        return new ListTag<>(iterable);
    }

    private Class<T> tagType;

    /**
     * Constructs a new {@link ListTag}.
     */
    public ListTag() {
    }

    private ListTag(Iterable<T> iterable) {
        iterable.forEach(this::add);
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
