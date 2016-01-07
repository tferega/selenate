// +-------------------------------------------------------------------------------------+
// | Dependency graph SBT plugin (https://github.com/jrudolph/sbt-dependency-graph)      |
// | Lists all library dependencies in a nicely formatted way for easy inspection.       |
// +-------------------------------------------------------------------------------------+
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")


// +-------------------------------------------------------------------------------------+
// | SBT-Pack SBT plugin (https://github.com/xerial/sbt-pack)                            |
// | A sbt plugin for creating distributable Scala packages that include dependent jars  |
// | and launch scripts.                                                                 |
// +-------------------------------------------------------------------------------------+
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.7.5")

// +-------------------------------------------------------------------------------------+
// | SBT-PGP SBT plugin (https://github.com/sbt/sbt-pgp)                                 |
// | Provides PGP signing of compile artifacts for SBT.                                  |
// +-------------------------------------------------------------------------------------+
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

// +-------------------------------------------------------------------------------------+
// | SBT-Sonatype SBT plugin (https://github.com/xerial/sbt-sonatype)                    |
// | Plugin for publishing projects to the Maven central repository through the REST API |
// | of Sonatype Nexus.                                                                  |
// +-------------------------------------------------------------------------------------+
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")