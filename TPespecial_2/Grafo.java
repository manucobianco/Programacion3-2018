import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;


public class Grafo {
	
	ArrayList <Nodo> vertices;
	int cantRecorridos; 
	Hashtable<Nodo, EstadoDeVisita> tablaVisita;
	
	public Grafo(){
		vertices = new ArrayList <Nodo>();
		reader();
		cantRecorridos = 0;
		this.tablaVisita = new Hashtable<Nodo, EstadoDeVisita>();
		for (Nodo v : vertices) {
			tablaVisita.put(v, EstadoDeVisita.NO_VISITADO);
		}
	}
	private void reader(){ // privado para el informe
	
		
		String csvFile = "dataset1.csv";
		String line = "";
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){			
			br.readLine();//salta la primera linea
			
			while ((line = br.readLine()) != null) {
				String anterior = null;
				String[] generos = line.split(",");
				
				for (int i = 0; i < generos.length; i++) {
					
					if (!existeNodo(generos[i])) {
						add(generos[i]);
					}
					if (anterior != null) {
						if (existeArista(anterior, generos[i])) {
							getArista(anterior, generos[i]).aumentarArista();
						}else{
							addArista(anterior,generos[i]);	
						}
					}
					anterior = generos[i];
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
//		for (int j = 0; j < vertices.size(); j++) {                        //para saber el peso de todas las aristas
//			for (int i = 0; i < vertices.get(j).getAristas().size(); i++) {
//				System.out.println(vertices.get(j).getAristaAt(i).getPeso());
//			}
//		}
	}
	
	public boolean existeNodo(String valor){ //busca si en el grafo existe el vertice con ese valor y retorna un boolean
		for (int i=0; i<vertices.size(); i++) {
			if (valor.equals(vertices.get(i).getValor())){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Nodo> getMasBuscados(int cant, String genero){ // retorna la N cantidad de generos mas buscados luego del genero q le pasan como parametro
		ArrayList<Nodo> ret = new ArrayList<Nodo>();
		if (existeNodo(genero)) {
			ret = buscarMientrasAdyacentes(cant,buscaVerticeEnGrafo(genero));
		}else{
			System.out.println("No existe el genero.");
		}
		return ret;
	}
	
	private ArrayList<Nodo> buscarMientrasAdyacentes(int cant,Nodo n){ // buscar el adyacente mas grande, y se llama recursivamente con el nodo ese
		ArrayList<Nodo> ret = new ArrayList<Nodo>();
			
 		if (n.tieneAdyacentes()&&cantRecorridos < cant) {
 			Nodo aux2 = n.getNodoAdyacenteMayor();
 			if (tablaVisita.get(aux2) == EstadoDeVisita.NO_VISITADO) {
 				cantRecorridos++;
 				tablaVisita.put(aux2, EstadoDeVisita.VISITADO);
 				ret = buscarMientrasAdyacentes(cant,aux2);
			}
		}
 		ret.add(n);
 		
 		return ret; 
	}
	
	public void add(String valor){//agrega un vertice al grafo
		vertices.add(new Nodo(valor));

	}
	
	public int cantidadAristas(){//retorna la cantidad de aristas que tiene un nodo
		int aristas = 0;
		for (int i=0; i<vertices.size(); i++) {
			aristas += vertices.get(i).getAristas().size();
		}
		return aristas;
	}
	
	public Nodo buscaVerticeEnGrafo(String valor){ //busca si en el grafo existe el vertice con ese valor
		for (int i=0; i<vertices.size(); i++) {
			if (valor.equals(vertices.get(i).getValor())){
				return vertices.get(i);
			}
		}
		return null;
	}
	
	public boolean existeArista(String i,String j){// devuelve si existe la arista entre los 2 valores dados
		try {									 		 //puede devolver null en caso de que no exista, por eso tiene que estar en try
			for (int j2 = 0; j2 < buscaVerticeEnGrafo(i).getAristas().size(); j2++) {
				if( buscaVerticeEnGrafo(i).getAristaAt(j2).getDestino().getValor().equals(j)){
					return true;
				}
			}
			return false;
		} catch (Exception exe) {					// en caso de que no encuentre la exepcion se encarga de retornar false.
			return false;
		}
	}

	public Arista getArista(String i,String j){ //retorna la arista entre 2 strings dados
		try {									 		 
			return (buscaVerticeEnGrafo(i).getArista(j));
		} catch (Exception exe) {					
			return null;
		}
	}	
	
	public void addArista(String i,String j){ 	//relaciona los 2 vercices dados 
		
		Arista aux = new Arista(buscaVerticeEnGrafo(j));
		buscaVerticeEnGrafo(i).addArista(aux);
	}
	
	public ArrayList<Arista> arista(String i){		//retorna los adyacentes de un vertice
		return buscaVerticeEnGrafo(i).getAristas();
	}
	
	
	private ArrayList<Nodo> dfsRecu(Nodo nodoActual,Hashtable<Nodo, EstadoDeVisita> estadoNodos){
		ArrayList<Nodo> nodosRet = new ArrayList<Nodo>();
		
		if (estadoNodos.get(nodoActual) == null) {
			nodosRet.add(nodoActual);
			estadoNodos.put(nodoActual,EstadoDeVisita.VISITANDO);
		}
		
		if (nodoActual.tieneAdyacentes()) {
			for (Arista arista : nodoActual.getAristas()) {
				if (estadoNodos.get(arista.getDestino()) == null) { // si no tiene estado 
					nodosRet.addAll(dfsRecu(arista.getDestino(),estadoNodos));
				}
			}	
		}

		return nodosRet;
    }
 
    public ArrayList<Nodo> dfs(String nodo){
    	Hashtable<Nodo, EstadoDeVisita> estadoNodos = new Hashtable<Nodo, EstadoDeVisita>();
    	return dfsRecu(this.buscaVerticeEnGrafo(nodo), estadoNodos);
    }
	
	////*****************////
	

	
	public boolean esCiclico_Recursivo(){	//busca y devuelve si el grafo es ciclico o no, trabaja de manera recursiva
		Hashtable<String, EstadoDeVisita> nodos = new Hashtable<String, EstadoDeVisita>();
			for (int i=0; i<vertices.size(); i++){
				nodos.put(vertices.get(i).getValor(), EstadoDeVisita.NO_VISITADO);//crea la tabla con los valores por defecto en NO_VISITADO
			}
			
			for (int i=0; i<vertices.size(); i++){	//recorre los valores, si el vertice en el que estoy parado no esta visitado, lo visita con el dfs_visit, y retorna true, significa que hay ciclo ya que vuele a un nodo que esta visitando                         
				if ((nodos.get(vertices.get(i).getValor()) == EstadoDeVisita.NO_VISITADO)){
					if (dfs_visit(vertices.get(i), nodos)){  
						return true;
					}
				}
			}
		return false;
	}
	
	public boolean dfs_visit(Nodo nodo, Hashtable<String, EstadoDeVisita> nodos){ 		//visita los nodos
		
		if (nodos.get(nodo.getValor()) == EstadoDeVisita.VISITANDO){
			return true;
		}
			
		nodos.put(nodo.getValor(), EstadoDeVisita.VISITANDO);
		
		for (Arista aristas : nodo.getAristas()){
			if( dfs_visit(aristas.getDestino(), nodos)){
				return true;
			}
		}
		
		nodos.put(nodo.getValor(), EstadoDeVisita.VISITADO);
		return false;
	}
	
	public boolean esCiclico_Iterable(){	//recorre el grafo de forma iterable. Usa una pila resolver 
		Hashtable<String, EstadoDeVisita> nodosVisita = new Hashtable<String, EstadoDeVisita>();
		
		Stack<Nodo> pila = new Stack<Nodo>();
		
		for (int i=0; i<vertices.size(); i++){
			nodosVisita.put(vertices.get(i).getValor(), EstadoDeVisita.NO_VISITADO);
		}
		
		pila.add(vertices.get(0));
		
		while (!pila.isEmpty()){//mientras la pila tenga vertices sigue funcionando
			Nodo aux = pila.peek();
			
			if(nodosVisita.get(aux.getValor()) == EstadoDeVisita.NO_VISITADO){
				nodosVisita.put(aux.getValor(), EstadoDeVisita.VISITANDO);
				if (vertices.size() > 0){
					for (int i = 0; i < aux.getAristas().size(); i++) {
						if( (nodosVisita.get(aux.getAristaAt(i).getDestino().getValor()) == (EstadoDeVisita.NO_VISITADO))){
							pila.add(aux.getAristaAt(i).getDestino());
						}else if ((nodosVisita.get(aux.getAristaAt(i).getDestino().getValor()) == (EstadoDeVisita.VISITANDO))){
							return true;
						}
					}
				}else{
					nodosVisita.put(aux.getValor(), EstadoDeVisita.VISITADO);
					pila.pop();
				}
				
			}else if(nodosVisita.get(aux.getValor()) == EstadoDeVisita.VISITADO){
				pila.pop();
			}else{
				nodosVisita.put(aux.getValor(), EstadoDeVisita.VISITADO);
				pila.pop();
			}
		}
		return false;
	}
	
}