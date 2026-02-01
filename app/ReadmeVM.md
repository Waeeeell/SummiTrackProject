En aquesta pràctica he reestructurat el flux de Registre i Login perquè les pantalles 
deixin de prendre decisions i es limitin a pintar la interfície.

1. RegistroViewModel i IniciarSesionViewModel
He creat aquestes classes per centralitzar tota la lògica de validació fora de l'Activity.

Gestió de Dades: He definit variables MutableLiveData (privades) per gestionar l'estat intern i LiveData (públiques) 
perquè la UI les pugui llegir sense modificar-les.

En lloc de fer comprovacions al botó, el ViewModel rep les dades i decideix si compleixen els requisits, 
com ara camps buits o longitud mínima de la contrasenya.

2. menu_registrarse i menu_iniciar_sesion
Les Activities ara són molt més lleugeres i "netes",
he vinculat cada pantalla amb el seu cervell mitjançant la propietat by viewModels() i 
he implementat la funció setupObservers(), on l'Activity se subscriu als canvis del ViewModel. 
En utilitzar this com a LifeCycleOwner, el sistema sap que si la pantalla es tanca, ha de deixar d'observar automàticament
per no malbaratar recursos.

3. Resum
Ara, quan l'usuari prem "Acceptar", l'Activity només passa el text al ViewModel. 
El ViewModel processa la informació i actualitza els seus estats. Al moment, 
l'Activity detecta el canvi gràcies als observadors i actualitza el 
missatge d'error o canvia de pantalla de forma reactiva.