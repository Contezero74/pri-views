/** This module defines a set of utility function to work with string.
 *
 *  version 1.4
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	STRUTIL_H__
#define			STRUTIL_H__

#include <sstream>
#include <string>
#include <vector>

using namespace std;

namespace util {
	/** This function converts string into the specified template type.
	 *
	 *  @param T		template param representing the output format
	 *	@param value	the value to convert
	 *
	 *	@return			the value converted in the wanted format
	 */
	template<typename T> inline T convertStr(const string value) {
		T result;

		if ( value.npos == value.find("0x") ) {
			istringstream tmp(value);
			tmp >> result;
		} else {
			// can be an hex value
			sscanf( value.c_str(), "%x", &result );
		}

		return result;
	}

	/** This function convert a generic input argument in a string (if
	 *  possible.
	 *
	 *  @param t	the input argument to convert
	 *
	 * @return		the converted input argument
	 */
	template<typename T> inline string toStr(const T& t) {
		stringstream ss;
		ss << t;
		return ss.str();
	}

	/** This function delete all the spaces at the start and at the end
	 *  of a string.
	 *
	 *  @param s	the string to trim
	 */
	void trim(string &s);

	/** This function split the input string in a vector of string using
	 *  the input delimiter.
	 *
	 *  @param str			the string to split
	 *  @param trim			true if each splitted string have to be trimed
	 *	@param delimiters	representing the string used as delimiter
	 *
	 *  @return				a vector of string representing the splited input string
	 */
	vector<string> tokenize(const string& str, const bool trim = true, const string& delimiters = " ");
}

#endif //		STRUTIL_H__
