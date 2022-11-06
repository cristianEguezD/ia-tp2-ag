# Inteligencia Artificial TP N° 2 - Sistemas inteligentes

Para podes ejecutar el código debemos tener instalado java y sbt. 

Instalacion java:

`sudo apt install openjdk-8-jre-headless`

Instalacion sbt: 

```
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt_old.list
sudo curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add
sudo apt-get update
sudo apt-get install sbt
```

Una vez descargado el repoe instalado java y sbt podemos entrar a la carpeta del proyecto y hacer

`sbt run` 

Como tenemos 2 ejecutables debemos seleccionar `Main` quien es el que contiene la lógica del algoritmo genético implementado. LogProcessor fue implementado para tomar los logs y pasarlos a csv para luego generar los gráficos que piden el trabajo práctico.

![image](https://user-images.githubusercontent.com/47781771/200196512-a5508980-f2bb-4557-bb9b-fb835ae005ef.png)
