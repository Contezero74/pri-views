/** This module defines the const parameters used in this
 *  program.
 *
 *  version 1.0
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	CONFIG_H__
#define			CONFIG_H__

#include <string>

using namespace std;

/** Maximum number of attributes */
#define MAX_NUMBER_ATTRIBUTES	(128)

/** Defines the percentage that defines the maximum number of constraint
 *  vs the current number of attributes.
 */
#define	CONSTRAINTS_CONST		(0.85)

/** Defines the percentage that defines the maximum number of constraint
 *  vs the current number of attributes. TO CHANGE
 */
#define	TARGETS_CONST			(1.00)

/** Defines the percentage that defines the maximum number of attributes
 *  used in each constraint vs the current number of attributes.
 */
#define	ATTRIBUTES_CONST		(0.50)

/** Defines the maximum const associated to each target */
#define MAX_TARGET_COST			(10)

/** This function adjust the input string representing a set of attributes
 *  in order to match exactly the number of attributes requested for the
 *  experiment.
 *
 *  @param As			the input arguments to be adjusted
 *  @param NumOfAttrs	the real number of attributes to use
 *
 *  @return		a string representation with the correct numbert of attributes
 */
string adjust(const string As, const int &NumOfAttrs);

#endif //		CONFIG_H__
