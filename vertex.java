import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/*
 * vertex stores the latitude, longitude and list of edges connected to that vertex
 */
public class vertex {
	public String name;
	float lat;
	float lon;
	List<edge> edgelist;
	public void addedge(vertex a, vertex b, int c)
	{
		edge ed=new edge();
		if(this.name.equals(a.name))
		{
			ed.point1=a;
			ed.point2=b;
			ed.weight=c;
		}
		else
		{
			ed.point1=b;
			ed.point2=a;
			ed.weight=c;
		}
		edgelist.add(ed);
	}
	public void sortedges()
	{
		for(int i=0;i<edgelist.size();i++)
		{
			Collections.sort(edgelist,edge.getCompByName());
		}
	}
	public vertex(String city, float a, float b)
	{
		name = city;
		lat=a;
		lon=b;
		edgelist=new ArrayList<edge>();
	}
	public vertex()
	{
		
	}
	public String getname()
	{
		return name;
	}
	public float getlatitude()
	{
		return lat;
	} 
	public float getlongitude()
	{
		return lon;
	}
	public edge getedgebyvertices(vertex dest)
	{
		edge e;
		for(int i=0;i<edgelist.size();i++)
		{
			if(edgelist.get(i).point2.equals(dest))
				return e=edgelist.get(i);
		}
		return null;
	}
}
