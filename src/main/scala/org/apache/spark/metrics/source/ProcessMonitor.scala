package org.apache.spark.metrics.source

import org.hyperic.sigar.{ProcCpu, ProcMem, Sigar}


class ProcessMonitor(sigar : Sigar, pid : Long) {
  var procCpu = new ProcCpu()
  val procMem = new ProcMem()
  val numberCPU = sigar.getCpuList().length

  /**percentage*/
  def getCPUUsage() : Double = {
    procCpu.gather(sigar, pid)
    procCpu.getPercent() / numberCPU
  }

  /**unit: bytes*/
  def getUsedMem() : Long = {
    procMem.gather(sigar, pid);
    procMem.getSize()
  }

  /**unit: bytes*/
  def getFreedMem() : Long = {
    procMem.gather(sigar, pid);
    procMem.getResident()
  }
}
