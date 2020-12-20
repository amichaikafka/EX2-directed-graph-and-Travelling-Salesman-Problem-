
# part one
# Directed weighted graph
* The goal of this program is to implement the data structure directed and weighted graph.
## The main operations that the program supports:
* create a grpah (you can add vertexes and edges)
* Isconected: check if the graph is connected.
* shortestPathDist: returns the length shortest path between source vertex to destination vertex consider the weight of each edge.
* shortestPath: returns the shortest path between source vertex to destination vertex consider the weight of each edge.
* copy: Compute a deep copy of a weighted graph.
* save: save your graph in json format.
* load: load a graph from a json format. 
## Classes 
### DWGraph_DS
This class implements directed_weighted_graph interface represents a directional weighted graph.
can support a large number of nodes (over 100,000).
The implementation using a hashmap.
### WGraph_Algo
 This class implements the dw_graph_algorithms interface including:
 0. clone(); (copy)
 1. init(graph);
 2. isConnected(); // strongly (all ordered pais connected) using BFS algorithm
 3. double shortestPathDist(int src, int dest);using Dijkstra algorithm;
 4. List<node_data> shortestPath(int src, int dest);using Dijkstra algorithm;
 5. Save(file); // JSON file
 6. Load(file); // JSON file
### NodeData
This class implements node_data interface represents the set of operations applicable on a
node (vertex) in a (directional) weighted graph.
### edgedata
 This class implements edge_data interface the set of operations applicable on a
 directional edge(src,dest) in a (directional) weighted graph.
### Geo
This class implements geo_location interface represents a geo location <x,y,z>, aka Point3D

## Interfaces
### directed_weighted_grap
### dw_graph_algorithms
### node_data
### edge_data
### geo_location
  
 ## Description of the main classes 
 ### DWGraph_DS
 * __getNode(int key)__\
 returns the node_data by the node_id
 * __getEdge(int src, int dest)__\
 returns the data of the edge (src,dest), null if none.
 * __addNode(node_data n)__\
 adds a new node to the graph with the given node_data.
 * __connect(int src, int dest, double w)__\
 Connects an edge with weight w between node src to node dest.
 * __getV()__\
 This method returns a pointer  for the collection representing all the nodes in the graph.
 * __getE(int node_id)__\
 This method returns a pointer (shallow copy) for the collection representing all the edges getting out of
 the given node (all the edges starting (source) at the given node).
 * __removeNode(int key)__\
 Deletes the node (with the given ID) from the graph -and removes all edges which starts or ends at this node.
 * __removeEdge(int src, int dest)__\
 Deletes the edge from the graph.
 * __nodeSize()__\
 Returns the number of vertices (nodes) in the graph.
 * __edgeSize()__\
 Returns the number of edges.
 * __getMC()__\
 Returns the Mode Count - for testing changes in the graph.
 
 ### WGraph_Algo
 * __init(directed_weighted_graph g)__\
 Init the graph on which this set of algorithms operates on.
 * __getGraph()__\
 Return the underlying graph of which this class works.
 * __copy()__\
 Compute a deep copy of this graph.
 * __isConnected()__\
 return true if and only if (iff) there is a valid path from EVREY node to each
 other node.  directed graph. using BFS algorithm.
 * __shortestPathDist(int src, int dest)__\
 returns the length of the shortest path between src to dest using Dijkstra algorithm
 * __shortestPath(int src, int dest)__\
 returns the the shortest path between src to dest using Dijkstra algorithm- as an ordered List of nodes.
 * __save(String file)__\
 Saves this weighted (directed) graph to the given file name - in JSON format.
 * __load(String file)__\
 This method load a graph to this graph algorithm from a JSON file.


# Part two
# Catch the pokemon
The goal of this part of the second part of the program is to illustrate the algorithems that implemented in the first class via a game, the game is very simpele 
there is a graph on the graph there are pokemons which you have  to catch. How do you do that? you have the control on the agents using the algorithems from the first part you need to give the best way for each agent to catch them all ;)
# classes
* __Ex2__\
This class represents the  "Client-Game" main class
which uses the "server for moving the "Agents".
* __Arena__\
This class represents a multi Agents Arena which move on a graph - grabs Pokemons 
* __CL_Agent__\
This class represents an agent
* __CL_Pokemon__\
This class represents a pokemon
* __MyWindow__\
This class is the frame of the  game (GUI)
* __MyPanel__\
This class is the panel of the frame in the game(GUI)
* __myOpen__\
This class if the frame of the opening of the game(GUI)
* __openPanel__\
This class is the panel of the frame in the start of the game(GUI)
* __myEnd__\
This class is the frame of the end of the game(GUI)
* __endgame__\
This class is the panel of the frame in the end of the game (GUI)
* __SimplePlayer__\
The goal of this class is to play the background music of the game.

## Interfaces
* __game_service__



