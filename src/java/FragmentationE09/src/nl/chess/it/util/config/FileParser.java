/**
 * Copyright (c) 2003, 2004, Chess iT
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of Chess iT, nor the names of its contributors may be used 
 *   to endorse or promote products derived from this software without specific
 *   prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package nl.chess.it.util.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Guus Bosman (Chess-iT)
 */
public class FileParser {

    public File getExistingReadableFile(String key, String value) {
        value = getFileName(key, value);
        File file = new File(value);
        if (!file.exists()) {
            throw new InvalidPropertyException(key, value, "existing file (doesn't exist)");
        }
        if (!file.canRead()) {
            throw new InvalidPropertyException(key, value, "readable file (can't read)");
        }
        return file;
    }

    /**
     * <p>
     * Returns a writable, existing File with the given name.
     * </p>
     * <b>Experimental </b>. Does not support names with whitespace in it.
     * 
     * @param key
     * @return
     */
    public File getWriteableFile(String key, String value) {

        value = getFileName(key, value);
        File file = new File(value);
        try {

            if (!file.exists()) {
                /**
                 * Try to create the file, including the directories leading to it.
                 */
                File parentFile = file.getParentFile();
                if (parentFile != null && (!parentFile.exists())) {
                    if (!parentFile.mkdirs()) {
                        throw new InvalidPropertyException(key, value, "writeable file (can't create directories '"
                                + parentFile + "')");
                    }
                }
                if (!file.createNewFile()) {
                    throw new InvalidPropertyException(key, value, "writeable file (can't create)");
                }
            }
            if (!file.canWrite()) {
                throw new InvalidPropertyException(key, value, "writeable file (can't write)");
            }
            return file;
        } catch (IOException e) {
            throw new InvalidPropertyException(key, value, "writeable file (I/O exception)");
        }

    }

    protected String getFileName(String key, String value) {
        if (containsStrangeWhiteSpace(value)) {
            throw new InvalidPropertyException(key, value, "writeable file (strange whitespace in name; wrong slashes used?)");
        }
        try {
            URL url = new URL(value);
            value = url.getFile().toString();
        } catch (MalformedURLException e1) {
            // okay, not an URL. Let's continue and assume it's a local file reference.

        }
        return value;
    }

    /**
     * Whether or not the filename contains whitespace that is not usuable for filenames (on most
     * operating systems). These are: tab (\t) and newline (\n).
     * 
     * @param fileName
     * @return
     */
    private boolean containsStrangeWhiteSpace(String fileName) {
        char[] cs = fileName.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if ("\t\n".indexOf(cs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }
}