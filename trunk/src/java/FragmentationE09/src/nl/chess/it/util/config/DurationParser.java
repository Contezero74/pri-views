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

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


/**
 * Helper class that parses a String and interprets it as a duration.
 *
 * @author Guus Bosman (Chess iT)
 * @version $Revision: 1.1.1.1 $
 */
class DurationParser {
    /**
     * Main parse method.
     *
     * @param key Is only used to display better error messages if necessary.
     * @param value String to parse. Cannot be <code>null</code>.
     *
     * @return A duration (long) in milliseconds. Never a negative number.
     */
    public long getDurationInMs(String key, String value)
                         throws ConfigurationException {
        if (value == null) {
            throw new NullPointerException("value cannot be null here.");
        }

        /**
         * Get rid of all comma's and connecting words. Makes parsing SO much easier.
         */
        value = value.replaceAll(",", "");
        value = value.replaceAll("and", "");

        if (value.endsWith(".")) {
            value = value.substring(0, value.length() - 1);
        }

        try {
            /**
             * Convert the String to a List of token.
             */
            List tokens = parseTokens(value);

            /**
             * Parse as numbers...
             */
            List realValues = convertToRealNumbers(tokens);

            /**
             * ...and add all the numbers found to each other (taken Units into account).
             */
            double intermediateResult = calculateDuration(realValues);

            return (long) intermediateResult; // calculateDuration() made sure this is okay.
        }
        catch (InvalidPropertyException e) {
            // fill in InvalidPropertyException with details.
            e.setExpectedtype("duration");
            e.setValue(value);
            e.setPropertyname(key);
            throw e;
        }
    }

    private List parseTokens(String stringValue) {
        StringTokenizer st = new StringTokenizer(stringValue);
        List tokens = new ArrayList();

        while (st.hasMoreTokens()) {
            String currentToken = st.nextToken();
            currentToken = currentToken.trim();

            //            System.out.println("Token: '" + currentToken + "'");
            ParsePosition pp = new ParsePosition(0);

            Number number = NumberFormat.getInstance(Locale.US).parse(currentToken, pp);
            String leftOver = currentToken.substring(pp.getIndex());

            if (number != null) {
                tokens.add(number);
            }

            if ((leftOver != null) && (!leftOver.trim().equals(""))) {
                tokens.add(leftOver);
            }
        }

        return tokens;
    }

    /**
     * Where applicable, convert string representations to Number objects (i.e. replace "4" with new Integer(4), and "two" with new
     * Integer(2).
     *
     * @param tokens List to convert. Cannot be <code>null</code>.
     *
     * @return New List with Numbers and Strings.
     */
    private List convertToRealNumbers(List tokens) {
        List realValues = new ArrayList();

        for (Iterator iter = tokens.iterator(); iter.hasNext();) {
            Object element = iter.next();

            if (element instanceof String) {
                Number currentNumberValue = getNumberValue((String) element);

                if (currentNumberValue != null) {
                    realValues.add(currentNumberValue);
                }
                else {
                    realValues.add(element);
                }
            }
            else {
                realValues.add(element);
            }
        }

        return realValues;
    }

    private double calculateDuration(List realValues) {
        boolean expectingNumber = true; //basically a state. If <true> we want to see a number token, otherwise we want to see a unit token.
        double finalResult = 0;

        Number currentNumberValue = null;
        
        if (realValues.size() == 0) {
			throw new PropertyParseException("Nothing to parse.");
        }
        	
        for (Iterator iter = realValues.iterator(); iter.hasNext();) {
            Object element = iter.next();

            if (element instanceof Number) {
            	
                if (!expectingNumber) {
                    throw new PropertyParseException("Unexpected token '" + element + "'. Didn't expect a number.");
                }

                currentNumberValue = (Number) element;

                expectingNumber = false;
            }
            else {
                // String, probably.
                if (expectingNumber) {
                    if (currentNumberValue == null) {
                        throw new PropertyParseException("Unexpected token '" + element + "'. Expected a number.");
                    }
                }

                if (currentNumberValue instanceof Double) {
                    if (((Double) currentNumberValue).isInfinite()) {
                        throw new PropertyParseException("Overflow");
                    }
                }

                double currentDoubleValue = currentNumberValue.doubleValue();

                if (currentDoubleValue < 0) {
                    throw new PropertyParseException("Negative number '" + currentDoubleValue + "' not allowed.");
                }
                
                long currentLongValue = currentNumberValue.longValue();

                if (currentLongValue < currentDoubleValue - 1) {
                    throw new PropertyParseException("Overflow");
                }

                long multiplier = getMultiplierForTimeString((String) element, (currentLongValue == 1));
                double intermediateResult = multiplier * currentDoubleValue;

                finalResult += intermediateResult;

                long finalResultTest = (long) finalResult;

                if (finalResultTest < finalResult - 1) {
                    throw new PropertyParseException("Overflow");
                }

                // next one must be a number again
                expectingNumber = true;
                currentNumberValue = null;
            }
        }

        if (!expectingNumber) {
            // we're still expected an unit to be read. Not good.
            throw new PropertyParseException("Expected a unit but reached end of input.");
        }

        return finalResult;
    }

    private Number getNumberValue(String string) {
        string = string.toLowerCase();

        if (string.equals("zero")) {
            return Integer.valueOf("0");
        }

        if (string.equals("a")) {
            return Integer.valueOf("1");
        }

        if (string.equals("one")) {
            return Integer.valueOf("1");
        }

        if (string.equals("two")) {
            return Integer.valueOf("2");
        }

        if (string.equals("three")) {
            return Integer.valueOf("3");
        }

        if (string.equals("four")) {
            return Integer.valueOf("4");
        }

        if (string.equals("five")) {
            return Integer.valueOf("5");
        }

        if (string.equals("six")) {
            return Integer.valueOf("6");
        }

        if (string.equals("seven")) {
            return Integer.valueOf("7");
        }

        if (string.equals("eight")) {
            return Integer.valueOf("8");
        }

        if (string.equals("nine")) {
            return Integer.valueOf("9");
        }

        if (string.equals("ten")) {
            return Integer.valueOf("10");
        }

        return null;
    }

    private long getMultiplierForTimeString(String string, boolean singleWord) {
        string = string.toLowerCase();

        boolean addS = !singleWord;

        if (string.equals("week" + (addS ? "s" : ""))) {
            return 1000 * 60 * 60 * 24 * 7;
        }

        if (string.equals("day" + (addS ? "s" : ""))) {
            return 1000 * 60 * 60 * 24;
        }

        if (string.equals("hour" + (addS ? "s" : ""))) {
            return 60 * 60 * 1000;
        }

        if (string.equals("minute" + (addS ? "s" : ""))) {
            return 60 * 1000;
        }

        if (string.equals("second" + (addS ? "s" : ""))) {
            return 1000;
        }

        if (string.equals("ms")) {
            return 1;
        }

        if (string.equals("s")) {
            return 1000;
        }

        throw new PropertyParseException("Unknown unit: '" + string + "'");
    }
}
