package org.apache.spark.metrics.source

import com.codahale.metrics.{Gauge, MetricRegistry}


class ProcMetrics extends Source {
  val pid = SigarHolder.sigar.getPid()
  override val sourceName: String = "proc_" + pid

  override val metricRegistry: MetricRegistry =  new MetricRegistry()


  val procMonitor = new ProcessMonitor(SigarHolder.sigar, pid)

  metricRegistry.register(MetricRegistry.name("cpu_usage"), new Gauge[Double] {
    override def getValue: Double = procMonitor.getCPUUsage()
  })

  metricRegistry.register(MetricRegistry.name("vm_size"), new Gauge[Long] {
    override def getValue: Long = procMonitor.getUsedMem()
  })

  metricRegistry.register(MetricRegistry.name("res_size"), new Gauge[Long] {
    override def getValue: Long = procMonitor.getFreedMem()
  })
}
