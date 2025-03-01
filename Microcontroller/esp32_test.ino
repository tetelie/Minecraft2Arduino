#include <WiFi.h>
#include <ArduinoWebsockets.h>
#include <string.h>

#define LED_PIN 2

// Informations Wi-Fi
const char* ssid = "Livebox-E4D0";  // Remplace par ton SSID
const char* password = "Deletang49"; // Remplace par ton mot de passe Wi-Fi

// Adresse et port du serveur WebSocket
const char* websocket_server = "141.95.159.229";  // Remplace par l'IP de ton serveur WebSocket
const uint16_t websocket_port = 8080;             // Remplace par le port de ton serveur WebSocket

// Créer un objet WebSocketClient avec l'espace de noms 'websockets'
websockets::WebsocketsClient client;  // Client WebSocket

int led = 0;

// Fonction de gestion des événements WebSocket
void onMessageCallback(websockets::WebsocketsMessage message) {
  Serial.print("Message reçu : ");
  Serial.println(message.data());
  const char* led_str = "led";
  if(strcmp(message.data().c_str(), led_str) == 0)
  {
    led = led ? 0 : 1;
    digitalWrite(LED_PIN, led);
  }

}

void onEventsCallback(websockets::WebsocketsEvent event, String data) {
  switch (event) {
    case websockets::WebsocketsEvent::ConnectionOpened:
      Serial.println("Connecté au serveur WebSocket.");
      break;
    case websockets::WebsocketsEvent::ConnectionClosed:
      Serial.println("Déconnecté du serveur WebSocket.");
      break;
    case websockets::WebsocketsEvent::GotPong:
      Serial.println("Pong reçu.");
      break;
    case websockets::WebsocketsEvent::GotPing:
      Serial.println("Ping reçu.");
      break;
  }
}

void initWiFi() {
  Serial.print("Connexion au Wi-Fi...");
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }

  Serial.println();
  Serial.print("Connecté au Wi-Fi, adresse IP : ");
  Serial.println(WiFi.localIP());
}

void setup() {

  pinMode(LED_PIN, OUTPUT);

  // Initialisation de la communication série
  Serial.begin(115200);

  // Connexion Wi-Fi
  initWiFi();

  // Connexion au serveur WebSocket
  client.onMessage(onMessageCallback);
  client.onEvent(onEventsCallback);
  client.connect(websocket_server, websocket_port, "/ws");  // Connexion au serveur WebSocket

  Serial.println("Client WebSocket démarré");
}

void loop() {
  client.poll();  // Assurer que la gestion des événements WebSocket fonctionne
}
