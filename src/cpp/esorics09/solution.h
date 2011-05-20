/** This module defines the types and alias for
 *  solution management.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	SOLUTION_H__
#define			SOLUTION_H__

#include "config.h"

#include <bitset>
#include <string>
#include <vector>

using namespace std;

/** This alias defines the set of attributes used as solution */
typedef bitset<MAX_NUMBER_ATTRIBUTES>	solution_attrs;

/** This struct defines the solution struct as a set of attributes used
 *  as solution and the associated cost.
 */
struct solution {
	/** The default constructor create an empty solution with an
	 * undefined cost (represented as -1).
	 */
	solution();

	/** This constructor initialize the solution struct with the
	 *  input arguments.
	 *
	 *  @param T	the string representing the set of attributes
	 *				used as solution
	 *  @param C	the cost of the solution
	 */
	solution(const string &T, const int &C);

	/** This copy constructor create a new instance of a solution,
	 * copying the information from the input solution.
	 *
	 * @param S	the input solution to copy
	 */
	solution(const solution &S);

	/** The set of attributes used as solution. */
	solution_attrs SA;

	/** The cost of the solution. */
	int Cost;
};

#endif //		SOLUTION_H__
