package FuncionamientoDeLaAplicacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo<T> {
	// Lista de adyacencia: cada vertice apunta a su conjunto de vecinos
    private Map<T, List<Arista<T>>> adyacencia;

    public Grafo()
    {
        adyacencia = new HashMap<>();
    }

    public void agregarVertice(T vertice)
    {
        verificarNoNulo(vertice);
        adyacencia.putIfAbsent(vertice, new ArrayList<>());
    }

    public void eliminarVertice(T vertice)
    {
        verificarVertice(vertice);

        // Eliminamos el vertice de las listas de vecinos de los demas
        for (Arista<T> otro : adyacencia.get(vertice))
        {
        	if (otro.obtenerDestino().equals(vertice)) {
        		adyacencia.get(otro).remove(vertice);
        	}
        }

        // Eliminamos el vertice en si
        adyacencia.remove(vertice);
    }

    public boolean existeVertice(T vertice)
    {
        return adyacencia.containsKey(vertice);
    }

    public void agregarArista(T origen, T destino, double peso)
    {
        verificarVertice(origen);
        verificarVertice(destino);
        verificarDistintos(origen, destino);

        agregarVertice(origen);
        agregarVertice(destino);
        
        if (existeArista(origen, destino)) {
			return;
		}
        
        adyacencia.get(origen).add(new Arista<>(origen, destino, peso));
        
    }

    public List<Arista<T>> obtenerAdyacentes(T vertice) {
        return adyacencia.getOrDefault(vertice, Collections.emptyList());
    }
    
    public void eliminarArista(T origen, T destino)
    {
        verificarVertice(origen);
        verificarVertice(destino);
        verificarDistintos(origen, destino);

        adyacencia.get(origen).remove(destino);
        adyacencia.get(destino).remove(origen);
    }

    public boolean existeArista(T origen, T destino)
    {
        verificarVertice(origen);
        verificarVertice(destino);
        verificarDistintos(origen, destino);

        return adyacencia.get(origen).contains(destino);
    }

    public int tamano()
    {
        return adyacencia.size();
    }

    public Set<T> vertices()
    {
        return Collections.unmodifiableSet(adyacencia.keySet());
    }

    public Set<T> vecinos(T vertice)
    {
        verificarVertice(vertice);
        Set<T> resultado = new HashSet<>();
        for (Arista<T> t : adyacencia.get(vertice)) {
			resultado.add(t.obtenerDestino());
		}
        return Collections.unmodifiableSet(resultado);
    }

    // --- Validaciones ---

    private void verificarNoNulo(T v)
    {
        if (v == null)
            throw new IllegalArgumentException("El vertice no puede ser null");
    }

    private void verificarVertice(T vertice)
    {
        verificarNoNulo(vertice);
        if (!existeVertice(vertice))
            throw new IllegalArgumentException("El vertice no pertenece al grafo: " + vertice);
    }

    private void verificarDistintos(T v1, T v2)
    {
        if (v1.equals(v2))
            throw new IllegalArgumentException("No se permiten loops: (" + v1 + ", " + v2 + ")");
    }
}
