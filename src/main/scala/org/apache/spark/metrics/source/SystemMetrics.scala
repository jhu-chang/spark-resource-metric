package org.apache.spark.metrics.source

import com.codahale.metrics.{Gauge, MetricRegistry}

class SystemMetrics extends Source {
  override val sourceName: String = "system"

  override val metricRegistry: MetricRegistry = new MetricRegistry()


  val systemMonitor = new SystemMonitor(SigarHolder.sigar)

  metricRegistry.register(MetricRegistry.name("cpu_usage"), new Gauge[Double] {
    override def getValue: Double = systemMonitor.getCPUUsage()
  })

  metricRegistry.register(MetricRegistry.name("used_mem"), new Gauge[Long] {
    override def getValue: Long = systemMonitor.getUsedMem()
  })

  metricRegistry.register(MetricRegistry.name("free_mem"), new Gauge[Long] {
    override def getValue: Long = systemMonitor.getFreedMem()
  })

  metricRegistry.register(MetricRegistry.name("disk_read_bytes"), new Gauge[Long] {
    override def getValue: Long = systemMonitor.getDiskReadBytes()
  })

  metricRegistry.register(MetricRegistry.name("disk_write_bytes"), new Gauge[Long] {
    override def getValue: Long = systemMonitor.getDiskWriteBytes()
  })

  metricRegistry.register(MetricRegistry.name("total_rx_bytes"), new Gauge[Long] {
    override def getValue: Long = systemMonitor.getNICTotalRxBytes()
  })

  metricRegistry.register(MetricRegistry.name("total_tx_bytes"), new Gauge[Long] {
    override def getValue: Long = systemMonitor.getNICTotalTxBytes()
  })

//  metricRegistry.register(MetricRegistry.name("total_speed"), new Gauge[Long] {
//    override def getValue: Long = systemMonitor.getNICTotalSpeed()
//  })

  def registerNIC(nicName : String) = {
    metricRegistry.register(MetricRegistry.name( nicName  + "_tx_bytes"), new Gauge[Long] {
      override def getValue: Long = systemMonitor.getNICTxBytes(nicName)
    })

    metricRegistry.register(MetricRegistry.name( nicName  + "_rx_bytes"), new Gauge[Long] {
      override def getValue: Long = systemMonitor.getNICRxBytes(nicName)
    })

//    metricRegistry.register(MetricRegistry.name( nicName  + "_speed"), new Gauge[Long] {
//      override def getValue: Long = systemMonitor.getNICSpeed(nicName)
//    })
  }

  systemMonitor.nics.map(registerNIC)
}
