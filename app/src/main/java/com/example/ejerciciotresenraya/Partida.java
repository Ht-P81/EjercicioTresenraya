package com.example.ejerciciotresenraya;

import java.util.Random;

public class Partida {
        //constructor
        //no confundir el nombre de la variable con el nombre del parámetro del constructor (this)
        //el this hace referencia al campo clase y la otra dificultad al argumento del constructor.
        public Partida (int dificultad){

            this.dificultad=dificultad;
            jugador = 1;

            //array para saber si la casilla está o no ocupada
            casilla_marcada = new int[9];
            //bucle for para recorrer el array y lo rellenamos a 0
            for(int i= 0; i<9; i++){
                casilla_marcada[i]=0;
            }

        }

        //Hacemos método que compruebe si la casilla esté o no esté ocupada
        public boolean comprueba_casilla(int casilla){
            //actualmente todos los valores del tablero están a 0
            // Si casilla_marcada de casilla es diferente de 0
            if(casilla_marcada[casilla]!=0){
                return false; //
            }else {
                casilla_marcada[casilla]=jugador;//como jugador es igual 1 significaría que está ocupada
            }
            return true;
        }


        //creamos el método turno para cambiar entre jugadores
        public int turno(){

            //dentro de este método declara una variable booleana que sea igual a true.
            boolean empate = true;

            //con la de ultimo movimiento puedo evaluar si ha hecho tres en raya o no.
            boolean ult_movimiento = true;

            //array muldimensional explicado en Java (busca clase nombre: PruebaArray3RayaAndroid)
            //el que recorre la 1 dimesión, 8
            for(int i=0; i<COMBINACIONES.length; i++) {
                //el que recorre la 2 dimensión, 3
                for(int pos: COMBINACIONES[i]) {
                    //aquí vemos que todas las posiciones están rellenas con ceros, significando
                    //que el tablero está vacío.
                    System.out.println("Valor en posición: " + pos + " " + casilla_marcada[pos]) ;

                    //con este if está evaluando si seguir o no seguir jugando,
                    //si ult_movimiento sera falso (no continuará)
                    if (casilla_marcada[pos] != jugador) {
                        ult_movimiento = false;
                    }

                    //aquí dentro vamos establecer la condicion booleana de empate a false
                    if(casilla_marcada [pos] == 0){
                        empate = false;
                    }
                }//se cierra el for anidado
                System.out.println("-------------------------------------------------------------");
                if (ult_movimiento)return jugador;
                ult_movimiento = true;
                if (ult_movimiento) {
                    System.out.println("Tus muertos a caballo" + jugador);
                }
            }//llave de cierre del for principal

            //cuando invoquemos este método conseguiremos que el jugador no sea igual a 1
            //al incrementarlo cambia de jugador, pero en este caso sobrepasaría rápido el 2
            //lo que nos interesa es que cuando se incremente al 2, vuelva al 1
            jugador++;
            //para que vuelva al 1 lo hacemos con un if
            if(jugador>2){
                //si juegador es mayor a 2 vuelva al 1, así siempre estará entre 1 y 2.
                jugador = 1;
            }

            return 0;
        }

        //Creamos un método que nos diga cuando estemos a punto de hacer 3 en raya. 2 de 3.
    //de momento creado pero no lo llama nadie.
    public int dosEnRaya(int jugador_en_turno){

            //vamos a almacenar en esta casilla la casilla clave (la que quede por hacer 3 en raya).
            //la iniciamos en una casilla que no existe para no confundir, por eso -1
            int casilla=-1;

            //otra variable para saber cuantas lleva
        //si esta variable en algún momento del método es igual a 2 quiere decir que el jugddor
        //que estamos evaluando ahora mismo tiene 2 en raya.
            int cuantas_lleva = 0;

        //array muldimensional explicado en Java (busca clase nombre: PruebaArray3RayaAndroid)
        //el que recorre la 1 dimesión, 8
        for(int i=0; i<COMBINACIONES.length; i++) {
            //el que recorre la 2 dimensión, 3
            for(int pos: COMBINACIONES[i]) {
                //evaluamos a ver si la casilla que estamos evaluando a cada vuelta de bucle está o no
                //está marcada por el jugador que le toca tirar y le digo que aumente la variable cuantas_lleva.
                if(casilla_marcada[pos]==jugador_en_turno){
                    cuantas_lleva++;
                }

                if(casilla_marcada[pos]==0){
                    casilla = pos;
                }
            }//es la llave del for anidado

            //aquí decimos que si lleva 2 y casilla es diferente del que se inició pues devuélveme la casilla clave
            if(cuantas_lleva==2 && casilla !=-1){
                return casilla;
            }

            //reseteamos los valores
            casilla = -1;
            cuantas_lleva = 0;

            }

        return -1;
    }



        //creamos el método que genere un número aleatorio, lo llamamos ia (inteligencia artificial).
        public int ia(){
            int casilla;

            //el método dosEnRaya recibe por parámetro el jugador a quien le toca tirar.
            //este método o devuelve la casilla clave o devuelve -1
            // así le decimos que mire si el jugador 2 (la máquina) si tiene posibilidad de hacer 2 en raya.
            casilla = dosEnRaya(2);
            //allí donde yo esté llamando al método dosEnRaya voy a conseguir que me devuelva la casilla clave


            //si la casilla es diferente de -1 devuélveme la casilla que sea
            //por tanto si la casilla que devuelve es -1 aquí no entra
            if(casilla != -1) return casilla;

            //con le decimos a la máquina si el humano tiene posibilidad de hacer 3 en raya o no la tiene.
            if (dificultad>0){
                casilla = dosEnRaya(1);
                if(casilla != -1) return casilla;
            }

            //ahora vamos a evaluar el nivel de dificultad igual a 2.
            if (dificultad == 2){

                //en el tablero si no hay posibilidad de hacer 3 en raya, entonces la máquina marque una esquina
                //en este caso que marque la casilla de la esquina superior izquierda, la primera. Posicion 0.
                if(casilla_marcada[0] == 0) return 0;

                //Ahora vamos a hacer lo mismo para el resto de las esquinas, para la 2, para la 6 y para la 8.
                //en este caso que marque la casilla de la esquina superior derecha, la tercera. Posición 2.
                if(casilla_marcada[2] == 0) return 0;

                //en este caso que marque la casilla de la esquina inferior izquierda, la septima.Posicion 6.
                if(casilla_marcada[6] == 0) return 0;

                //en este caso que marque la casilla de la esquina inferior derecha, la novena. Posición 8.
                if(casilla_marcada[8] == 0) return 0;

                if(casilla_marcada[4] == -1) return casilla;
            }

            //primero instanciamos la clase Random, y en esa variable podemos utilizar el método nextInt();
            Random casilla_azar = new Random();

            //luego
            casilla = casilla_azar.nextInt(9);

            return casilla; //nos pide un return pq no es void, es un getter
        }


        //variables
        public final int dificultad;

        public int jugador;

        public int [] casilla_marcada;

        //array muldimensional explicado en Eclipse (busca clase nombre: PruebaArray3RayaAndroid)
        private final int[][] COMBINACIONES = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    }

