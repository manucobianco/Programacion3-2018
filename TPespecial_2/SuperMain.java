import java.util.ArrayList;

public class SuperMain {

	public static void main(String[] args) {

		Timer reloj = new Timer();
		
//		reloj.start();
		Grafo SuperGrafo = new Grafo();
//		System.out.println("El tiempo creacion del grafo fue de: " + reloj.stop());


					//-----Punto 1-----
	/*	reloj.start();
		ArrayList<Nodo> tester = SuperGrafo.getMasBuscadosDe(100, "terror");
		
		for (Nodo nodito : tester) {
			System.out.println(nodito.getValor());
		}
		System.out.println("El tiempo de la consigna 1 fue de: " + reloj.stop());
	*/	
					//-----Punto 2-----
	/*	reloj.start();
		ArrayList<Nodo> tester = SuperGrafo.nodosUnidosA("terror");
		for (Nodo nodito : tester) {
			System.out.println(nodito.getValor());
		}
		System.out.println("El tiempo de la consigna 2 fue de: " + reloj.stop());
	*/	
					//-----Punto 3-----
	/*	reloj.start();
		ArrayList<Nodo> tester = SuperGrafo.generosAfines("terror");
		for (Nodo nodito : tester) {
			System.out.println(nodito.getValor());
		}
		System.out.println("El tiempo de la consigna 3 fue de: " + reloj.stop());
	*/
	}
}