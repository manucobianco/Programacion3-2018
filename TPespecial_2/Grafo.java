import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.attribute.FileOwnerAttributeView;
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
		
		String csvFile = "dataset4.csv";
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
	
	//Obtener los N géneros más buscados luego de buscar por el género A.
	public ArrayList<Nodo> getMasBuscadosDe(int cant, String genero) {  //Devuelve los mas buscados entre los adyacentes del nodo parametro
		ArrayList<Nodo> ret = new ArrayList<Nodo>();
		Nodo n = buscaVerticeEnGrafo(genero);
		if (n != null) {
			ArrayList<Arista> aristasAux = n.getAristas();
			int i = 0;
			int size = aristasAux.size();
		  
			while (i < size && i < cant) {
				ret.add(getMayorNodo(aristasAux));
				i++;
			}
		}	  
	  return ret;
	}
	 
	private Nodo getMayorNodo(ArrayList<Arista> a) {
		Nodo n = new Nodo();
		int mayorValor = 0;
		int index = 0;
	  
		for (Arista arista : a) {
			if (arista.getPeso() > mayorValor) {
				mayorValor = arista.getPeso();
				index = a.indexOf(arista);
			}
		}
		n = a.remove(index).getDestino();  
		return n;
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
	
	private ArrayList<Arista> arista(String i){		//retorna los adyacentes de un vertice
		return buscaVerticeEnGrafo(i).getAristas();
	}
	
	//Obtener todos los géneros que fueron buscados luego de buscar por el género A.
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
 
    public ArrayList<Nodo> nodosUnidosA(String nodo){
    	if(existeNodo(nodo)){
        	Hashtable<Nodo, EstadoDeVisita> estadoNodos = new Hashtable<Nodo, EstadoDeVisita>();
           	return dfsRecu(this.buscaVerticeEnGrafo(nodo), estadoNodos);
    	}else{
    		return new ArrayList<Nodo>();
    	}
    }
	
	//Obtener el grafo únicamente con los géneros afines, es decir, que se vinculan entre sí (pasando o no por otros géneros).
    private void  buscarAfines(Nodo nodo,Hashtable<Nodo, Boolean> afines,Hashtable<Nodo, EstadoDeVisita> estadoNodos, Nodo objetivo){
    	if (nodo.tieneAdyacentes()) {
        	for (Arista arista : nodo.getAristas()) {
        		if (estadoNodos.get(arista.getDestino()) == EstadoDeVisita.NO_VISITADO) {
        			if (!arista.getDestino().equals(objetivo)) {
        				if (arista.getDestino().tieneAdyacentes()) {
        		        	estadoNodos.put(arista.getDestino(), EstadoDeVisita.VISITADO);
        					buscarAfines(arista.getDestino(),afines,estadoNodos,objetivo);
						}
					}
        			else{
    					afines.put(arista.getDestino(), true);
    				}
				}
        		if (afines.get(arista.getDestino())) {
        			afines.put(nodo, true);
				}
    		}
		}
    }
    
    public ArrayList<Nodo> generosAfines(String nodo){
    	Nodo buscar = buscaVerticeEnGrafo(nodo);
    	
    	ArrayList<Nodo> arrRet = new ArrayList<Nodo>();
    	if (buscar != null) {
    		Hashtable<Nodo, EstadoDeVisita> estadoNodos = new Hashtable<Nodo, EstadoDeVisita>();
        	Hashtable<Nodo,Boolean> afines = new Hashtable<Nodo,Boolean>();
        	
        	for (Nodo actual : vertices) {
        		afines.put(actual, false);
        		estadoNodos.put(actual, EstadoDeVisita.NO_VISITADO);
    		}	
        	
        	buscarAfines(buscar,afines,estadoNodos,buscar);
        	
        	for (Nodo clave : afines.keySet()) {
    			if (afines.get(clave)) {
    				arrRet.add(clave);
    			}
    		}
		}
    	return arrRet;
    }
}