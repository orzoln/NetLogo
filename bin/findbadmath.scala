#!/bin/sh
exec bin/scala -classpath bin -deprecation -nocompdaemon "$0" "$@" 
!# 
// Local Variables:
// mode: scala
// End:

// The purpose of all this is to make sure that every class and interface is declared strictfp, and
// that we never ever use Math, only StrictMath.

import Scripting.{shell,read}

def withoutComments(lines:Iterator[String]):List[String] = {
  def helper(lines:List[String]):List[String] =
    if(lines.isEmpty) lines
    else {
      val (nonComment,rest) = lines.span(!_.containsSlice("/*"))
      nonComment ::: helper(rest.dropWhile(!_.containsSlice("*/")).drop(1))
    }
  helper(lines.filter(!_.matches("""^//.*""")).toList)
}

// first do the strictfp check

val okDeclarations =
  List("strictfp class","public strictfp class","public abstract strictfp","public final strictfp",
       "public strictfp class", "final strictfp class", "strictfp class", "strictfp final class",
       "abstract strictfp class", "public strictfp final class", "public enum","interface", "public interface",
       "class TokenLexer") // let's not bother making JFlex emit "strictfp"
for {
  path <- shell("""find src -name \*.java""")
  if !path.containsSlice("/gl/render/")  // we don't care if OpenGL stuff is strictfp
} {
  val lines = for{line <- withoutComments(read(path))
                  if !line.matches("""\s*""")
                  if !line.matches("""package.*""")
                  if !line.matches("""import.*""")}
              yield line
  if(!lines.exists(line => okDeclarations.exists(ok => line.startsWith(ok + " "))))
    println("needs strictfp: " + path)
}

// now do the StrictMath check

for{path <- shell("""find src -name \*.java""")
    if !path.containsSlice("/gl/render/")  // we don't care if OpenGL stuff is strictfp
    if path != "src/org/nlogo/headless/TestCommands.java"}
  // this isn't the absolutely correct check to be doing, but it seems like a good enough heuristic
  // for now - ST 5/8/03
  if(read(path)
     .filter(!_.containsSlice("Mathematica"))
     .filter(!_.containsSlice("DummyMath"))
     .exists(_.matches(""".*[^t]Math.*""")))
    println("needs StrictMath: " + path)
