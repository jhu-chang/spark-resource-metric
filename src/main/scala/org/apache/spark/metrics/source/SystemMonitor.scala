package org.apache.spark.metrics.source

import org.hyperic.sigar.{FileSystem, NetFlags, Sigar}

import scala.util.Try;

class SystemMonitor(val sigar : Sigar) {
  val CACHED_DURATION = 1000
  val fileSystemNames : Seq[String] = Try(sigar.getFileSystemList().filter(
    _.getType() == FileSystem.TYPE_LOCAL_DISK).map(_.getDirName).toSeq).getOrElse(Seq())

  val nics : Seq[String] = Try(sigar.getNetInterfaceList().filter(
    x => (sigar.getNetInterfaceConfig(x).getFlags() &  NetFlags.IFF_UP) != 0
  ).toSeq).getOrElse(Seq())

  def getCPUUsage() : Double = {
    sigar.getCpuPerc.getCombined
  }

  def getUsedMem() : Long = {
    sigar.getMem.getActualUsed
  }

  def getFreedMem() : Long = {
    sigar.getMem.getActualFree
  }
  /* unit: bytes */
  def getDiskReadBytes() : Long = {
    fileSystemNames.map(
        x => sigar.getFileSystemUsage(x).getDiskReadBytes).reduceOption(_ + _).getOrElse(0L)
  }

  /* unit: bytes/second */
  def getDiskWriteBytes() : Long = {
   fileSystemNames.map(
        x => sigar.getFileSystemUsage(x).getDiskWriteBytes).reduceOption(_ + _).getOrElse(0L)
  }

  def getNICTotalRxBytes() : Long = {
    nics.map(
        x => sigar.getNetInterfaceStat(x).getRxBytes).reduceOption(_ + _).getOrElse(0L)
  }

  def getNICTotalTxBytes() : Long = {
    nics.map(
      x => sigar.getNetInterfaceStat(x).getTxBytes).reduceOption(_ + _).getOrElse(0L)
  }

  def getNICTotalSpeed() : Long = {
    nics.map(
      x => sigar.getNetInterfaceStat(x).getSpeed).reduceOption(_ + _).getOrElse(0L)
  }

  def getNICRxBytes(nicName : String) : Long = {
    sigar.getNetInterfaceStat(nicName).getRxBytes
  }

  def getNICTxBytes(nicName : String) : Long = {
    sigar.getNetInterfaceStat(nicName).getTxBytes
  }

  def getNICSpeed(nicName : String) : Long = {
    sigar.getNetInterfaceStat(nicName).getSpeed
  }
}
