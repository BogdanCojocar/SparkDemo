#!/bin/bash

cd Development/spark-1.1.0-bin-hadoop2.4/
./sbin/start-master.sh --ip bogdan-Aspire-V5-571PG --port 7077 --webui-port 8080

./bin/spark-class org.apache.spark.deploy.worker.Worker spark://bogdan-Aspire-V5-571PG:7077
