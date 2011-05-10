package org.nlogo.prim.etc

import org.nlogo.agent.Agent
import org.nlogo.api.{ Dump, LogoListBuilder }
import org.nlogo.nvm.{ Context, EngineException, Reporter, Syntax }

class _sorton extends Reporter {

  override def syntax =
    Syntax.reporterSyntax(
      Array(Syntax.TYPE_REPORTER_BLOCK, Syntax.TYPE_AGENTSET),
      Syntax.TYPE_LIST,
      "OTPL", "?")  // ? = reporter block has unknown agent type

  override def report(context: Context) = {
    val reporterBlock = args(0)
    val agents = argEvalAgentSet(context, 1)
    val freshContext = new Context(context, agents)
    reporterBlock.checkAgentSetClass(agents, context)
    val pairs = new Array[(Agent, AnyRef)](agents.count)
    val it = agents.shufflerator(world.mainRNG)
    var i = 0
    while(i < pairs.length) {
      val a = it.next()
      pairs(i) = (a, freshContext.evaluateReporter(a, reporterBlock))
      i += 1
    }
    implicit val o = ordering(context)
    scala.util.Sorting.stableSort(pairs)
    val result = new LogoListBuilder
    result.addAll(pairs.view.map(_._1))
    result.toLogoList
  }

  def ordering(context: Context) =
    new Ordering[(Agent, AnyRef)] {
      def compare(pair1: (Agent, AnyRef), pair2: (Agent, AnyRef)) =
        (pair1._2, pair2._2) match {
          case (d1: java.lang.Double, d2: java.lang.Double) =>
            d1.compareTo(d2)
          case (s1: String, s2: String) =>
            s1.compareTo(s2)
          case (a1: Agent, a2: Agent) =>
            a1.compareTo(a2)
          case (o1: AnyRef, o2: AnyRef) =>
            throw new EngineException(
              context, _sorton.this , 
              "SORT-ON works on numbers, strings, or agents of the same type, " +
              "but not on " + Syntax.aTypeName(o1) + " and " + Syntax.aTypeName(o2))
        }
    }

}