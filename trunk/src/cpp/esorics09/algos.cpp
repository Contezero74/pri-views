/** This module defines two functions used to calculate the solution of
 *  the problem. One function calculates the exact solution, the other
 *  calculates the greedy one.
 *
 *  version 1.2
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */
#include "config.h"

#include "algos.h"

#include <queue>
#include <string>
#include <vector>
#include <set>


#include <iostream>

using namespace std;

/** This function return the next set of attributes candidate to be evaluated:
 *
 *  @param V			a string representing the current set of attributes in
 *						binary format
 *  @param NumOfAttrs	the number of attributes used in the binary format rappresentation
 *
 *  @return the next set of attributes candidate to be evaluated
 */
string Inc(const string &V, const int &NumOfAttrs) {
	string Result = V;

	bool Carry = true;
	for(int i = 0; i != NumOfAttrs; ++i) {
		char NewValue = '1';
		bool NewCarry = false;

		if ( ('0' == V[i] && false == Carry) || ('1' == V[i] && true == Carry) ) {
			NewValue = '0';
		}

		if ( '1' == V[i] && true == Carry ) {
			NewCarry = true;
		}

		Result[i]	= NewValue;
		Carry		= NewCarry;
	}

	//cout << Result << endl;

	return Result;
}

/** This function calculates the exhaustive (exact) solution of the problem searching
 *  into the full solutions space.
 *
 *  @param NumOfAttrs	the number of attributes
 *  @param Cs			the full set of constraints (see constraints.h file)
 *  @param Ts			the full set of targets (see targets.h file)
 *
 *  @return				the evaluated solution (see solution.h file)
 */
solution calculateExhaustiveSolution(const int &NumOfAttrs, const constraints &Cs, const targets &Ts) {
	solution S;

	// create starting solution:
	string Value;
	for(int i = 0; i < NumOfAttrs; ++i) { Value += "0"; }
	Value[0] = '1';

	// create last solution:
	string EndValue;
	for(int i = 0; i < NumOfAttrs; ++i) { EndValue += "1"; }

	const constraints::const_iterator &EndConstraint	= Cs.end();
	const targets::const_iterator &EndTargets			= Ts.end();

	while (EndValue != Value) {
		bitset<MAX_NUMBER_ATTRIBUTES> V = bitset<MAX_NUMBER_ATTRIBUTES>(Value);

		// check constraints: each constraint...
		bool FoundASolution = true;
		for(constraints::iterator C = const_cast<constraints&>( Cs ).begin(); C != EndConstraint && FoundASolution; ++C) {
			if ( C->count() >= 2) {
				// need to be mapped in the solution...
				if ( !(*C & V).any() ) { FoundASolution = false; }
			
				// but has to be not contained in it
				//if ( *C == (*C & V) ) { FoundASolution = false; }
			}
		}

		if (FoundASolution) {
			// found a solution
			int NewCost = 0;
			for(targets::iterator T = const_cast<targets&>( Ts ).begin(); T != EndTargets; ++T) {
				if ( (T->TA & V).any() ) {
					NewCost += T->Cost;
				}
			}

			if (-1 == S.Cost || S.Cost > NewCost) {
				S.SA	= V;
				S.Cost	= NewCost;
			}
		}

		// Increment Value
		Value = Inc(Value, NumOfAttrs);
	}

	return S;
}

/** This internal struct is used to represent a single node of the greedy
 *  solution.
 */
struct node {
	/** The struct constructor initializes the node properties.
	 *
	 *  @param ID	the unique identifier of the node
	 */
	node(const int &ID) {
		AttributeID			= ID;
		NumOfConstraints	= 0;
		TargetCost			= 0;
	}

	/** The unique indetifier of the node */
	int AttributeID;

	/** Number of constraints within the node */
	int NumOfConstraints;

	/** The cost of the node with respect of the targets */
	int TargetCost;
};

/** This utility class compares the nodes Number of Constraints
 *  vs Target cost in STL way.
 */
class node_comparison {
public:
	/** The default constructor does nothing.
	 */
	node_comparison() {}

	/** This class has to evaluate the < (less) operator, i.e. if the
	 *  first node is strictly less return true
	 *
	 *  @param lsh	the first node to compare
	 *  @param rhs	the second node to compare
	 *
	 *  @return		the result of comparison
	 */
	bool operator() (const node& lhs, const node &rhs) const {
		//*
		if ( 0 == lhs.TargetCost && 0 == lhs.NumOfConstraints ) {
			return true; // lhs < rhs
		}
		//*/


		if ( 0 == lhs.TargetCost ) {
			return false; // lhs >= rhs
		}

		//*
		if ( 0 == rhs.TargetCost && 0 == rhs.NumOfConstraints ) {
			return false; // lhs >= rhs
		}
		//*/

		if ( 0 == rhs.TargetCost ) {
			return true; // lhs < rhs
		}

		double lhsCost = ((double)lhs.NumOfConstraints)/((double)lhs.TargetCost);
		double rhsCost = ((double)rhs.NumOfConstraints)/((double)rhs.TargetCost);

		return lhsCost < rhsCost;
	}
};

/** This utility class compares the nodes unique identifier in STL way.
 *  This class orders the nodes in first to last created list of nodes.
 */
class node_id_comparison {
public:
	/** This class orders the nodes in first to last created list of nodes,
	 *  using the unique indetifier for the comparison.
	 *
	 *  @param lsh	the first node to compare
	 *  @param rhs	the second node to compare
	 *
	 *  @return		the result of comparison
	 */
	bool operator() (const node& lhs, const node &rhs) const {
		return lhs.AttributeID < rhs.AttributeID;
	}
};

/** This utility class compares the target cost of nodes in STL way.
 */
class node_solution_comparison {
public:
	/** This utility class compares the target cost of nodes in STL way.
	 *
	 *  @param lsh	the first node to compare
	 *  @param rhs	the second node to compare
	 *
	 *  @return the result of comparison
	 */
	bool operator() (const node& lhs, const node &rhs) const {
		return lhs.TargetCost < rhs.TargetCost;
	}
};

typedef priority_queue<node, vector<node>, node_comparison> node_pq;
typedef set<node, node_id_comparison> node_set;
typedef priority_queue<node, vector<node>, node_solution_comparison> node_sol_pq;

/** This function fills the nodes priority queue initializing nodes them self.
 *
 *  @param NumOfAttrs		the number of attributes of the problem
 *  @param Ns				the set of nodes to be added to the priority queue
 *  @param Cs				the set of constraints involving nodes (see constraints.h file)
 *  @param Ts				the set of targets involving nodes (see targets.h file)
 *
 *  @return					the filed nodes priority queue
 */
node_pq fillPriorityQueue(const int &NumOfAttrs, const node_set &Ns, const constraints &Cs, const targets &Ts) {
	node_pq PQ;

	const constraints::const_iterator &EndConstraint	= Cs.end();
	const targets::const_iterator &EndTargets			= Ts.end();

	const node_set::const_iterator &NodeEnd				=  Ns.end();

	for(node_set::iterator N = const_cast<node_set&>(Ns).begin(); N != NodeEnd; ++N) {
		node TmpNode = *N;

		// set constraints number
		for(constraints::iterator C = const_cast<constraints&>( Cs ).begin(); C != EndConstraint; ++C) {
			if ( C->test(NumOfAttrs - TmpNode.AttributeID - 1) ) {
				++TmpNode.NumOfConstraints;
			}
		}

		// set targets cost
		for(targets::iterator T = const_cast<targets&>( Ts ).begin(); T != EndTargets; ++T) {
			if ( T->TA.test(NumOfAttrs - TmpNode.AttributeID - 1) ) {
				TmpNode.TargetCost += T->Cost;
			}
		}

		PQ.push(TmpNode);
	}

	return PQ;
}

/** This function calculates the greedy solution of the problem searching using an heuristic
 *  to search into the solution space.
 *
 *  @param NumOfAttrs	the number of attributes
 *  @param Cs			the full set of constraints (see constraints.h file)
 *  @param Ts			the full set of targets (see targets.h file)
 *
 *  @return				the evaluated solution (see solution.h file)
 */
solution calculateGreedySolution(const int &NumOfAttrs, const constraints &Cs, const targets &Ts) {
	solution S;

	constraints TmpCs	= Cs;
	targets TmpTs		= Ts;

	// create nodes
	node_set Nodes;
	for(int a = 0; a < NumOfAttrs; ++a) {
		node N(a);
		Nodes.insert(N);
	}

	node_pq PQ = fillPriorityQueue(NumOfAttrs, Nodes, TmpCs, TmpTs);

	node_sol_pq Nodes2Check;
	node_set Solution;
	while ( !PQ.empty() ) {
		node E = PQ.top();
		PQ.pop();

		if ( 0 == E.NumOfConstraints ) { break; }

		// remove constraints
		constraints Tmp2Cs;
		const constraints::const_iterator &CsEnd = TmpCs.end();
		for(constraints::iterator c = TmpCs.begin(); c != CsEnd; ++c) {
			if ( !c->test(NumOfAttrs - E.AttributeID - 1) ) {
				Tmp2Cs.push_back( *c );
			}
		}

		TmpCs = Tmp2Cs;


		// remove targets
		targets Tmp2Ts;
		const targets::const_iterator &TsEnd = TmpTs.end();
		for(targets::iterator t = TmpTs.begin(); t != TsEnd; ++t) {
			if ( !t->TA.test(NumOfAttrs - E.AttributeID - 1) ) {
				Tmp2Ts.push_back( *t );
			}
		}

		TmpTs = Tmp2Ts;


		// remove node
		Nodes.erase(E);

		// update solution
		Solution.insert(E);
		Nodes2Check.push(E);

		// update priority Queue
		PQ = fillPriorityQueue(NumOfAttrs, Nodes, TmpCs, TmpTs);
	}

	if ( !Solution.empty() ) {
		S.Cost = 0;
	}

	const node_set::const_iterator SolutionEnd = Solution.end();
	for(node_set::iterator i = Solution.begin(); i != SolutionEnd; ++i) {
		S.SA.set(NumOfAttrs - i->AttributeID - 1);
	}

	while( !Nodes2Check.empty() ) {
		node E = Nodes2Check.top();
		Nodes2Check.pop();

		solution_attrs Tmp = S.SA;
		Tmp.set(NumOfAttrs - E.AttributeID - 1, false);

		bool FoundASolution = true;
		for(constraints::iterator C = const_cast<constraints&>( Cs ).begin(); C != Cs.end() && FoundASolution; ++C) {
			// need to be mapped in the solution...
			if ( !(*C & Tmp).any() ) { FoundASolution = false; }
		}

		if (FoundASolution) {
			S.SA = Tmp;
		}		
	}

	int NewCost = 0;
	for(targets::iterator T = const_cast<targets&>( Ts ).begin(); T != Ts.end(); ++T) {
		if ( (T->TA & S.SA).any() ) {
			NewCost += T->Cost;
		}
	}
	S.Cost = NewCost;

	return S;
}
