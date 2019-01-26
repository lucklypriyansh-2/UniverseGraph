# UniverseGraph
This universal graph application 


This application apply  optimised single source shortest  path algorithm in a graph which is loaded from CSV file and find the shortest path 
The graph is present in form of  CSV file which is loaded at startup into derby db  and once graph is loaded. it applies optimised version of single source shorted path in all the nodes of graph and stored it in cache .
whenever UI request source and target it brings data from cache and show results

please find link attached to see the implementaion detail
https://pdfs.semanticscholar.org/d75a/87a3232bef187e710ef59e202816187c8669.pdf



Installation steps
run : maven clean install

UI  can be seen at 

http://localhost:9090/Index.html

Select source and destination path and it will find the shortest path using above mentioned optimised algorithm

