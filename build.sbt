lazy val commonSettings: Seq[Setting[_]] = Seq(
  git.baseVersion in ThisBuild := "2.1.0",
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
    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.0.0-RC1"),
    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-core" % "2.17.2"
    ),
    crossSbtVersions := Seq("0.13.16", "1.2.8"),
    crossScalaVersions := Seq("2.11.8", "2.12.10"),   
    publishArtifact in (Compile, packageBin) := true,
    publishArtifact in (Test, packageBin) := false,
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in (Compile, packageSrc) := true,
  )
