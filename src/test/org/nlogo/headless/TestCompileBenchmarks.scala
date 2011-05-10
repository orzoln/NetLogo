package org.nlogo.headless

import org.nlogo.api.Version
import org.scalatest.FunSuite
import org.nlogo.util.SlowTest

class TestCompileBenchmarks extends FunSuite with SlowTest{ 

  val names = Seq("Ants", "BZ", "CA1D", "Erosion", "Fire", "FireBig", "Flocking", "GasLabCirc",
                  "GasLabNew", "GasLabOld", "GridWalk", "Heatbugs", "Ising", "Life", "PrefAttach",
                  "Team", "Termites", "VirusNet", "Wealth", "Wolf", "ImportWorld")

  // this is here to help us remember that when the list of benchmarks changes, this file and the
  // contents of test/bench both need updating too - ST 2/11/09
  test("names") {
    assert(names.mkString("\n") === Dump.allBenchmarks.mkString("\n"))
  }

  if(Version.useGenerator && !Version.is3D)
    for(name <- names)
      test(name) {
        val dump = {
          val workspace = HeadlessWorkspace.newInstance
          workspace.open("test/models/benchmarks/" + name + " Benchmark.nlogo")
          val result = workspace.report("__dump")
          workspace.dispose()
          result
        }
        assert(dump === io.Source.fromFile("test/bench/" + name + ".txt").getLines.mkString("","\n","\n"))
      }
}