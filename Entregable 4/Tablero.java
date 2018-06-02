
public class Tablero {
		
	int cantidadSoluciones,cantidadCuadrados,valorSolucion,maximoAsignable;
	int tablero[][];
	boolean nroUsados[];
	
	public Tablero (int cantidadCuadrados,int valorSolucion, int maximoAsignable){ // counstructor
		this.cantidadSoluciones = 0;
		this.cantidadCuadrados = cantidadCuadrados;
		this.tablero = new int[cantidadCuadrados][cantidadCuadrados]; 
		this.valorSolucion = valorSolucion;
		this.maximoAsignable = maximoAsignable;
		this.nroUsados = new boolean[maximoAsignable+1];
	}
	
	public void cargarTablero(int posActual){ // este metodo se llama recursivamente, y carga el tablero  con nro validos.
		if(posActual == cantidadCuadrados*cantidadCuadrados && chequeaTablero()){ 
			imprimeTablero();
		}else{
			if(posActual < cantidadCuadrados*cantidadCuadrados){
				for (int nro = 1; nro <= maximoAsignable; nro++) {
					if (!nroUsados[nro] && nroValido(posActual,nro)) {
						nroUsados[nro] = true;
						tablero[posActual/cantidadCuadrados][posActual%cantidadCuadrados] = nro; 
						cargarTablero(posActual+1);
						tablero[posActual/cantidadCuadrados][posActual%cantidadCuadrados] = 0; 
						nroUsados[nro] = false;					  
					}
				}
			}
		}
	}
	
	public boolean nroValido(int pos, int nro){ //valida si el nro dado no supera en ninguna linea el valorSolucion en la pos
		int solucion = nro, solucion2 = nro;
		for (int i = 0; i < cantidadCuadrados; i++) {
			solucion += tablero[pos/cantidadCuadrados][i];
			solucion2 += tablero[i][pos%cantidadCuadrados];
			// if (solucion > valorSolucion || solucion2 > valorSolucion){
			// 	return  false;
			// }
		}
		return (solucion <= valorSolucion && solucion2 <= valorSolucion);
	}
	
	public boolean chequeaTablero(){ // valida q el todas las lineas del tablero sumen el valorSolucion
		int solucion = 0,solucion2 = 0;
		for (int i = 0; i < cantidadCuadrados; i++) {
			solucion = 0;
			solucion2 = 0;
			for (int j = 0; j < cantidadCuadrados; j++) {
				solucion += tablero[j][i];
				solucion2 += tablero[i][j]; 
				if(solucion > valorSolucion || solucion2 > valorSolucion){
					return false;
				}
			}
		}
		if (solucion != valorSolucion || solucion2 != valorSolucion) {
			return false;
		} else {
			return true;
		}
	}
	
	public void imprimeTablero() { //imprime todas las soluciones encontradas 
		System.out.println("");
		System.out.println("Resultado nro: "+ ++cantidadSoluciones);
		for (int i = 0; i < cantidadCuadrados; i++) {
			for (int j = 0; j < cantidadCuadrados; j++) {
				System.out.print("["+tablero[i][j]+"]");
			}
			System.out.println("");
		}
	}
}
