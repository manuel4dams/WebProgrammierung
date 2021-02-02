package model;

public class Player {
	
	int Id;
	String name;
	int points;
	State status;
	
	public static enum State { WAITING, ACTIV, GAMEOVER, UNKNOWN };
	
	public Player(String name)
	{
		this.name=name;
	}

	public void setId(int id) {this.Id=id;}
	public int getId(){ return Id; }
	
	public void setName(String name) {this.name=name;}
	public String getName(){ return name; }
	
	public void setPoints(int p) {this.points=p;}
	public int getPoints(){ return points; }
	
	public void setStatus(State s) {this.status=s;}
	public State getStatus(){ return status; }
	
	
}
