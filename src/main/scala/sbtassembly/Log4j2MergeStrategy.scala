package sbtassembly

import org.apache.logging.log4j.core.config.plugins.processor.PluginCache
import sbt.IO
import sbt.io.Using
import sbtassembly.Assembly.JarEntry

import java.io.{BufferedInputStream, FileInputStream, FileOutputStream}
import java.util.UUID
import scala.collection.JavaConverters.asJavaEnumerationConverter

object Log4j2MergeStrategy {
  val plugincache: MergeStrategy = MergeStrategy("log4j2::plugincache", 2) { dependencies =>
    val DatFileExt = ".dat"
    val (datFiles, datURIs) = dependencies.map { dep =>
      IO.withTemporaryFile(UUID.randomUUID().toString, DatFileExt, true) { datFile =>
        IO.transfer(dep.stream(), datFile)
        datFile -> datFile.toURI.toURL
      }
    }.unzip

    val stream = IO.withTemporaryFile(UUID.randomUUID().toString, DatFileExt, true) { mergedDatFile =>
      Using.bufferedOutputStream(new FileOutputStream(mergedDatFile)) { os =>
        val aggregator = new PluginCache()
        aggregator.loadCacheFiles(datURIs.toIterator.asJavaEnumeration)
        aggregator.writeCache(os)
        () => new BufferedInputStream(new FileInputStream(mergedDatFile))
      }
    }
    Right(JarEntry(dependencies.head.target, stream) +: Vector.empty)
  }
}
