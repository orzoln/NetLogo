package org.nlogo.compiler
import org.nlogo.compiler.CompilerExceptionThrowers._
import org.nlogo.nvm.{Command,Reporter}
import org.nlogo.prim._
/**
 * This is an AstVisitor that connects "error-message" reporters to
 * their enclosing "carefully" commands.
 */
private class CarefullyVisitor extends DefaultAstVisitor {
  private val stack = new collection.mutable.Stack[_carefully]
  override def visitStatement(stmt:Statement) {
    stmt.command match {
      case c:_carefully =>
        // carefully takes two arguments, both command blocks.
        // error-message is allowed only within the second block.
        stmt(0).accept(this)
        stack.push(c)
        stmt(1).accept(this)
        stack.pop()
      case _ => super.visitStatement(stmt)
    }
  }
  override def visitReporterApp(app:ReporterApp) {
    app.reporter match {
      case em:_errormessage =>
        if(stack.isEmpty) exception(em.token.name + " cannot be used outside of CAREFULLY",app)
        em.let = stack.top.let
      case _ => super.visitReporterApp(app)
    }
  }
}