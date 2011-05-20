/** This module define the function that load and initialize an
 *  experiment from a file.
 *
 *  version 1.0
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	PROBLEMLOADER_H__
#define			PROBLEMLOADER_H__

#include <string>

#include "constraints.h"
#include "targets.h"

/** This function loads the constraints and the targets from a configuration
 *  file used as argument
 *
 *  @param	FilePath	the path of the config file
 *  @param NumOfAttrs	the real number of attributes to use
 *	@param	Cs			the set of constraints to be used (output)
 *  @param	Ts			the set of targets to be used (output)
 *
 *  @return				true if all is ok, false in the others case
 */
bool initializeFromFile(const string FilePath, const int &NumOfAttrs, constraints &Cs, targets &Ts);

#endif //		PROBLEMLOADER_H__
