# How to Use: a simple example #

After the start of the Java program, the reader has to create a new project using the menu File > New.... Assuming the project is called Example and 7 attributes are used, the reader has to insert Example and 7 in the two fields of the dialog, and then press the Ok button.

Now the reader can insert the following 5 constraints represented as a string of bits where 1 means that the attribute is part of the constraint and the left most value represents the first attribute:
  * 1 0 0 0 1 0 0
  * 1 0 1 0 0 0 0
  * 0 1 0 1 1 0 0
  * 0 1 1 1 0 0 0
  * 0 0 0 0 1 1 0

For each constraint, the reader has to click the menu Project > Add Constraint..., check the checkboxes corresponding to the attributes set to 1, and then click the Add button.

After that, the reader can insert the following 6 targets with the associated weight:
  * 0 0 0 1 1 0 0, with weight 5
  * 0 1 0 0 1 0 0, with weight 4
  * 0 0 0 0 1 1 0, with weight 10
  * 0 0 1 0 1 0 0, with weight 1
  * 0 0 0 0 1 0 0, with weight 7
  * 0 0 1 1 0 0 1, with weight 7

For each target, the reader has to click the menu Project > Add Target..., insert the relative weight, and check the checkboxes corresponding to the attributes set to 1. Finally, the reader can click the Add button to save the target.

The last step is to run the experiment using the menu Project > Run.... Now, the application asks to save the project, and then shows the dialog used to start the experiments. The reader has to insert the values 7 and 7 in the fields of the dialog and then press the Ok button.

After a while a new dialog will  show the result of the experiment.
The table of this dialog is composed of the following columns:
  * the number of attributes used for the experiment;
  * the cost of the exhaustive solution;
  * the cost of the greedy solution;
  * the time spent to find the exhaustive solution (in milliseconds);
  * the time spent to find the greedy solution (in milliseconds);
  * the time spent to find the exhaustive solution (in cpu ticks);
  * the time spent to find the greedy solution (in cpu ticks);
  * the exhaustive solution (represented ad a string of bits);
  * the greedy solution (represented ad a string of bits).

In the string of bits representing the solution, value 1 means that the attribute belongs to Fo, the remaining attributes compose Fs.