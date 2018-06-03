import java.util.ArrayList;

public class SuperMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Grafo SuperGrafo = new Grafo();

		//ArrayList<Nodo> tester = SuperGrafo.getMasBuscados(50, "viajes");
		ArrayList<Nodo> tester = SuperGrafo.dfs("terror");
		
		for (Nodo nodito : tester) {
			System.out.println(nodito.getValor());
		}
		
//		SuperGrafo.add("a");
//		SuperGrafo.add("b");
//		SuperGrafo.add("c");
//		SuperGrafo.add("d");
//		SuperGrafo.add("e");
//		SuperGrafo.add("f");
//		SuperGrafo.add("g");
//
//		SuperGrafo.addArista("a","b");
//		SuperGrafo.addArista("b","c");
//		SuperGrafo.addArista("c","a");

		
//		System.out.println("Hay ciclo Iterable: "+SuperGrafo.esCiclico_Iterable());//funciona bien
		
//		System.out.println("Hay ciclo Recursivo: "+SuperGrafo.esCiclico_Recursivo());//con grandes valores genera errores de tipo Stackoverflow
		
	}

}
