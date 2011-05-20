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
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Configuration class. It's most important feature is an extensive self-validation call (
 * {@link #validateConfiguration()}) that not only makes sure that required properties exists, but
 * also that they are of the correct type and don't cause exceptions.
 * <p>
 * Partly based on the old Config class by Rolf Heller.
 * </p>
 * 
 * @author Guus Bosman (Chess-iT)
 */
public abstract class Config {

    private final InstrumentedProperties properties;

    /**
     * Name of the file that has been used to fill the configuration data. Used for displaying
     * messages when something goes wrong.
     */
    private final String sourceDescription;

    /**
     * Constructs a Config based on an existing Properties. This allows for a custom location of the
     * properties. Also, it makes it possible to change the values of the properties used in this
     * class at runtime. The Properties given is used directly (no defensive copy is made). Do
     * whatever you like, but changing values at run-time this way has <b>not </b> been tested, and
     * may lead to interesting problems.
     * 
     * @param properties Cannot be <code>null</code>.
     * @throws NullPointerException If properties is <code>null</code>.
     */
    protected Config(Properties properties) {
        this.sourceDescription = "<Properties outside this class>";

        if (properties == null) {
            throw new NullPointerException("properties cannot be null here.");
        }

        this.properties = new InstrumentedProperties(properties);
    }

    /**
     * Constructs a Config based on a resource. This resource will be lookup using this class'
     * classloader, and then be read using an URL.
     * 
     * @param resourceName Name of the resource to look for, as used in {@link
     *            java.lang.ClassLoader#getResource(java.lang.String)}. Cannot be <code>null</code>.
     * @throws NullPointerException If resourceName is <code>null</code>.
     */
    protected Config(String resourceName) throws ConfigurationException {
        if (resourceName == null) {
            throw new NullPointerException("resourceName cannot be null here.");
        }

        URL location = this.getClass().getClassLoader().getResource(resourceName);

        if (location == null) {
            throw new ConfigurationException("Cannot find resource '" + resourceName
                    + "' to load configuration properties from.");
        }

        /*
         * Store for displaying later.
         */
        this.sourceDescription = location.toString();

        InputStream inputStream;

        try {
            inputStream = location.openStream();

            Properties tmpproperties = new Properties();
            tmpproperties.load(inputStream);
            properties = new InstrumentedProperties(tmpproperties);
        } catch (Exception e) {
            throw new ConfigurationException("Could not read resource '" + resourceName
                    + "' to load configuration properties from.", e);
        }
    }

    /**
     * Validates the configuration properties. That means that all getters of this class are tested
     * once; any exceptions thrown when calling a getter are reported.
     * 
     * @return ConfigValidationResult the result of the validation. Never <code>null</code>.
     */
    public final ConfigValidationResult validateConfiguration() {
        Class toCheck = this.getClass();
        Method[] methods = toCheck.getMethods();

        Object object = this;
        ConfigValidationResult result = new ConfigValidationResult();
        result.setSourceDescription(sourceDescription);

        /*
         * Keep track of which properties are used.
         */
        properties.startInstrumenting();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            if (isPublicGetter(method)) {
                tryMethodAndRememberResult(object, method, result);
            }
        }

        Set usedKeys = properties.getUsedKeys();
        Set allKeys = new HashSet(properties.getProperties().keySet());
        allKeys.removeAll(usedKeys);
        result.setUnusedProperties(allKeys);
        properties.stopInstrumenting();

        return result;
    }

    private void tryMethodAndRememberResult(Object object, Method method, ConfigValidationResult result) {
        try {
            properties.clearResult();

            Object resultValue = method.invoke(object, null);
            String lastUsedKey = properties.getLastUsedKey();
            String lastUsedValue = properties.getLastUsedValue();
            PropertyDisplayItem displayItem = new PropertyDisplayItem(method.getName(), lastUsedKey, lastUsedValue,
                    (resultValue == null ? null : resultValue.toString()));
            result.getUsedProperties().add(displayItem);
        } catch (Exception e) {
            // the 'result' will construct an error message from the exception.
            result.addError(method.getName(), e);
        }
    }

    /**
     * Whether or not a method is a public getter. That means a method whose name starts with "get"
     * or with "is". Additionally a check could be made on whether or not the right return type is
     * used ("is" must match boolean), but that's optional.
     * 
     * @param method Method to look at.
     * @return <code>true</code> is the method is a public getter, <code>false</code> otherwise.
     */

    /*
     * @todo Make sure that Exceptions are also taken into account when looking at the signature of
     * a method.
     */
    private boolean isPublicGetter(Method method) {
        if (method.getName().equals("getClass")) {
            return false;
        }

        boolean isOkay = Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())
                && ((method.getName().startsWith("get")) || method.getName().startsWith("is"))
                && (!method.getReturnType().equals(Void.TYPE));

        return isOkay;
    }

    /**
     * Gets the String value for the given key. <b>Note: </b> this method is not the one that gives
     * access to the underlying Properties, see {@link #getValue(String)}for that.
     * 
     * @param key Key to search on.
     * @return String value. Never <code>null</code>, always <code>trimmed()</code>.
     * @throws MissingPropertyException if the key does not result in a value.
     */
    protected final String getString(String key) {
        String value = getValue(key);

        if (value == null) {
            throw new MissingPropertyException(key);
        }

        return value.trim();
    }

    /**
     * Gets the value String for the given key. Throws an exception that describes the type of the
     * expected value, which is nice to let the user know what kind of value should be filled in.
     * 
     * @param key Key to search on.
     * @param expectedType Description of the expected type of the value of the key we're looking
     *            for. Used in error messages.
     * @return String value. Never <code>null</code>, always <code>trimmed()</code>.
     * @throws MissingPropertyException if the key does not result in a value.
     */
    protected final String getString(String key, String expectedType) {
        String value = getValue(key);

        if (value == null) {
            throw new MissingPropertyException(key, expectedType);
        }

        return value.trim();
    }

    /**
     * This method gives access to the underlying Properties this class is wrapped around. You could
     * overwrite this to do special tricks for every value that is read from the property-file
     * (Properties object, really).
     * 
     * @param key. Cannot be <code>null</code>.
     * @return Value for the given key, or <code>null</code> if such a value cannot be found.
     */
    protected String getValue(String key) {
        return properties.getProperty(key);
    }

    protected final int getInt(String key) {
        String stringValue = getString(key, "integer");

        try {
            return Integer.parseInt(stringValue);
        } catch (Exception e) {
            throw new InvalidPropertyException(key, stringValue, "integer");
        }
    }

    protected final long getSizeInBytes(String key) {
        String stringValue = getString(key, "size in bytes");
        Number result;
        ParsePosition pp = new ParsePosition(0);
        NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

        try {
            result = format.parse(stringValue, pp);
        } catch (Exception e) {
            throw new InvalidPropertyException(key, stringValue, "file size");
        }

        long multiplier = 1;

        /**
         * Determine unit.
         */
        if (pp.getIndex() < stringValue.length()) {
            String leftOver = stringValue.substring(pp.getIndex()).trim();
            boolean understood = false;

            if (leftOver.equals("KB")) {
                multiplier = 1024;
                understood = true;
            }

            if (leftOver.equals("kB")) {
                multiplier = 1000;
                understood = true;
            }

            if (leftOver.equals("MB")) {
                multiplier = 1024 * 1024;
                understood = true;
            }

            if (leftOver.equals("mB")) {
                multiplier = 1000 * 1000;
                understood = true;
            }

            if (leftOver.equals("GB")) {
                multiplier = 1024 * 1024 * 1024;
                understood = true;
            }

            if (leftOver.equals("gB")) {
                multiplier = 1000 * 1000 * 1000;
                understood = true;
            }

            if (leftOver.equalsIgnoreCase("bytes")) {
                multiplier = 1;
                understood = true;
            }

            if (leftOver.equalsIgnoreCase("byte")) {
                multiplier = 1;
                understood = true;
            }

            if (understood == false) {
                throw new InvalidPropertyException(key, stringValue, "file size (unit not understood)");
            }
        }

        long l = new Double(result.doubleValue() * multiplier).longValue();

        if (l != (result.doubleValue() * multiplier)) {
            throw new InvalidPropertyException(key, stringValue, "file size (would have rounded value)");
        }

        return l;

        //return Integer.parseInt(stringValue);
    }

    /**
     * Returns a duration.
     * 
     * @param key Key to search on.
     * @return A duration (long) in milliseconds. Never a negative number.
     */
    protected final long getDurationInMs(String key) {
        String stringValue = getString(key, "duration");

        return new DurationParser().getDurationInMs(key, stringValue);
    }

    /**
     * Checks if the value stored under the given key is a pattern that can be used to construct a
     * MessageFormat. See {@link #testMessageFormat(String, String, Object[])}for more information.
     * 
     * @param key String Key that will be used to retrieve the messageformat pattern. Cannot be
     *            <code>null</code>.
     * @param testArguments Arguments to test the pattern with. Cannot be <code>null</code>.
     * @return String MessageFormat pattern
     */
    protected final String getMessageFormatPattern(String key, Object[] testArguments) {
        String value = getString(key, "MessageFormat string");

        testMessageFormat(key, value, testArguments);

        return value;
    }

    /**
     * <p>
     * Verifies that the given pattern can be used to construct a MessageFormat. The testArguments
     * array should contains values that represent reasonable values that will be used for this
     * messageFormat.
     * </p>
     * <p>
     * <b>Example: </b>In your properties file you have the following messageformat pattern:
     * <code>files.description=There {0,choice,0#are no files|1#is one file|1 &lt;are {0,number,integer} files}</code>
     * You could use the following calls in your getXxx() method to testMessageFormat to verify the
     * messageformat deals with a variety of numbers correctly:
     * 
     * <pre><code>
     * 
     * public String getFilesDescription() {
     *     String key = &quot;files.description&quot;;
     *     String value = getString(key, &quot;MessageFormat string&quot;);
     * 
     *     testMessageFormat(key, value, new Object[] {new Integer(0)}); // tests the {0, choice, 0#} part
     *     testMessageFormat(key, value, new Object[] {new Integer(1)}); // tests the {0, choice ... |1#} part
     *     testMessageFormat(key, value, new Object[] {new Integer(10)});// tests the {0, choice ... &lt;1} part
     *     return value;
     * }
     * </code>
     * </pre>
     * 
     * The above code would for example make sure the following entry in a property-file is not
     * accepted:
     * <code>files.description=There {0,choice,0#are no files|1#is one file|1 &lt;are {0,invaliddatatype} files}</code>
     * </p>
     * 
     * @param key Key that was used to retrieve the messageformat pattern.
     * @param pattern The messageformat pattern to test.
     * @param testArguments Arguments to test the pattern with.
     */
    protected final void testMessageFormat(String key, String pattern, Object[] testArguments) {
        if (key == null) {
            throw new NullPointerException("key cannot be null here.");
        }
        if (pattern == null) {
            throw new NullPointerException("pattern cannot be null here.");
        }
        if (testArguments == null) {
            throw new NullPointerException("testarguments cannot be null here.");
        }
        try {
            MessageFormat.format(pattern, testArguments);
        } catch (IllegalArgumentException e) {
            StringBuffer testArgumentsDescription = new StringBuffer();
            for (int i = 0; i < testArguments.length; i++) {
                Object object = testArguments[i];
                testArgumentsDescription.append("{" + i + "} = (");
                if (object != null) {
                    testArgumentsDescription.append(object.toString() + ", " + object.getClass().getName() + ")");
                } else {
                    testArgumentsDescription.append("<null>)");
                }
                if ((i + 1) < testArguments.length) {
                    testArgumentsDescription.append(", ");
                }

            }
            throw new InvalidPropertyException(key, pattern, "valid MessageFormat string for "
                    + testArgumentsDescription);
        }
    }

    protected final Date getDate(String key) {
        String stringValue = getString(key, "date");

        ParsePosition pp = new ParsePosition(0);
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.US);
        formatter.setLenient(false);

        Date result = null;

        /**
         * Have an exception ready, just in case. Contains an example of how a date should be
         * written.
         */
        InvalidPropertyException informativeException = new InvalidPropertyException(key, stringValue,
                ("date (such as " + formatter.format(new Date())) + ")");

        try {
            result = formatter.parse(stringValue, pp);

            if (pp.getIndex() != stringValue.length()) {
                throw informativeException;
            }
        } catch (Exception e) {
            throw informativeException;
        }

        return result;
    }

    /**
     * Gets the value for the given key. The value is expected to be an String which subvalues are
     * separated using comma's. Empty Strings will <b>not </b> be returned.
     * 
     * @param key Key to search on.
     * @param itemsDescription Description of individual values, should be written in plural.
     *            Example: "email adresses". Optional, can be <code>null</code>.
     * @return A String array. Never <code>null</code>, might be empty (zero elements).
     */
    protected final String[] getStringArrayFromCommaString(String key, String itemsDescription) {
        String valueDescription = "comma separated list";
        if ((itemsDescription != null) && (!itemsDescription.equals(""))) {
            valueDescription += " of " + itemsDescription;
        }
        String stringValue = getString(key, valueDescription);

        StringTokenizer st = new StringTokenizer(stringValue, ",");
        List tokens = new ArrayList();

        while (st.hasMoreTokens()) {
            String currentToken = st.nextToken();
            currentToken = currentToken.trim();
            if (!currentToken.equals("")) {
                tokens.add(currentToken);
            }
        }
        return (String[]) tokens.toArray(new String[] {});
    }

    /**
     * Gets the value for the given key. The value is expected to be an String which subvalues are
     * separated using comma's. Empty Strings will <b>not </b> be returned.
     * 
     * @param key Key to search on.
     * @return A String array. Never <code>null</code>, might be empty (zero elements).
     */
    protected final String[] getStringArrayFromCommaString(String key) {
        return getStringArrayFromCommaString(key, null);
    }

    /**
     * Gets the value for the given key. The value is expected to be an URL. A String is an URL if
     * it can be read by the {@linkURL#URL(java.lang.String) constructor of the URL class}.
     * 
     * @param key Key to search on.
     * @return An URL. Never <code>null</code>.
     */
    protected final URL getURL(String key) {
        String stringValue = getString(key, "url");
        URL url;

        try {
            url = new URL(stringValue);
        } catch (MalformedURLException e) {
            throw new InvalidPropertyException(key, stringValue, "url");
        }

        return url;
    }

    /**
     * Gets the value for the given key. The value is boolean, so <code>true</code> or
     * <code>false</code>. The following texts are recognized: on, true, yes and 1, and off,
     * false, no and 0. Any other value causes an exception.
     * 
     * @param key Key to search on.
     * @return boolean
     * @throws InvalidPropertyException If the value cannot be parsed as a boolean.
     */
    protected final boolean getBoolean(String key) {
        String stringValue = getString(key, "boolean");
        stringValue = stringValue.toLowerCase();

        if (stringValue.equals("on") || stringValue.equals("true") || stringValue.equals("yes")
                || stringValue.equals("1")) {
            return true;
        }

        if (stringValue.equals("off") || stringValue.equals("false") || stringValue.equals("no")
                || stringValue.equals("0")) {
            return false;
        }

        throw new InvalidPropertyException(key, stringValue, "boolean");
    }

    protected final File getExistingReadableFile(String key) {
        String value = getString(key, "filename");
        return new FileParser().getExistingReadableFile(key, value);
    }

    protected final File getWritableFile(String key) {
        String value = getString(key, "filename");
        return new FileParser().getWriteableFile(key, value);
    }

    /**
     * Indicates whether or not a value is given for this key.
     * 
     * @param key Cannot be <code>null</code>.
     * @return <code>true</code> if a value has been given; <code>false</code> otherwise.
     */
    protected final boolean hasValue(String key) {
        return (getValue(key) != null);
    }
}

/**
 * Simple adapter around Properties, used to keep track of which keys in the properties are used
 * during a given interval. The measuring starts when {@link #startInstrumenting()}is called, and
 * stopped with {@link #stopInstrumenting()}.
 * 
 * @author Guus Bosman (Chess iT)
 * @version $Revision: 1.1.1.1 $
 */

class InstrumentedProperties {

    private Properties properties;
    private Set usedKeys = new HashSet();
    private String lastUsedKey;
    private String lastUsedValue;
    private boolean instrumentingEnabled = false;

    InstrumentedProperties(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);

        if (instrumentingEnabled) {
            usedKeys.add(key);
            lastUsedKey = key;
            lastUsedValue = value;
        }

        return value;
    }

    /**
     * Start a session to instrument the property file.
     */
    public void startInstrumenting() {
        instrumentingEnabled = true;
        usedKeys = new HashSet();
    }

    /**
     * Stops a session instrumenting the property file.
     */
    public void stopInstrumenting() {
        instrumentingEnabled = false;
    }

    public Set getUsedKeys() {
        return usedKeys;
    }

    /**
     * Allows direct access to underlying properties. If you use this you are able to divert the
     * instrumenting of course.
     * 
     * @return
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Returns the last used key (key in property file).
     * 
     * @return String. Might be <code>null</code> if no key has been used since the last call to
     *         {@link #clearResult}.
     */
    public String getLastUsedKey() {
        return lastUsedKey;
    }

    /**
     * Returns the last used value.
     * 
     * @return String. Might be <code>null</code> if a value was really <code>null</code> or if
     *         no key has been used since the last call to {@link #clearResult}.
     */
    public String getLastUsedValue() {
        return lastUsedValue;
    }

    public void clearResult() {
        lastUsedKey = null;
        lastUsedValue = null;
    }
}