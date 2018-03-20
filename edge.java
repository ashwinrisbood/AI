import java.util.Comparator;
/*
 *  each edge stores the vertex where it originates from , vertex which it goes to and the weight of the edge 
 */
public class edge {
	vertex point1;
	vertex point2;
	int weight;
	public edge(vertex a, vertex b, int c)
	{
		if(a.name.equals(b.name))
		{
			point1=a;
			point2=b;
			weight=0;
		}
		else
		{
			point1=a;
			point2=b;
			weight=c;
		}
	}
	public edge() {
		// TODO Auto-generated constructor stub
	}
	public int getweight()
	{
		return weight;
	}
	public vertex getpoint1()
	{
		return point1;
	}
	public vertex getpoint2()
	{
		return point2;
	}
	/* comapre edge based upon destination name 
	 * used in order to sort the edge names in alphabetical order
	 */
	public static Comparator<edge> getCompByName()
	{   
	 Comparator comp = new Comparator<edge>(){
	     @Override
	     public int compare(edge s1, edge s2)
	     {
	         return s1.point2.name.compareTo(s2.point2.name);
	     }        
	 };
	 return comp;
	}  
	public boolean checkedge(edge a , edge b)
	{
		if((a.point1.name.equals(b.point1.name)) && (a.point2.name.equals(b.point2.name)))
		{
			return true;
		}
		if((a.point1.name.equals(b.point2.name)) && (a.point2.name.equals(b.point1.name)))
		{
			return true;
		}
		return false;
	}
}
