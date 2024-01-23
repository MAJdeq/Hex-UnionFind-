import java.util.Arrays;

public class UnionFind{
    private final int[] set;

    public UnionFind(int size){
        set = new int [size];
        Arrays.fill(set, -1);
    }

    public int getSize(){
        return set.length;
    }

    public void union(int root1, int root2){
        root1 = find(root1);
        root2 = find(root2);
        if (root1 == root2) return;
        if (set[root2] < set[root1]){
            set[root1] = root2;
        } else {if (set[root1] == set[root2]){
            set[root1]--;
        }
        set[root2] = root1;}
    }

    public int find(int x){
           if (set[x] < 0){
               return x;
           }
           set[x] = find( set[x] );
           return set[x];
    }

    public void printSet(){
        int count = 0;
            for (Integer i: set){
                System.out.println(count + " " + i);
                count += 1;
            }
    }

    public void testFind(int root1, int root2){
        if (find(root1) == find(root2)){
            System.out.println(root1 + " and " + root2 + " are in the same subset");
        } else {
            System.out.println(root1 + " and " + root2 + " aren't in the same subset");
        }
    }

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(8);
        uf.union(4,3);
        uf.union(3,5);
        uf.union(6,7);
        uf.union(5,7);
        uf.printSet();

        uf.testFind(0, 7);
        uf.testFind(3,5);
        uf.testFind(5,3);
        uf.testFind(3, 6);
        uf.testFind(0,1);
        uf.testFind(0, 2);
    }
}

