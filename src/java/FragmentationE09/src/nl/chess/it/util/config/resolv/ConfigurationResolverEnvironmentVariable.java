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
package nl.chess.it.util.config.resolv;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nl.chess.it.util.config.ConfigurationException;

/**
 * Opens a file or URL which name is given in an environment variable. Two situations can occur:
 * <ul>
 * <li>file is okay and readable: {@link #getSourceDescription()}and {@link #getProperties()}are
 * garantueed to be not null</li>
 * <li>file is not usable or cannot be read: {@link #getSourceDescription()}and
 * {@link #getProperties()}might be null</li>
 * </ul>
 * <b>Note: </b> this class is still in development; the external interface might change (slightly)
 * later.
 * 
 * @author Guus Bosman (Chess-iT)
 */
public class ConfigurationResolverEnvironmentVariable {

    private Exception exception;
    private String sourceDescription;
    private Properties properties;

    public ConfigurationResolverEnvironmentVariable(final String key) {
        String fileName = System.getProperty(key);
        if (fileName == null) {
            exception = new ConfigurationException(
                    "no property for configuration file or URL was given as JVM argument. Please add the following to the java.exe startup command: -D"
                            + key + "=<location of file>");
            return;
        }
        boolean isURL = false;
        try {
            new URL(fileName);
            isURL = true;
        } catch (Exception e) {
            // it's not an URL. Assume it's a file then.
            isURL = false;
        }
        if (isURL) {
            String interpretedURLName = null;
            Properties tmpProperties = new Properties();
            try {
                URL url = new URL(fileName);
                interpretedURLName = url.toExternalForm();

                byte[] bytes = readEntireFileContents(url.openStream());

                InputStream is = new ByteArrayInputStream(bytes);

                PropertyInputStreamValidator.validate(is);
                // re-open
                is.reset();
                tmpProperties.load(is);
            } catch (Exception e) {
                exception = new ConfigurationException("environment property '" + key + "' had value '" + fileName
                        + "', which I interpreted as url '" + interpretedURLName
                        + "' but this can't be used to read the configuration from: " + e.getMessage(), e);
                return;
            }

            sourceDescription = "url " + interpretedURLName + " (from environment property '" + key + "')";
            properties = tmpProperties;
        } else {
            File file = new File(fileName);
            String interpretedFileName = null;
            Properties tmpProperties = new Properties();
            try {
                interpretedFileName = file.getCanonicalPath();

                FileInputStream is = new FileInputStream(file);
                PropertyInputStreamValidator.validate(is);
                // re-open
                is = new FileInputStream(file);
                tmpProperties.load(is);
            } catch (Exception e) {
                exception = new ConfigurationException("environment property '" + key + "' had value '" + fileName
                        + "', which I interpreted as '" + interpretedFileName
                        + "' but this can't be used to read the configuration from: " + e.getMessage(), e);
                return;
            }

            sourceDescription = "file " + interpretedFileName + " (from environment property '" + key + "')";
            properties = tmpProperties;
        }

    }

    /**
     * Returns the Exception that occurred while reading from the environment variable (if any).
     * 
     * @return Might be <code>null</code>.
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Description of the file or URL read.
     * 
     * @return If {@link #getException()}was not null then this might be <code>null</code>.
     */
    public String getSourceDescription() {
        return sourceDescription;
    }

    /**
     * Returns Properties object for configuration
     * 
     * @return If {@link #getException()}was not null then this might be <code>null</code>.
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Reads a file into a byte-array. When an Exception is thrown the inputstream will always be
     * closed. Memory expensive method, use only with limited size of InputStreams.
     * 
     * @param toRead the file to be read
     * @return a file data byte-array
     * @throws NullPointerException If toRead is <code>null</code>.
     * @throws IOException if reading the file fails somehow
     */
    private static byte[] readEntireFileContents(InputStream toRead) throws IOException {
        if (toRead == null) {
            throw new NullPointerException("toRead cannot be null here.");
        }

        InputStream in = null;
        byte[] buf = null; // output buffer

        /**
         * A large buffer for reading from the InputStream. The larger the better as it increases
         * the speed in which the file can be read.
         */
        int bufLen = 10000 * 1024;

        try {
            in = new BufferedInputStream(toRead);
            buf = new byte[bufLen];

            byte[] tmp = null;
            int len = 0;
            List data = new ArrayList();

            while ((len = in.read(buf, 0, bufLen)) != -1) {
                tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                data.add(tmp);
            }

            len = 0;

            /**
             * Trivial case.
             */
            if (data.size() == 1) {
                return (byte[]) data.get(0);
            }

            for (int i = 0; i < data.size(); i++)
                len += ((byte[]) data.get(i)).length;

            /**
             * The result.
             */
            buf = new byte[len];
            len = 0;

            for (int i = 0; i < data.size(); i++) {
                tmp = (byte[]) data.get(i);
                System.arraycopy(tmp, 0, buf, len, tmp.length);
                len += tmp.length;
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return buf;
    }
}