Taller 1 - Hello Android

## Información del Estudiante
- Nombre: María Fernanda Sánchez Bautista
- Código: 1095841257
- Fecha: 2023/03/05

## Respuestas

### 1. Función del AndroidManifest.xml
Es el archivo de configuración central de toda app Android. Le dice al sistema operativo todo lo que necesita saber antes de ejecutar la app:
Declara las Activities (pantallas) que existen en la app
Define los permisos que necesita (cámara, internet, ubicación, etc.)
Especifica el ícono y nombre de la app
Indica cuál Activity se abre primero (el launcher)
Declara servicios, receptores de eventos y otros componentes
Sin este archivo, Android simplemente no puede ejecutar la app.

### 2. Diferencia entre activity_main.xml y MainActivity.kt
La diferencia principal entre activity_main.xml y MainActivity.kt es que cada uno cumple un rol distinto dentro de la misma pantalla.
El archivo activity_main.xml es un archivo de diseño escrito en XML que define cómo se ve la interfaz: los botones, textos, imágenes y su posición en pantalla. 
Por su parte, MainActivity.kt está escrito en Kotlin y define cómo se comporta esa interfaz, es decir, contiene la lógica, las funciones y los eventos que 
ocurren cuando el usuario interactúa con los elementos visuales. Una buena forma de recordarlo es pensar que el .xml es el "cuerpo" visual de la pantalla y 
el .kt es su "cerebro". Ambos archivos trabajan juntos: sin el diseño no hay nada que mostrar, y sin el código no hay nada que hacer.

### 3. Gestión de recursos en Android
Android usa varias estrategias:
Ciclo de vida de Activities — pausa, detiene y destruye pantallas que no están en uso para liberar memoria
Gestión de memoria automática — el recolector de basura (Garbage Collector) libera objetos que ya no se usan
Proceso de baja prioridad — si el sistema necesita memoria, mata apps en segundo plano automáticamente
Recursos adaptables — usa imágenes y layouts diferentes según la densidad de pantalla y tamaño del dispositivo (drawable-hdpi, layout-land, etc.)
WorkManager y Jobs — programa tareas pesadas para cuando el dispositivo tenga recursos disponibles

### 4. Aplicaciones famosas que usan Kotlin
Duolingo: Migró su app Android completamente a Kotlin
Pinterest: Usa Kotlin para toda su capa de Android
Uber: Adoptó Kotlin para mejorar la seguridad y reducir bugs en su app de conductores

## Capturas de Pantalla
app/docs/captura_emulador.png

## Taller 2 - Arquitectura MVVM

### Respuestas a Preguntas Conceptuales

#### 1. ¿Qué problema resuelve el ViewModel en Android?
El ViewModel resuelve el problema de perder los datos cuando la pantalla rota o cuando el sistema reinicia la Activity. Antes de ViewModel, si girabas el celular todos los datos que tenías cargados se perdían y tocaba volver a cargarlos. El ViewModel guarda esos datos de forma separada a la pantalla, entonces aunque el Fragment se destruya y recree, los datos siguen ahí.

#### 2. ¿Por qué LiveData es "lifecycle-aware" y qué beneficio trae?
LiveData es "lifecycle-aware" porque sabe en qué estado está el Fragment, si está activo, pausado o destruido. El beneficio es que solo actualiza la pantalla cuando el Fragment está activo, evitando errores como intentar actualizar una pantalla que ya no existe, lo cual antes causaba crashes en la app.

#### 3. Explica con tus propias palabras el flujo de datos en MVVM
La pantalla (Fragment) le pide datos al ViewModel. El ViewModel los busca a través del Repository, que es quien sabe de dónde sacarlos, ya sea de internet o de una base de datos local. Cuando los datos llegan, el ViewModel los guarda en LiveData y la pantalla que está observando ese LiveData se actualiza automáticamente.

#### 4. ¿Qué ventaja tiene usar Fragments vs múltiples Activities?
Con Fragments podemos tener varias "pantallas" dentro de una sola Activity, lo que hace la app más liviana y la navegación más fluida. Además los Fragments pueden compartir el mismo ViewModel, lo que facilita pasar datos entre pantallas sin complicaciones.

#### 5. ¿Cómo ayuda el Repository Pattern a la arquitectura?
El Repository actúa como intermediario entre el ViewModel y las fuentes de datos. Su ventaja es que el ViewModel no necesita saber de dónde vienen los datos, si son de una API, de una base de datos local o de cualquier otro lado. Eso hace que el código sea más organizado y fácil de modificar sin dañar otras partes.

### Diagrama de Arquitectura

<img width="738" height="673" alt="image" src="https://github.com/user-attachments/assets/236c61ed-678b-490c-9dc4-a3e50c8a8082" />

### Capturas de Pantalla

![Lista de usuarios](docs/screenshot_list.png)
![Detalle de usuario](docs/screenshot_detail.png)
