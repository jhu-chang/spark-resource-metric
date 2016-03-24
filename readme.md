## System monitor for spark applications

### How to build
1. Pre-requirement: 
   Sigar is needed, in folder `lib`, there is a `sigar.jar` in this folder, which is sigar 1.6.4 and all in one, you can download the [sigar lib](https://github.com/hyperic/sigar) 
2. run 
   ```bash
   maven package
   ```

### How to use
1. using "--jars" option to passed in the `spark-system-metrics-1.0.jar` and `sigar.jar`, if you use manual download jar, make sure the jni binary is in system load path
2. Configure the new source class in `metrics.properties` by
   ```
   *.source.system.class=org.apache.spark.metrics.source.SystemMetrics
   *.source.proc.class=org.apache.spark.metrics.source.ProcMetrics
   ```
