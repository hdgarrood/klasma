B              = $(shell pwd)
SOURCE_DIR     = src
GEN_SOURCE_DIR = build/gen_src
CLASS_DIR      = build/class
PACKAGING_DIR  = build/packaging
ANTLR_JAR_PATH = lib/antlr-4.2-complete.jar
SOURCEPATH     = "$(SOURCE_DIR):$(GEN_SOURCE_DIR)"
CLASSPATH      = $(ANTLR_JAR_PATH)

SOURCES = $(wildcard src/*.java)
CLASSES = $(patsubst %.java,%.class,$(addprefix $(CLASS_DIR)/,$(notdir $(SOURCES))))
GEN_SOURCES = $(addprefix $(GEN_SOURCE_DIR)/,TrackBaseListener.java TrackLexer.java TrackLexer.tokens TrackListener.java TrackParser.java Track.tokens)
GEN_CLASSES = $(addprefix $(CLASS_DIR)/,TrackBaseListener.class TrackLexer.class TrackListener.class TrackParser$$Block_bodyContext.class TrackParser$$Block_declContext.class TrackParser$$Block_list_lineContext.class TrackParser$$Block_nameContext.class TrackParser$$Channel_bodyContext.class TrackParser$$Channel_declContext.class TrackParser$$Channel_list_lineContext.class TrackParser$$Channel_nameContext.class TrackParser.class TrackParser$$DeclContext.class TrackParser$$EndlineContext.class TrackParser$$NoteContext.class TrackParser$$Note_list_lineContext.class TrackParser$$Track_bodyContext.class TrackParser$$TrackContext.class TrackParser$$Track_declContext.class)

GRAMMAR = src/Track.g4
MANIFEST = build/Manifest.txt
JAR_FILE = klasma.jar

ANTLR_EXTRACTED_FILE = $(PACKAGING_DIR)/org/antlr/v4/Tool.class

# Main rule: builds the JAR file
$(JAR_FILE): $(GEN_CLASSES) $(CLASSES) $(ANTLR_EXTRACTED_FILE)
	cd $(PACKAGING_DIR);					\
	cp $B/$(CLASS_DIR)/* . ;				\
	jar cmf $B/$(MANIFEST) $B/$(JAR_FILE) *

# Build rule to generate .java files (i.e. with Antlr)
$(GEN_SOURCES): $(GRAMMAR)
	cd $(SOURCE_DIR); \
		java -jar $B/$(ANTLR_JAR_PATH) \
			-o $B/$(GEN_SOURCE_DIR) \
			$(notdir $<)

# Build rule for .class files from generated .java files
$(CLASS_DIR)/%.class: $(GEN_SOURCE_DIR)/%.java
	javac -sourcepath $(SOURCEPATH) \
		-cp $(CLASSPATH) \
		-d $(CLASS_DIR) \
		-Xlint:all \
		$<

# Build rule for normal .class files
$(CLASS_DIR)/%.class: $(SOURCE_DIR)/%.java
	javac -sourcepath $(SOURCEPATH) \
		-cp $(CLASSPATH) \
		-d $(CLASS_DIR) \
		-Xlint:all \
		$<

# Extracts the contents of antlr's JAR file into the packaging directory so
# that it can be repackaged in our JAR file.
$(ANTLR_EXTRACTED_FILE):
	cd $(PACKAGING_DIR); \
		jar xf $B/$(ANTLR_JAR_PATH) org

.PHONY: clean
# Deletes all files created by the build.
# 'subst' is necessary to escape dollars from the shell. :(
clean:
	-rm -f $(CLASSES)
	-rm -f $(GEN_SOURCES)
	-rm -f $(subst $$,\$$,$(GEN_CLASSES))
	-rm -rf $(PACKAGING_DIR)/*
	-rm -f $(JAR_FILE)
