/** This module defines the functions and alias for constraints
 *  management.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	CONSTRAINTS_H__
#define			CONSTRAINTS_H__

#include "config.h"

#include <bitset>
#include <vector>

using namespace std;

/** This alias defines the format of a constraint: a set of bit where the
 *  ordinal position defines the attribute, and the fact that the position
 *  is set to 0 or 1 defines if the attribute is used in the constraint or
 *  not respectively.
 */
typedef bitset<MAX_NUMBER_ATTRIBUTES>	constraint;

/** This alias defines a set of constraints using a vector data structure */
typedef vector<constraint>				constraints;

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
constraints generateConstraints(const int &NumOfAttrs);

/** This function creates the predefined set of constraints for the
 *  experiment, adjusting it accordling with the number of attributes
 *  used as input.
 *
 *  @param NumOfAttrs	the number of attributes to be used
 *
 *  @return				the set of constriants
 */
constraints getExpConstraints(const int &NumOfAttrs);

/** This function creates a small problem instance used to debug the
 *  algorithm.
 *
 *  @return				the set of constriants
 */
constraints createTestConsrtaints();


#endif //		CONSTRAINTS_H__
