package FuncionamientoDeLaAplicacion;

public class UnionFind {
	private int[] A;

    public UnionFind(int n)
    {
        A = new int[n];
        for (int i = 0; i < n; i++)
        {
            A[i] = i; // cada elemento es su propia raiz al principio
        }
    }

    public int root(int i)
    {
        while (A[i] != i)
            i = A[i];
        return i;
    }

    public boolean find(int i, int j)
    {
        return root(i) == root(j);
    }

    public void union(int i, int j)
    {
        int ri = root(i);
        int rj = root(j);

        A[ri] = rj;
    }
}
