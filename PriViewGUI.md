# Graphical User Interface Introduction #

In page we describe the GUI of the presented tool.

The main application window is characterized by the following three areas:
  1. on the top of the form there is the application menus area;
  1. under the application menus area there is the constraints view area, showing the constraints already inserted;
  1. under the constraints view area there is the targets view area, showing the targets already inserted;

## Menus ##

The application menus area is characterized by the following structure:
  * File
    * New..., to create a new experimental project;
    * Load..., to load an already saved project;
    * Save As..., to save a project in a specific location in the file system;
    * Save, to save the project (only if it has already been saved with the Save As... menu);
    * Preferences..., to configure the application preferences (i.e., which application has to use for the experiments);
    * Exit, to close the application;
  * Project
    * Add Constraint..., to add a new constraint to the project;
    * Add Target..., to add a new target to the project;
    * Run..., to execute the experiments represented by the current project;
  * ?
    * About..., to show the credit information about the application.

### Menu: File>>New ###

This menu item shows the dialog used to create a new project. In this dialog there are two mandatory fields: Project Name, representing the name of the new project, and Num. Of Attributes, representing the maximum number of attributes that can be used to describe constraints and targets. This last field has to be an integer number in the range between 4 and 120. The button Ok closes the dialog, creating the new project; while the button Cancel closes the dialog without creating any project.

### Menu: File>>Preferences ###

This menu item shows the dialog used to configure the preferences of the application. The dialog is composed of a combobox representing the type of OS supported, and a text field representing the C++ application to run to generate the experiments. The combobox can assume the following values:
  * Windows, for MS Windows machines;
  * Unix like, for Unix based machines (e.g., Linux, Mac OS X, etc.);
  * Others, for all the other machines.

The text field (that can be modified only when Others is selected in the combobox) represents the name of the application to run to generate the experiments; this application has to be stored in the GUI application directory.

### Menu: Project>>Add Constraint ###

This menu item shows the dialog used to add a constraint to the project. The main area is composed of a set of checkboxes representing all the attributes in the project. Checked boxes represent the attributes composing the constraint. The button Add closes the dialog adding the constraint, while the button Cancel closes the dialog only.

### Menu: Project>>Add Target ###

This menu item shows the dialog used to add a target to the project. On the top of the dialog there is a field to set the weight for the target; while the main area is composed of a set of checkboxes representing the attributes composing the target set. The button Add closes the dialog adding the target, while the button Cancel closes the dialog only.

### Menu: Project>>Run ###

This menu item shows the dialog used to start experiments. The dialog is composed of a checkbox named Run Only Greedy Algorithm, and two fields named Min Number of Attributes and Max Number of Attributes. The checkbox, if selected, permits to lunch the experiments using only the greedy algorithm, while the two fields represent the extremes of the range for the number of attributes used to produce the experimental results. This means that the program will be invoked iteratively with, as a number of attributes, all the values in the range defined by the two fields. Note that constraints and targets are adjusted in length (trunking or padding) according with the number of attributes in use. The button Run executes experiments, while the button Cancel closes the dialog only.

When the experiments are finished the dialog showed in the next picture is displayed. This dialog is a table composed of the following columns:
  * the number of attributes used for the experiment;
  * the cost of the exhaustive solution;
  * the cost of the greedy solution;
  * the time spent to find the exhaustive solution (in milliseconds);
  * the time spent to find the greedy solution (in milliseconds);
  * the time spent to find the exhaustive solution (in cpu ticks);
  * the time spent to find the greedy solution (in cpu ticks);
  * the exhaustive solution (represented ad a string of bits);
  * the greedy solution (represented ad a string of bits).

Each row represents the results related with the specific number of attributes, indicated in the first cell of the row itself. The button Save... permits to save the table content in a text file, while the button Cancel closes the dialog

## Constraints view and targets view ##

Constraints view and targets view areas support specific actions related with the management of constraints and targets, respectively, via popup menus. The two popup menus are identical. In the following we describe only the popup menu related with the constraints view:
  * Add Constraint..., to add a new constraint to the project;
  * Modify Constraint..., to modify the selected constraint;
  * Delete Constraint, to delete the selected constraint;
  * Delete All Constraint, to delete all the constraints already inserted.