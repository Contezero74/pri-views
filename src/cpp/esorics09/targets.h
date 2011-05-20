/** This module defines the functions, types, and alias for
 *  targets management.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	TARGETS_H__
#define			TARGETS_H__

#include "config.h"

#include <bitset>
#include <string>
#include <vector>

using namespace std;

/** This alias defines the set of attributes used as target */
typedef bitset<MAX_NUMBER_ATTRIBUTES>	target_attrs;

/** This struct defines the target struct as a set of attributes used
 *  as target and the associated cost.
 */
struct target {
	/** This constructor initialize the target struct with the
	 *  input arguments.
	 *
	 *  @param T	the string representing the set of attributes
	 *				used as target
	 *  @param C	the cost of the target
	 */
	target(const string &T, const int &C);

	/** The set of attributes used as target. */
	target_attrs TA;

	/** The cost of the target. */
	int Cost;
};

/** This alias defines a set of targets using a vector data structure */
typedef vector<target> targets;

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
targets generateTargets(const int &NumOfAttrs);

/** This function creates the predefined set of targets for the
 *  experiment, adjusting it accordling with the number of attributes
 *  used as input.
 *
 *  @param NumOfAttrs	the number of attributes to be used
 *
 *  @return				the set of targets
 */
targets getExpTargets(const int &NumOfAttrs);

/** This function creates a small problem instance used to debug the
 *  algorithm.
 *
 *  @return				the set of targets
 */
targets createTestTargets();

#endif //		TARGETS_H__
