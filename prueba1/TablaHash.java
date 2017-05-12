package prueba1;
import librerias.estructurasDeDatos.modelos.ListaConPI; 
import librerias.estructurasDeDatos.lineales.LEGListaConPI;

/**Implementacion simplificada de una TablaHash con Listas con PI 
 * @param <C>, tipo de las claves de la Tabla Hash, deben implementar hashCode
 * @param <V>, tipo de los valores asociados a las claves de una Tabla Hash
 * @author (EXAMEN EDA)
 * @version (Abril 2017)
 */
public class TablaHash<C, V> {
    public static final double FACTOR_CARGA = 0.75;  
    protected ListaConPI<EntradaHash<C, V>>[] elArray;
    protected int talla; 

    /** Crea una Tabla Hash vacia, con una capacidad (inicial) maxima  
    *  de tallaMaximaEstimada entradas y factor de carga 0.75
    */
    @SuppressWarnings("unchecked") 
    public TablaHash(int inicial) {
        int capacidad = siguientePrimo((int) (inicial / FACTOR_CARGA));
        elArray = new LEGListaConPI[capacidad];
        for (int i = 0; i < elArray.length; i++) { 
            elArray[i] = new LEGListaConPI<EntradaHash<C, V>>();
        }
        talla = 0;
    }    
    // Devuelve un numero primo MAYOR o IGUAL a n, i.e. el primo que sigue a n
    public static final int siguientePrimo(int n) {
        int nn = n;
        if (nn % 2 == 0) { nn++; }
        for ( ; !esPrimo(nn); nn += 2) { ; } 
        return nn;
    } 
    // Comprueba si n es un numero primo
    protected static final boolean esPrimo(int n) {
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) { return false; } // n NO es primo
        }
        return true; // n SI es primo
    } 
    // Devuelve el indice hash de la clave c 
    protected int indiceHash(C c) {
        int indiceHash = c.hashCode() % this.elArray.length;
        if (indiceHash < 0) { indiceHash += this.elArray.length; }
        return indiceHash;
    }    

    /** Inserta la entrada (c, v)  a una Tabla Hash y devuelve  
     *  el antiguo valor asociado a c, o null si no hay ninguna 
     *  entrada con clave c en la Tabla
     */
    public V insertar(C c, V v) {
        int pos = indiceHash(c);
        ListaConPI<EntradaHash<C, V>> l = elArray[pos];
        V antiguo = null;
        l.inicio();
        while (!l.esFin() && !l.recuperar().clave.equals(c)) { 
            l.siguiente(); 
        }
        if (l.esFin()) { 
            l.insertar(new EntradaHash<C, V>(c, v));
            talla++;
        }
        else { 
            antiguo = l.recuperar().valor; l.recuperar().valor = v;
        }
        return antiguo;
    }   

    public ListaConPI<C> clavesSinColisiones(){
        ListaConPI<C> aux = new LEGListaConPI<C>(); //creation of the list
        for(int i = 0; i< elArray.length; i++){ //going through every position in "elArray"
            int elementos = elArray[i].talla(); //saving the number of elements of that bucket
            if(elementos == 1){ //if there is only 1 element
             elArray[i].inicio(); //put the point of interest at the beginning
             aux.insertar(elArray[i].recuperar().clave);  //retrieve the key and insert it in the initial list
            }            
        }
        return aux; //return the initial list
    }
}