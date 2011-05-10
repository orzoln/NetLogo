ifeq ($(origin NETLOGO), undefined)
  NETLOGO=../..
endif

ifeq ($(origin SCALA_HOME), undefined)
  SCALA_HOME=../..
endif

SRCS=$(wildcard src/*.scala)

include config.mk

$(NAME).jar: $(SRCS) $(JARS) manifest.txt Makefile
	mkdir -p classes
	$(SCALA_HOME)/bin/scalac -g -deprecation -unchecked -encoding us-ascii -classpath $(NETLOGO)/NetLogo.jar:$(JARSPATH) -d classes $(SRCS)
	jar cmf manifest.txt $(NAME).jar -C classes .