SOURCE_DIR	   = src
GEN_SOURCE_DIR = gen_src
CLASS_DIR      = bin
GEN_CLASS_DIR  = bin/gen
PACKAGING_DIR  = packaging
ANTLR_JAR_PATH = lib/antlr-4.2-complete.jar
SOURCEPATH     = "$(SOURCE_DIR):$(GEN_SOURCE_DIR)"
CLASSPATH      = $(ANTLR_JAR_PATH)

SOURCES = $(wildcard src/*.java)

CLASSES = $(patsubst %.java,%.class,$(addprefix $(CLASS_DIR)/,$(notdir $(SOURCES))))

GEN_SOURCES = $(addprefix $(GEN_SOURCE_DIR)/,TrackBaseListener.java TrackLexer.java TrackLexer.tokens TrackListener.java TrackParser.java Track.tokens)

GEN_CLASSES = $(addprefix $(GEN_CLASS_DIR)/,TrackBaseListener.class TrackLexer.class TrackListener.class TrackParser$$Block_bodyContext.class TrackParser$$Block_declContext.class TrackParser$$Block_list_lineContext.class TrackParser$$Block_nameContext.class TrackParser$$Channel_bodyContext.class TrackParser$$Channel_declContext.class TrackParser$$Channel_list_lineContext.class TrackParser$$Channel_nameContext.class TrackParser.class TrackParser$$DeclContext.class TrackParser$$EndlineContext.class TrackParser$$NoteContext.class TrackParser$$Note_list_lineContext.class TrackParser$$Track_bodyContext.class TrackParser$$TrackContext.class TrackParser$$Track_declContext.class)

GRAMMAR = src/Track.g4
MANIFEST = Manifest.txt
JAR_FILE = inf1op.jar

.PHONY: extract_antlr clean

# Main rule: builds the JAR file
$(JAR_FILE): $(GEN_CLASSES) $(CLASSES) extract_antlr
	cd $(PACKAGING_DIR);					\
	cp ../$(CLASS_DIR)/* . ;				\
	cp ../$(GEN_CLASS_DIR)/* . ;			\
	jar cmf ../$(MANIFEST) ../$(JAR_FILE) *

# Build rule to generate .java files (i.e. with Antlr)
$(GEN_SOURCES): $(GRAMMAR)
	cd $(SOURCE_DIR); \
		java -jar ../$(ANTLR_JAR_PATH) \
			-o ../$(GEN_SOURCE_DIR) \
			$(notdir $<)

# Build rule for .class files from generated .java files
$(GEN_CLASS_DIR)/%.class: $(GEN_SOURCE_DIR)/%.java
	javac -sourcepath $(SOURCEPATH) \
		-cp $(CLASSPATH) \
		-d $(GEN_CLASS_DIR) \
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
extract_antlr: $(ANTLR_JAR_PATH)
	cd $(PACKAGING_DIR); \
		jar xf ../$(ANTLR_JAR_PATH)

clean:
	-rm $(CLASSES)
	-rm $(GEN_SOURCES)
	# escape dollars in filenames
	-rm $(subst $$,\$$,$(GEN_CLASSES))
	-rm -r $(PACKAGING_DIR)/*
	-rm $(JAR_FILE)
