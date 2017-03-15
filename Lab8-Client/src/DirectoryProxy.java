public class DirectoryProxy {
	private MainDirectory dir; 
	
	public DirectoryProxy(){
		dir = new MainDirectory();
	}
	
	public void add(String json){
		dir.add(json);
	}
	
	public void clear(){
		dir.clear();
	}
	
	public void print(){
		dir.print();
	}
}
