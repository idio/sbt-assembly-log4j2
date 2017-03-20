# Log4j2 plugins cache file merger

**Problem**: When including several projects based on log4j2, `sbt-assembly` will encounter two different versions of `org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat` that it won't be able to merge automatically

**Solution**: Log4j2 provides a `PluginCache` class that creates consistent, serialisable cache from multiple different ones. 

**This plugin** provides a simple function to merge those files in a safe way.

## Usage

In your merge strategy, add the following case:

```scala
assemblyMergeStrategy in assembly := {
    //...
    case PathList(ps @ _*) if ps.last == "Log4j2Plugins.dat" =>
        new MergeStrategy {
          import org.idio.log4j.merger.DatMerger

          val name = "merge Log4j2Plugins.dat files"

          def apply(tempDir: File, path: String, files: Seq[File]): Either[String, Seq[(File, String)]] = {
           val file = MergeStrategy.createMergeTarget(tempDir, path)
           DatMerger.merge(file, files)
           Right(Seq(file -> path))
          }
        }
    //...
```
