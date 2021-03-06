CURRENT_DIR := $(shell pwd)
SRC_DIR = $(CURRENT_DIR)/src
BUILD_DIR = $(CURRENT_DIR)/build
JAVAC := javac
JAVA = java -Dio.netty.leakDetection.level=advanced -Dlogging.level.reactor.ipc.netty=DEBUG -Dio.netty.leakDetection.level=paranoid

empty:=
space:=$(empty) 
CLASSES = $(patsubst $(SRC_DIR)%.java,$(BUILD_DIR)%.class,$(shell find $(SRC_DIR)/ -name "*.java"))
LIBS = $(subst $(space),,$(patsubst %,:%, $(shell find $(CURRENT_DIR)/libs $(CURRENT_DIR)/extlib $(CURRENT_DIR)/plugin/libs -name "*.jar")))
RUNLIBS = $(subst $(space),,$(patsubst %,:%, $(shell find $(CURRENT_DIR)/libs $(CURRENT_DIR)/plugin/libs -name "*.jar")))
JAR = $(CURRENT_DIR)/bin/out.jar


all: $(JAR)
	@echo "all done"

clean:
	rm -r $(BUILD_DIR)/*
	rm $(JAR)

run: $(JAR) $(SO)
	$(JAVA) -Xms32m -Xmx800m -Dfile.encoding=utf-8 -Xbootclasspath/a:$(RUNLIBS) -jar $(JAR)
	
	
$(CLASSES): $(BUILD_DIR)%.class: $(SRC_DIR)%.java
	$(JAVAC) -Xlint:deprecation -encoding utf-8 -d $(BUILD_DIR) -classpath $(LIBS):$(BUILD_DIR):$(SRC_DIR) $<

$(JAR): %.jar: $(CLASSES)
	mkdir -p $(CURRENT_DIR)/bin 2>/dev/null
	mkdir -p $(BUILD_DIR)/META-INF 2>/dev/null
	cp $(SRC_DIR)/MANIFEST.MF $(BUILD_DIR)/META-INF/
	cd $(BUILD_DIR) && zip -r $(JAR) . >/dev/null
