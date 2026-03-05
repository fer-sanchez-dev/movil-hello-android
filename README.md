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
(docs/captura_emulador.png)
