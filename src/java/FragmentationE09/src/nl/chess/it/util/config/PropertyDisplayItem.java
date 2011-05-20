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

/**
 * Simple place holder that can be used to display the value of a property. Most properties are read
 * from a Properties, then the original values and key-name will be stored as well. For hard-coded
 * properties (i.e., public getMethods that don't use the Properties).
 * 
 * @author Guus Bosman (Chess iT)
 * @version $Revision: 1.1.1.1 $
 */
public class PropertyDisplayItem implements Comparable {

    /**
     * Never <code>null</code>.
     */
    private final String methodName;
    private final String originalValue;
    private final String parseValue;
    private final String propertyName;

    /**
     * Creates a new PropertyDisplayItem object.
     * 
     * @param methodName Name of the method. Cannot be <code>null</code>.
     * @param propertyName Name of the property. Can be <code>null</code>.
     * @param originalValue Original value of the property. Can be <code>null</code>.
     * @param parseValue Value as interpreted by Java Config. This is a String representation. Can
     *            be <code>null</code>.
     */
    public PropertyDisplayItem(String methodName, String propertyName, String originalValue, String parseValue) {
        if (methodName == null) {
            throw new NullPointerException("methodName cannot be null here.");
        }

        this.methodName = methodName;
        this.propertyName = propertyName;
        this.originalValue = originalValue;
        this.parseValue = parseValue;
    }

    public String toString() {
        String result = methodName + " = " + parseValue;

        if (propertyName == null) {
            result += " (hard-coded)";
        } else {
            result += ", from property " + propertyName + " = "
                    + (originalValue == null ? "<null>" : "'" + originalValue + "'");
        }

        return result;
    }

    /**
     * Name of the method for the property.
     * 
     * @return String. Never <code>null</code>.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Original, unparsed value of the element in the property file.
     * 
     * @return String. Might be <code>null</code> if the element isn't read from the properties.
     */
    public String getOriginalValue() {
        return originalValue;
    }

    /**
     * Parsed value of the element in the property file.
     * 
     * @return String. Never <code>null</code>.
     */
    public String getParseValue() {
        return parseValue;
    }

    /**
     * Name of the element in the property file.
     * 
     * @return String. Might be <code>null</code> if the element isn't read from the properties.
     */
    public String getPropertyName() {
        return propertyName;
    }

    public int hashCode() {
        return (methodName.hashCode());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof PropertyDisplayItem) {
            PropertyDisplayItem other = (PropertyDisplayItem) o;

            return (other.methodName.equals(methodName) && areEquals(propertyName, other.propertyName)
                    && areEquals(originalValue, other.originalValue) && areEquals(parseValue, other.parseValue));
        } else {
            return false;
        }
    }

    public int compareTo(Object o) {
        PropertyDisplayItem rhs = (PropertyDisplayItem) o;
        int result = compareStrings(methodName, rhs.methodName);
        if (result != 0) return result;
        result = compareStrings(propertyName, rhs.propertyName);
        if (result != 0) return result;
        result = compareStrings(originalValue, rhs.originalValue);
        if (result != 0) return result;
        result = compareStrings(parseValue, rhs.parseValue);
        return result;
    }

    private boolean areEquals(String string1, String string2) {
        if (string1 == null) {
            if (string2 == null) {
                return true;
            } else {
                return false;
            }
        }
        return string1.equals(string2);
    }

    private int compareStrings(String string1, String string2) {
        if (string1 == string2) return 0;
        if (string1 == null) return -1;
        if (string2 == null) return 1;
        return (string1.compareTo(string2));
    }
}