package FuncionamientoDeLaAplicacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Kruskal {
	public static <T> List<Arista<T>> ejecutar(Grafo<T> grafo) {
		int tamanio = grafo.tamanio();
		List<Arista<T>> ET = new ArrayList<>();
		int i = 0;
		
        // 1. Obtener todos los vértices y asignarles un índice
        List<T> vertices = new ArrayList<>(grafo.vertices());
        Map<T, Integer> indice = new HashMap<>();

        for (int k = 0; k < vertices.size(); k++) {
            indice.put(vertices.get(k), k);
        }

        int n = vertices.size();

        // 2. Obtener todas las aristas únicas
        List<Arista<T>> aristas = obtenerAristasUnicas(grafo);

        // 3. Ordenar las aristas por peso
        Collections.sort(aristas);

        // 4. Crear tu UnionFind
        UnionFind uf = new UnionFind(n);

        List<Arista<T>> mst = new ArrayList<>();

        int posicion = 0;

        while (i <= tamanio - 1 && posicion < aristas.size()) {
        	Arista<T> e = aristas.get(posicion);
        	posicion++;
        	
            int u = indice.get(e.obtenerOrigen());
            int v = indice.get(e.obtenerDestino());

            // Si no están en el mismo conjunto → no forma ciclo
            if (!uf.find(u, v)) {
                uf.union(u, v);

                ET.add(e);
                i = i + 1;
            }
        }

        return ET;
    }

    /**
     * Obtiene las aristas únicas (evita duplicados en grafos no dirigidos)
     */
    private static <T> List<Arista<T>> obtenerAristasUnicas(Grafo<T> grafo) {
        List<Arista<T>> lista = new ArrayList<>();
        Set<String> vistas = new HashSet<>();

        for (T vertice : grafo.vertices()) {
            for (Arista<T> a : grafo.obtenerAdyacentes(vertice)) {

                String clave = a. obtenerOrigen().toString().compareTo(a. obtenerDestino().toString()) < 0
                        ? a. obtenerOrigen() + "|" + a. obtenerDestino()
                        : a. obtenerDestino() + "|" + a. obtenerOrigen();

                if (!vistas.contains(clave)) {
                    vistas.add(clave);
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    /**
     * Calcula el peso total del MST
     */
    public static <T> double pesoTotal(Grafo<T> grafo) {
        double total = 0;
        for (Arista<T> a : ejecutar(grafo)) {
            total += a. obtenerPeso();
        }
        return total;
    }
}
