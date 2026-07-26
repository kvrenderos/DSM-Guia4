# Mi Perfil - Desafio practico 1

Aplicacion Android desarrollada para el desafio de DSM441. Incluye tres pantallas:

- Bienvenida con boton para iniciar.
- Registro de perfil con formulario, validaciones y solicitud de permiso de camara en tiempo de ejecucion.
- Resumen del perfil guardado con opciones para regresar al inicio o crear un nuevo perfil.

## Validaciones

- Campos obligatorios.
- Correo electronico con formato valido.
- Telefono numerico de 8 a 15 digitos.
- Fecha de nacimiento en formato `dd/mm/aaaa`.

## Compilacion

Abrir el proyecto en Android Studio y ejecutar la aplicacion en un emulador o dispositivo Android. El APK de depuracion se genera con:

```bash
./gradlew assembleDebug
```
