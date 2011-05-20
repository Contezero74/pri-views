/** This module defines the const parameters used in this
 *  program.
 *
 *  version 1.0
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */
#include "config.h"

/** This function adjust the input string representing a set of attributes
 *  in order to match exactly the number of attributes requested for the
 *  experiment.
 *
 *  @param As			the input arguments to be adjusted
 *  @param NumOfAttrs	the real number of attributes to use
 *
 *  @return		a string representation with the correct numbert of attributes
 */
string adjust(const string As, const int &NumOfAttrs) {
	string Result;

	if ( NumOfAttrs <= As.length() ) {
		Result = As.substr(0, NumOfAttrs);
	} else {
		Result = As;
		for(int i = As.length(); i < NumOfAttrs; ++i) {
			Result += "0";
		}
	}

	return Result;
}
