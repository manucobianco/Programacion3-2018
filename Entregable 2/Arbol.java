public class Arbol {
	private Node raiz;
	
	private class Node {
    private	int value;
    private	Node izq;
    private	Node der;
	
		private Node(int val){
			this.value = val;
			this.izq = null;
			this.der = null;
		}
		private int getVal(){
			return  value;
		}
		private void setIzq(Node n){
			this.izq = n;
		}
		private void setDer(Node n){
			this.der = n;
		}
		private Node getIzq(){
			return this.izq;
		}
		private Node getDer(){
			return this.der;
		}
	}
	
	public Arbol(){
		this.raiz = null;
	}
	
	public void insert(int val){
		if (this.raiz == null){
			this.raiz = new Node(val);
		}else{
			insertRecu(this.raiz,val);		
		}
	}
	
	private void insertRecu(Node actual,int val){
		if(val < actual.getVal()){//si es menor q el actual va a la izq
			if(actual.getIzq()==null){
				actual.setIzq(new Node(val));	
			}else{
				insertRecu(actual.getIzq(),val);
			}
			
		}else{//no puede haber valores repetidos
			if(actual.getDer()==null){//si no va a la derecha
				actual.setDer(new Node(val));
			}else{
				insertRecu(actual.getDer(),val);
			}
		}
	}
	
	public boolean hasElem(int val){
		if (this.raiz == null){
			return  false;
		}else{
			return hasElemRecu(this.raiz,val);
		}
	}
	
	private boolean hasElemRecu(Node actual,int val){
		if(actual.getVal() == val){
			return  true;
		}else if(actual.getVal()>val){
			if(actual.getIzq()!=null){
				return hasElemRecu(actual.getIzq(),val);
			}else{
				return false;
			}
		}else{
			if(actual.getDer()!=null){
				return hasElemRecu(actual.getDer(),val);
			}else{
				return false;
			}
		}
	}
	
}