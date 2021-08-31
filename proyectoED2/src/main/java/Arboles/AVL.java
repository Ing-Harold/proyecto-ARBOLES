/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

/**
 *
 * @author HAROLD
 * @param <K>
 * @param <V>
 */
public class AVL <K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V>{
    private static final byte DIFERENCIA_PERMITIDA = 1;
    
    @Override 
    public void insertar (K claveAInsertar, V valorAInsertar){
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        this.raiz = insertar(this.raiz, claveAInsertar,valorAInsertar);
        
    }
    
    private NodoBinario<K,V> insertar(NodoBinario<K,V> nodoActual, K claveAInsertar, V valorAInsertar){
        if (NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual) < 0 ){
            NodoBinario<K,V> nuevoSupuestoHijo = insertar(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAInsertar);
            nodoActual.setHijoIzquierdo(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(claveActual) > 0 ){
            NodoBinario<K,V> nuevoSupuestoHijo = insertar(nodoActual.getHijoDerecho(),claveAInsertar,valorAInsertar);
            nodoActual.setHijoDerecho(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        //CLAVE ENCONTRAD, ACTUALIZO EL VALOR
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }
    
    private NodoBinario<K,V> balancear(NodoBinario<K,V> nodoActual){
        int alturaPorIzquierda = super.alturaRec(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = super.alturaRec(nodoActual.getHijoDerecho());
        int diferenciaDeAltura = alturaPorIzquierda-alturaPorDerecha;
        if (diferenciaDeAltura > DIFERENCIA_PERMITIDA){
            //balancear hacia izquierda rotar derecha
            NodoBinario<K,V> hijoIzquierdoDelActual = nodoActual.getHijoIzquierdo();
            alturaPorIzquierda = super.alturaRec(hijoIzquierdoDelActual.getHijoIzquierdo());
            alturaPorDerecha = super.alturaRec(hijoIzquierdoDelActual.getHijoDerecho());
            if (alturaPorDerecha > alturaPorIzquierda){
                return this.rotacionDobleADerecha(nodoActual);
            }else {
                return this.rotacionSimpleADerecha(nodoActual);
            }
        }else if(diferenciaDeAltura < - DIFERENCIA_PERMITIDA){
            //balancear hacia derecha rotar izquierda
            NodoBinario<K,V> hijoIzquierdoDelActual = nodoActual.getHijoDerecho();
            alturaPorIzquierda = super.alturaRec(hijoIzquierdoDelActual.getHijoIzquierdo());
            alturaPorDerecha = super.alturaRec(hijoIzquierdoDelActual.getHijoDerecho());
            if (alturaPorDerecha < alturaPorIzquierda){
                return this.rotacionDobleAIzquierda(nodoActual);
            }else {
                return this.rotacionSimpleAIzquierda(nodoActual);
            }
        }
        
        //si llego hasta aca no hay que hacer rotacion
        return nodoActual;
    }
  
    private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();//nodoIzqu para balanceo
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());//actualizar punteroNodo
        nodoQueRota.setHijoDerecho(nodoActual);//rotacion hecha apunta a nodo actual
        return nodoQueRota;
    }
    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoDePrimeraRotacion= rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoDePrimeraRotacion);
        return rotacionSimpleADerecha(nodoActual); 
    }
    //metodo falta completado
    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoDerecho();//nodoDere para balanceo
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());//actualizar punteroNodo
        nodoQueRota.setHijoIzquierdo(nodoActual);//rotacion hecha apunta a nodo actual
        return nodoQueRota;
    }
   //metodo completado
    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> nodoActual){
        //rotacion simple a derecha luego rotacion a izquierda
        NodoBinario<K,V> nodoDePrimeraRotacion= rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoDePrimeraRotacion);
        return rotacionSimpleAIzquierda(nodoActual); 
    }
    
    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null){
            throw new IllegalArgumentException("Clave a eliminar no puede ser nula");
        }
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null){
            throw new IllegalArgumentException("La clave no existe en el arbol");
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }

    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual, K claveAEliminar){
        K claveActual  = nodoActual.getClave();
        if (NodoBinario.esNodoVacio(nodoActual)){
            return null;
        }
        if (claveAEliminar.compareTo(claveActual) > 0){
            NodoBinario<K,V> posibleNuevoHDerecho = eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
            nodoActual.setHijoDerecho(posibleNuevoHDerecho);
            return this.balancear(nodoActual);//balancear
        }
        if (claveAEliminar.compareTo(claveActual) < 0){
            NodoBinario<K,V> posibleNuevoHIzquierdoo = eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleNuevoHIzquierdoo);
            return this.balancear(nodoActual);//balancear
        }
        //SI LLEGO ACA ENCONTRO EL NODO CON LA CLAVE A ALEIMINAR
        //CASO 1
        if (nodoActual.esHoja()){
            return (NodoBinario<K,V>) NodoBinario.nodoVacio();
        }
        //CASO 2
        //2.1 si solo tiene hijo izquierdo
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoIzquierdo();
        }
        //2.2 si solo tiene hijo derecho
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoDerecho();
        }
        //CASO 3
        NodoBinario<K,V> nodoReemplazo = this.buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> posibleNuevoHDerecho = eliminar(nodoActual.getHijoDerecho(),nodoReemplazo.getClave());
        nodoActual.setHijoDerecho(posibleNuevoHDerecho);//puede ser que cambie dependiendo del caso 1 o 2
        //lo mas facil reemplazar la clave y el valor del nodo
        nodoActual.setClave(nodoReemplazo.getClave());
        nodoActual.setValor(nodoReemplazo.getValor());
        return nodoActual;
    }
}
