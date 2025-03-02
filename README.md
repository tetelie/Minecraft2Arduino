<p align="center">
  <img src="doc/m2a_logo_txt.png" width="300" height="300" />
</p>

# Description

Minecraft2Arduino is a project that enables interactions between Minecraft and Arduino, allowing actions in the real world based on in-game events. Think of it as a direct portal between Minecraft and reality!


# Summary

- Global Architecture
- Arduino Side
- Gateway Side
- Minecraft Side
- Documentation
- Setup Example 🚀
  
# Global Architecture
The system consists of three main components: <br>
  1. Arduino: Connects to the gateway via WebSockets and executes received commands. <br>
  2. Gateway: Acts as a WebSocket server relaying messages between Arduino and Minecraft. <br>
  3. Minecraft Plugin: Captures in-game interactions and forwards them to the gateway. <br>

<p align="center">
  <img src="doc/architecture.png" />
</p>

# Arduino Side
You can found the associated folder here : [Microcontroller](./scripts/script.sh)
<br>
Arduino connect to the gateway with websockets and handle message from it

Library used : 
- WiFi.h
- ArduinoWebsockets.h
- string.h

# Gateway Side
You can found the associated folder here : [WebSocketGateway](./scripts/script.sh)
<br>
The Gateway is a web socket server relaying message between Arduino and Minecraft Server

Technology used :
- Node.js

# Minecraft Side
You can found the associated folder here : [Plugin](./scripts/script.sh)
<br>
A plugin which is a client socket relai messages from the player interaction to the gateway server

Library used:
- Spigot-Api
- Socket


