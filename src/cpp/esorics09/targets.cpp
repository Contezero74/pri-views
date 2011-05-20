/** This module defines the functions, types, and alias for
 *  targets management.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */

#include "targets.h"

#include <ctime>

#include <set>

using namespace std;

/** This constructor initialize the target struct with the
 *  input arguments.
 *
 *  @param T	the string representing the set of attributes
 *				used as target
 *  @param C	the cost of the target
 */
target::target(const string &T, const int &C) {
	TA		= target_attrs(T);
	Cost	= C;
}

/** This function creates a random set of targets starting from the
 *  number of attributes used as input.
 *  The parameters for defining the dimension of the set and the maximum
 *  number of attributes to be used for constraint is defined in
 *  config.h file.
 *
 *  @param NumOfAttrs	the number of attributes to be used
 *
 *  @return				the set of targets
 */
targets generateTargets(const int &NumOfAttrs) {
	targets Result;
	set<string> ResultCheck;

	const int MaxNumOfTarget	= ((double)TARGETS_CONST*(double)NumOfAttrs);
	const int MaxNumOfAttrsForC	= ((double)ATTRIBUTES_CONST*(double)NumOfAttrs);

	int NumOfTarget = 0;
	do {
		int NumOfAttrs4T = rand() % MaxNumOfAttrsForC;
		NumOfAttrs4T = NumOfAttrs4T >= 1 ? NumOfAttrs4T : 1;

		set<int> Ts;
		while (Ts.size() < NumOfAttrs4T) {
			int a = rand() % NumOfAttrs + 1;
			Ts.insert(a);
		}

		string strTarget = "";
		for(int ic = 1; ic <= NumOfAttrs; ++ic) {
			if ( 1 == Ts.count(ic) ) {
				strTarget = "1" + strTarget;
			} else {
				strTarget = "0" + strTarget;
			}
		}

		if ( ResultCheck.end() == ResultCheck.find(strTarget) ) {
			int TargetCost = rand() % MAX_TARGET_COST + 1;

			Result.push_back( target(strTarget, TargetCost) );
			ResultCheck.insert(strTarget);

			++NumOfTarget;
		}

	} while (NumOfTarget < MaxNumOfTarget);

	return Result;
}

/** This function creates the predefined set of targets for the
 *  experiment, adjusting it accordling with the number of attributes
 *  used as input.
 *
 *  @param NumOfAttrs	the number of attributes to be used
 *
 *  @return				the set of targets
 */
targets getExpTargets(const int &NumOfAttrs) {
	targets Result;

									//    01234567890123456789012345
	if (NumOfAttrs >= 5) {
		Result.push_back( target( adjust("0001100", NumOfAttrs), 5 ) );
		Result.push_back( target( adjust("0100100", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("0010100", NumOfAttrs), 1 ) );
		Result.push_back( target( adjust("0000100", NumOfAttrs), 7 ) );
	}
	if (NumOfAttrs >= 6) {
		Result.push_back( target( adjust("0000110", NumOfAttrs), 10 ) );
	}
	if (NumOfAttrs >= 7) {
		Result.push_back( target( adjust("0011001", NumOfAttrs), 7 ) );
	}
	if (NumOfAttrs >= 8) {
		Result.push_back( target( adjust("0000000100", NumOfAttrs), 5 ) );
		Result.push_back( target( adjust("0000010100", NumOfAttrs), 8 ) );
	}
	if (NumOfAttrs >= 9) {
		Result.push_back( target( adjust("1100000010", NumOfAttrs), 3 ) );
	}
	if (NumOfAttrs >= 10) {
		Result.push_back( target( adjust("0010000011", NumOfAttrs), 7 ) );
		Result.push_back( target( adjust("0000010001", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("0100001011", NumOfAttrs), 10 ) );
		Result.push_back( target( adjust("0010100001", NumOfAttrs), 2 ) );
		Result.push_back( target( adjust("0001001001", NumOfAttrs), 7 ) );
	}
	if (NumOfAttrs >= 11) {
		Result.push_back( target( adjust("100000000010000", NumOfAttrs), 5 ) );
	}
	if (NumOfAttrs >= 12) {
		Result.push_back( target( adjust("101100100001000", NumOfAttrs), 10 ) );
	}
	if (NumOfAttrs >= 13) {
		Result.push_back( target( adjust("101100101001100", NumOfAttrs), 10 ) );
	}
	if (NumOfAttrs >= 14) {
		Result.push_back( target( adjust("100010111000010", NumOfAttrs), 10 ) );
		Result.push_back( target( adjust("100100001100110", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("100000000110110", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("101010000000010", NumOfAttrs), 1 ) );
	}
	if (NumOfAttrs >= 15) {
		Result.push_back( target( adjust("100001001001011", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("100001101000011", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("000000000000001", NumOfAttrs), 10 ) );
	}
	if (NumOfAttrs >= 16) {
		Result.push_back( target( adjust("00100100000000010000", NumOfAttrs), 5 ) );
		Result.push_back( target( adjust("00000000000000010000", NumOfAttrs), 6 ) );
	}
	if (NumOfAttrs >= 17) {
		Result.push_back( target( adjust("00100111000000011000", NumOfAttrs), 6 ) );
	}
	if (NumOfAttrs >= 18) {
		Result.push_back( target( adjust("00000000000001000100", NumOfAttrs), 9 ) );
	}
	if (NumOfAttrs >= 19) {
		Result.push_back( target( adjust("00000101001000000010", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("00000010100000000010", NumOfAttrs), 10 ) );
		Result.push_back( target( adjust("01100000100010001010", NumOfAttrs), 8 ) );
	}
	if (NumOfAttrs >= 20) {
		Result.push_back( target( adjust("00010010110101100011", NumOfAttrs), 9 ) );
		Result.push_back( target( adjust("00010000000001000001", NumOfAttrs), 1 ) );
	}
	if (NumOfAttrs >= 21) {
		Result.push_back( target( adjust("0000000000000000000010000", NumOfAttrs), 5 ) );
		Result.push_back( target( adjust("0011001100100010010010000", NumOfAttrs), 9 ) );
		Result.push_back( target( adjust("1000001000101111010110000", NumOfAttrs), 6 ) );
	}
	if (NumOfAttrs >= 22) {
		Result.push_back( target( adjust("0000011100000001001001000", NumOfAttrs), 6 ) );
		Result.push_back( target( adjust("1000001100000100111001000", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("0001011011000010000001000", NumOfAttrs), 3 ) );
	}
	if (NumOfAttrs >= 23) {
		Result.push_back( target( adjust("0110000010010011100011100", NumOfAttrs), 10 ) );
		Result.push_back( target( adjust("0000000000000000000000100", NumOfAttrs), 9 ) );
		Result.push_back( target( adjust("0110010110011001000000100", NumOfAttrs), 10 ) );
		Result.push_back( target( adjust("0000000100000100000000100", NumOfAttrs), 1 ) );
	}
	if (NumOfAttrs >= 24) {
		Result.push_back( target( adjust("0010000100010000100100010", NumOfAttrs), 7 ) );
		Result.push_back( target( adjust("0000000011000000000001010", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("0110100101000101000100010", NumOfAttrs), 8 ) );
		Result.push_back( target( adjust("0000000000001000010000010", NumOfAttrs), 5 ) );
		Result.push_back( target( adjust("1100000100010100000001010", NumOfAttrs), 5 ) );
	}
	if (NumOfAttrs >= 25) {
		Result.push_back( target( adjust("0000001100010100101101011", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("1000100001000100011101111", NumOfAttrs), 2 ) );
		Result.push_back( target( adjust("0000000101001000000010011", NumOfAttrs), 7 ) );
		Result.push_back( target( adjust("0000000000000000000000001", NumOfAttrs), 7 ) );
		Result.push_back( target( adjust("0000000000001000000101001", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("0010010000000000000100001", NumOfAttrs), 2 ) );
		Result.push_back( target( adjust("1000010000010001000000001", NumOfAttrs), 3 ) );
	}
	if (NumOfAttrs >= 26) {
		Result.push_back( target( adjust("000000100000001010000100010000", NumOfAttrs), 6 ) );
	}
	if (NumOfAttrs >= 27) {
		Result.push_back( target( adjust("010100001000010101101111001000", NumOfAttrs), 3 ) );
	}
	if (NumOfAttrs >= 28) {
		Result.push_back( target( adjust("100000000001100000001101000100", NumOfAttrs), 10 ) );
		Result.push_back( target( adjust("100011000001100100101010001100", NumOfAttrs), 6 ) );
		Result.push_back( target( adjust("111110000010010001001001001100", NumOfAttrs), 8 ) );
		Result.push_back( target( adjust("011000101110111110000010001100", NumOfAttrs), 8 ) );
		Result.push_back( target( adjust("100010000000000000000001001100", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("100011001110100011001000100100", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("000011100010111001000010010100", NumOfAttrs), 7 ) );
	}
	if (NumOfAttrs >= 29) {
		Result.push_back( target( adjust("010000001010001000000000010010", NumOfAttrs), 8 ) );
		Result.push_back( target( adjust("001000000100000000000000101010", NumOfAttrs), 6 ) );
		Result.push_back( target( adjust("000000000000000000000000000010", NumOfAttrs), 9 ) );
		Result.push_back( target( adjust("100111000000000111010110101010", NumOfAttrs), 1 ) );
		Result.push_back( target( adjust("011100001000001110011000100010", NumOfAttrs), 5 ) );
	}
	if (NumOfAttrs >= 30) {
		Result.push_back( target( adjust("100000110101101100100010110011", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("100000001101000011001110010001", NumOfAttrs), 4 ) );
		Result.push_back( target( adjust("000000000100000000000001000001", NumOfAttrs), 3 ) );
		Result.push_back( target( adjust("101100100000010001110000001101", NumOfAttrs), 7 ) );
		Result.push_back( target( adjust("000100110010100000001101101001", NumOfAttrs), 7 ) );
		Result.push_back( target( adjust("100100100000001111011000100101", NumOfAttrs), 8 ) );
		Result.push_back( target( adjust("011111000000000000001000000111", NumOfAttrs), 10 ) );
	}

	return Result;
}

/** This function creates a small problem instance used to debug the
 *  algorithm.
 *
 *  @return				the set of targets
 */
targets createTestTargets() {
								//  0123456
	target DI = target(		string("0001100"), 5 );
	target RI = target(		string("0100100"), 4 );
	target JI = target(		string("0000110"), 10 );
	target IT = target(		string("0010100"), 1 );
	target I = target(		string("0000100"), 7 );
	target DHT = target(	string("0011001"), 7 );

	targets Result;
	Result.push_back(DI);
	Result.push_back(RI);
	Result.push_back(JI);
	Result.push_back(IT);
	Result.push_back(I);
	Result.push_back(DHT);

	return Result;
}
