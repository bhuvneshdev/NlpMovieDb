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
towards this project and to successfully answer these questions. Following are the statistics:

![alt text] (https://calitripmagblog.files.wordpress.com/2016/02/picture1.png?w=320&h=280&crop=1)
![alt text](https://calitripmagblog.files.wordpress.com/2016/02/picture2.png?w=332&h=280&crop=1)

## Tools and Technologies used
1. Java: Java is a general-purpose object oriented programming language.
2. Python: Python is a widely used general-purpose, high-level programming language.
3. Knowledge Parser: K-Parser is a semantic parser that translates any English sentence into a directed acyclic semantic graph. It is used in our project for event extraction, event-event relation extraction. [K-Parser,   Link](http://bioai8core.fulton.asu.edu/kparser/about.jsp)
4. Stanford Core NLP : Stanford CoreNLP provides a set of natural language analysis tools. It is used in our project for named entity recognition, coreference resolution, parts of speech and extraction object dependency for semantic representation. [Stanford CoreNLp, Link: ](http://stanfordnlp.github.io/CoreNLP/) 


