package org.apache.spark.metrics.source

import org.hyperic.sigar.Sigar

/**
  * Created by jhu on 2016/3/23.
  */
object SigarHolder {
  /// singleton of sigar object
  val sigar : Sigar = new Sigar()
}
