/** This module defines two functions used to calculate the solution of
 *  the problem. One function calculates the exact solution, the other
 *  calculates the greedy one.
 *
 *  version 1.2
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	ALGO_H__
#define			ALGO_H__

#include "constraints.h"
#include "solution.h"
#include "targets.h"

/** This function calculates the exhaustive (exact) solution of the problem searching
 *  into the full solutions space.
 *
 *  @param NumOfAttrs	the number of attributes
 *  @param Cs			the full set of constraints (see constraints.h file)
 *  @param Ts			the full set of targets (see targets.h file)
 *
 *  @return				the evaluated solution (see solution.h file)
 */
solution calculateExhaustiveSolution(const int &NumOfAttrs, const constraints &Cs, const targets &Ts);

/** This function calculates the greedy solution of the problem searching using an heuristic
 *  to search into the solution space.
 *
 *  @param NumOfAttrs	the number of attributes
 *  @param Cs			the full set of constraints (see constraints.h file)
 *  @param Ts			the full set of targets (see targets.h file)
 *
 *  @return				the evaluated solution (see solution.h file)
 */
solution calculateGreedySolution(const int &NumOfAttrs, const constraints &Cs, const targets &Ts);

#endif //		ALGO_H__
