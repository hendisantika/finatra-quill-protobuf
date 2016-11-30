import sbt.Keys._

name := "finatra-mysql-quill-protobuff"
organization := "com.hendisantika"
version := "0.0.1"

scalaVersion := "2.11.8"

fork in run := true
parallelExecution in Test := false

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.jcenterRepo,
  "Twitter Maven" at "https://maven.twttr.com",
  "Finatra Repo" at "http://twitter.github.com/finatra",
  "jitpack" at "https://jitpack.io",
  "Sonatype OSS Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2",
  "wasted.io/repo" at "http://repo.wasted.io/mvn")

compileOrder := CompileOrder.JavaThenScala

PB.targets in Compile := Seq(
  scalapb.gen(grpc=false) -> (sourceManaged in Compile).value
)

// Support Quill Query Probing
//unmanagedClasspath in Compile += baseDirectory.value / "src" / "main" / "resources"

// assembly for packaging as single jar
assemblyMergeStrategy in assembly := {
  case "BUILD" => MergeStrategy.first
  case PathList("META-INF", "maven", "org.slf4j", xs @ _*) => MergeStrategy.first
  case PathList("META-INF", "maven", "org.reflections",  xs @ _*) => MergeStrategy.first
  case PathList("META-INF", "maven", "ch.qos.logback",  xs @ _*) => MergeStrategy.first
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.discard
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("META-INF","io.netty", "netty-transport", xs @ _*) => MergeStrategy.last
  case PathList("io" ,"netty", xs @ _*) => MergeStrategy.last
  case PathList("org","apache", xs @ _*) => MergeStrategy.last
  case PathList("commons-logging", "commons-logging" , xs @ _*) => MergeStrategy.first
  case PathList("org", "slf4j" , xs @ _*) => MergeStrategy.first
  case other => MergeStrategy.defaultMergeStrategy(other)
}
assemblyJarName in assembly := s"${name.value}-${version.value}.jar"

test in assembly := {}

lazy val versions = new {
  val finatra             = "2.5.0"
  val finagle             = "6.39.0"
  val guice               = "4.0"
  val logback             = "1.1.+"
  val protobuf            = "3.0.0"
  val scalapb             = "0.5.42"
  val scalapb_json4s      = "0.1.3-SNAPSHOT"
  val jwtscala            = "0.8.1"
  val getquill            = "0.10.0"
  val async               = "0.9.5"
  var twitterAsync        = "516e77a"
  val typesafeConfig      = "1.3.0"
  val ficus               = "1.2.6"
  val typesafeConfigGuice = "0.0.3"
  //  val commonsEmail        = "1.4"
  val hamsters            = "1.0.7"
  val accord              = "0.6"
  val circe               = "0.5.4"

  val mockito = "1.9.5"
  val scalatest = "2.2.6"
  val specs2 = "2.3.12"
}

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
  "org.scala-lang.modules" % "scala-async_2.11" %  versions.async,

  "com.twitter" % "finatra-http_2.11" % versions.finatra,
  "com.twitter" % "finagle-redis_2.11" % versions.finagle,
  "ch.qos.logback" % "logback-classic" % versions.logback,
  "com.google.protobuf" % "protobuf-java" % versions.protobuf,
  "com.google.protobuf" % "protobuf-java-util" % versions.protobuf,
  "com.pauldijou" % "jwt-core_2.11" % versions.jwtscala,
  "io.getquill" % "quill-finagle-mysql_2.11" % versions.getquill,
  "com.github.foursquare" % "twitter-util-async" % versions.twitterAsync,
  "com.typesafe" % "config" % versions.typesafeConfig,
  "com.github.racc" % "typesafeconfig-guice" % versions.typesafeConfigGuice,
  "com.iheart" % "ficus_2.11" % versions.ficus,
  //  "org.apache.commons" % "commons-email" % versions.commonsEmail,
  "com.trueaccord.scalapb" %% "scalapb-json4s" % versions.scalapb_json4s,
  "io.github.scala-hamsters" %% "hamsters" % versions.hamsters,
  "com.wix" %% "accord-core" % versions.accord,
  "io.circe" %% "circe-core" % versions.circe,
  "io.circe" %% "circe-generic" % versions.circe,
  "io.circe" %% "circe-parser" % versions.circe,

  "com.trueaccord.scalapb" %% "scalapb-runtime" % versions.scalapb % "protobuf",

  "com.twitter" % "finatra-http_2.11" % versions.finatra % Test,
  "com.twitter" % "inject-server_2.11" % versions.finatra % Test,
  "com.twitter" % "inject-app_2.11" % versions.finatra % Test,
  "com.twitter" % "inject-core_2.11" % versions.finatra % Test,
  "com.twitter" % "inject-modules_2.11" % versions.finatra % Test,
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % Test,

  "com.twitter" % "finatra-http_2.11" % versions.finatra % Test classifier "tests",
  "com.twitter" % "inject-server_2.11" % versions.finatra % Test classifier "tests",
  "com.twitter" % "inject-app_2.11" % versions.finatra % Test classifier "tests",
  "com.twitter" % "inject-core_2.11" % versions.finatra % Test classifier "tests",
  "com.twitter" % "inject-modules_2.11" % versions.finatra % Test classifier "tests",

  "org.mockito" % "mockito-core" % versions.mockito % Test,
  "org.scalatest" % "scalatest_2.11" % versions.scalatest % Test,
  "org.specs2" % "specs2_2.11" % versions.specs2 % Test,
  "jp.sf.amateras.solr.scala" %% "solr-scala-client" % "0.0.12",
  "io.wasted" %% "wasted-util" % "0.12.4",
  "com.twitter" % "finagle-serversets_2.11" % "6.39.0",
  "com.tsukaby" %% "naive-bayes-classifier-scala" % "0.1.0"
).map(_.exclude("org.slf4j","slf4j-log4j12")).map(_.exclude("org.slf4j","slf4j-jdk14"))

Revolver.settings
    