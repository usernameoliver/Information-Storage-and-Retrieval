Instruction on Running ISR1:
Step 1.   Open Eclipse
Step 2. Import project
Click File -> import -> Existing project into Workspace
In the Import window, click browse and configure the path where the testing scripts is stored. 
Step 3. Import Data 
Click File -> import -> File System
In the Import window, click browse and configure the path where the data(docset.trectext, docset.trecweb, stopword.txt) is stored. 
Step 4. Run the main file
In the Project Explorer of Eclipse, double click HW1Main.java, click run. 

P.S. Configuration
About Eclipse:
Eclipse Java EE IDE for Web Developers.
Version: Mars Release (4.5.0)
Build id: 20150621-1200
About Java:
javac 1.7.0_79

//***************************************************************************************************//
Instructions on running ISR2
Step 1. Create directory
 cd /home/hadoop/isr/isrAssignment1/ISR1/src Step 2.Compile java files in Indexing folder.
 javac Indexing/PreProcessedCorpusReader.java
 javac Indexing/MyIndexWriter.java
 javac Indexing/MyIndexReader.java
 javac HW2Main.java
 java HW2Main
