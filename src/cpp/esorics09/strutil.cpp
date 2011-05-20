/** This module defines a set of utility function to work with string.
 *
 *  version 1.4
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */

#include "strutil.h"

namespace util {

	/** This function delete all the spaces at the start and at the end
	 *  of a string.
	 *
	 *  @param s	the string to trim
	 */
	void trim(string &s) {
		string::size_type pos = s.find_last_not_of(' ');
			
		if(pos != string::npos) {
			s.erase(pos + 1);
			pos = s.find_first_not_of(' ');
			
			if(pos != string::npos) {
				s.erase(0, pos);
			}
		}
		
		else s.erase(s.begin(), s.end());
	}

	/** This function split the input string in a vector of string using
	 *  the input delimiter.
	 *
	 *  @param str			the string to split
	 *  @param trim			true if each splitted string have to be trimed
	 *	@param delimiters	representing the string used as delimiter
	 *
	 *  @return				a vector of string representing the splited input string
	 */
	vector<string> tokenize(const string& str, const bool trim, const string& delimiters) {
		vector<string> tokens;
		// Skip delimiters at beginning.
		string::size_type lastPos = str.find_first_not_of(delimiters, 0);
		
		// Find first "non-delimiter".
		string::size_type pos     = str.find_first_of(delimiters, lastPos);
		
		while (string::npos != pos || string::npos != lastPos) {
			string TmpToken = str.substr(lastPos, pos - lastPos);

			if (trim) { util::trim(TmpToken); }

			// Found a token, add it to the vector.
			tokens.push_back(TmpToken);
			
			// Skip delimiters.  Note the "not_of"
			lastPos = str.find_first_not_of(delimiters, pos);
			
			// Find next "non-delimiter"
			pos = str.find_first_of(delimiters, lastPos);
		}

		return tokens;
	}
}
