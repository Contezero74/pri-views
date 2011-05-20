/** This module defines the functions and alias for constraints
 *  management.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */

#include "constraints.h"

#include <ctime>

#include <set>
#include <string>

using namespace std;

/** This function creates a random set of constraints starting from the
 *  number of attributes used as input.
 *  The parameters for defining the dimension of the set and the maximum
 *  number of attributes to be used for constraint is defined in
 *  config.h file.
 *
 *  @param NumOfAttrs	the number of attributes to be used
 *
 *  @return				the set of constriants
 */
constraints generateConstraints(const int &NumOfAttrs) {
	constraints Result;
	set<string> ResultCheck;

	const int MaxNumOfConstraint	= ((double)CONSTRAINTS_CONST*(double)NumOfAttrs);
	const int MaxNumOfAttrsForC		= ((double)ATTRIBUTES_CONST*(double)NumOfAttrs);

	int NumOfConstraint = 0;
	do {
		int NumOfAttrs4C = rand() % MaxNumOfAttrsForC;
		NumOfAttrs4C = NumOfAttrs4C >= 2 ? NumOfAttrs4C : 2;

		set<int> Cs;
		while (Cs.size() < NumOfAttrs4C) {
			int a = rand() % NumOfAttrs + 1;
			Cs.insert(a);
		}

		string strConstr = "";
		for(int ic = 1; ic <= NumOfAttrs; ++ic) {
			if ( 1 == Cs.count(ic) ) {
				strConstr = "1" + strConstr;
			} else {
				strConstr = "0" + strConstr;
			}
		}

		if ( ResultCheck.end() == ResultCheck.find(strConstr) ) {
			Result.push_back( constraint(strConstr) );
			ResultCheck.insert(strConstr);

			++NumOfConstraint;
		}

	} while (NumOfConstraint < MaxNumOfConstraint);

	return Result;
}

/** This function creates the predefined set of constraints for the
 *  experiment, adjusting it accordling with the number of attributes
 *  used as input.
 *
 *  @param NumOfAttrs	the number of attributes to be used
 *
 *  @return				the set of constriants
 */
constraints getExpConstraints(const int &NumOfAttrs) {
	constraints Result;

										//    01234567890123456789012345
	if (NumOfAttrs >= 3) {
		Result.push_back( constraint( adjust("1010000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 4) {
		Result.push_back( constraint( adjust("0111000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 5) {
		Result.push_back( constraint( adjust("1000100", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0101100", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 6) {
		Result.push_back( constraint( adjust("0000110", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 7) {
		Result.push_back( constraint( adjust("0011000100", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 8) {
		Result.push_back( constraint( adjust("0011000100", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0100001100", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 9) {
		Result.push_back( constraint( adjust("0000000110", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("1000000010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0101010010", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 10) {
		Result.push_back( constraint( adjust("0000011001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0010010011", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 12) {
		Result.push_back( constraint( adjust("100000000001000", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000001000001000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 13) {
		Result.push_back( constraint( adjust("100010000000100", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("100000011100100", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 15) {
		Result.push_back( constraint( adjust("000000000000011", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000110100010101", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000000000000101", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000010000000011", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 17) {
		Result.push_back( constraint( adjust("01000000100010001000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 18) {
		Result.push_back( constraint( adjust("00000101100001010100", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 19) {
		Result.push_back( constraint( adjust("00010101000010001010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("10110000100011001010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("01001111010000011010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("01010001010101100010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("00010000010001000010", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 20) {
		Result.push_back( constraint( adjust("01000010011001010011", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("00010010010000011111", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("10010000000000001111", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("00101000110000010101", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("01001100010010000001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("00010000000000000101", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 21) {
		Result.push_back( constraint( adjust("0001100001001111110010000", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("1100001000110010110010000", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("1101011110011000000110000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 22) {
		Result.push_back( constraint( adjust("0010101001110011011001000", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0000000000100000000001000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 23) {
		Result.push_back( constraint( adjust("1000100011100000000001100", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 24) {
		Result.push_back( constraint( adjust("0010110000001000000000110", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0100010000001011100010010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0100000001011000101010010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0100000000100001000010010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0111001000000000001100110", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 25) {
		Result.push_back( constraint( adjust("0101101110001101000000101", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0000000000011000000000101", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0000001011010000100011001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0111100110010010000000001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("0001001000001101000110101", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 26) {
		Result.push_back( constraint( adjust("000101000001000000000000010000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 27) {
		Result.push_back( constraint( adjust("000000000000010000101000001000", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("100001001000000001000000001000", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 28) {
		Result.push_back( constraint( adjust("000010010100000000001010011100", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 29) {
		Result.push_back( constraint( adjust("001010011000000000001101000010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("001001010010000001010010011010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("010001101000001100101000111010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000010000000000100110000000110", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000000100010000000000100001010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000000000000100000000000000010", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("011111000010100110010000001110", NumOfAttrs) ) );
	}
	if (NumOfAttrs >= 30) {
		Result.push_back( constraint( adjust("000000000000000000001000000001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000011000000000000000110000001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000100111100010010001100000001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("001101001101110110000010001101", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000010111000000000001000010101", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("000100001000001110010000000001", NumOfAttrs) ) );
		Result.push_back( constraint( adjust("011001010000100010010000000101", NumOfAttrs) ) );
	}

	return Result;
}

/** This function creates a small problem instance used to debug the
 *  algorithm.
 *
 *  @return				the set of constriants
 */
constraints createTestConsrtaints() {
										//  0123456
	constraint NI = constraint(		string("1000100") );
	constraint NT = constraint(		string("1010000") );
	constraint DRI = constraint(	string("0101100") );
	constraint DRT = constraint(	string("0111000") );
	constraint JI = constraint(		string("0000110") );

	constraints Result;
	Result.push_back(NI);
	Result.push_back(NT);
	Result.push_back(DRI);
	Result.push_back(DRT);
	Result.push_back(JI);

	return Result;
}
