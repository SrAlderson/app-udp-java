# UDP Cliente-Servidor en Java

## 📌 Descripción
Implementación de un sistema Cliente-Servidor utilizando el protocolo **UDP** en Java con interfaz gráfica (Swing).

El proyecto contempla dos funcionalidades independientes desarrolladas como práctica académica para comprender el funcionamiento de la comunicación no orientada a conexión mediante sockets:

- **Funcionalidad 1:** Broadcast a múltiples clientes.
- **Funcionalidad 2:** Envío de mensaje dirigido a un cliente específico mediante identificación por puerto.

---
## 🏗 Arquitectura
- **Lenguaje:** Java  
- **Protocolo:** UDP  
- **Comunicación:** `DatagramSocket` / `DatagramPacket`  
- **Interfaz gráfica:** Swing  
- **Puerto del servidor:** `12345`

El servidor mantiene un registro dinámico de clientes utilizando una estructura **Map** para almacenar dirección IP y puerto.

---
## ▶️ Cómo Ejecutar el Proyecto
1. Ejecutar la clase PrincipalSrv (Servidor).
2. Ejecutar múltiples instancias de PrincipalCli (Clientes).
3. En el cliente:
   - Ingresar el puerto destino.
   - Escribir el mensaje.
   - Presionar Enviar.

---
## ⚠ Limitaciones
- UDP no garantiza entrega ni orden de los mensajes.
- No existe manejo de desconexiones automáticas.
- Identificación basada solo en puerto.
- No existe control de congestión.
- Vulnerable a pérdida de paquetes y spoofing.

---
## 🎯 Posibles mejoras
- Mejorar la interfaz gráfica para una experiencia de usuario más intuitiva y estructurada.
- Realizar el envio de archivos binarios.
- Implementar manejo de timeout y detección de clientes inactivos.
- Implementar autenticación básica de clientes.
- Implementar comunicación Peer-to-Peer (P2P) entre clientes sin retransmisión por el servidor.
- Diseñar un protocolo estructurado para un chat multicliente con identificación de usuario y gestión de usuarios activos.

---
## 👨‍💻 Autor
- Desarrollado por Giovanni Gonzalez
- Proyecto académico
