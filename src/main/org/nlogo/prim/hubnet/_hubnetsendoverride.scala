package org.nlogo.prim.hubnet

import org.nlogo.nvm.Syntax._
import org.nlogo.nvm.{Context, Syntax}
import org.nlogo.agent.{ArrayAgentSet, Agent, AgentSet}
import org.nlogo.util.JCL._
import org.nlogo.api.{CommandRunnable, LogoException}

class _hubnetsendoverride extends org.nlogo.nvm.Command {
  override def syntax = Syntax.commandSyntax(
    Array(TYPE_STRING, TYPE_AGENTSET | TYPE_AGENT, TYPE_STRING, TYPE_REPORTER_BLOCK),
    "OTPL", "?", false)

  @throws(classOf[LogoException])
  override def perform(context: Context) {
    val client = argEvalString(context, 0)
    val target = args(1).report(context)
    val varName = argEvalString(context, 2)

    val set = target match {
      case a: Agent =>
        val aas = new ArrayAgentSet(a.getAgentClass(), 1, false, world)
        aas.add(a)
        aas
      case as: AgentSet => as
      case _ => throw new IllegalStateException("cant happen...")
    }

    val freshContext = new Context(context, set)
    args(3).checkAgentSetClass(set, context)

    // ugh..set.iterator is not a real iterator.
    val overrides = new collection.mutable.HashMap[Long,Any]()
    val it = set.iterator
    while(it.hasNext) {
      val agent = it.next
      overrides += (agent.id -> freshContext.evaluateReporter(agent, args(3)))
    }

    workspace.waitFor(new CommandRunnable() {
      @throws(classOf[LogoException])
      def run() { workspace.getHubNetManager.sendOverrideList(client, set.`type`(), varName, overrides.toMap) }
    })
    context.ip = next
  }
}