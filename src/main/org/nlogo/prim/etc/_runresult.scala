package org.nlogo.prim.etc

import org.nlogo.api.{ CompilerException, LogoException }
import org.nlogo.nvm.{ Activation, ArgumentTypeException, Context, EngineException, Reporter, ReporterLambda, Syntax }

class _runresult extends Reporter {

  override def syntax =
    Syntax.reporterSyntax(
      Array(Syntax.TYPE_STRING | Syntax.TYPE_REPORTER_LAMBDA,
        Syntax.TYPE_REPEATABLE | Syntax.TYPE_WILDCARD),
      Syntax.TYPE_WILDCARD,
      1)

  override def report(context: Context) =
    args(0).report(context) match {
      case s: String =>
        if(args.size > 1)
          throw new EngineException(context, this,
            token.name + " doesn't accept further inputs if the first is a string")
        try {
          val procedure = workspace.compileForRun(
            argEvalString(context, 0), context, true)
          val newActivation = new Activation(
            procedure, context.activation, context.ip)
          newActivation.setUpArgsForRunOrRunresult()
          val result = context.callReporterProcedure(newActivation)
          if (result == null)
            throw new EngineException(context, this, "failed to report a result")
          result
        } catch {
          case ex: CompilerException =>
            throw new EngineException(
              context, this, ex.getMessage)
          case ex: EngineException =>
            throw new EngineException(context, ex.instruction, ex.getMessage)
          case ex: LogoException =>
            throw new EngineException(context, this, ex.getMessage)
        }
      case lambda: ReporterLambda =>
        val n = args.size - 1
        if(n < lambda.formals.size)
          throw new EngineException(
            context, this, lambda.missingInputs(n))
        val actuals = new Array[AnyRef](n)
        var i = 0
        while(i < n) {
          actuals(i) = args(i + 1).report(context)
          i += 1
        }
        lambda.report(context, actuals)
      case obj =>
        throw new ArgumentTypeException(
          context, this, 0, Syntax.TYPE_REPORTER_LAMBDA | Syntax.TYPE_STRING, obj)
    }

}