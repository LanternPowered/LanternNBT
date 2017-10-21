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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Represents the compression format of data.
 */
public enum Compression {
    GZIP {
        @Override
        public OutputStream transform(OutputStream outputStream) throws IOException {
            return new GZIPOutputStream(outputStream);
        }

        @Override
        public InputStream transform(InputStream inputStream) throws IOException {
            return new GZIPInputStream(inputStream);
        }

        @Override
        public boolean test(InputStream inputStream) {
            int magic;
            try {
                magic = inputStream.read() & 0xff | ((inputStream.read() << 8) & 0xff00);
            } catch (IOException e) {
                return false;
            }
            return magic == GZIPInputStream.GZIP_MAGIC;
        }
    },
    NONE {
        @Override
        public OutputStream transform(OutputStream outputStream) throws IOException {
            return outputStream;
        }

        @Override
        public InputStream transform(InputStream inputStream) throws IOException {
            return inputStream;
        }

        @Override
        public boolean test(InputStream inputStream) {
            // Every file can be read through this compression,
            // the data may not be so useful though.
            return true;
        }
    },
    ;

    /**
     * Transforms the {@link OutputStream} for this
     * {@link OutputStream}.
     *
     * @param outputStream The output stream
     * @return The output stream
     */
    public abstract OutputStream transform(OutputStream outputStream) throws IOException;

    /**
     * Transforms the {@link InputStream} for this
     * {@link Compression}.
     *
     * @param inputStream The input stream
     * @return The input stream
     */
    public abstract InputStream transform(InputStream inputStream) throws IOException;

    /**
     * Tests whether the compression is applied to the
     * {@link InputStream}. The {@link InputStream} may be
     * useless afterwards.
     *
     * @param inputStream The input stream
     * @return Whether the compression is applied
     */
    public abstract boolean test(InputStream inputStream);
}
