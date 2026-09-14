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

        // 1. Obtener todos los vértices y asignarles un índice
        List<T> vertices = new ArrayList<>(grafo.vertices());
        Map<T, Integer> indice = new HashMap<>();

        for (int i = 0; i < vertices.size(); i++) {
            indice.put(vertices.get(i), i);
        }

        int n = vertices.size();

        // 2. Obtener todas las aristas únicas
        List<Arista<T>> aristas = obtenerAristasUnicas(grafo);

        // 3. Ordenar las aristas por peso
        Collections.sort(aristas);

        // 4. Crear tu UnionFind
        UnionFind uf = new UnionFind(n);

        List<Arista<T>> mst = new ArrayList<>();

        // 5. Recorrer las aristas ordenadas
        for (Arista<T> arista : aristas) {
            int u = indice.get(arista. obtenerOrigen());
            int v = indice.get(arista. obtenerDestino());

            // Si no están en el mismo conjunto → no forma ciclo
            if (!uf.find(u, v)) {
                uf.union(u, v);
                mst.add(arista);

                // Si ya tenemos n-1 aristas, terminamos
                if (mst.size() == n - 1) {
                    break;
                }
            }
        }

        if (mst.size() < n - 1) {
            System.out.println("⚠ El grafo no es conexo. Se obtuvo un bosque de expansión mínima.");
        }

        return mst;
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
