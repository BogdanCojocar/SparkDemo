SparkDemo
=========

A demo on how to use Spark. This program reads data in GPX format from a local
file system (or HDFS, S3). The GPX files should contain GPS tracks. After the tracks are read, the number of point in each track is reduced using Ramer Douglas Peucker reduction[1]. In the final step the data is saved in geoJson format in a local mongo db.

To build the project, and create a jar with external dependencies run:

gradle fatJar

After the jar has be created it can be executed on top of a Spark Cluster. Information on how to run a Spark cluster or standalone application can be found here: https://spark.apache.org/docs/latest/
 
[1] http://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm
