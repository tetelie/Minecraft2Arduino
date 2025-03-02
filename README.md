<p align="center">
  <img src="doc/m2a_logo_txt.png" width="300" height="300" />
</p>
<h1>Description</h1>

Minecraft2Arduino is a project that aims to perform actions with Arduino through interaction in minecraft. Some will say that it is the direct portal between minecraft and the real world.


# Summary
- Functioning
  - Global Architecture
  - Ardiuno Side
  - Gateway Side
  - Minecraft Side
- Documentation
- Setup example

# Fonctioning

## Global Architecture
<p align="center">
  <img src="doc/architecture.png" />
</p>

## Arduino Side
You can found the associated folder here : [Microcontroller](./scripts/script.sh)
<br>
Arduino connect to the gateway with websockets and handle message from it

Library used : 
- WiFi.h
- ArduinoWebsockets.h
- string.h

## Gateway Side
You can found the associated folder here : [WebSocketGateway](./scripts/script.sh)
<br>
The Gateway is a web socket server relaying message between Arduino and Minecraft Server

Technology used :
- Node.js

## Minecraft Side
You can found the associated folder here : [Plugin](./scripts/script.sh)
<br>
A plugin which is a client socket relai messages from the player interaction to the gateway server

Library used:
- Spigot-Api
- Socket


