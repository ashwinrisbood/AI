import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SearchUSA {
	
	public static class Graph
	{
	    List<vertex> v_list=new ArrayList<vertex>();  // List of vertices (look in vertex.java for more info on a vertex)  
	    /* 
	     * dfs implimented through a stack of vertices
	     */
		public int dfs(String start, String end)
		{
			if(start.equals(end))
			{
				System.out.println("same source and destination!!enter something different");
				return 0;
			}
			vertex v_start=new vertex();
			vertex v_end=new vertex();
			boolean flag=false;
			int path_length=0;
			Stack<vertex> s= new Stack<vertex>();
			/* Hash map to record visited nodes in order to avoid cyclic exploration of nodes */
			HashMap<vertex,Boolean> visited=new HashMap<vertex,Boolean>(); 
			/* HashMap to trace back the path of traversal from source to destination */
			HashMap<String, String> pathmap = new HashMap<String,String>();
			
			for(int i=0;i<v_list.size();i++)
			{
				visited.put(v_list.get(i), false);
				if(v_list.get(i).name.equals(start))
				{
					v_start=v_list.get(i);
				}
				if(v_list.get(i).name.equals(end))
				{
					v_end =v_list.get(i);
				}
			}
			s.push(v_start);
			while(!s.isEmpty())	// pop elements from the stack do goal test, if goal test is negetive push the children of the popped nodes to the stack
			{
				vertex temp=s.pop();
				if(temp.name.equals(end))
				{
					System.out.println(temp.name);
					System.out.println("found");
					flag=true;
					break;
				}
				if(!visited.get(temp))
				{
					path_length++;
					System.out.println(temp.name);
					visited.replace(temp, true);
				}
				List<edge> connections = temp.edgelist;
				connections=reverse(connections);
				for(int i=0;i<connections.size();i++)
				{	
					if(!(temp.name.equals(connections.get(i).point2.name)) && !(visited.get(connections.get(i).point2)) )
					{	
						s.push(connections.get(i).point2);
						pathmap.put(connections.get(i).point2.name,temp.name);
					}
				}	
			}
			if(flag==false)
			{
				System.out.println("not found!!");
				return -1;
			}
			path_length=path_length+1;     // adding source to path length
			Stack<String> display=new Stack<String>();// Displaying the solution path by retracing the parent and child vertices from destination to source
			System.out.println("displaying path -------------------->");
			display.push(end);
			String temp=pathmap.get(end);
			while(!temp.equals(start))
			{
				display.push(temp);
				temp=pathmap.get(temp);
			}
			display.push(start);
			int solution_length=display.size();
			while(!display.isEmpty())
			{
				System.out.println(display.pop());
			}
			System.out.println("Number of expanded nodes = "+path_length);
			System.out.println("Solution length is = "+solution_length);
			return 1;
					
		}
		/* 
		 * bfs implimented by using a queue of vertices. 
		 */
		public int bfs(String start, String end)
		{
			if(start.equals(end))
			{
				System.out.println("same source and destination!!enter something different");
				return 0;
			}
			vertex v_start=new vertex();
			vertex v_end=new vertex();
			boolean flag=false;
			int path_length=0;
			Queue<vertex> q= new LinkedList<vertex>();
			/* Hash map to record visited nodes in order to avoid cyclic exploration of nodes */
			HashMap<vertex,Boolean> visited=new HashMap<vertex,Boolean>(); 
			/* HashMap to trace back the path of traversal from source to destination */
			HashMap<String, String> pathmap = new HashMap<String,String>();
			for(int i=0;i<v_list.size();i++)
			{
				visited.put(v_list.get(i), false);
				if(v_list.get(i).name.equals(start))
				{
					v_start=v_list.get(i);
				}
				if(v_list.get(i).name.equals(end))
				{
					v_end =v_list.get(i);
				}
			}
			q.add(v_start);
			visited.replace(v_start, true);
			System.out.println(v_start.name);
			while(!q.isEmpty())					// pop the queue do goal test and if the node is not a goal state push its childeren in the queue
			{
				vertex vert=q.remove();
				String vertex_name=vert.name;
				List<edge> connections=vert.edgelist;
				if(vertex_name.equals(end))
				{
					path_length++;
					System.out.println(end);
					System.out.println("found!!");
					flag=true;
					break;
				}
				if(!visited.get(vert))
				{
					System.out.println(vertex_name);
					visited.replace(vert, true);
					path_length++;
				}
				for(int i=0;i<connections.size();i++)
				{
					if(!(vertex_name.equals(connections.get(i).point2.name)) && !(visited.get(connections.get(i).point2)) )
					{
						q.add(connections.get(i).point2);
						pathmap.put(connections.get(i).point2.name,vertex_name);
					}
				}
			}
			path_length = path_length+1 ;  // adding the start vertices to path_length
			if(flag==false)
			{
				System.out.println("not found!!");
				return -1;
			}
			Stack<String> display=new Stack<String>();// Displaying the solution path by retracing the parent and child vertices from destination to source 
			System.out.println("displaying path -------------------->");
			display.push(end);
			String temp=pathmap.get(end);
			while(!temp.equals(start))
			{
				display.push(temp);
				temp=pathmap.get(temp);
			}
			display.push(start);
			int solution_length=display.size();
			while(!display.isEmpty())
			{
				System.out.println(display.pop());
			}
			System.out.println("Number of expanded node is " + path_length);
			System.out.println("Solution length is " + solution_length);
			return 1;
		}
		public List<edge> reverse(List<edge> connections) {
		    for(int i = 0, j = connections.size() - 1; i < j; i++) {
		        connections.add(i, connections.remove(j));
		    }
		    return connections;
		}
		//Uses priorityqueue to pick the next vertex with minimum heuristic + distance value.
		public int astar(String start, String end)
		{
			if(start.equals(end))
			{
				System.out.println("same source and destination!!enter something different");
				return 0;
			}
			vertex v_start=new vertex();
			vertex v_end=new vertex();
			boolean flag=false;
			int cost=0,path_length=0,solution_length=0;
			
			HashMap<vertex,Boolean> visited=new HashMap<vertex,Boolean>(); 
			HashMap<String, String> pathmap = new HashMap<String,String>();  // to find the solution path . Map to retrace the vertices.
			HashMap<String, Double> running_weight =new HashMap<String, Double>();
			for(int i=0;i<v_list.size();i++)
			{
				visited.put(v_list.get(i), false);
				if(v_list.get(i).name.equals(start))
				{
					v_start=v_list.get(i);
				}
				if(v_list.get(i).name.equals(end))
				{
					v_end =v_list.get(i);
				}
			}
			HashMap<vertex,Double> h_func = init_heuristic(v_end);	
			PriorityQueue<edge> pq =new PriorityQueue<edge>(100,  new Comparator<edge>(){
               // override compare method
					public int compare(edge i, edge j){
						if((running_weight.get(i.point2.name) + h_func.get(i.point2)) > ((running_weight.get(j.point2.name) + h_func.get(j.point2)))){
							return 1;
						}
						else if ((running_weight.get(i.point2.name) + h_func.get(i.point2)) < ((running_weight.get(j.point2.name) + h_func.get(j.point2)))){	
							return -1;
						}

						else	{
							return 0;
						}
					}
				}
			);	
			edge startedge=new edge(v_start,v_start,0);
			pq.add(startedge);
			running_weight.put(start,  0.0);
			pathmap.put(start,start);
			while(!pq.isEmpty())
			{
				/*if(flag)
				{
					break;
				}*/
				
				edge temp= pq.poll();
				vertex current=temp.point2;
				if(current.name.equals(end))
				{
					System.out.println(current.name);
					path_length++;
					flag=true;
					break;
				}
				if(!visited.get(current))
				{
					System.out.println(current.name);
					path_length++;
				}
				visited.replace(current,true);	
				for(int i=0;i<current.edgelist.size();i++)
					{		
						if(!visited.get(current.edgelist.get(i).point2))
						{
							pathmap.put(current.edgelist.get(i).point2.name,current.name);		
							running_weight.put(current.edgelist.get(i).point2.name, current.edgelist.get(i).weight+running_weight.get(pathmap.get(current.edgelist.get(i).point2.name)));
							pq.add(current.edgelist.get(i));
						}
					}
				}
			if(flag==false)
			{
				System.out.println("not found!!");
				return -1;
			}
			Stack<String> display=new Stack<String>();
			System.out.println("displaying path -------------------->");
			display.push(end);
			String temp=pathmap.get(end);
			while(!temp.equals(start))
			{
				display.push(temp);
				temp=pathmap.get(temp);
			}
			display.push(start);
			solution_length = display.size();
			String costvertex1="";
			String costvertex2="";
			costvertex1=display.pop();
			while(!display.isEmpty())
			{
				costvertex2 = display.pop();
				cost+=astarcost(costvertex1,costvertex2);
				System.out.println(costvertex1);
				costvertex1=costvertex2;
			}
			if(costvertex2.equals(costvertex1))
			{
				System.out.println(costvertex1);
			}
			System.out.println("Number of nodes expanded ="  +path_length);
			System.out.println("Solution length = " +solution_length);
			System.out.println("cost of solution is= " + cost);
			return 1;
			
		}
		public int astarcost(String a, String b)
		{
			vertex vcost1=getvertexbyname(a);
			vertex vcost2=getvertexbyname(b);
			return vcost1.getedgebyvertices(vcost2).weight;
		}
		// Makes a map heuristic values of a vertex with respect to the end vertex(destination) 
		public HashMap<vertex, Double> init_heuristic(vertex end)
		{
			HashMap<vertex, Double> heuristic_map=new HashMap<vertex, Double>();
			for(int i=0;i<v_list.size();i++)
			{
				vertex  v =v_list.get(i);
				//sqrt((69.5 * (Lat1 - Lat2)) ^ 2 + (69.5 * cos((Lat1 + Lat2)/360 * pi) * (Long1 - Long2)) ^ 2)
				double h_value=Math.sqrt( (Math.pow((69.5*(v.lat-end.lat)), 2) ) +   Math.pow((69.5* Math.cos ( (v.lat+end.lat)/360 * Math.PI)  * (v.lon -end.lon) ),2));
				heuristic_map.put(v, h_value);
			}
			return heuristic_map;
		}
		// returns the vertex object corresponding to a city name 
		public vertex getvertexbyname(String a)
		{
			vertex v=new vertex();
			for(int i=0;i<v_list.size();i++)
			{
				if(v_list.get(i).name.equals(a))
				{
					v=v_list.get(i);
				}
			}
			return v;
		}
	}	
	
	public static void main(String args[])
	{
		Graph g=setvertices();
		sortlist(g);
		String functioncall= args[0];
		String src_city=args[1];
		String des_city=args[2];
			if(functioncall.equals("bfs"))
			{
				g.bfs(src_city,des_city);
			}
		else
			if(functioncall.equals("dfs"))
			{
				g.dfs(src_city,des_city);
			}
		else
			if(functioncall.equals("astar"))
			{
				g.astar(src_city,des_city);
			}
			else
			{
				System.out.println("wrong option!!");
			}
	}
	public static void sortlist(Graph g)			// sorting the edge list so that the destinations are arranged in alphabetical order.
	{
		for(int i=0;i<g.v_list.size();i++)
		{
			g.v_list.get(i).sortedges();
		}
	}
	public static Graph setvertices()				// parses the roads.txt file making vertices and edges to complete a graph 
	{
		Graph g=new Graph();
		File f_road=new File("roads.txt");
		 try {
			 FileReader fr=new FileReader(f_road);
			 BufferedReader br = new BufferedReader(fr);
			 String line;
			 while ((line = br.readLine()) != null) {
			  if (line.contains("Cities (name, latitude, longitude)")){
			    break; 
			  }
			 }
			 line=br.readLine();
			 while ((line = br.readLine()) != null && !line.contains("= Roads =")) {
				if(!line.equals(""))
				{
					 String[] place = line.split("\\s*,\\s*");
						 String name=place[0];
						 float latitude=Float.parseFloat(place[1]);
						 float longitude=Float.parseFloat(place[2]);
						 g.v_list.add(new vertex(name,latitude,longitude));
				}
		 	  }
			 line=br.readLine();
			 while ((line = br.readLine()) != null) {
				 	vertex verta=new vertex();
				 	vertex vertb=new vertex();
				  	String[] place = line.split("\\s*,\\s*");
				  	int w=Integer.parseInt(place[2]);
				  	for(int i=0;i<g.v_list.size();i++)
				  	{
				  		if(g.v_list.get(i).name.equals(place[0]))
				  		{
				  			verta=g.v_list.get(i);
				  			
				  		}
				  		if(g.v_list.get(i).name.equals(place[1]))
				  		{
				  			vertb=g.v_list.get(i);	
				  		}
				  		
				  	}
				  	verta.addedge(verta, vertb, w);
				  	vertb.addedge(vertb, verta, w);
			 	}
			 }	 
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return g;
	}
}
