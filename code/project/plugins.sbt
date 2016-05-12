// +-------------------------------------------------------------------------------------+
// | Dependency graph SBT plugin (https://github.com/jrudolph/sbt-dependency-graph)      |
// | Lists all library dependencies in a nicely formatted way for easy inspection.       |
// +-------------------------------------------------------------------------------------+
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")


// +-------------------------------------------------------------------------------------+
// | SBT-Pack SBT plugin (https://github.com/xerial/sbt-pack)                            |
// | A sbt plugin for creating distributable Scala packages that include dependent jars  |
// | and launch scripts.                                                                 |
// +-------------------------------------------------------------------------------------+
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.8.0")
