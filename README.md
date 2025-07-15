Juan Sebastian Velandia Pedraza
14/07/2025

# PizarraRealTime

Proyecto de pizarra colaborativa en tiempo real usando Spring Boot (backend) y React (frontend).

## Descripción
Este proyecto permite que varios usuarios dibujen simultáneamente en una pizarra virtual. Los cambios se sincronizan en tiempo real entre todos los clientes conectados usando WebSockets.

## Tecnologías
- **Backend:** Java 17, Spring Boot, Spring WebSocket
- **Frontend:** React (no incluido en este repositorio)

## Estructura básica del backend
- `WebSocketConfig`: Configura el broker de mensajes y el endpoint WebSocket (`/ws`).
- `PizarraController`: Controlador que recibe los datos de dibujo y los retransmite a todos los clientes suscritos.

## Endpoints WebSocket
- **Conexión:** `/ws` (con SockJS)
- **Enviar datos:** `/app/draw` (STOMP)
- **Recibir datos:** `/topic/pizarra` (STOMP)

## Ejemplo de flujo
1. El cliente React se conecta al endpoint `/ws` usando STOMP/SockJS.
2. Cuando un usuario dibuja, el frontend envía los datos a `/app/draw`.
3. El backend recibe el mensaje y lo retransmite a `/topic/pizarra`.
4. Todos los clientes suscritos a `/topic/pizarra` reciben el nuevo estado de la pizarra.

## Ejemplo de integración en React
Instala las dependencias:
```bash
npm install @stomp/stompjs sockjs-client
```

Código básico de conexión:
```js
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const stompClient = new Client({
  webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
});

stompClient.onConnect = () => {
  stompClient.subscribe('/topic/pizarra', (message) => {
    const drawData = JSON.parse(message.body);
    // Actualiza la pizarra con drawData
  });
};

stompClient.activate();

// Para enviar datos:
function sendDraw(drawData) {
  stompClient.publish({
    destination: '/app/draw',
    body: JSON.stringify(drawData),
  });
}
```

## Cómo ejecutar el backend
1. Clona el repositorio.
2. Ejecuta el backend:
   ```bash
   ./mvnw spring-boot:run
   ```
3. El backend estará disponible en `http://localhost:8080`.

## Notas
- Para producción, se recomienda asegurar el WebSocket (HTTPS y autenticación).
- El frontend debe estar configurado para conectarse al endpoint correcto (`/ws`).

