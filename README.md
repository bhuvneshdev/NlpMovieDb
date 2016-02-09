# NlpMovieDb

## Synopsis
The project is semantic search engine on movie database using Natural Language Processing and Information retrieval techniques
The user provides a search query to the search engine and it returns a movie list with scores.
Here is how it is different from conventional search engines.  
1. It is based on semantic search and not keywords based approach which is how popular search engines implementation in IMDB, rotten tomatoes works.  
2. Information of the complete plot is parsed to form a graph model, representing the series of events, characters and their properties.


## Existing Problem & Motivation
Based on web mining and analysis from the websites like Movies.stackexchange, IMDB, Quora and others, we observed that more
than 90% of the questions posted online go unanswered. Clearly the magnitude of this problem is big, which got us motivated
towards this project and to successfully answer these questions. 
[Web mined Data](https://drive.google.com/file/d/0B1Lohn8B06JHUXhYT1h2MzhlRXJkR0t5UUdCcnpqMzF3WEMw/view?usp=sharing)
Following are the statistics:
![alt text] (https://calitripmagblog.files.wordpress.com/2016/02/picture1.png?w=320&h=280&crop=1)
![alt text](https://calitripmagblog.files.wordpress.com/2016/02/picture2.png?w=332&h=280&crop=1)

## Tools and Technologies used
1. Java: Java is a general-purpose object oriented programming language.
2. Python: Python is a widely used general-purpose, high-level programming language.
3. Knowledge Parser: K-Parser is a semantic parser that translates any English sentence into a directed acyclic semantic graph.
Used for: Event extraction, Event-Event relation extraction. [K-Parser](http://bioai8core.fulton.asu.edu/kparser/about.jsp)
4. Stanford Core NLP : Stanford CoreNLP provides a set of natural language analysis tools. [Stanford CoreNLp](http://stanfordnlp.github.io/CoreNLP/) 
Used for: Named entity recognition, Co-reference resolution, Parts of speech, Extraction object dependency for semantic representation. 
5. NLTK : Leading platform for building Python programs to work with NLP. [NLTK](http://www.nltk.org/) 
Used for: NER detection and name and coreference unification for text.
6. WS4J (WordNet Similarity for Java): Provides a pure Java API for semantic relatedness.[WS4J](https://code.google.com/archive/p/ws4j/) 
Used for: Wordnet based word-word similarity algorithms PATH, LIN, LESK.
7. Tf-IDF: Term frequency–inverse document frequency, is a numerical statistic that is intended to reflect how importance a word is to a document in a collection or corpus.[Tf-IDF](https://en.wikipedia.org/wiki/Tf%E2%80%93idf)
Used for: Creating event importance vector for each plot to give weights to the event similarity






