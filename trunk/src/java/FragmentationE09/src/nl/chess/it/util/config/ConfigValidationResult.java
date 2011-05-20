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

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is the result of a check on the validity of the properties in a
 * {@link nl.chess.it.util.config.Config}object. It's basically a wrapper around a list of Strings,
 * with some helper methods like {@link #addError(String, Throwable)}. It can also store a second
 * list, with unused properties.
 * 
 * @author Guus Bosman (Chess iT)
 * @version $Revision: 1.1.1.1 $
 */
public class ConfigValidationResult {

    /**
     * Describes the source of the configuration (such as a filename).
     */
    private String sourceDescription;

    /**
     * List of Strings that describe the errors that occurred.
     */
    private final List errors = new ArrayList();

    /**
     * List of Strings that describe the properties that are available but not used by the Config.
     */
    private Set unusedProperties = new HashSet();

    /**
     * Set of {PropertyDisplayItem PropertyDisplayItems} that shows which properties are in use.
     */
    private Set usedProperties = new HashSet();

    /**
     * Makes a nice description of an error that happens when calling a method and adds this
     * description to the list with errors.
     * 
     * @param methodName Methodname that was called.
     * @param e Throwable that was thrown.
     */
    public void addError(String methodName, Throwable e) {
        if ((e instanceof InvocationTargetException) && (e.getCause() != null)) {
            e = e.getCause();
        }

        if (e instanceof MissingPropertyException) {
            MissingPropertyException mpe = ((MissingPropertyException) e);
            String missingPropertyDescription = "Missing property " + mpe.getPropertyname();

            if (mpe.getExpectedType() != null) {
                missingPropertyDescription += " which must be " + getAorAn(mpe.getExpectedType()) + " "
                        + mpe.getExpectedType() + " value";
            }

            missingPropertyDescription += ".";
            errors.add(missingPropertyDescription);

            return;
        }

        if (e instanceof InvalidPropertyException) {
            InvalidPropertyException ipe = ((InvalidPropertyException) e);
            errors.add("Property " + ipe.getPropertyname() + " had value '" + ipe.getValue() + "' which is not "
                    + getAorAn(ipe.getExpectedtype()) + " " + ipe.getExpectedtype() + " value.");

            return;
        }

        errors.add("Couldn't call " + methodName + ": " + e);
    }

    private String getAorAn(String word) {
        if (word == null) return "";
        if (word.startsWith("i") || word.startsWith("e") || word.startsWith("o") || word.startsWith("a")) {
            return "an";
        } else {
            return "a";
        }
    }

    /**
     * Whether or not any exceptions occured when validating the configuration.
     * 
     * @return <code>true</code> if something serious went wrong during validation of the getXxx()
     *         methods, <code>false</code> otherwise.
     */
    public boolean thereAreErrors() {
        return (!errors.isEmpty());
    }

    /**
     * Indicates whether or not there are unused properties.
     * 
     * @return <code>true</code> if there are any properties in the Properties object that have
     *         not been used while validation the various getXxx() methods, <code>false</code>
     *         otherwise.
     */
    public boolean thereAreUnusedProperties() {
        return (!unusedProperties.isEmpty());
    }

    /**
     * Returns a List with Strings that indicate any errors that might have occured.
     * 
     * @return List. Never <code>null</code>, unmodifiable.
     */
    public List getErrors() {
        return Collections.unmodifiableList(errors);
    }

    /**
     * Returns a Set with the unused properties. These are properties in the Properties object that
     * have not been used while validating the various getXxx() methods, and thus seem to have no
     * function in the original property-file or Properties object.
     * 
     * @return Unmodiafable set. Never <code>null</code>. Elements are of type String.
     */
    public Set getUnusedProperties() {
        return Collections.unmodifiableSet(unusedProperties);
    }

    public String toString() {
        String toReturn = "";
        if (thereAreErrors()) {
            toReturn += "Configuration errors: " + errors + ".";
        } else {
            toReturn += "No configuration errors. Unused properties: " + unusedProperties.size()
                    + ", used properties: " + usedProperties.size() + ".";
        }
        if (sourceDescription != null) {
            toReturn += " Source description: " + sourceDescription + ".";
        }
        return toReturn;
    }

    /**
     * Sets the set with unusedProperties. It is highly unlikely that an application would want to
     * use this method; it is only used by Config itself.
     * 
     * @param set Set with unusedProperties. Cannot be <code>null</code>. Elements must be of
     *            type String.
     */
    public void setUnusedProperties(Set set) {
        if (set == null) {
            throw new NullPointerException("set cannot be null here.");
        }
        unusedProperties = set;
    }

    /**
     * Allows replacement of the set with usedProperties. There is no reason any application should
     * want to overwrite those, so this method should probably never be used. In Config it is not
     * used either (instead, {#getUsedProperties()} is used to get access to the set)
     * 
     * @param usedProperties. Cannot be <code>null</code>. Item must be of type
     *            {PropertyDisplayItem}.
     */
    public void setUsedProperties(Set usedProperties) {
        if (usedProperties == null) {
            throw new NullPointerException("usedProperties cannot be null here.");
        }
        this.usedProperties = usedProperties;
    }

    /**
     * Returns a set of {PropertyDisplayItem PropertyDisplayItems} that shows which properties are
     * in use.
     * 
     * @return Set. Never <code>null</code>.
     */
    public Set getUsedProperties() {
        return usedProperties;
    }

    /**
     * Describes the location where the properties come from. Human readable String.
     * 
     * @return String. Might be <code>null</code>, if it is unknown.
     */
    public String getSourceDescription() {
        return sourceDescription;
    }

    /**
     * Set the location where the properties come from. Human readable String.
     * 
     * @param sourceDescription Can be <code>null</code>.
     */
    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

}