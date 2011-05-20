/** This module defines main access point to the program.
 *
 *  version 1.3
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */

#include <iostream>
#include <fstream>

#include <cstdlib>

#include <time.h>
#include <sys/timeb.h>

#include "config.h"

#include "algos.h"
#include "cmdline.h"
#include "constraints.h"
#include "solution.h"
#include "targets.h"
#include "problemloader.h"


using namespace std;

/** This functions extracts from the solution a string representing
 *  the attributes used in the solution itself.
 *
 *  @param S			the solution from which extract the attributes
 *  @param NumOfAttrs	the number of attributes used in the problem
 *
 *  @return				the string representing the attributes used
 *						in the solution
 */
string extractSolution(const solution &S, const int &NumOfAttrs) {
	string Result = "";

	for(int a = 0; a < NumOfAttrs; ++a) {
		if ( S.SA.test(a) ) {
			Result = "1" + Result;
		} else {
			Result = "0" + Result;
		}
	}

	return Result;
}

/** This functions extracts from constraints a multilines string representing
 *  the attributes used in constraints.
 *
 *  @param S			the set of constraints from which extract the attributes
 *  @param NumOfAttrs	the number of attributes used in the problem
 *
 *  @return				the multilines string representing the attributes
 *						used in constraints
 */
string extractCSolution(const constraints &S, const int &NumOfAttrs) {
	string Result = "";

	Result = "";
	for(int i=0; i < S.size(); ++i) {
		Result += "- ";

		string Result2;
		for(int a = 0; a < NumOfAttrs; ++a) {
			if ( S.at(i).test(a) ) {
				Result2 = "1" + Result2;
			} else {
				Result2 = "0" + Result2;
			}
		}

		Result += Result2 + "\n";
	}

	return Result;
}

/** This functions extracts from targets a multilines string representing
 *  the attributes used in targets.
 *
 *  @param S			the set of targets from which extract the attributes
 *  @param NumOfAttrs	the number of attributes used in the problem
 *
 *  @return				the multilines string representing the attributes
 *						used in targets
 */
string extractTSolution(const targets &S, const int &NumOfAttrs) {
	string Result = "";

	for(int i=0; i < S.size(); ++i) {
		char buf[1024] = {'\0'};
		sprintf(buf, "- (%d) - ", S.at(i).Cost);
		Result += string(buf);

		string Result2;
		for(int a = 0; a < NumOfAttrs; ++a) {
			if ( S.at(i).TA.test(a) ) {
				Result2 = "1" + Result2;
			} else {
				Result2 = "0" + Result2;
			}
		}

		Result += Result2 + "\n";
	}

	return Result;
}

/** This is the access point of the program (as usualy in C++).
 *  It has the responsibility to manage the input arguments and
 *  to draw the flow of the program.
 *
 *  @param argc	the standard C++ argc argument of the main function:
 *				it represents the number of arguments on the command line
 *  @param argv	the standard C++ argv argument of the main function:
 *				it represents an array of "string" (in C faction) representing
 *				the command line arguments
 *
 *  @return		EXIT_FAILURE or EXIT_SUCCESS if the program termintates with
 *				some error or not, respectively.
 */
int main(int argc, char *argv[]) {
	// initialize random generator
	srand( (unsigned)time( NULL ) );

	// command line parsing
	cmd_line::getInstance().reset();

	if ( !cmd_line::getInstance().parseCommandLine(argc, argv) ){
		// help showing
		cout << "Warning: some problem in command line." << endl;
		cmd_line::getInstance().printCommandLineProblems(cout);
		cout << endl;
		cmd_line::getInstance().printHelp(cout);
		cout << endl;

		return EXIT_FAILURE;
	}

	if ( cmd_line::getInstance().isHelpToShow() ) {
		cmd_line::getInstance().printHelp(cout);
		cout << endl;

		return EXIT_SUCCESS;
	}

	constraints Cs = createTestConsrtaints();
	targets		Ts = createTestTargets();

	if ( cmd_line::getInstance().isTest2Run() ) {
		solution SE = calculateExhaustiveSolution(7, Cs, Ts);

		solution SG = calculateGreedySolution(7, Cs, Ts);

		cout << "Test Solution" << endl;
		cout << "-------------" << endl;

		cout << "\t- Exhaustive Solution (C: " << SE.Cost <<"):\t" << extractSolution(SE, 7) << endl;
		cout << "\t- Greedy Solution     (C: " << SG.Cost <<"):\t" << extractSolution(SG, 7) << endl;

	} else {
		const int MinNumOfAttrs = cmd_line::getInstance().getMinNumberOfAttributes();
		const int MaxNumOfAttrs = cmd_line::getInstance().getMaxNumberOfAttributes();

		timeb StartTime;
		timeb EndTime;

		cout << "# Attr.\tExhaustive\tGreedy\tTime E.\tTime G.\tTicks E.\tTicks G.\tSolution E.\tSolution G." << endl;

		for(int noa = MinNumOfAttrs; noa <= MaxNumOfAttrs; ++noa) {
			if ( cmd_line::getInstance().isExp2Run() ) {
				Cs	= getExpConstraints(noa);
				Ts	= getExpTargets(noa);
			} else if ( cmd_line::getInstance().isRandomExp2Run() ) {
				Cs	= generateConstraints(noa);
				Ts	= generateTargets(noa);
			} else {
				int NumOfAttributes = -1;
				bool isAllOk = initializeFromFile( cmd_line::getInstance().getExperimentPath(), noa, Cs, Ts);

				if (!isAllOk) {
					return EXIT_FAILURE;
				}
			}

			//cout << "C: " << endl << extractCSolution(Cs, noa) << endl;
			//cout << "T: " << endl << extractTSolution(Ts, noa) << endl;

			clock_t StartClock;
			clock_t EndClock;
			solution SE;
			double TimeE;
			long TicksE;

			if ( !cmd_line::getInstance().isOnlyGreedy() ) {
				ftime(&StartTime);
				StartClock = clock();
				SE = calculateExhaustiveSolution(noa, Cs, Ts);
				EndClock = clock();
				ftime(&EndTime);

				TimeE	= 1000.0*(EndTime.time-StartTime.time) + (EndTime.millitm-StartTime.millitm);
				TicksE		= EndClock - StartClock;
			}


			ftime(&StartTime);
			StartClock = clock();
			solution SG = calculateGreedySolution(noa, Cs, Ts);
			EndClock = clock();
			ftime(&EndTime);

			double TimeG	= 1000.0*(EndTime.time-StartTime.time) + (EndTime.millitm-StartTime.millitm);
			long TicksG		= EndClock - StartClock;

			if ( !cmd_line::getInstance().isOnlyGreedy() ) {
				cout << noa << "\t";
				cout << SE.Cost << "\t" << SG.Cost << "\t";
				cout << TimeE << "\t" << TimeG << "\t";
				cout << TicksE << "\t" << TicksG << "\t";
				cout << extractSolution(SE, noa) << "\t";
				cout << extractSolution(SG, noa) << endl;
			} else {
				cout << noa << "\t";
				cout << "---" << "\t" << SG.Cost << "\t";
				cout << "---" << "\t" << TimeG << "\t";
				cout << "---" << "\t" << TicksG << "\t";
				cout << "---" << "\t";
				cout << extractSolution(SG, noa) << endl;
			}
		}
	}

	return EXIT_SUCCESS;
}
