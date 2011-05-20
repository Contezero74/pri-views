/** This module defines the types and alias for
 *  solution management.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */

#include "solution.h"

/** The default constructor create an empty solution with an
 * undefined cost (represented as -1).
 */
solution::solution() {
	Cost = -1;
}

/** This constructor initialize the solution struct with the
 *  input arguments.
 *
 *  @param T	the string representing the set of attributes
 *				used as solution
 *  @param C	the cost of the solution
 */
solution::solution(const string &T, const int &C) {
	SA		= solution_attrs(T);
	Cost	= C;
}

/** This copy constructor create a new instance of a solution,
 * copying the information from the input solution.
 *
 * @param S	the input solution to copy
 */
solution::solution(const solution &S) {
	SA		= S.SA;
	Cost	= S.Cost;
}
