package org.nlogo.prim.gui

import org.nlogo.api.{ Dump, I18N, LogoException, ReporterRunnable }
import org.nlogo.nvm.{ Command, Context, Syntax }
import org.nlogo.swing.OptionDialog
import org.nlogo.window.GUIWorkspace

class _usermessage extends Command {

  def syntax =
    Syntax.commandSyntax(Array(Syntax.TYPE_WILDCARD))

  override def perform(context: Context) {
    val message = Dump.logoObject(args(0).report(context))
    workspace match {
      case gw: GUIWorkspace =>
        gw.updateUI()
        val canceled = workspace.waitForResult(
          new ReporterRunnable[java.lang.Boolean] {
            override def run = {
              gw.view.mouseDown(false)
              java.lang.Boolean.valueOf(1 ==
                OptionDialog.show(gw.getFrame, "User Message", message,
                                  Array(I18N.gui.get("common.buttons.ok"),
                                        I18N.gui.get("common.buttons.halt"))))
            }}).booleanValue
        if(canceled)
          throw new org.nlogo.nvm.HaltException(true)
      case _ =>
        // if not in GUI, just do nothing
    }
    context.ip = next
  }

}