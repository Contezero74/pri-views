/** This module define the function that load and initialize an
 *  experiment from a file.
 *
 *  version 1.0
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */

#include <iostream>
#include <fstream>

#include "problemloader.h"

#include "strutil.h"

/** This function loads the constraints and the targets from a configuration
 *  file used as argument
 *
 *  @param	FilePath	the path of the config file
 *	@param	Cs			the set of constraints to be used (output)
 *  @param	Ts			the set of targets to be used (output)
 *
 *  @return				true if all is ok, false in the others case
 */
bool initializeFromFile(const string FilePath, const int &NumOfAttrs, constraints &Cs, targets &Ts) {
	bool Result = false;

	Cs.clear();
	Ts.clear();

	bool areConstraints				= true; // true if loading constraints, otherwize false

	string Line;

	ifstream f( FilePath.c_str() );
	if ( f.is_open() ) {
		while ( !f.eof() ) {
			getline(f, Line);

			util::trim(Line);

			if ( 0 != Line.size() ) {
				if ( '#' == Line.at(0) ) {
					; // comment (nothing to do)
				} else if ( '-' == Line.at(0) ) {
					// check what have to load
					if ( "- CONSTRAINTS" == Line ) {
						areConstraints = true;
					} else if ( "- TARGETS" == Line ) {
						areConstraints = false;
					}
				}else if ( '\n' != Line.at(0) ) {
					// data
					if (areConstraints) {
						// Constraints data: bit-array
						Cs.push_back( constraint( adjust(Line, NumOfAttrs) ) );
					} else {
						// Targets data: cost [tab] bit-array
						string::size_type TabPos = Line.find_first_of("\t");
						string CostStr		= Line.substr(0, TabPos);
						string TargetStr	= Line.substr(TabPos + 1);

						Ts.push_back( target( adjust(TargetStr, NumOfAttrs), util::convertStr<int>(CostStr) ) );
					}
				}
			}
		}

		Result = true;
	} else {
		cout << "Error: Impossible to open " << FilePath << " file" << endl;
	}

	return Result;
}
