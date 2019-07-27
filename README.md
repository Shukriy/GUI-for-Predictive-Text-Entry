# GUI-for-Predictive-Text-Entry
GUI for Predictive Text Entry


Model-View separation
    The interface between the GUI and the dictionary uses the Observer pattern to achieve a
    model-view separation. (This is also called the "Model View Controller" architecture, even
    though we don't necessarily use a separate "controller".) The dictionary, the text message
    etc. is to be encapsulated in a Model class. The GUI with the user interface components is
    built in a separate View class. The View class is an Observer (implements Observer) and
    the Model class is an Observable (extends Observable).
    
The Model
    The Model class should be called DictionaryModel. It should implement the interface
      DictionaryModelInterface. It has two constructors:
      public DictionaryModel(String dictionaryFile) throws java.io.IOException
      public DictionaryModel() throws java.io.IOException
      
   The first constructor takes the path name of a dictionary file and initialises the internal data
    structures. The second constructor uses a default dictionary file of your choice.
    
    The DictionaryModel will be given the keys typed on the keypad via the addCharacter
    method. It should use the dictionary to predict the possible words (or prexes of words)
    the user is trying to enter, and store them internally. The nextMatch method allows cycling
    through the matches to select the next matching word (or prefix) among the possible matches.
    In addition, the entire message being composed is also stored internally. The acceptWord
    method allows the current matched word to be accepted and added to the composed message.

The View
    A class called View is provided for you in the starter pack. It creates multiple copies of the
    GUI shown at the beginning of this handout, and links all of them to the same instance
    of the DictionaryModel that you build. Interacting with any one of them updates all the
    copies of the GUI in a consistent manner.
    
  
    
    
