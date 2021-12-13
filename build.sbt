lazy val commonSettings: Seq[Setting[_]] = Seq(
  git.baseVersion in ThisBuild := "2.0.2",
  organization in ThisBuild := "org.idio"
)

lazy val root = (project in file(".")).
  enablePlugins(GitVersioning).
  settings(commonSettings: _*).
  settings(
    sbtPlugin := true,
    name := "sbt-assembly-log4j2",
    description := "sbt assembly plugin merge strategy for log4j2 plugins",
    licenses := Seq("MIT License" -> url("https://github.com/idio/sbt-assembly-log4j2/blob/master/LICENSE")),
    scalacOptions := Seq("-deprecation", "-unchecked"),
    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10"),
    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-core" % "2.15.0"
    ),
    crossSbtVersions := Seq("0.13.16", "1.2.8"),
    crossScalaVersions := Seq("2.11.8", "2.12.10"),   
    publishArtifact in (Compile, packageBin) := true,
    publishArtifact in (Test, packageBin) := false,
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in (Compile, packageSrc) := true,
    publishTo := Some("Blob Snapshots" at "blob://optimizelyds/artifacts")
  )
