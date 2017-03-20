package org.idio.log4j.merger

import org.apache.logging.log4j.core.config.plugins.processor.PluginCache
import scala.collection.JavaConverters._
import java.io.{FileOutputStream, File}

object DatMerger {
  def merge(destination: File, files: Seq[File]) = {
    val aggregator = new PluginCache()
    val filesEnum: java.util.Enumeration[java.net.URL] = files.toIterator.map(_.toURI.toURL).asJavaEnumeration
    val outStream = new FileOutputStream(destination)
    
    try {
        aggregator.loadCacheFiles(filesEnum)
        aggregator.writeCache(outStream)
    }
    finally {
        outStream.close()
    }
  }
}
